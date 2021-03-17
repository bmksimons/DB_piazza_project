package piazza;

import java.sql.*;
import java.util.*;
import java.time.*;

public class Post extends ActiveDomainObject {

    private int postID = 0;
    String title;
    String description;
    String colorCode;
    LocalDate date;
    LocalTime time;
    private static final int NOID = -1;
    private int threadID = 0;
    private int replyToID;
    private int userID;
    private ArrayList<PiazzaUser> users;

    public Post(int postID, int threadID, String title, String description, String colorCode) {
        this.postID = postID;
        this.title = title;
        this.description = description;
        this.colorCode = colorCode;
        this.threadID = threadID;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        
    }
    
    public Post(String title, String description, int threadID) {
        this.title = title;
        this.description = description;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.users = new ArrayList<>();
        replyToID = NOID;
   
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
            	System.out.println("eajifoe");
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
        	System.out.println(threadID + "thread");
        	System.out.println(postID + "post");
            Statement stmt = conn.createStatement(); 
            
            stmt.executeUpdate("insert into Post values (" + postID + "," + userID + ", '" + title + "', '" + description + "', '" + colorCode + "', NULL, NULL, "+threadID+", NULL)");
            ResultSet rs = stmt.executeQuery("select * from Post");
            
            while (rs.next()) {
            	System.out.println(rs.getString("Title"));
            } 
            
        } catch (Exception e) {
            System.out.println("db error during insert of Post="+e);
            return;
        }
        /*for (int i=0;i<brukere.size();i++) {
            try {    
                Statement stmt = conn.createStatement(); 
                stmt.executeUpdate("insert into HarAvtale values ("+brukere.get(i).getBid()+",LAST_INSERT_ID())");
            } catch (Exception e) {
                System.out.println("db error during insert of HarAvtale="+e);
                return;
            }
        }*/
    }

    public void setColor(PiazzaUser user, String colorCode) {
        this.colorCode = colorCode;
        
    }
    
}
