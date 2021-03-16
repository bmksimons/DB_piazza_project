package piazza;

import java.sql.Connection;
import java.sql.*;
import java.util.*;

public class PiazzaUser {
	
	private int pid;
	private String email;
	private String userPassword;
	private String name;
	private String type;
	
	public PiazzaUser(String email, int pid) {
		this.pid = pid;
		this.email = email;
	}
	
	public int getPid() {
		return this.pid;
	}
	
	public void initialize(Connection conn) {
		try {
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from PiazzaUser where UserID=" + this.pid);
			ResultSet rs1 = stat.executeQuery("select count(*) from PiazzaUser where Email=" + this.email);
			while (rs.next()) {
				this.email = rs.getString("Email");
				this.userPassword = rs.getString("UserPassword");
				this.name = rs.getString("Name");
				this.type = rs.getString("Type");
			}
		} catch(Exception e) {
			System.out.println("db error during user");
			return;
		}
	}
	
	/*public boolean checkPassword(String passWord) {
		if (this.userPassword == passWord)
	}*/
	
	public String getEmail() {
		return this.email;
	}
    
}
