package piazza;

import java.sql.*;
import java.util.*;


public class Post extends ActiveDomainObject {

    private int postID;
    String title;
    String description;
    String colorCode;
    private Integer threadID;
    private Integer replyToID;
    private Integer userID;

    //Opprettelse av Post når det er en "random" post i threaden. Lager threaden rett før Post'en så vi får inn ThreadID.
    //User case 2. 
    public Post(int threadID, String title, String description) {
        this.title = title;
        this.description = description;
        this.threadID = threadID;  
    }
    
    //Opprettelse av Post når det er et svar på en annen Post. ID'en til Post'en som besvares er relpyToID. 
    public Post(String title, String description, int replyToID, Connection conn) {
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
            ResultSet rs = stmt.executeQuery("select MAX(PostID) from Post");
            boolean rsResult = rs.next();
            if (!rsResult) {
            	this.postID = 1;
            } else {
            	this.postID = rs.getInt(1) + 1;
            }
        } catch (Exception e) {
            System.out.println("db error during select of PostID from Post= "+e);
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
            System.out.println("Successfull creation of Post");
            
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
    		this.colorCode = "White";
    	}
    	try {
            PreparedStatement stmt = conn.prepareStatement("update Post set ColorCode = '" + this.colorCode + "' where PostID=" + replyToID);
            stmt.execute();
        } catch (Exception e) {
            System.out.println("db error during update of colorcode from Post= "+e);
            return;
        }
    }
    
    public void deletePost(Connection conn) {
    	try {
            PreparedStatement stmt = conn.prepareStatement("delete Post set ColorCode = '" + this.colorCode + "' where PostID=" + replyToID);
            stmt.execute();
        } catch (Exception e) {
            System.out.println("db error during update of colorcode from Post= "+e);
            return;
        }
    }
    
}
