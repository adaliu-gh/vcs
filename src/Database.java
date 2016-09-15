import java.sql.*;

public class Database{
    private static final String driver="com.mysql.jdbc.Driver";
    private static Connection con;
    private static CallableStatement cstmt;
    private static ResultSet rs;


    public static boolean connect(String url,String user, String pass){
        boolean connection=false;
        try{
            Class.forName(driver).newInstance();
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

    public static String checkIdentity(String id,String pass){
        try {
            rs=executeSql(String.format("call get_role('%s','%s')",id,pass));
            if (!rs.next()){
                return "f";
            }
            else{
                return rs.getString(1);
            }
        } catch(Exception e){
            e.printStackTrace();
            return "f";
        }
    }


    public static ResultSet executeSql(String sql) throws Exception{
        cstmt=con.prepareCall(sql);
        cstmt.execute();
        rs=cstmt.getResultSet();
        return rs;
    }


}
