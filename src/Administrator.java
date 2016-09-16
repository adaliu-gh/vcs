import java.util.*;
import java.sql.*;
public class Administrator extends User{

    private ResultSet rs=null;
    private Scanner scanner=new Scanner(System.in);
    private String sql=null;

    Administrator(String user, String password){
        super(user,password);
    }
    public void enterSystem(){
        while (true){
            System.out.println("Enter 'q' to quit:");
            System.out.println("Enter SQL statement:");
            sql=scanner.nextLine();
            if (sql.equals("q")){
                return;
            }
            try{
                rs=Database.executeSql(sql);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
