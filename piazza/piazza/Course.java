package piazza;

import java.sql.*;

public class Course extends ActiveDomainObject {
	private int courseID;
	private String courseName;
	private String courseTerm;
	private int allowAnonymous;
	
	public Course(int courseID, String courseName, String courseTerm, int allowAnonymous) {
		this.courseID = courseID;
		this.courseName = courseName;
		this.courseTerm = courseTerm;
		this.allowAnonymous = allowAnonymous;
	}

	@Override
	public void initialize(Connection conn) {
		try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("insert into Course values (" + courseID + ", '" + 
            courseName+ "', '"+courseTerm+"', 0");
            

        } catch (Exception e) {
            System.out.println("db error during select of course= "+e);
            return;
        }
	}

	@Override
	public void refresh(Connection conn) {
		initialize(conn);
	}

	@Override
	public void save(Connection conn) {
		// TODO Auto-generated method stub
		
	}
	
}