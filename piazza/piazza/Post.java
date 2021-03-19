package piazza;

import java.sql.*;

/**
 * An object of the class Post represents a Post in piazza. 
 * Contains constructors, registration of the user who published the post and methods to interact with the database.
 */
public class Post extends ActiveDomainObject {

    private Integer postID;
    private String title;
    private String description;
    private String colorCode;
    private Integer threadID;
    private Integer replyToID;
    private Integer userID;

    /**
     * This constructor is used when creating a stand alone Post in a thread.
     */
    public Post(Integer threadID, String title, String description, PiazzaUser user) {
        this.title = title;
        this.description = description;
        this.threadID = threadID;  
        this.userID = user.getPid();
    }
    
    /**
     * This constructor is used when creating a reply to another Post with the ID 'replyToID'.
     */
    public Post(String title, String description, Integer replyToID, PiazzaUser user, Connection conn) {
        this.title = title;
        this.description = description;
        this.replyToID = replyToID;
        this.userID = user.getPid();
        this.setThreadID(replyToID, conn);
    }
    
    /**
     * Private method used by a reply to find the ThreadID of the Post.
     */
    private void setThreadID(Integer replyToID, Connection conn) {
    	try {
            Statement stmt = conn.createStatement();
            //The reply and the post it replies to has the same ThreadID
            ResultSet rs = stmt.executeQuery("select ThreadID from Post where PostID=" + replyToID);
            if (rs.next()) {
            	this.threadID = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("db error during select of ThreadID from Post= "+e);
            return;
        }
    }
    
    /**
     * Creates unique ID to the Post by finding the max value of the current Post ID's and adding 1
     */
    @Override
    public void initialize(Connection conn) {
    	try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select MAX(PostID) from Post");
            boolean rsResult = rs.next();
            //If it doesn't exists any Posts yet, the ID is set to 1.
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
    
    /**
     * Saves the Post object with all it's parameters in the database.
     */
    @Override
    public void save(Connection conn) {
        try {    
        	PreparedStatement stmt = conn.prepareStatement
        			("insert into Post values (" + postID + "," + userID + ", '" + title + "', '" + description + "', 'Red', NOW(), " + threadID + ", " + replyToID +")");
            stmt.execute();
            System.out.println("Successfull creation of Post");
            
        } catch (Exception e) {
            System.out.println("db error during insert of Post="+e);
            return;
        }
    }

    /**
     * Changes the colorCode of the Post this post is replying to. 
     */
    public void setColor(Connection conn) {
    	String type = "";
    	//Finds the type of the user who published the reply to decide color of the original post.
    	try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select Type from PiazzaUser where UserID=" + userID);
            if (rs.next()) {
            	type = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("db error during select of Type from PiazzaUser= "+e);
            return;
        }
    	if (type.equals("Student")) {
    		colorCode = "Yellow";
    	} else if (type.equals("Instructor")) {
    		colorCode = "White";
    	}
    	try {
            PreparedStatement stmt = conn.prepareStatement("update Post set ColorCode = '" + colorCode + "' where PostID=" + replyToID);
            stmt.execute();
        } catch (Exception e) {
            System.out.println("db error during update of colorcode from Post= "+e);
            return;
        }
    }
}
