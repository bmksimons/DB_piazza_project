package piazza;

import java.sql.*;

/**
 * Abstract class which all classes who connects to the database inherits from.
 */
public abstract class ActiveDomainObject {
    public abstract void initialize (Connection conn);
    public abstract void refresh (Connection conn);
    public abstract void save (Connection conn);
}
