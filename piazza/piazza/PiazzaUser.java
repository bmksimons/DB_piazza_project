package piazza;

import java.sql.*;

/**
 * An object of the class Post represents a Piazzauser in piazza. 
 * Contains constructor, getters and methods to interact with the database.
 */
public class PiazzaUser extends ActiveDomainObject {
	
	private int pid;
	private String email;
	private String userPassword;
	private String name;
	private String type;
	
	public PiazzaUser(String email) {
		this.email = email;
	}
	
	/**
     * Finds the data saved of the user in the database with the given email and saves it in this PiazzaUser object.
     */
	@Override
	public void initialize(Connection conn) {
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from piazza.PiazzaUser");
		
			while (rs.next()) {
				if (rs.getString("Email").equals(this.email)) {
					
					this.userPassword = rs.getString("UserPassword");
					this.pid = rs.getInt(1);
					this.name = rs.getString("Name");
					this.type = rs.getString("Type");
					break;
				} 	
			}
			
		} catch(Exception e) {
			System.out.println("db error, could not find user with this email");
			return;
		}
	}
	
	@Override
	public void refresh (Connection conn) {
        initialize (conn);
    }
    
	/**
     * All users are already made before running this program, so no saving of user info to the database is needed.
     */
	@Override
    public void save(Connection conn) {

    }
	
	public int getPid() {
		return this.pid;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.userPassword;
	}
}
