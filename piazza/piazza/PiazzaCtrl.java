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
            System.out.println("Inne i pizzactrl");
        } catch (SQLException e) {
            System.out.println("db error during setAutocommit of PiazzaCtrl" + e);
            return;
        }
    }
    
    public void logIn(String email, int pid) {
    	
    	this.piazzaUser = new PiazzaUser(email, pid);
    	this.piazzaUser.initialize(conn);
    	System.out.println(this.piazzaUser.getEmail());
    
    }
}
