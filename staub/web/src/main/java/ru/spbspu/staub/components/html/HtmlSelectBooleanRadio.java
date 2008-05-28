package ru.spbspu.staub.components.html;

import ru.spbspu.staub.components.renderkit.BooleanRadioRenderer;

import javax.el.ValueExpression;
import javax.faces.component.UISelectBoolean;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Component class that represents single radio.
 *
 * @author Konstantin Grigoriev
 */
public class HtmlSelectBooleanRadio extends UISelectBoolean {
    private static final String OPTIMIZED_PACKAGES[] = {
            "javax.faces.component", "javax.faces.component.html", "ru.spbspu.staub.components.html"
    };
    public static final String COMPONENT_TYPE = "ru.spbspu.HtmlSelectBooleanRadio";
    private String accesskey;
    private String dir;
    private Boolean disabled;
    private String label;
    private String lang;
    private String onblur;
    private String onchange;
    private String onclick;
    private String ondblclick;
    private String onfocus;
    private String onkeydown;
    private String onkeypress;
    private String onkeyup;
    private String onmousedown;
    private String onmousemove;
    private String onmouseout;
    private String onmouseover;
    private String onmouseup;
    private String onselect;
    private Boolean readonly;
    private String style;
    private String styleClass;
    private String tabindex;
    private String title;
    private String group;

    public HtmlSelectBooleanRadio() {
        setRendererType(BooleanRadioRenderer.RENDERER_TYPE);
    }


    public String getAccesskey() {
        if (null != accesskey)
            return accesskey;
        ValueExpression ve = getValueExpression("accesskey");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
        handleAttribute("accesskey", accesskey);
    }

    public String getDir() {
        if (null != dir)
            return dir;
        ValueExpression ve = getValueExpression("dir");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setDir(String dir) {
        this.dir = dir;
        handleAttribute("dir", dir);
    }

    public boolean isDisabled() {
        if (null != disabled) {
            return disabled;
        }
        ValueExpression ve = getValueExpression("disabled");
        return ve != null && (Boolean) ve.getValue(getFacesContext().getELContext());
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getLabel() {
        if (null != label)
            return label;
        ValueExpression ve = getValueExpression("label");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLang() {
        if (null != lang)
            return lang;
        ValueExpression ve = getValueExpression("lang");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setLang(String lang) {
        this.lang = lang;
        handleAttribute("lang", lang);
    }

    public String getOnblur() {
        if (null != onblur)
            return onblur;
        ValueExpression ve = getValueExpression("onblur");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
        handleAttribute("onblur", onblur);
    }

    public String getOnchange() {
        if (null != onchange)
            return onchange;
        ValueExpression ve = getValueExpression("onchange");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
        handleAttribute("onchange", onchange);
    }

    public String getOnclick() {
        if (null != onclick)
            return onclick;
        ValueExpression ve = getValueExpression("onclick");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
        handleAttribute("onclick", onclick);
    }

    public String getOndblclick() {
        if (null != ondblclick)
            return ondblclick;
        ValueExpression ve = getValueExpression("ondblclick");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
        handleAttribute("ondblclick", ondblclick);
    }

    public String getOnfocus() {
        if (null != onfocus)
            return onfocus;
        ValueExpression ve = getValueExpression("onfocus");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
        handleAttribute("onfocus", onfocus);
    }

    public String getOnkeydown() {
        if (null != onkeydown)
            return onkeydown;
        ValueExpression ve = getValueExpression("onkeydown");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
        handleAttribute("onkeydown", onkeydown);
    }

    public String getOnkeypress() {
        if (null != onkeypress)
            return onkeypress;
        ValueExpression ve = getValueExpression("onkeypress");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
        handleAttribute("onkeypress", onkeypress);
    }

    public String getOnkeyup() {
        if (null != onkeyup)
            return onkeyup;
        ValueExpression ve = getValueExpression("onkeyup");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
        handleAttribute("onkeyup", onkeyup);
    }

    public String getOnmousedown() {
        if (null != onmousedown)
            return onmousedown;
        ValueExpression ve = getValueExpression("onmousedown");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
        handleAttribute("onmousedown", onmousedown);
    }

    public String getOnmousemove() {
        if (null != onmousemove)
            return onmousemove;
        ValueExpression ve = getValueExpression("onmousemove");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
        handleAttribute("onmousemove", onmousemove);
    }

    public String getOnmouseout() {
        if (null != onmouseout)
            return onmouseout;
        ValueExpression ve = getValueExpression("onmouseout");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
        handleAttribute("onmouseout", onmouseout);
    }

    public String getOnmouseover() {
        if (null != onmouseover)
            return onmouseover;
        ValueExpression ve = getValueExpression("onmouseover");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
        handleAttribute("onmouseover", onmouseover);
    }

    public String getOnmouseup() {
        if (null != onmouseup)
            return onmouseup;
        ValueExpression ve = getValueExpression("onmouseup");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
        handleAttribute("onmouseup", onmouseup);
    }

    public String getOnselect() {
        if (null != onselect)
            return onselect;
        ValueExpression ve = getValueExpression("onselect");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setOnselect(String onselect) {
        this.onselect = onselect;
        handleAttribute("onselect", onselect);
    }

    public boolean isReadonly() {
        if (null != readonly)
            return readonly;
        ValueExpression ve = getValueExpression("readonly");
        return ve != null && (Boolean) ve.getValue(getFacesContext().getELContext());
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public String getStyle() {
        if (null != style)
            return style;
        ValueExpression ve = getValueExpression("style");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setStyle(String style) {
        this.style = style;
        handleAttribute("style", style);
    }

    public String getStyleClass() {
        if (null != styleClass)
            return styleClass;
        ValueExpression ve = getValueExpression("styleClass");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getTabindex() {
        if (null != tabindex)
            return tabindex;
        ValueExpression ve = getValueExpression("tabindex");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
        handleAttribute("tabindex", tabindex);
    }

    public String getTitle() {
        if (null != title)
            return title;
        ValueExpression ve = getValueExpression("title");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setTitle(String title) {
        this.title = title;
        handleAttribute("title", title);
    }

    public String getGroup() {
        if (null != group)
            return group;
        ValueExpression ve = getValueExpression("group");
        if (ve != null)
            return (String) ve.getValue(getFacesContext().getELContext());
        else
            return null;
    }

    public void setGroup(String group) {
        this.group = group;
        handleAttribute("group", group);
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[26];
        values[0] = super.saveState(context);
        values[1] = accesskey;
        values[2] = dir;
        values[3] = disabled;
        values[4] = label;
        values[5] = lang;
        values[6] = onblur;
        values[7] = onchange;
        values[8] = onclick;
        values[9] = ondblclick;
        values[10] = onfocus;
        values[11] = onkeydown;
        values[12] = onkeypress;
        values[13] = onkeyup;
        values[14] = onmousedown;
        values[15] = onmousemove;
        values[16] = onmouseout;
        values[17] = onmouseover;
        values[18] = onmouseup;
        values[19] = onselect;
        values[20] = readonly;
        values[21] = style;
        values[22] = styleClass;
        values[23] = tabindex;
        values[24] = title;
        values[25] = group;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        accesskey = (String) values[1];
        dir = (String) values[2];
        disabled = (Boolean) values[3];
        label = (String) values[4];
        lang = (String) values[5];
        onblur = (String) values[6];
        onchange = (String) values[7];
        onclick = (String) values[8];
        ondblclick = (String) values[9];
        onfocus = (String) values[10];
        onkeydown = (String) values[11];
        onkeypress = (String) values[12];
        onkeyup = (String) values[13];
        onmousedown = (String) values[14];
        onmousemove = (String) values[15];
        onmouseout = (String) values[16];
        onmouseover = (String) values[17];
        onmouseup = (String) values[18];
        onselect = (String) values[19];
        readonly = (Boolean) values[20];
        style = (String) values[21];
        styleClass = (String) values[22];
        tabindex = (String) values[23];
        title = (String) values[24];
        group = (String) values[25];
    }

    private void handleAttribute(String name, Object value) {
        List setAttributes = null;
        String pkg = getClass().getPackage().getName();
        if (Arrays.binarySearch(OPTIMIZED_PACKAGES, pkg) >= 0) {
            setAttributes = (List) getAttributes().get("javax.faces.component.UIComponentBase.attributesThatAreSet");
            if (setAttributes == null) {
                setAttributes = new ArrayList(6);
                getAttributes().put("javax.faces.component.UIComponentBase.attributesThatAreSet", setAttributes);
            }
            if (value == null)
                setAttributes.remove(name);
            else if (!setAttributes.contains(name))
                setAttributes.add(name);
        }
    }
}
