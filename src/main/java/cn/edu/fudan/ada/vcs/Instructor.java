package cn.edu.fudan.ada.vcs;
import java.sql.*;
import java.util.*;

public class Instructor extends User{
    private static final String MENU="What do you want to do next?\n"
        +"(a) get the register for a specific course\n"
        +"(b) get a list of course that I teach\n"
        +"(c) input scores\n"
        +"(x) exit\n";
    private ResultSet rs=null;
    private Scanner scanner=new Scanner(System.in);
    private String input=null;
    private String sql=null;


    Instructor(String user, String password){
        super(user,password);
    }

    public void enterSystem(){

        while (true){
            System.out.println(MENU);
            input=scanner.nextLine();
            switch(input){
                case "a":
                    getRegister();
                    break;
                case "b":
                    getCourses();
                    break;
                case "c":
                    insertScores();
                    break;
                case "x":
                    return;
            }
        }

    }

    private void getRegister() {
        sql=null;
        String course_id=null;
        System.out.println("Enter 'q' to quit'");
        System.out.println("Please input the id of the course you want to drop:");
        course_id=scanner.next();
        if (course_id.equals("q")){
            return;
        }
        sql=String.format("call pro_ins_register('%s')",course_id);
        try{
            rs=Database.executeSql(sql);
            System.out.println("student_id\t name\t major\t grade\t gender");
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
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Execution failed");
        }
    }

    private void getCourses() {
        sql=String.format("call pro_ins_courses('%s')",id);
        try {
            rs=Database.executeSql(sql);
            printOutInsCourses();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Execution failed");
        }
    }

    private void insertScores() {
        sql=null;
        String course_id=null;
        String student_id=null;
        int score=-1;

        System.out.println("Enter 'q' to quit'");
        System.out.println("Which course?...");
        course_id=scanner.next();
        if (course_id.equals("q")){
            return;
        }
        System.out.println("Which student?...");
        student_id=scanner.next();
        if (student_id.equals("q")){
            return;
        }
        while ((score<0)||(score>100)){
            System.out.println("How many scores (between 0 and 100)?...");
            try{
                input=scanner.next();
                if (input.equals("q")){
                    return;
                }
                score=Integer.parseInt(input);
            } catch (Exception e){
                continue;
            }
        }
        sql=String.format("call pro_ins_input_scores('%s','%s',%d)",course_id,student_id,score);
        try{
            rs=Database.executeSql(sql);
            System.out.println("Execution succeeded");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Execution failed");
        }
    }


    private void printOutInsCourses(){

        try{
            System.out.println("id\t name\t instructor_name\t credits\t department_name\t campus\t classroom\t start_week\t end_week\t weekday\t start_time\t end_time\t notes");
            while (rs.next()){
                String c_id=rs.getString("id");
                String c_name=rs.getString("name");
                String c_instructor_name=rs.getString("instructor_name");
                Float c_credits=rs.getFloat("credits");
                String c_department_name=rs.getString("department_name");
                String c_campus=rs.getString("campus");
                String c_classroom=rs.getString("classroom");
                int c_start_week=rs.getInt("start_week");
                int c_end_week=rs.getInt("end_week");
                String c_weekday=rs.getString("weekday");
                int c_start_time=rs.getInt("start_time");
                int c_end_time=rs.getInt("end_time");
                String c_notes=rs.getString("notes");
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
                                   +c_notes);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Execution failed");
        }
    }
}
