package piazza;

import java.sql.*;
import java.util.*;

public class PiazzaCtrl extends DBConn {
	//trenger vi post og piazzauser her?
    private Post post;
    private PiazzaUser piazzaUser;
    private List<Thread> threads = new ArrayList<>();
    // int to be able to create unique postID's, not sure if this solution is good enough. 
    private int numberOfPosts = 0;

    public PiazzaCtrl() {
        connect();
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("db error during setAutocommit of PiazzaCtrl" + e);
            return;
        }
    }
    
    //User case 1.
    public void logIn(String email, String password) {
    	
        piazzaUser = new PiazzaUser(email);
        piazzaUser.initialize(conn);
        System.out.println(piazzaUser.getEmail());
        System.out.println(piazzaUser.getPassword());
        
        if (! piazzaUser.getPassword().equals(password)) {
        	System.out.println("Password is incorrect, please try again:");
        } else {
        	System.out.println("Successfully logged in");
        }
    }
    
    //User case 3, men bruker vel egt createReply i stedet..
    public void createPostInThread(int threadID, String title, String description, String email) {
    	int postID = this.numberOfPosts + 1;
    	post = new Post(postID, threadID, title, description);
    	this.numberOfPosts += 1;
    	//post.initialize(conn);
    	post.regUser(email, conn);
    	post.save(conn);
    }
    
    //User case 2
    public void createThread(String title, String description, String tag, String email, String folderName) {
    	int threadID = this.threads.size()+1;
    	int postID = this.numberOfPosts + 1;
    	Integer tagID = null;
    	Integer folderID = null;
    	Integer courseID = null;
    	//Bruker queries for � finne TagID og FolderID for � oppfylle user case 2. Studass sa det var dumt � hardcode ID'en selv om vi vet de
    	try {
    		Statement stmt = conn.createStatement();
        	ResultSet rs = stmt.executeQuery("select TagID from Tag where Title = '"  + tag + "'");
        	if (rs.next()) {
        		tagID = rs.getInt(1);
        	}
    	} catch (Exception e) {
                System.out.println("db error during select of Tag= "+e);
                return;
        }
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
    	Thread thread = new Thread(threadID, tagID, folderID);
    	thread.initialize(conn);
    	this.threads.add(thread);
    	Post post = new Post(postID, threadID, title, description);
    	post.regUser(email, conn);
    	post.save(conn);
    	this.numberOfPosts += 1;
    }
   
    
    public void createReply(String title, String description, int replyToID, String email) {
    	int postID = this.numberOfPosts + 1;
    	Post post = new Post(postID, title, description, replyToID, conn);
    	this.numberOfPosts += 1;
    	post.regUser(email, conn);
    	post.save(conn);
    	post.setColor(replyToID, conn);
    }
    
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
            System.out.println("db error during select of Posts= "+e);
            return null;
    	}
    }
    
}
