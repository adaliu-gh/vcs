
import java.util.*;

public class CourseSystem{

    private static String id;
    private static String pass;
    private static ArrayList<String> identities;
    private static Scanner scanner=new Scanner(System.in);
    private static String input=null;
    private static String MENU="What is your identity?\n" +
    		                       "(a)dministrator\n" +
    		                       "(s)tudent\n" +
    		                       "(i)structor\n" +
    		                       "(q)uit";

    private static void getIdPass(){
        System.out.println("Please input your id number:");
        id=scanner.next();
        System.out.println("Enter password:");
        pass=scanner.next();
    }

    public static void main(String[] args){

        //initialize identities-list;
        identities=new ArrayList<String>();
        identities.add("a");
        identities.add("s");
        identities.add("i");


        //input id and password
        boolean checkIdentityDone=false;
        while (!checkIdentityDone){
            System.out.println(MENU);
            input=scanner.next();
            switch (input){
                        //if quit
                    case "q":
                        checkIdentityDone=true;
                        System.out.println("Bye~");
                        System.exit(0);
                        //if the user is a student
                    case "s":
                        getIdPass();
                        Student stu=new Student(id,pass);
                        stu.useDatabase();
                        break;
                        //if the user is an instructor
                    case "i":
                      getIdPass();
                      Instructor ins=new Instructor(id,pass);
                      ins.useDatabase();
                      break;
                        //if the user is an administrator;
                    case "a":
                        getIdPass();
                        Administrator admin=new Administrator(id,pass);
                        admin.useDatabase();
                        break;
                    default:
                        System.out.println("Wrong input, please try again...");
                        System.out.println("a for administrator, s for student, i for instructor, q for quit...");
            }
        }

    }
}
