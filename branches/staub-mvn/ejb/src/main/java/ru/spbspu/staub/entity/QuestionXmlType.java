package ru.spbspu.staub.entity;

import org.hibernate.usertype.UserType;
import org.hibernate.HibernateException;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Types;

import ru.spbspu.staub.model.question.QuestionType;
import ru.spbspu.staub.util.JAXBUtil;

/**
 * The <code>QuestionXmlType</code> class is the <code>UserType</code> implementation for the Question definition xml
 * handling.
 *
 * @author Alexander V. Elagin
 */
public class QuestionXmlType implements UserType, Serializable {
    private static final long serialVersionUID = 4593109763254958017L;

    private static final int[] SQL_TYPES = {Types.CLOB};

    private static final Class RETURNED_CLAS = QuestionType.class;

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class returnedClass() {
        return RETURNED_CLAS;
    }

    public boolean equals(Object o, Object o1) throws HibernateException {
        return (o != null) ? o.equals(o1) : (o1 == null);
    }

    public int hashCode(Object o) throws HibernateException {
        return (o != null) ? o.hashCode() : 0;
    }

    public Object nullSafeGet(ResultSet resultSet, String[] strings, Object o) throws HibernateException, SQLException {
        String xml = resultSet.getString(strings[0]);
        return (xml != null) ? JAXBUtil.parseQuestionXML(xml) : null;
    }

    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i) throws HibernateException,
            SQLException {
        if (o != null) {
            String xml = JAXBUtil.createQuestionXML((QuestionType) o);
            preparedStatement.setString(i, xml);
        } else {
            preparedStatement.setNull(i, SQL_TYPES[0]);
        }
    }

    public Object deepCopy(Object o) throws HibernateException {
        return null;
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object o) throws HibernateException {
        return null;
    }

    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return null;
    }

    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return null;
    }
}
