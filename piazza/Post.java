package piazza;

import java.sql.Date;
import java.sql.Time;

public class Post {

    int postID;
    String title;
    String description;
    String colorCode;
    Date date;
    Time time;

    public Post(int postID, String title, String description, String colorCode, Date date, Time time) {
        this.postID = postID;
        this.title = title;
        this.description = description;
        this.colorCode = colorCode;
        this.date = date;
        this.time = time;
    }

    public void setColor(PiazzaUser user, String colorCode) {
        this.colorCode = colorCode;
    }
    
}
