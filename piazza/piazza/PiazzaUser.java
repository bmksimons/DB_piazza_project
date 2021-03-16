package piazza;

import java.sql.*;

public class PiazzaUser {
	
	private int pid;
	private String email;
	private String userPassword;
	private String name;
	private String type;
	
	public PiazzaUser(int pid) {
		this.pid = pid;
	}
	
	public PiazzaUser(String email) {
		this.email = email;
		
		//Check to see if email is in database
		//checkValidUser(conn);
	}
	
	
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
					System.out.println("success with registering user");
					break;
				} 	
			}
			
		} catch(Exception e) {
			System.out.println("db error, could not find user with this email");
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
