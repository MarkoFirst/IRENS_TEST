import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {


    String databaseURL = "jdbc:firebirdsql:localhost:C:/My documets/FreeLans/IRENS/IRENS_DB/IRENS_DB.fdb?charSet=Cp1251&lc_ctype=WIN1251";
    String user = "SYSDBA";
    String password = "masterkey";

    private Connection conn = null;

    public MyDataBase() throws SQLException {
        conn = DriverManager.getConnection(databaseURL, user, password);
        if (conn.isClosed()) {
            System.out.println("CLOSE");
        }
    }
    public Connection getConn() {
        return conn;
    }

    public void getCloseConn() throws SQLException {
        conn.close();
    }
}

