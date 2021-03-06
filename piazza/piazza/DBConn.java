package piazza;

import java.sql.*;
import java.util.Properties;

/**
 * 
 * The class which sets up the connection to the database used in the ADO.
 *
 */
public abstract class DBConn {
    protected Connection conn;
    
    public void connect() {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver"); 
            // Properties for user and password.
            Properties p = new Properties();
            p.put("user", "bmsimons_user");
            p.put("password", "HeiHei");   
           
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/piazza?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false",p);
        } catch (Exception e)
    	{
            throw new RuntimeException("Unable to connect", e);
    	}
    }
}
