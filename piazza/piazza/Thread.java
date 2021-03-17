package piazza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Thread extends ActiveDomainObject {
	private Integer threadID;
	private Integer tagID;
	private Integer folderID;
	private List<Post> posts = new ArrayList<>();
	
	//skal vi bare anta at vi vet courseID og folderID? Vet egt tagID og, men tenkte det var en lett � finne p� egenh�nd. kan egt gj�re det med folderID og hvis vi rekker
	public Thread(Integer threadID, Integer tagID, Integer folderID) {
		this.threadID = threadID;
		this.tagID = tagID;
		this.folderID = folderID;
	}
	
	public void addPost(Post post) {
		if (!posts.contains(post)) {
			posts.add(post);
		}
	}

	@Override
	public void initialize(Connection conn) {
		try {
            PreparedStatement stmt = conn.prepareStatement("insert into Thread values (" + threadID + ", 1, " + folderID + ", " + tagID + ")");
            stmt.execute();
            System.out.println("successfull insert of thread");
        } catch (Exception e) {
            System.out.println("db error during insert of Thread= "+e);
            return;
        }
	}

	@Override
	public void refresh(Connection conn) {
		initialize(conn);
	}

	@Override
	public void save(Connection conn) {
		//TODO - eller trenger vel egentlig ikke denne metoden
	}

}
