package piazza;

import java.sql.*;
import java.util.*;

public class PiazzaCtrl extends DBConn {
    private Post post;
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
}
