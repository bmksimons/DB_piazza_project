package piazza;

import java.sql.*;
import java.util.*;


public class Post extends ActiveDomainObject {

    private int postID;
    String title;
    String description;
    String colorCode;
    private static final int NOID = -1;
    private int threadID;
    private Integer replyToID;
    private int userID;
    private ArrayList<PiazzaUser> users;

    //Opprettelse av Post når det er en "random" post i threaden. Lager threaden rett før Post'en så vi får inn ThreadID.
    //User case 2. 
    public Post(int postID, int threadID, String title, String description) {
        this.postID = postID;
        this.title = title;
        this.description = description;
        this.threadID = threadID;  
    }
    
    //Opprettelse av Post når det er et svar på en annen Post. ID'en til Post'en som besvares er relpyToID. 
    public Post(int postID, String title, String description, int replyToID, Connection conn) {
    	this.postID = postID;
        this.title = title;
        this.description = description;
        this.replyToID = replyToID;
        this.setThreadID(postID, replyToID, conn);
    }
    
    private void setThreadID(int postID, int replyToID, Connection conn) {
    	try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select ThreadID from Post where PostID=" + replyToID);
            if (rs.next()) {
            	this.threadID = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("db error during select of ThreadID from Post= "+e);
            return;
        }
    	
    }
    
    
    public void regUser (String email, Connection conn) {
        PiazzaUser u = new PiazzaUser(email);
        u.initialize (conn);
        this.userID = u.getPid();
    }
    
    @Override
    public void initialize(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Post");
            
            if (rs.next() == false) {
            	postID = 1;
            	threadID = 1;
            } else {
                postID = rs.getInt(1)+1;
                if (threadID <= rs.getInt("ThreadID")) {
                    threadID = rs.getInt("ThreadID")+1;
                    //Must create new thread with this new ID into threadID
                }
            }
            while (rs.next()) {   
            }
        } catch (Exception e) {
            System.out.println("db error during select of Post= "+e);
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
        	PreparedStatement stmt = conn.prepareStatement
        			("insert into Post values (" + postID + "," + userID + ", '" + title + "', '" + description + "', 'Red', NOW(), " + threadID + ", " + replyToID +")");
            stmt.execute();
            Statement stmt1 = conn.createStatement(); 
            ResultSet rs = stmt1.executeQuery("select * from Post");
            
            while (rs.next()) {
            	System.out.println(rs.getString("Title"));
            } 
            
        } catch (Exception e) {
            System.out.println("db error during insert of Post="+e);
            return;
        }
    }

    //relplyToID er ID'en til Post'en som blir svart på.
    public void setColor(int replyToID, Connection conn) {
    	String type = "";
    	try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select Type from PiazzaUser where UserID=" + this.userID);
            if (rs.next()) {
            	type = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("db error during select of Type from PiazzaUser= "+e);
            return;
        }
    	if (type.equals("Student")) {
    		this.colorCode = "Yellow";
    	} else if (type.equals("Instructor")) {
    		this.colorCode = "Green";
    	}
    	try {
            PreparedStatement stmt = conn.prepareStatement("update Post set ColorCode = '" + this.colorCode + "' where PostID=" + replyToID);
            stmt.execute();
        } catch (Exception e) {
            System.out.println("db error during update of colorcode from Post= "+e);
            return;
        }
    }
    
}
