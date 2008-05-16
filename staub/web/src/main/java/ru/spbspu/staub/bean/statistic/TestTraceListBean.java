package ru.spbspu.staub.bean.statistic;

import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import ru.spbspu.staub.bean.GenericExportableListBean;
import ru.spbspu.staub.entity.Discipline;
import ru.spbspu.staub.entity.Group;
import ru.spbspu.staub.entity.Student;
import ru.spbspu.staub.entity.TestTrace;
import ru.spbspu.staub.export.Cell;
import ru.spbspu.staub.model.list.FormProperties;
import ru.spbspu.staub.model.list.FormTable;
import ru.spbspu.staub.service.DisciplineService;
import ru.spbspu.staub.service.GroupService;
import ru.spbspu.staub.service.StudentService;
import ru.spbspu.staub.service.TestTraceService;

import java.util.Date;
import java.util.List;

/**
 * Webbean for manipulating list data of <code>TestTrace</code> entities.
 *
 * @author Konstantin Grigoriev
 */
@Name("testTraceListBean")
@Scope(SESSION)
public class TestTraceListBean extends GenericExportableListBean<TestTrace> {
    private static final long serialVersionUID = -4536509504869042322L;

    @In
    private TestTraceService testTraceService;
    @In
    private DisciplineService disciplineService;
    @In
    private GroupService groupService;
    @In
    private StudentService studentService;

    private Group group;
    private Student student;
    private Discipline discipline;
    private Date begin;
    private Date end;

    private List<Discipline> disciplineList;
    private List<Group> groupList;
    private List<Student> studentList;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prepareBean() {
        fillGroupList();
        fillDisciplineList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected FormTable findObjects(FormProperties formProperties) {
        return testTraceService.findTestTraces(formProperties, group, student, discipline, begin, end);
    }

    private void fillDisciplineList() {
        disciplineList = disciplineService.findAll();
    }

    private void fillGroupList() {
        groupList = groupService.findAll();
    }

    private void fillStudentList() {
        if (group != null) {
            studentList = studentService.findStudents(group);
        } else {
            studentList = null;
        }
    }

    public void setDiscipline() {
        doRefresh();
    }

    public void setGroup() {
        fillStudentList();
        doRefresh();
    }

    public void setStudent() {
        doRefresh();
    }

    public List<Discipline> getDisciplineList() {
        return disciplineList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String[] getColumns() {
        return new String[]{"test.name", "student.name", "started", "score", "testPassed"};
    }

    @Override
    public String getBundlePrefix() {
        return "test.trace.list.";
    }

    @Override
    protected List<Cell[]> getHeader() {
        List<Cell[]> header = super.getHeader();
        if (discipline != null) {
            header.add(new Cell[]{new Cell(getBundledString("test.trace.list.test.category.discipline.name")), new Cell(discipline.getName())});
        }
        if (group != null) {
            header.add(new Cell[]{new Cell(getBundledString("test.trace.list.student.group.name")), new Cell(group.getName())});
        }
        if (student != null) {
            header.add(new Cell[]{new Cell(getBundledString("test.trace.list.student.name")), new Cell(student.getName())});
        }
        return header;
    }
}
