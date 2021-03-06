package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

/**
 * The AbstractDAO class implements select all, select by id, update, insert and delete queries on each table.
 * The methods use java reflection techniques to get the class and the field names of each object.
 * In order to use this data, the classes in the model package must have the same fields with the tables in the database.
 * @param <T>
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * creates a string with a Select query in mysql language
     * @param field
     * @return
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    private String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }
    //DELETE FROM table_name [WHERE Clause]

    /**
     * The delete query takes an integer and an object and delets the corresponding element from the table
     * @param field
     * @return
     */
    private String createDeleteQueryID(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
     /*UPDATE table_name SET field1 = new-value1, field2 = new-value2
            [WHERE Clause]*/

    /**
     * the update query takes as parameter a string and an object
     * the values of the object will be given as strings in the final string that will represent the query
     * The update query takes an object fromt the model package and an id integer and update sthe coresponding field in the coresponding table.
     * @param field
     * @param t
     * @return
     */
    private String createUpdateQuery(String field,T t){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for (Field fld : t.getClass().getDeclaredFields()) {
            fld.setAccessible(true);
            Object value;
            try {
                sb.append(fld.getName() + " = ");
                value = fld.get(t);
                sb.append("'"+value+"'" + ", ");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        StringBuilder sb2=new StringBuilder();
        String result= sb.substring(0,sb.length()-2);
        sb2.append(result);
        sb2.append(" WHERE " + field + " =?");

        return sb2.toString();
    }

    /**
     * function that uses the delete query by connecting to the database and executing the query
     * @param id
     */
    public void deleteElementID(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteQueryID("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int x = statement.executeUpdate();


        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:DeleteById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
    /*INSERT INTO table_name (column1, column2, column3, ...)
VALUES (value1, value2, value3, ...); nu sunt sigur ca e ok*/

    /**
     * The insert query does not use an id integer because it is generated by the databse uppon insertion.
     * An object of any type from the model package is given.
     * The class and the fields excluding the id of the object are used to write the mysql query and the values of the fields are given to be inserted in the table. All data is stored in the table
     * @param t
     * @return
     */
    private String createInsertQuery(T t){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName() + " (");
        for (Field field : t.getClass().getDeclaredFields()) {
            if(!field.getName().equals("id") ) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(t);
                    sb.append(field.getName() + ", ");
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        StringBuilder sb2=new StringBuilder();
        String result= sb.substring(0,sb.length()-2);
        sb2.append(result);
        sb2.append(")");
        sb2.append(" VALUES (");
        for (Field field : t.getClass().getDeclaredFields()) {
            if (!field.getName().equals("id")) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(t);
                    sb2.append("'"+value+"'" + ", ");
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        StringBuilder sb3=new StringBuilder();
         result= sb2.substring(0,sb2.length()-2);
        sb3.append(result);
        sb3.append(")");
        return sb3.toString();
    }

    /**
     * The findAll function takes an object and returns a list of objects that were stored in the coresponding table.
     * @return
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * The findById function takes an integer and an object to determine the table and returns the object with the given id from the corresponding table
     * @param id
     * @return
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * function that uses the insert query by connecting to the database and executing the query
     * @param t
     */
    public void insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createInsertQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            //statement.setInt(1, id);
            int x = statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

    }

    /**
     * function that uses the update query by connecting to the database and executing the query
     * @param t
     * @param id
     */
    public void update(T t, int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createUpdateQuery("id",t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int x = statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

    }
}

