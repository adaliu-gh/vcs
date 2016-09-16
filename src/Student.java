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

    private ResultSet rs=null;
    private Scanner scanner=new Scanner(System.in);
    private String input=null;
    private String sql=null;

    private static String [] queryColumns={"course_id","course_name","instructor_name","department_name","campus","weekday"};
    private static HashMap<String, String> conditions=new HashMap<String, String>();

    Student(String user, String password){
        super(user,password);
    }

    public void enterSystem(){
        //open a menu
        while (true){
            System.out.println(MENU);
            input=scanner.nextLine();
            switch(input){
                case "a":
                    queryForAdded();
                    break;
                case "b":
                    queryForCourses();
                    break;
                case "c":
                    addCourse();
                    break;
                case "d":
                    dropCourse();
                    break;
                case "e":
                    queryForScores();
                    break;
                case "x":
                    return;
                }
        }
    }

    private void queryForScores(){
        sql=String.format("call pro_stu_scores('%s')",id);
        try {
            rs=Database.executeSql(sql);
            System.out.println("course_id\t course_name\t instructor_name\t score\t");
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
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Execution failed");
        }
    }

    private void dropCourse(){

        String course_id=null;
        sql=null;

        System.out.println("Enter 'q' to quit'");
        System.out.println("Please input the id of the course you want to drop:");
        course_id=scanner.nextLine();
        if (course_id.equals("q")) {
            return;
        }
        sql=String.format("call pro_stu_drop_course('%s','%s')",course_id,id);
        System.out.println(sql);
        try {
            rs=Database.executeSql(sql);
            System.out.println("drop the course "+ course_id+" successfully");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Execution failed");
        }
    }

    private void addCourse(){
        sql=null;
        String course_id=null;
        System.out.println("Enter 'q' to quit'");
        System.out.println("Please input the id of the course you want to add:");
        course_id=scanner.nextLine();
        if (course_id.equals("q")) {
            return;
        }
        sql=String.format("call pro_stu_add_course('%s','%s')",course_id,id);
        try {
            rs=Database.executeSql(sql);
            System.out.println("add the course "+ course_id+" successfully");
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Excecution failed");
        }
    }

    private void queryForCourses(){
        String input=null;
        sql="call pro_stu_courses(";
        System.out.println("Enter 'q' to quit'");
        for (String column:queryColumns){
            System.out.println(column+"(if you are not sure, just hit 'ENTER'):");
            input=scanner.nextLine();
            if (input.equals("q")){
                return;
            }
            if (input.equals("")){
                conditions.put(column,"null");
            }
            else{
                conditions.put(column,input+"%");
            }
        }

        int i=1;
        for (String column:queryColumns){
            if (i==6){
                sql=sql+String.format("'%s'",conditions.get(column))+")";
                break;
            }
            sql=sql+String.format("'%s'",conditions.get(column))+",";
            i++;
        }
        try {
            rs=Database.executeSql(sql);
            System.out.println(sql);
            printOutCourses();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Execution failed");
        }
    }

    private void queryForAdded(){
        sql=String.format("call pro_stu_added('%s')",id);
        try {
            rs=Database.executeSql(sql);
            printOutCourses();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Execution failed");
        }
    }


    private void printOutCourses(){

        try{
            System.out.println("id\t name\t instructor_name\t credits\t department_name\t campus\t classroom\t start_week\t end_week\t weekday\t start_time\t end_time\t notes\t allowance\t maximum");
            while (rs.next()){
                String c_id=rs.getString("c.id");
                String c_name=rs.getString("c.name");
                String c_instructor_name=rs.getString("c.instructor_name");
                Float c_credits=rs.getFloat("c.credits");
                String c_department_name=rs.getString("c.department_name");
                String c_campus=rs.getString("c.campus");
                String c_classroom=rs.getString("c.classroom");
                int c_start_week=rs.getInt("c.start_week");
                int c_end_week=rs.getInt("c.end_week");
                String c_weekday=rs.getString("c.weekday");
                int c_start_time=rs.getInt("c.start_time");
                int c_end_time=rs.getInt("c.end_time");
                String c_notes=rs.getString("c.notes");
                int n_allowance=rs.getInt("n.allowance");
                int n_maximum=rs.getInt("n.maximum");
                System.out.println(c_id+"\t"
                                   +c_name+"\t"
                                   +c_instructor_name+"\t"
                                   +c_credits+"\t"
                                   +c_department_name+"\t"
                                   +c_campus+"\t"
                                   +c_classroom+"\t"
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
            e.printStackTrace();
            System.out.println("printing result failed");
        }
    }
}
