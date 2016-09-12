
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
            if (input.equals("q")){
                checkIdentityDone=true;
                System.out.println("Bye~");
                System.exit(0);
            }
            else {
                if (identities.contains(input)){
                    System.out.println("Please input your id number:");
                    id=scanner.next();
                    System.out.println("Enter password:");
                    pass=scanner.next();

                    switch (input){

                        //if the user is a student
                        case "s":
                            Student stu=new Student(id,pass);
                            stu.useDatabase();
                            break;

                        //if the user is an instructor
                        case "i":
                        	Instructor ins=new Instructor(id,pass);
                        	ins.useDatabase();
                              break;

                        //if the user is an administrator;
                        case "a":
                            Administrator admin=new Administrator(id,pass);
                            admin.useDatabase();
                            break;
                    }

                }
                else {
                    //wrong input
                    System.out.println("Wrong input, please try again...");
                    System.out.println("a for administrator, s for student, i for instructor, q for quit...");
                }
            }
        }

    }
}
