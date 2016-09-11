
import java.util.*;

public class CourseSystem{

    private static String id;
    private static String pass;
    private static ArrayList<String> identities;
    private static Scanner scanner=new Scanner(System.in);
    private static String input=null;

    public static void main(String[] args){
        

        //initialize identities-list;
        identities=new ArrayList<String>();
        identities.add("a");
        identities.add("s");
        identities.add("i");

        //choose user group
        System.out.println("Welcome to Virtual Course System ^_^ ~");
        System.out.println("What is your identity?");
        System.out.println("(a)dministrator");
        System.out.println("(s)tudent");
        System.out.println("(i)nstructor");
        System.out.println("(q)uit");

        //input id and password
        boolean checkIdentityDone=false;
        while (!checkIdentityDone){
            input=scanner.next();
            if (input.equals("q")){
                checkIdentityDone=true;
                System.out.println("Bye~");
                System.exit(0);
            }
            else {
                if (identities.contains(input)){
                    checkIdentityDone=true;
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
