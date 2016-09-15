//import required packages
import java.sql.*;
import java.util.*;

public class Student extends User{
    private static final String MENU="What do you want to do next?\n"
        +"(a) get a list of added courses\n"
        +"(b) course inquiry\n"
        +"(c) add a course\n"
        +"(d) drop a course\n"
        +"(e) score inquiry\n"
        +"(x) exit\n";

    private static final String pro_stu_added="call pro_stu_added(?)";
    private static final String pro_stu_courses="call pro_stu_courses(?,?,?,?,?,?,?)";
    private static final String pro_stu_add_course="call pro_stu_add_course(?,?)";
    private static final String pro_stu_drop_course="call pro_stu_drop_course(?,?)";
    private static final String pro_stu_scores="call pro_stu_scores(?)";

    private boolean done=false;
    private Connection con=null;
    private CallableStatement cstmt=null;
    private Statement stmt=null;
    private ResultSet rs=null;
    private Scanner scanner=new Scanner(System.in);
    private String input=null;

    private static String [] queryColumns={"course_id","course_code","course_name","instructor_name","department_name","campus","weekday"};
    private static HashMap<String, String> conditions=new HashMap<String, String>();

    Student(String user, String password){
        super(user,password);
    }
    public void useDatabase(){


        try{
            //register jdbc driver
            Class.forName(JDBC_DRIVER).newInstance();
            System.out.println("Driver status OK");

            //open a connection
            System.out.println("Connecting to the course system...");
            con=DriverManager.getConnection(DB_URL,"root","");
            System.out.println("Connection OK");

            if (!checkIdentityStu(id,pass)){
                System.out.println("Sorry, identify checking failed");
                return;
            }
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
                        catch (Exception e){
                            System.out.println("query for added courses failed");
                        }
                        break;
                    case "b":
                    	try{
                    		queryForCourses();
                    	}
                    	catch (Exception e){
                    		System.out.println("query for courses failed");
                    	}
                      break;
                    case "c":
                        try{
                            addCourse();
                        }
                        catch (Exception e){
                            System.out.println("course not added successfully");
                        }
                        break;
                    case "d":
                        try{
                            dropCourse();
                        }
                        catch (Exception e){
                            System.out.println("course not dropped successfully");
                        }
                        break;
                    case "e":
                        try{
                            queryForScores();
                        }
                        catch (Exception e){
                            System.out.println("query for scores failed");
                        }
                        break;
                    case "x":
                        done=true;
                }

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.out.println("connection failed");
        }
    }

    private void queryForScores() throws Exception{
        cstmt=con.prepareCall(pro_stu_scores);
        cstmt.setString(1,id);
        cstmt.execute();
        rs=cstmt.getResultSet();
        while (rs.next()){
            String course_id=rs.getString(1);
            String course_name=rs.getString(2);
            String instructor_name=rs.getString(3);
            int score=rs.getInt(4);
            System.out.println(course_id+"\t"
                               +course_name+"\t"
                               +instructor_name+"\t"
                               +score);
        }
        System.out.println("query for scores succeeded");
    }
    private void dropCourse() throws Exception{
        //get course id
        String course_id=null;
        System.out.println("Please input the id of the course you want to drop:");
        course_id=scanner.next();

        //begin add
        cstmt=con.prepareCall(pro_stu_drop_course);

        cstmt.setString(1,course_id);
        cstmt.setString(1,id);

        cstmt.execute();
        System.out.println("drop the course "+ course_id+" successfully");

    }

    private void addCourse() throws Exception{

        //get course id
        String course_id=null;
        System.out.println("Please input the id of the course you want to add:");
        course_id=scanner.next();

        //begin add
        cstmt=con.prepareCall(pro_stu_add_course);

        cstmt.setString(1,course_id);
        cstmt.setString(1,id);

        cstmt.execute();
        System.out.println("add the course "+ course_id+" successfully");
    }
    private void queryForCourses() throws Exception{

        //get conditions
        for (String column:queryColumns){
            System.out.println(column+"(if you are not sure, just input a '0'):");
            input=scanner.next();
            if (input.equals("0")){
                conditions.put(column,"null");
            }
            else{
                conditions.put(column,input+"%");
            }
        }

        //begin query
        cstmt=con.prepareCall(pro_stu_courses);

        //set parameters
        int i=1;
        for (String column:queryColumns){
            cstmt.setString(i,conditions.get(column));
            i++;
        }

        //execute
        try{
            cstmt.execute();}
        catch (Exception e){
            e.printStackTrace(System.out);
        }
        rs=cstmt.getResultSet();

        //print the result
        printOutCourses();
        System.out.println("query for added courses success~");
    }

    private void queryForAdded() throws Exception{

        cstmt=con.prepareCall(pro_stu_added);
        cstmt.setString(1,id);
        cstmt.execute();
        rs=cstmt.getResultSet();
        printOutCourses();
        System.out.println("query for added courses success~");
    }

    private boolean checkIdentityStu(String id, String pass){
    	boolean identity=true;
        try {
    	stmt=con.createStatement();
        String sql=String.format("select id,pass from student where id='%s' and pass='%s'",id,pass);
        rs=stmt.executeQuery(sql);
        if (!rs.next()){
           identity=false;
        	}
        }
        catch (Exception e){
        	e.printStackTrace();
        }
        return identity;
    }

    private void printOutCourses(){

        try{
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
                                   +c_notes+"\t"
                                   +n_allowance+"\t"
                                   +n_maximum);
            }
        }
        catch (Exception e){
            e.printStackTrace(System.out);
        }
    }
}
