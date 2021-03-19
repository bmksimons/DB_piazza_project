package piazza;

import java.sql.*;
import java.util.*;

/**
 * The controller class which contains methods to realize all user cases and sets up the connection with the database.
 */
public class PiazzaCtrl extends DBConn {
	private Thread thread;
    private PiazzaUser piazzaUser;
    
    /**
     * Connects the project to the database via the class DBConn.
     */
    public PiazzaCtrl() {
        connect();
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("db error during setAutocommit of PiazzaCtrl" + e);
            return;
        }
    }
    
    /**
     * Checks if the given input corresponds with a saved tuple in the database.
     *
     * @param email, password from the user trying to log into piazza.
     */
    public void logIn(String email, String password) {
    	
        piazzaUser = new PiazzaUser(email);
        piazzaUser.initialize(conn);
        
        if (! piazzaUser.getPassword().equals(password)) {
        	System.out.println("Password is incorrect, please try again:");
        } else {
        	System.out.println("Successfully logged in");
        }
    }
    
    /**
     * Creates a new Thread and the first Post in that Thread. Saves both in the database.
     *
     * @param title, description - for creating the Post
     * @param tagTitle, folderName - for creating the thread with corresponding tag and folder.
     */
    public void createFirstPostInThread(String title, String description, String tagTitle, String folderName) {
    	//If saving the Post after saving the Thread causes an exception, the newly created Thread will be deleted to avoid redundancy.
    	try {
    		Thread thread = new Thread(tagTitle, folderName);
        	thread.initialize(conn);
        	Post post = new Post(thread.getTid(), title, description, piazzaUser);
        	post.initialize(conn);
        	thread.save(conn);
        	post.save(conn);
    	} catch (Exception e) {
            System.out.println("db error during creation of new Post or Thread= "+e);
            if (thread != null) {
            	thread.deleteThread(conn);
            }
    	}
    }
   
    /**
     * Creates a Post which is a reply to another Post and saves it in the database.
     *
     * @param title, description - for creating the Post
     * @param replyToID - ID of the post the newly made post is replying to.
     */
    public void createReply(String title, String description, Integer replyToID) {
    	Post post = new Post(title, description, replyToID, piazzaUser, conn);
    	post.initialize(conn);
    	post.save(conn);
    	post.setColor(conn);
    }
    
    /**
     * Search for a given keyword in titles and descriptions of all Posts in the database.
     * 
     * @return A list of the ID's of the Posts which contained the keyword.
     */
    public List<Integer> searchForKeyword(String keyword) {
    	try {
    		List<Integer> correspondingPosts = new ArrayList<>();
    		Statement stmt = conn.createStatement();
    		String query = "select PostID "
    				+ "from Post "
    				+ "where Title like '%" + keyword + "%' or Description like '%" + keyword + "%'";
    		ResultSet rs = stmt.executeQuery(query);
    		//ResultSet rs = stmt.executeQuery("select * from Post");
    		
    		while(rs.next()) {
    			int id = rs.getInt("PostID");
    			correspondingPosts.add(id);
    		}
    		return correspondingPosts;
    	} catch (Exception e) {
            System.out.println("db error during select for keyword= "+e);
            return null;
    	}
    }
    
    /**
     * Finds how many Threads a user has read and how many Posts a user has Posted and prints the result to the interface.
     */
    public void viewStatistics() {
    	try {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery("select piazzauser.name, count(hasread.UserID) as ThreadsRead, T2.PostsPosted\r\n" + 
        			"from piazzauser left outer join hasread using (UserID)\r\n" + 
        			"    left outer join (select piazzauser.UserID, count(post.UserID) as PostsPosted\r\n" + 
        			"    from piazzauser left outer join post using (UserID)\r\n" + 
        			"    group by piazzauser.UserID)\r\n" + 
        			"    as T2 Using(UserID)\r\n" + 
        			"group by piazzauser.name\r\n" + 
        			"order by ThreadsRead DESC");
        	while (rs.next()) {
        		System.out.println("Username:" + rs.getString(1) + ",  Threads read:" + rs.getString(2) + ",  Posts posted: " +rs.getString(3));
        	}
    	} catch (Exception e) {
                System.out.println("db error during select for statistics= "+e);
                return;
        }
    }  
}
