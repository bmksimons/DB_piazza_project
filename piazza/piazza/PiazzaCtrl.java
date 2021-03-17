package piazza;

import java.sql.*;
import java.util.*;

public class PiazzaCtrl extends DBConn {
	//trenger vi post og piazzauser her?
    private Post post;
    private PiazzaUser piazzaUser;
    private List<Thread> threads = new ArrayList<>();
    // int to be able to create unique postID's
    private int numberOfPosts = 0;

    public PiazzaCtrl() {
        connect();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("db error during setAutocommit of PiazzaCtrl" + e);
            return;
        }
    }
    
    
    public void logIn(String email, String password) {
    	
        piazzaUser = new PiazzaUser(email);
        piazzaUser.initialize(conn);
        
        if (! piazzaUser.getPassword().equals(password)) {
        	System.out.println("Password is incorrect, please try again:");
        } else {
        	System.out.println("Successfully logged in");
        }
    }
    
    public void createPostInThread(int threadID, String title, String description, String email) {
    	int postID = this.numberOfPosts + 1;
    	post = new Post(postID, threadID, title, description, "Red");
    	this.numberOfPosts += 1;
    	//post.initialize(conn);
    	post.regUser(email, conn);
    	post.save(conn);
    }
    
    public void createThread(String title, String description, String tag, String email) {
    	int threadID = this.threads.size()+1;
    	int postID = this.numberOfPosts + 1;
    	Object tagID = null;
    	try {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery("select TagID from Tag where Title = '"  + tag + "'");
        	if (rs.next()) {
        		tagID = rs.getInt(1);
        	}
        	Thread thread = new Thread(threadID, tagID);
        	thread.initialize(conn);
        	this.threads.add(thread);
        	Post post = new Post(postID, threadID, title, description, "Red");
        	post.regUser(email, conn);
        	post.save(conn);
        	this.numberOfPosts += 1;
    	} catch (Exception e) {
            System.out.println("db error during select of Tag= "+e);
            return;
        }
    }
    
    //lagde denne bare for å se om det funket
    public void createThread() {
    	Thread thread = new Thread(1, 1);
        thread.initialize(conn);
    }
    
    public void createReply() {
    	
    }
    
}
