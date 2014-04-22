package db;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.annotation.PreDestroy;

//import org.apache.log4j.Logger;


public class MysqlClient {

//    private static Logger logger = Logger.getLogger(PgDbDataReader.class);
    
    protected String dbDriverName;
    protected String connectionUrl;
    protected String dbUserName;
    protected String dbPassword;
    protected String SCHEMA;
    protected Properties properties;
    
    protected Connection connection = null;
    protected Statement statement = null;
    protected ResultSet resultSet = null;
    
    public MysqlClient() {
    	try {
			initParams();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @PreDestroy
    public void tearDown(){
    	closeAllQuietly();
    }
    
    private void initParams() throws IOException {
    	connectionUrl = "localhost:3306";
    	dbUserName = "root";
    	dbPassword = "root";
    	dbDriverName = "com.mysql.jdbc.Driver";
    	SCHEMA = "butterflys";
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException{
    	return getConnection(connectionUrl, dbUserName, dbPassword);
    }
    
    private Connection getConnection(String connectionUrl, String dbUserName, String dbPassword)
    		throws SQLException, ClassNotFoundException {

        Class.forName(dbDriverName);
    	connectionUrl = "jdbc:mysql://" + connectionUrl;
        return DriverManager.getConnection(connectionUrl, dbUserName, dbPassword);
    }
   
    
    public void select(String table , String whereClause) 
    		throws ClassNotFoundException, SQLException, IOException{
    	
    	String query = "SELECT * FROM " + SCHEMA + "." + table;
    	if(!whereClause.isEmpty() && whereClause != null)
    		query += " WHERE " + whereClause;
    	executeQuery(query);
    }
    
    
    public void executeQuery(String query)
    		throws ClassNotFoundException, IOException {

    	try {
    		System.out.println("DB Connection URL=" + connectionUrl);
    		System.out.println("DB Username=      " + dbUserName);
    		System.out.println("DB Password=      " + dbPassword);
            connection = getConnection(connectionUrl, dbUserName, dbPassword);
            statement = connection.createStatement();
            System.out.println("Executing query: \n" + query);
            resultSet = statement.executeQuery(query);
        } catch (SQLException e){
        	e.printStackTrace();
        }
    }
    
    
    public boolean insert(String table , String setClause) 
    		throws ClassNotFoundException, SQLException, IOException{
    	
    	String insert = "INSERT INTO " + SCHEMA +  "." + table + " SET " + setClause;
    	return insert(insert);
    }
    
    public boolean insert(String insert)
    		throws SQLException, ClassNotFoundException, IOException {

    	try {
    		System.out.println("DB Connection URL=" + connectionUrl);
    		System.out.println("DB Username=      " + dbUserName);
    		System.out.println("DB Password=      " + dbPassword);
            connection = getConnection(connectionUrl, dbUserName, dbPassword);
            statement = connection.createStatement();
            System.out.println("Executing query: " + insert);
            return statement.execute(insert);
        } catch (SQLException e){
        	e.printStackTrace();
            throw e;
        } finally{
        	//connection.close();
        }
    }
    
    public void printFirstColFromResultSet() throws SQLException{
    	
    	while(resultSet.next()){
    		System.out.println(resultSet.getFetchSize());
    		System.out.println(resultSet.getString(2));
    	}
    }
    
    public void closeAllQuietly(Connection connection, Statement statement, ResultSet resultSet) {

    	closeQuietly(resultSet);
    	closeQuietly(statement);
    	closeQuietly(connection);
    }
    
    public void closeAllQuietly() {

    	closeQuietly(resultSet);
    	closeQuietly(statement);
    	closeQuietly(connection);
    }
    
    public void closeQuietly(ResultSet resultSet) {
        
    	try {
            if (resultSet != null)
            	resultSet.close();
        } catch (Exception e) {
        	System.out.println("Close ResultSet failed " + e);
        }
    }
    
    public void closeQuietly(Statement statement) {
        
    	try {
            if (statement != null)
            	statement.close();
        } catch (Exception e) {
        	System.out.println("Close Statement failed " + e);
        }
    }
    
    public void closeQuietly(Connection connection) {
        
    	try {
            if (connection != null)
            	connection.close();
        } catch (Exception e) {
        	System.out.println("Close Connection failed " + e);
        }
    }
    
    protected void loadProperties() throws IOException {
		// load SUT properties file
		properties = loadProperties(properties, "/src/test/resources/sut.properties");
	}
	
	protected Properties loadProperties(Properties properties, String id) throws IOException {

		properties = new Properties();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(id);
		properties.load(in);
		return properties;
	}
}