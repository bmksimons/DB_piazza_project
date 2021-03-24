package piazza;

import java.sql.*;

/**
 * An object of the class PiazzaUser represents a PiazzaUser in the database 'Piazza'. 
 * Contains constructor, getters and methods to interact with the database.
 * The methods initialize() and save() inherits from the abstract class ActiveDomainObject.
 */
public class PiazzaUser extends ActiveDomainObject {
	
	private Integer pid;
	private String email;
	private String userPassword;
	private String name;
	private String type;
	
	/**
	 * @param email - used as an unique identifier to retrieve the rest of the information about the user from the database in initialize()
	 */
	public PiazzaUser(String email) {
		this.email = email;
	}
	
	/**
     * Retrieves the saved data from the user with the given email and saves it in this PiazzaUser object.
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
    
	/**
     * All users are already made before running this program, so saving of a PiazzaUser to the database is not needed.
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
