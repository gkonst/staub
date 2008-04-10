package ru.spbspu.staub.entity;

import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.jboss.seam.log.Log;
import org.jboss.seam.log.Logging;
import ru.spbspu.staub.util.JAXBUtil;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Properties;

/**
 * The <code>XmlType</code> class is the <code>UserType</code> implementation for xml data handling.
 *
 * @author Alexander V. Elagin
 */
public class XmlType implements UserType, ParameterizedType, Serializable {
    private static final long serialVersionUID = 4593109763254958017L;

    private static final Log LOG = Logging.getLog(XmlType.class);

    private static final int[] SQL_TYPES = {Types.OTHER};

    private Class pojoClass;

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class returnedClass() {
        return pojoClass;
    }

    public boolean equals(Object o, Object o1) throws HibernateException {
        try {
            byte[] bytes = toByteArray((Serializable) o);
            byte[] otherBytes = toByteArray((Serializable) o1);
            boolean equal = Arrays.equals(bytes, otherBytes);
            LOG.debug(" equals(#0, #1) : #2", o, o1, equal);
            return equal;
        } catch (IOException e) {
            throw new HibernateException(e);
        }
    }

    public int hashCode(Object o) throws HibernateException {
        try {
            byte[] bytes = toByteArray((Serializable) o);
            int hashCode = Arrays.hashCode(bytes);
            LOG.debug(" hashCode(#0) : #1", o, hashCode);
            return hashCode;
        } catch (IOException e) {
            throw new HibernateException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public Object nullSafeGet(ResultSet resultSet, String[] strings, Object o) throws HibernateException, SQLException {
        LOG.debug(" nullSafeGet(#0, #1, #2)", resultSet, strings, o);
        InputStream is = resultSet.getBinaryStream(strings[0]);
        Object result;
        if (is != null) {
            result = JAXBUtil.parseXml(pojoClass, is);
        } else {
            result = null;
        }
        LOG.debug("  result : #0", result);
        return result;
    }

    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i) throws HibernateException,
            SQLException {
        LOG.debug(" nullSafeSet(#0, #1, #2)", preparedStatement, o, i);
        if (o != null) {
            byte[] bytes = JAXBUtil.createXml(o);
            preparedStatement.setBytes(i, bytes);
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

            LOG.debug(" deepCopy(#0) : #1", o, clone);            

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
        Serializable result = (Serializable) o;
        LOG.debug(" disassemble(#0) : #1", o, result);
        return result;
    }

    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        LOG.debug(" assemble(#0, #1) : #2", serializable, o, serializable);
        return serializable;
    }

    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        LOG.debug(" replace(#0, #1, #2) : #3", o, o1, o2, o1);
        return o1;
    }

    public void setParameterValues(Properties properties) {
        LOG.debug(" setParameterValues(#0)", properties);

        String pojoClassName = properties.getProperty("pojoClass");

        if (pojoClassName == null) {
            throw new IllegalArgumentException("Property pojoClass can not be null.");
        }

        try {
            pojoClass = Class.forName(properties.getProperty("pojoClass"));
        } catch (ClassNotFoundException e) {
            String message = MessageFormat.format("Could not found class with name \"{0}\".", pojoClassName);
            throw new IllegalArgumentException(message);
        }
    }

    private byte[] toByteArray(Serializable s) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(s);

        oos.close();

        return baos.toByteArray();
    }
}
