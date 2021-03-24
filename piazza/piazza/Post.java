package piazza;

import java.sql.*;

/**
 * An object of the class Post represents a Post in the table 'Post' in the database 'Piazza'. 
 * Contains constructors and methods to interact with the database. 
 * The methods initialize() and save() inherits from the abstract class ActiveDomainObject.
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
     * 
     * @param ThreadID - the ID of the Thread the Post is a part of. 
     * @param title,description - title and description of the Post.
     * @param user - the user who published the Post.
     */
    public Post(Integer threadID, String title, String description, PiazzaUser user) {
        this.title = title;
        this.description = description;
        this.threadID = threadID;  
        this.userID = user.getPid();
        //the colorCode is only used when saving the Post to the database.
        this.colorCode = "Red";
    }
    
    /**
     * This constructor is used when creating a reply to another Post.
     * 
     * @param description - description of the Post.
     * @param replyToID - the ID of the Post this post is replying to.
     * @param user - the user who published the Post.
     * @param conn - the connection with the database, initialized in the class DBConn.
     */
    public Post(String description, Integer replyToID, PiazzaUser user, Connection conn) {
        this.description = description;
        this.replyToID = replyToID;
        this.userID = user.getPid();
        this.setThreadID(replyToID, conn);
    }
    
    /**
     * Private method used by a reply to find the ThreadID of the Post.
     * 
     * @param replyToID - the ID of the Post this post is replying to.
     * @param conn - the connection with the database, initialized in the class DBConn.
     */
    private void setThreadID(Integer replyToID, Connection conn) {
    	try {
            Statement stmt = conn.createStatement();
            //The reply and the post it replies to has the same ThreadID, so we can find the ThreadID of the reply
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
     * Creates unique ID to the Post by finding the max value of the current Post ID's and adding 1.
     * 
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
    
    /**
     * Saves the Post object with all it's parameters in the database.
     * 
     */
    @Override
    public void save(Connection conn) {
        try {    
        	PreparedStatement stmt = conn.prepareStatement
        			("insert into Post values (" + postID + "," + userID + ", '" + title + "', '" + description + "', '" + colorCode + "', NOW(), " + threadID + ", " + replyToID +")");
            stmt.execute();
            System.out.println("Successfull creation of Post");
            
        } catch (Exception e) {
            System.out.println("db error during insert of Post="+e);
            return;
        }
    }

    /**
     * Changes the colorCode of the Post this post is replying to in the database. 
     * 
     * @param conn - the connection with the database, initialized in the class DBConn.
     */
    public void setColorCode(Connection conn) {
    	String type = "";
    	String colorCodeMainPost = "";
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
    		colorCodeMainPost = "Yellow";
    	} else if (type.equals("Instructor")) {
    		colorCodeMainPost = "White";
    	}
    	try {
            PreparedStatement stmt = conn.prepareStatement("update Post set ColorCode = '" + colorCodeMainPost + "' where PostID=" + replyToID);
            stmt.execute();
        } catch (Exception e) {
            System.out.println("db error during update of colorcode from Post= "+e);
            return;
        }
    }
}
