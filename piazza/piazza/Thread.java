package piazza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Thread extends ActiveDomainObject {
	private Integer threadID;
	private String tagTitle;
	private Integer tagID;
	private String folderName;
	private Integer folderID;
	
	public Thread(String tagTitle, String folderName) {
		this.tagTitle = tagTitle;
		this.folderName = folderName;
	}
	
	public Integer getTid() {
		return this.threadID;
	}

	@Override
	public void initialize(Connection conn) {
		//Creates unique ID to the Thread by finding the max value of the current Thread ID's and adding 1
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
		//Finds the ID to the tag with the title given in the constructor
		try {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery("select TagID from Tag where Title = '"  + tagTitle + "'");
        	if (rs.next()) {
        		tagID = rs.getInt(1);
        	}
    	} catch (Exception e) {
                System.out.println("db error during select of Tag= "+e);
                return;
        }
		//Finds the ID to the folder with the name given in the constructor
    	try {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery("select FolderID from Folder where FolderName = '"  + folderName + "'");
        	if (rs.next()) {
        		folderID = rs.getInt(1);
        	}
    	} catch (Exception e) {
                System.out.println("db error during select of Folder= "+e);
                return;
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
	
	public void deleteThread(Connection conn) {
		
	}

}
