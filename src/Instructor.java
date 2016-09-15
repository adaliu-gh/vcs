
//import required packages
import java.sql.*;
import java.util.*;

public class Instructor extends User{
    private static final String MENU="What do you want to do next?\n"
        +"(a) get the register for a specific course\n"
        +"(b) get a list of course that I teach\n"
        +"(c) input scores\n"
        +"(e) exit\n";

    private static final String pro_ins_register="call ppro_ins_register(?)";
    private static final String pro_ins_courses="call pro_ins_courses(?)";
    private static final String pro_ins_input_scores="call pro_ins_input_scores(?,?)";

    private boolean done=false;
    private Connection con=null;
    private Statement stmt=null;
    private CallableStatement cstmt=null;
    private ResultSet rs=null;
    private Scanner scanner=new Scanner(System.in);
    private String input=null;



    Instructor(String user, String password){
        super(user,password);
    }

    public void enterSystem(){


        try{
            //register jdbc driver
            Class.forName(JDBC_DRIVER).newInstance();
            System.out.println("Driver status OK");

            //open a connection
            System.out.println("Connecting to the course system...");
            con=DriverManager.getConnection(DB_URL,"root","");
            System.out.println("Connection OK");

            if (!checkIdentityIns(id,pass)){
                System.out.println("Sorry, identity checking failed");
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
                            getRegister();
                        }
                        catch (Exception e){
                            System.out.println("query for register failed");
                        }
                        break;
                    case "b":
                    	try{
                    		getCourses();
                    	}
                    	catch (Exception e){
                    		System.out.println("query for courses failed");
                    	}
                      break;
                    case "c":
                        try{
                            insertScores();
                        }
                        catch (Exception e){
                            System.out.println("scores not added successfully");
                        }
                        break;
                    case "e":
                        done=true;
                }

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.out.println("connection failed");
        }
    }

    private void getRegister() throws Exception{
        //get course id
    	String course_id=null;
        System.out.println("Please input the id of the course you want to drop:");
        course_id=scanner.next();

        //begin add
        cstmt=con.prepareCall(pro_ins_register);
        cstmt.setString(1,course_id);
        cstmt.execute();
        rs=cstmt.getResultSet();

        //print query result
        while(rs.next()){
            String  student_id=rs.getString(1);
            String  name=rs.getString(2);
            String  major=rs.getString(3);
            int  grade=rs.getInt(4);
            int  gender=rs.getInt(5);
            System.out.println(student_id+"\t"
                               +name+"\t"
                               +major+"\t"
                               +grade+"\t"
                               +gender);
        }

    }

    private void getCourses() throws Exception{

        cstmt=con.prepareCall(pro_ins_courses);
        cstmt.setString(1,id);
        cstmt.execute();
        rs=cstmt.getResultSet();
        printOutInsCourses();
        System.out.println("query for courses success~");
    }

    private void insertScores() throws Exception{
        String course_id=null;
        String student_id=null;
        int score=0;
        cstmt=con.prepareCall(pro_ins_input_scores);

        //get the ids of course and student
        System.out.println("Which course?...");
        course_id=scanner.next();
        System.out.println("Which student?...");
        student_id=scanner.next();
        System.out.println("How many scores?...");
        score=Integer.parseInt(scanner.next());

        if ((score>=0) && (score<=100)) {
            //set parameters;
            cstmt.setString(1,student_id);
            cstmt.setString(2,course_id);
            cstmt.setInt(3,score);

            //execute
            cstmt.execute();
            System.out.println("insert the score successfully");
        }
        else{
            System.out.println("the score "+score+" is out of range, please check it before you insert it");
        }

    }

    private boolean checkIdentityIns(String id, String pass){
    	boolean identity=true;
    	try{
        stmt=con.createStatement();
        String sql=String.format("select id,pass from instructor where id='%s' and pass='%s'",id,pass);
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

    private void printOutInsCourses(){

        try{
            while (rs.next()){
                String c_id=rs.getString("id");
                String c_code=rs.getString("code");
                String c_name=rs.getString("name");
                String c_instructor_name=rs.getString("instructor_name");
                String c_credits=rs.getString("credits");
                String c_department_name=rs.getString("department_name");
                String c_campus=rs.getString("campus");
                int c_start_week=rs.getInt("start_week");
                int c_end_week=rs.getInt("end_week");
                int c_weekday=rs.getInt("weekday");
                int c_start_time=rs.getInt("start_time");
                int c_end_time=rs.getInt("end_time");
                String c_notes=rs.getString("notes");
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
                                   +c_notes);
            }
        }
        catch (Exception e){
            e.printStackTrace(System.out);
        }
    }
}
