//import required packages
import java.sql.*;
import java.util.*;

public class Student extends User{
    static final String MENU="What do you want to do next?\n"
        +"(a) get a list of added courses\n"
        +"(b) course inquiry\n"
        +"(c) add a course\n"
        +"(d) drop a course\n"
        +"(e) exit\n";
    static final String pro_stu_added="call pro_stu_added(?)";
    static final String pro_stu_courses="call pro_stu_courses(?,?,?,?,?,?,?)";

    static boolean done=false;
    static Connection con=null;
    static Statement stmt=null;
    static CallableStatement cstmt=null;
    static ResultSet rs=null;
    static Scanner scanner=new Scanner(System.in);
    static String input=null;



    Student(String user, String password){
        super(user,password);
    }
    public void useDatabase(){


        try{
            //register jdbc driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Driver status OK");

            //open a connection
            System.out.println("Connecting to the course system...");
            con=DriverManager.getConnection(DB_URL,id,pass);
            System.out.println("Connection OK");
            stmt=con.createStatement();

            //open a menu
            while (!done){
                System.out.println(MENU);
                input=scanner.next();

                switch(input){
                    //query for added courses;
                    case "a":
                        try {
                            queryForAdded();
                        }
                        catch (Exception qe){
                            System.out.println("query for added courses failed");
                        }
                        done=true;
                    case "b":
                    	try{
                    		queryForCourses();
                    	}
                    	catch (Exception e){
                    		System.out.println("query for courses failed");
                    	}
                      done=true;
                    case "e":
                        System.exit(0);
                }

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.out.println("connection failed");
        }
    }

    private void queryForCourses() throws Exception{
        String [] columns={"course_id","course_code","course_name","instructor_name","department_name","campus","weekday"};
        HashMap<String, String> conditions=new HashMap<String, String>();

        //get conditions
        for (String column:columns){
            System.out.println(column+"(if you are not sure, just input a '0'):");
            input=scanner.next();
            if (input.equals("0")){
                conditions.put(column,"null");
            }
            else{
                conditions.put(column,input+"%");
            }
            System.out.println(conditions.get(column));
        }

        //begin query
        cstmt=con.prepareCall(pro_stu_courses);
        int i=1;
        for (String column:columns){
            cstmt.setString(i,conditions.get(column));
            i++;
        }
        System.out.println("begin execute the sql");
        try{
            cstmt.execute();}
        catch (Exception e){
            e.printStackTrace(System.out);
        }
        System.out.println("execute the sql successfully");
        rs=cstmt.getResultSet();
        try{
            printOutCourses();
        }
        catch (Exception e){
            System.out.println("print failed");
        }

    }

    private void printOutCourses() throws Exception{

        while (rs.next()){
            String c_id=rs.getString("c.id");
            String c_code=rs.getString("c.code");
            String c_name=rs.getString("c.name");
            String c_instructor_name=rs.getString("c.instructor_name");
            String c_credits=rs.getString("c.credits");
            String c_department_name=rs.getString("c.department_name");
            String c_campus=rs.getString("c.campus");
            int c_start_week=rs.getInt("c.start_week");
            int c_end_week=rs.getInt("c.end_week");
            int c_weekday=rs.getInt("c.weekday");
            int c_start_time=rs.getInt("c.start_time");
            int c_end_time=rs.getInt("c.end_time");
            String c_restricted_major=rs.getString("c.restricted_major");
            String c_restricted_grade=rs.getString("c.restricted_grade");
            int c_restricted_gender=rs.getInt("c.restricted_gender");
            String c_notes=rs.getString("c.notes");
            int n_allowance=rs.getInt("n.allowance");
            int n_maximum=rs.getInt("n.maximum");
            System.out.println(c_id+"\t"
                               +c_code+"\t"
                               +c_name+"\t"
                               +c_instructor_name+"\t"
                               +c_credits+"\t"
                               +c_department_name+"\t"
                               +c_campus+"\t"
                               +c_start_week+"\t"
                               +c_end_week+"\t"
                               +c_weekday+"\t"
                               +c_start_time+"\t"
                               +c_end_time+"\t"
                               +c_restricted_major+"\t"
                               +c_restricted_grade+"\t"
                               +c_restricted_gender+"\t"
                               +c_notes+"\t"
                               +n_allowance+"\t"
                               +n_maximum);
        }
    }
    private void queryForAdded() throws Exception{

        cstmt=con.prepareCall(pro_stu_added);
        cstmt.setString(1,id);
        cstmt.execute();
        System.out.println("execute the sql successfully");
        rs=cstmt.getResultSet();
        while (rs.next()){
            String c_id=rs.getString("c.id");
            String c_code=rs.getString("c.code");
            String c_name=rs.getString("c.name");
            String c_instructor_name=rs.getString("c.instructor_name");
            String c_credits=rs.getString("c.credits");
            String c_department_name=rs.getString("c.department_name");
            String c_campus=rs.getString("c.campus");
            int c_start_week=rs.getInt("c.start_week");
            int c_end_week=rs.getInt("c.end_week");
            int c_weekday=rs.getInt("c.weekday");
            int c_start_time=rs.getInt("c.start_time");
            int c_end_time=rs.getInt("c.end_time");
            String c_restricted_major=rs.getString("c.restricted_major");
            String c_restricted_grade=rs.getString("c.restricted_grade");
            int c_restricted_gender=rs.getInt("c.restricted_gender");
            String c_notes=rs.getString("c.notes");
            int n_allowance=rs.getInt("n.allowance");
            int n_maximum=rs.getInt("n.maximum");
            System.out.println(c_id+"\t"
                               +c_code+"\t"
                               +c_name+"\t"
                               +c_instructor_name+"\t"
                               +c_credits+"\t"
                               +c_department_name+"\t"
                               +c_campus+"\t"
                               +c_start_week+"\t"
                               +c_end_week+"\t"
                               +c_weekday+"\t"
                               +c_start_time+"\t"
                               +c_end_time+"\t"
                               +c_restricted_major+"\t"
                               +c_restricted_grade+"\t"
                               +c_restricted_gender+"\t"
                               +c_notes+"\t"
                               +n_allowance+"\t"
                               +n_maximum);
        }
    }
}
