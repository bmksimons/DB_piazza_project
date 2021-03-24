package piazza;

import java.sql.*;

/**
 * Abstract class which all classes who connects to the database inherits from.
 */
public abstract class ActiveDomainObject {
	/**
	 * 
	 * @param conn - The connection with the database, initialized in the class DBConn.
	 */
    public abstract void initialize (Connection conn);
    /**
	 * 
	 * @param conn - The connection with the database, initialized in the class DBConn.
	 */
    public abstract void save (Connection conn);
}
