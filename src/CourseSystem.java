
import java.util.*;

public class CourseSystem{


    public static void main(String[] args){

        Scanner scanner=new Scanner(System.in);
        String input=null;

        //connect to database
        boolean con=Database.connect();
        if (!con){
            System.out.println("Sorry, cannot connect to database");
            System.exit(-1);
        }

        //login
        while (true){
            System.out.println("Enter ID (if you want to quit, just enter 'q'):");
            String id=scanner.next();
            if (id.equals("q")){
                System.exit(0);
                System.out.println("Bye~");
            }
            System.out.println("Enter password:");
            String pass=scanner.next();

            //check identify
            String identity=Database.checkIdentity(id,pass);
            switch (identity){
                case "f":
                    System.out.println("Invalid id & pass");
                    break;
                case "s":
                    Student stu=new Student(id,pass);
                    stu.enterSystem();
                    break;
                case "t":
                    Instructor ins=new Instructor(id,pass);
                    ins.enterSystem();
                    break;
            }
        }



    }
}
