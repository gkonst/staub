package ru.spbspu.staub.entity;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import ru.spbspu.staub.model.answer.AnswerType;
import ru.spbspu.staub.util.JAXBUtil;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

/**
 * The <code>AnswerXmlType</code> class is the <code>UserType</code> implementation for the Answer xml handling.
 *
 * @author Alexander V. Elagin
 */
public class AnswerXmlType implements UserType, Serializable {
    private static final long serialVersionUID = -3876294930149605126L;

    private static final int[] SQL_TYPES = {Types.CLOB};

    private static final Class RETURNED_CLASS = AnswerType.class;

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class returnedClass() {
        return RETURNED_CLASS;
    }

    public boolean equals(Object o, Object o1) throws HibernateException {
        try {
            byte[] bytes = toByteArray((Serializable) o);
            byte[] otherBytes = toByteArray((Serializable) o1);
            return Arrays.equals(bytes, otherBytes);
        } catch (IOException e) {
            throw new HibernateException(e);
        }
    }

    public int hashCode(Object o) throws HibernateException {
        try {
            byte[] bytes = toByteArray((Serializable) o);
            return Arrays.hashCode(bytes);
        } catch (IOException e) {
            throw new HibernateException(e);
        }
    }

    public Object nullSafeGet(ResultSet resultSet, String[] strings, Object o) throws HibernateException, SQLException {
        String xml = resultSet.getString(strings[0]);
        return (xml != null) ? JAXBUtil.parseAnswerXML(xml) : null;
    }

    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i) throws HibernateException,
            SQLException {
        if (o != null) {
            String xml = JAXBUtil.createAnswerXML((AnswerType) o);
            preparedStatement.setString(i, xml);
        } else {
            preparedStatement.setNull(i, SQL_TYPES[0]);
        }
    }

    public Object deepCopy(Object o) throws HibernateException {
        try {
            byte[] bytes = toByteArray((Serializable) o);

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);

            Object clone = ois.readObject();

            ois.close();

            return clone;
        } catch (IOException e) {
            throw new HibernateException(e);
        } catch (ClassNotFoundException e) {
            throw new HibernateException(e);
        }
    }

    public boolean isMutable() {
        return true;
    }

    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return serializable;
    }

    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return o1;
    }

    private byte[] toByteArray(Serializable s) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(s);

        oos.close();

        return baos.toByteArray();
    }
}
