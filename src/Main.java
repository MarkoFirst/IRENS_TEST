import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    static Scanner in = new Scanner(System.in);
    static ResultSet rs = null;
    static Statement stmt = null;

    public static void main(String[] args) throws Exception{
        start();
    }

    public static void start() throws SQLException {
        MyDataBase mdb = new MyDataBase();
        mdb.getConn();
        String[] input = (in.nextLine() +" ").split(" ");
        check(input);
        rs.close();
        stmt.close();
        mdb.getCloseConn();
        start();
    }
    public static void check(String[] input) throws SQLException {

        switch (input[1]){
            case "add":
                if (Objects.equals(input[2], "status")){
                    onChange(input);
                    break;
                }else {
                    onAdd(input);
                    break;
                }
            case "print":
                if (Objects.equals(input[2], "-s")){
                    sorted(input[3]);
                    break;
                } else{
                    onPrint(3, input, "THE_NAME");
                    break;
                }
            case "status":
                rs = connect().executeQuery("SELECT STATUS FROM FIRST_TABLE WHERE THE_NAME = '"+theName(input, 2)+"'");
                rs.next();
                System.out.println(rs.getString("STATUS"));
                break;
            case "remove": onDelete(input); break;
            case "edit": onChange(input); break;
            case "": break;
        }

    }

    public static void onAdd(String[] input) throws SQLException {

        String all = "";
        String theName ="";
        String status = "";
        for (int i = 3; i < input.length; i++){
            all = all + input[i]+" ";
        }

        char[] arr = all.toCharArray();
        int dop1 = 0;
        int dop = 0;
        for(int i = 0; i<arr.length;i++){
            if (arr[i] == '"') {
                dop ++;
                i++;
                dop1++;
            }
            if (dop == 1) {
                theName = theName + arr[i];
                dop1 ++;
            }

        }
        for ( int i = dop1+1; i<arr.length;i++){
            status = status + arr[i];
        }
        connect().execute("INSERT INTO FIRST_TABLE(ID_FIRST_TABLE, KIND, THE_NAME, STATUS) VALUES( null, '"+ input[2] +"', " +
                "'"+ theName +"', '"+ status +"')"
        );

    }

    public static void onPrint(int arrNum, String[] input, String colum) throws SQLException {

        int id = 0;
        String dop = "";
        if (input.length != arrNum){
            dop = dop+" AND THE_NAME = "+theName(input, arrNum);
        } else {
            rs = connect().executeQuery("SELECT COUNT(THE_NAME) FROM FIRST_TABLE");
            rs.next();
            id = rs.getInt("COUNT");
            dop = dop + " AND ID_FIRST_TABLE = ";
            rs.close();
        }

        for(int i =1; i<=id; i++) {
            rs = connect().executeQuery("SELECT " + colum + " FROM FIRST_TABLE WHERE KIND = '" + input[(arrNum-1)] + "'"
                    + dop + String.valueOf(i));
            rs.next();
            try {
                System.out.println(rs.getString(colum));
            }catch (SQLException e){
                id++;
            }
        }
    }

    public static void onChange(String[] input) throws SQLException {
        System.out.println("Add new "+ input[2]);
        String newVar = in.nextLine();
        String set = "";
        if(Objects.equals(input[2], "name")){
            set = set +"THE_NAME";
        }else if(Objects.equals(input[2], "type")){
            set = set + "KIND";
        }else{
            set = set + "STATUS";
        }
        connect().execute("UPDATE FIRST_TABLE SET "+ set+"='"+newVar+"' WHERE THE_NAME= '"+theName(input, 3)+"'");
    }

    public static void onDelete(String[] input) throws SQLException {
        connect().execute("DELETE FROM FIRST_TABLE WHERE THE_NAME= '"+theName(input, 2)+"'");
    }

    public static Statement connect() throws SQLException {
        MyDataBase mdb = new MyDataBase();
        stmt = mdb.getConn().createStatement();
        return stmt;
    }

    public static void sorted(String kind) throws SQLException {
        String query[] = {"SELECT THE_NAME FROM FIRST_TABLE WHERE KIND = '"+kind+"' ORDER BY THE_NAME"};
        for (String q: query){
            rs = connect().executeQuery(q);
            while (rs.next()){
                System.out.println(rs.getString("THE_NAME"));
            }
        }
    }

    public static String theName(String[] input, int n){
        String theName ="";
        for (int i = n; i<input.length; i++){
            theName = theName + input[i]+" ";
        }
        return theName;
    }
}