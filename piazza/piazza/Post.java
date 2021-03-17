package piazza;

import java.sql.*;
import java.util.*;
import java.time.*;

public class Post extends ActiveDomainObject {

    int postID;
    String title;
    String description;
    String colorCode;
    LocalDate date;
    LocalTime time;
    private static final int NOID = -1;
    private int threadID;
    private int replyToID;
    private ArrayList<PiazzaUser> users;

    public Post(int postID, String title, String description, String colorCode) {
        this.postID = postID;
        this.title = title;
        this.description = description;
        this.colorCode = colorCode;
        
    }
    
    public Post(String title, String description) {
    	postID = NOID;
        this.title = title;
        this.description = description;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.users = new ArrayList<>();
        threadID = NOID;
        replyToID = NOID;
   
    }
    
    
    public void regUser (String email, Connection conn) {
        PiazzaUser u = new PiazzaUser(email);
        u.initialize (conn);
        users.add(u);
        
    }
    
    @Override
    public void initialize(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Post where PostID=" + postID);
            while (rs.next()) {
                
            }

        } catch (Exception e) {
            System.out.println("db error during select of avtale= "+e);
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
            Statement stmt = conn.createStatement(); 
            
            stmt.executeUpdate("insert into Post values (1,1, '"+title+"', '"+description+"', 'rod', NULL, NULL, 1, NULL)");
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
