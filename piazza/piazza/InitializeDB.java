package piazza;

import java.sql.SQLException;
import java.sql.Statement;

public class InitializeDB extends DBConn {
	public InitializeDB() {
		connect();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("db error during setAutocommit of Init" + e);
            return;
        }
	}
	
	public void createCourse() {
		try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("delete from Course");
            //stmt.executeUpdate("insert into Course values (1, 'DatDat', 'S2021', 0)");
            

        } catch (Exception e) {
            System.out.println("db error during select of course= "+e);
            return;
        }
	}
	
	public void closeConnection() {
		try {
			conn.close();
		} catch (Exception e) {
			
		}
	}
	
	public static void main(String[] args) {
		InitializeDB init = new InitializeDB();
		init.createCourse();
	}

}
