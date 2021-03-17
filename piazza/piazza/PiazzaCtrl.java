package piazza;

import java.sql.*;
import java.util.*;

public class PiazzaCtrl extends DBConn {
    private Post post;
    private PiazzaUser piazzaUser;
    //lag ulike ting

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
    
    public void createPost(String name, String description, String folder, String tag) {
    	post = new Post(name, description);
    	post.save(conn);
    }
    
}
