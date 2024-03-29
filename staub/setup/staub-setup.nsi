; Script generated by the HM NIS Edit Script Wizard.

; HM NIS Edit Wizard helper defines
!define PRODUCT_NAME "Staub"
!define PRODUCT_VERSION "1.0"
!define PRODUCT_PUBLISHER "SPBSPU"
!define PRODUCT_WEB_SITE "http://staub.googlecode.com"
!define PRODUCT_UNINST_KEY "Software\Microsoft\Windows\CurrentVersion\Uninstall\${PRODUCT_NAME}"
!define PRODUCT_UNINST_ROOT_KEY "HKLM"
!define PRODUCT_RUN_URL "http://localhost:8080/staub"

; MUI 1.67 compatible ------
!include "MUI.nsh"

!include nsDialogs.nsh
;!insertmacro NSD_FUNCTION_INIFILE
!include LogicLib.nsh
!include staub-setup.nsh

; MUI Settings
!define MUI_ABORTWARNING
!define MUI_ICON "${NSISDIR}\Contrib\Graphics\Icons\modern-install.ico"
!define MUI_UNICON "${NSISDIR}\Contrib\Graphics\Icons\modern-uninstall.ico"

; Language Selection Dialog Settings
!define MUI_LANGDLL_REGISTRY_ROOT "${PRODUCT_UNINST_ROOT_KEY}"
!define MUI_LANGDLL_REGISTRY_KEY "${PRODUCT_UNINST_KEY}"
!define MUI_LANGDLL_REGISTRY_VALUENAME "NSIS:Language"

; Welcome page
!insertmacro MUI_PAGE_WELCOME
; License page
;!insertmacro MUI_PAGE_LICENSE "c:\path\to\licence\YourSoftwareLicence.txt"
; Directory page
!insertmacro MUI_PAGE_DIRECTORY
; Username page
;Page custom usernamePage UpdateINIState ": username page"
; Instfiles page
!insertmacro MUI_PAGE_INSTFILES
; Finish page
!insertmacro MUI_PAGE_FINISH

; Language files
;!insertmacro MUI_LANGUAGE "English"
!insertmacro MUI_LANGUAGE "Russian"

; MUI end ------

Name "${PRODUCT_NAME} ${PRODUCT_VERSION}"
OutFile "staub-setup.exe"
;InstallDir "D:\Temp\Staub"
InstallDir $PROGRAMFILES\Staub
ShowInstDetails show
ShowUnInstDetails show

Function .onInit
  !insertmacro MUI_LANGDLL_DISPLAY
  InitPluginsDir
FunctionEnd

;Function usernamePage
  ;File /oname=$PLUGINSDIR\staub-setup.ini "staub-setup.ini"
  ;StrCpy $0 $PLUGINSDIR\staub-setup.ini
  ;Call CreateDialogFromINI
;FunctionEnd

;Var username
;Var password

;Section "CreateUser" SEC01
  ;ReadINIStr $username "$PLUGINSDIR\staub-setup.ini" "Field 1" "State"
  ;DetailPrint "username=$username"
  ;ReadINIStr $password "$PLUGINSDIR\staub-setup.ini" "Field 4" "State"
  ;DetailPrint "pass=$password"
  ;UserMgr::CreateAccount "$username" "$password" "Staub service account"
  ;Pop $0
  ;StrCmp $0 OK done
    ;MessageBox MB_OK "CreateUser failed Result : $0"
    ;Abort
  ;done:
;SectionEnd

Section "installDB" SEC02
  SetOverwrite try
  SetOutPath "$PLUGINSDIR"
  File "vcredist_x86.exe"
  ExecWait '"$PLUGINSDIR\vcredist_x86.exe"'
  SetOutPath "$INSTDIR"
  File /r /x .svn postgresql
  CreateDirectory $INSTDIR\data\db
  CreateDirectory $INSTDIR\data\images
  CreateDirectory $INSTDIR\installLogs
  SetOutPath "$PLUGINSDIR\db\conf"
  File "db\conf\postgrespw"
  SetOutPath "$PLUGINSDIR\db\script"
  File "db\script\create_database.sql"
  File "db\script\create_schema.sql"
  File "db\script\create_schema_objects.sql"
  File "db\script\initial_data.sql"
  
  ExecWait '"$INSTDIR\postgresql\bin\initdb.exe" -D "$INSTDIR\data\db" -U postgres --locale=english --encoding=UTF8  --auth=md5 --pwfile="$PLUGINSDIR\db\conf\postgrespw"'
  ;!insertmacro WriteToFile "$INSTDIR\data\db\pg_hba.conf" "host    all         all         127.0.0.1 255.255.255.255        md5"
  SimpleSC::InstallService "StaubDBService" "Staub :: Database Service" "16" "2" '"$INSTDIR\postgresql\bin\pg_ctl.exe" runservice -w -N "StaubDBService" -D "$INSTDIR\data\db\"' "" "" ""
  SimpleSC::StartService "StaubDBService"
  System::Call 'Kernel32::SetEnvironmentVariableA(t, t) i("PGPASSWORD", "postgres").r0'
  ExecWait '"$INSTDIR\postgresql\bin\psql.exe" -d postgres -U postgres -f "$PLUGINSDIR\db\script\create_database.sql" -L "$INSTDIR\installLogs\create_database.log"'
  ExecWait '"$INSTDIR\postgresql\bin\psql.exe" -d staubdb -U postgres -f "$PLUGINSDIR\db\script\create_schema.sql" -L "$INSTDIR\installLogs\create_schema.log"'
  System::Call 'Kernel32::SetEnvironmentVariableA(t, t) i("PGPASSWORD", "staub").r0'
  ExecWait '"$INSTDIR\postgresql\bin\psql.exe" -d staubdb -U staub -f "$PLUGINSDIR\db\script\create_schema_objects.sql" -L "$INSTDIR\installLogs\create_schema_objects.log"'
  ExecWait '"$INSTDIR\postgresql\bin\psql.exe" -d staubdb -U staub -f "$PLUGINSDIR\db\script\initial_data.sql" -L "$INSTDIR\installLogs\initial_data.log"'

  ;AccessControl::SetFileOwner "$INSTDIR\postgresql" "staub"
  ;AccessControl::GrantOnFile "$INSTDIR\data\db" "staub" "GenericRead + GenericWrite"
SectionEnd

Section "installJDK" SEC03
  SetOutPath "$INSTDIR"
  File /r /x .svn jdk
SectionEnd

Section "installAS" SEC04
  SetOutPath "$INSTDIR"
  File /r /x .svn jboss
  CreateDirectory $INSTDIR\jboss\server\default\log
;  SetOverwrite on
;  File /oname=$INSTDIR\jboss\server\default\lib\postgresql-8.3-603.jdbc3.jar "as\lib\postgresql-8.3-603.jdbc3.jar"
;  File /oname=$INSTDIR\jboss\server\default\conf\jboss-log4j.xml "as\conf\jboss-log4j.xml"
;  SetOverwrite try
  SetOutPath "$INSTDIR\jboss\bin"
;  File "jboss-native\service.bat"
;  File "jboss-native\jbosssvc.exe"
  ExecWait '"$INSTDIR\jboss\bin\service.bat" install'
SectionEnd

Section "installApp" SEC05
  File /oname=$INSTDIR\jboss\server\default\deploy\staub-ear-1.0-SNAPSHOT.ear "app\staub-ear-1.0-SNAPSHOT.ear"
  SetOutPath "$INSTDIR"
  File "startStaub.bat"
  File "stopStaub.bat"
  File "reference.pdf"
SectionEnd

Section "startService" SEC06
  SimpleSC::StartService "StaubASService"
SectionEnd

Section -AdditionalIcons
  CreateDirectory "$SMPROGRAMS\${PRODUCT_NAME}"
  WriteIniStr "$INSTDIR\${PRODUCT_NAME}.url" "InternetShortcut" "URL" "${PRODUCT_RUN_URL}"
  CreateShortCut "$SMPROGRAMS\${PRODUCT_NAME}\������� ������������ ������.lnk" "$INSTDIR\${PRODUCT_NAME}.url"
  CreateShortCut "$SMPROGRAMS\${PRODUCT_NAME}\��������� ������.lnk" "$INSTDIR\startStaub.bat"
  CreateShortCut "$SMPROGRAMS\${PRODUCT_NAME}\���������� ������.lnk" "$INSTDIR\stopStaub.bat"
  CreateShortCut "$SMPROGRAMS\${PRODUCT_NAME}\����������� ������������.lnk" "$INSTDIR\reference.pdf"
  CreateShortCut "$SMPROGRAMS\${PRODUCT_NAME}\�������.lnk" "$INSTDIR\uninst.exe"
SectionEnd

Section -Post
  WriteUninstaller "$INSTDIR\uninst.exe"
SectionEnd

Section Uninstall
  SimpleSC::StopService "StaubASService"
  SimpleSC::StopService "StaubDBService"
  SimpleSC::RemoveService "StaubASService"
  SimpleSC::RemoveService "StaubDBService"
  RMDir /r "$INSTDIR\data\db"
  RMDir /r "$INSTDIR\data\images"
  RMDir /r "$INSTDIR\data"
  RMDir /r "$INSTDIR\installLogs"
  RMDir /r "$INSTDIR\postgresql"
  RMDir /r "$INSTDIR\jdk"
  RMDir /r "$INSTDIR\jboss"
  Delete "$INSTDIR\uninst.exe"
  Delete "$INSTDIR\${PRODUCT_NAME}.url"
  RMDir /r "$SMPROGRAMS\${PRODUCT_NAME}"
  RMDir "$INSTDIR"
  SetAutoClose false
SectionEnd