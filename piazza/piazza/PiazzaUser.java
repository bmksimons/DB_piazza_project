package piazza;

import java.sql.*;

public class PiazzaUser extends ActiveDomainObject {
	
	private int pid;
	private String email;
	private String userPassword;
	private String name;
	private String type;
	
	public PiazzaUser(String email) {
		this.email = email;
		
		//Check to see if email is in database
		//checkValidUser(conn);
	}
	
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
    
	@Override
    public void save(Connection conn) {
        try {
            Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery("update PiazzaUser set UserID="+this.pid+", Email="+this.email+
            		", UserPassword="+this.userPassword+", type="+this.type+"where pid="+this.pid);
        } catch (Exception e) {
            System.out.println("db error during update of bruker="+e);
            return;
        }
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
