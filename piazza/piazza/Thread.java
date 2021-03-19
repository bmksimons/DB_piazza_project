package piazza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Thread extends ActiveDomainObject {
	private Integer threadID;
	private Integer tagID;
	private Integer folderID;
	
	public Thread(Integer tagID, Integer folderID) {
		this.tagID = tagID;
		this.folderID = folderID;
	}
	
	public Integer getTid() {
		return this.threadID;
	}

	@Override
	public void initialize(Connection conn) {
		try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select MAX(ThreadID) from Thread");
            boolean rsResult = rs.next();
            if (!rsResult) {
            	this.threadID = 1;
            } else {
            	this.threadID = rs.getInt(1) + 1;
            }
        } catch (Exception e) {
            System.out.println("db error during select of ThreadID from Thread= "+e);
        }

	}

	@Override
	public void refresh(Connection conn) {
		initialize(conn);
	}

	@Override
	public void save(Connection conn) {
		try {
            PreparedStatement stmt = conn.prepareStatement("insert into Thread values (" + threadID + ", 1, " + folderID + ", " + tagID + ")");
            stmt.execute();
            System.out.println("successfull creation of thread");
        } catch (Exception e) {
            System.out.println("db error during insert of Thread= "+e);
            return;
        }
	}

}
