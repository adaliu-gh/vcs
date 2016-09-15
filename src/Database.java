import java.util.*;
import java.sql.*;

public class Database{
    private final String driver;
    private Connection con;
    private Statement stmt;
    private CallableStatement cstmt;
    private ResultSet rs;
    private Scanner scanner;
    private String input;
    public  boolean sucOrFail;


    public static boolean connect(String url,String user, String pass){
        boolean connection=false;
        try{
            Class.forName(driver).newInstance;
            con=DriverManager.getConnection(url,user,pass);
            connection=true;
            System.out.println("\nConnection succeeded");
        }
        catch (Exception e ){
            e.printStackTrace();
            System.out.println("\nConnection failed");
        }
        return connection;
    }

    public static String checkIdentity(String id, String pass){
        rs=executeSql(String.format("call get_role(%s,%s)",id,pass));
        if (!rs.next()){
            return "f";
        }
        else{
            return rs.getString(1);
        }
    }

    public static ResultSet executeSql(String sql){
        try {
            cstmt=con.prepareCall(sql);
            cstmt.execute();
            rs=cstmt.getResultSet();
            sucOrFail=true;
        }
        catch (Exception e){
            e.printStackTrace();
            sucOrFail=false;
        }
        return rs;
    }


}
