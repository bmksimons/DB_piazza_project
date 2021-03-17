package piazza;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Thread extends ActiveDomainObject {
	private int threadID;
	private Object tagID;
	private List<Post> posts = new ArrayList<>();
	
	//skal vi bare anta at vi vet courseID og folderID? Vet egt tagID og, men tenkte det var en lett å finne på egenhånd. kan egt gjøre det med folderID og hvis vi rekker
	public Thread(int threadID, Object tagID) {
		this.threadID = threadID;
		this.tagID = tagID;
	}
	
	public void addPost(Post post) {
		if (!posts.contains(post)) {
			posts.add(post);
		}
	}

	@Override
	public void initialize(Connection conn) {
		try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("insert into Thread values (" + threadID + ", 1, 1, " + tagID + ")");
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
