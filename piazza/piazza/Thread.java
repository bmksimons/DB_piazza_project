package piazza;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Thread extends ActiveDomainObject {
	private Integer threadID;
	private Integer tagID;
	private Integer folderID;
	
	public Thread(Integer threadID, Integer tagID, Integer folderID) {
		this.threadID = threadID;
		this.tagID = tagID;
		this.folderID = folderID;
	}

	@Override
	public void initialize(Connection conn) {
		try {
            PreparedStatement stmt = conn.prepareStatement("insert into Thread values (" + threadID + ", 1, " + folderID + ", " + tagID + ")");
            stmt.execute();
            System.out.println("successfull insert of thread");
        } catch (Exception e) {
            System.out.println("db error during insert of Thread= "+e);
            return;
        }
	}

	@Override
	public void refresh(Connection conn) {
		initialize(conn);
	}

	@Override
	public void save(Connection conn) {
		//TODO - eller trenger vel egentlig ikke denne metoden
	}

}
