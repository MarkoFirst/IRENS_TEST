import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataBase {

    private Connection conn = null;

    public MyDataBase() throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/IRENS_DB", "postgres",
                    "and1");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        if (conn == null) {
            System.out.println("Failed to make connection!");
        }
    }




    /*

    String databaseURL = "jdbc:firebirdsql:localhost:C:/My documets/FreeLans/IRENS/IRENS_DB/IRENS_DB.fdb?charSet=Cp1251&lc_ctype=WIN1251";
    String user = "SYSDBA";
    String password = "masterkey";



    public MyDataBase() throws SQLException {
        conn = DriverManager.getConnection(databaseURL, user, password);
        if (conn.isClosed()) {
            System.out.println("CLOSE");
        }
    }
    */

    public Connection getConn() {
        return conn;
    }

    public void getCloseConn() throws SQLException {
        conn.close();
    }
}

