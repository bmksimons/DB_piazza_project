package piazza;

import java.sql.*;
import java.util.*;

public class PiazzaCtrl extends DBConn {
    private PiazzaUser piazzaUser;

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
    
    //User case 2
    public void createFirstPostInThread(String title, String description, String tagTitle, String email, String folderName) {
    	Integer tagID = null;
    	Integer folderID = null;
    	//Bruker queries for � finne TagID og FolderID for � oppfylle user case 2. Studass sa det var dumt � hardcode ID'en selv om vi vet de
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
    	//try: catch: slette thread og post
    	Thread thread = new Thread(tagID, folderID);
    	thread.initialize(conn);
    	Post post = new Post(thread.getTid(), title, description);
    	post.initialize(conn);
    	post.regUser(email, conn);
    	thread.save(conn);
    	post.save(conn);
    }
   
    //user case 3
    public void createReply(String title, String description, int replyToID, String email) {
    	Post post = new Post(title, description, replyToID, conn);
    	post.initialize(conn);
    	post.regUser(email, conn);
    	post.save(conn);
    	post.setColor(replyToID, conn);
    }
    
    //user case 4
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
