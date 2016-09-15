import java.io.*;

public class CourseSystem{

    public static void main(String[] args){
        String url="jdbc:mysql://localhost/vcs?useSSL=no";
        String user="root";
        String password="";
        String id=null;
        String pass=null;
        InputStreamReader isr=new InputStreamReader(System.in);
        BufferedReader buffer=new BufferedReader(isr);

        //connect to database
        boolean con=Database.connect(url,user,password);
        if (!con){
            System.out.println("Sorry, cannot connect to database");
            System.exit(-1);
        }

        //login
        while (true){
            System.out.println("Enter ID (if you want to quit, just enter 'q'):");
            try {
                id=buffer.readLine();
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
            if (id.equals("q")){
            	System.out.println("Bye~");
            	System.exit(0);                
            }
            System.out.println("Enter password:");
            try {
                pass=buffer.readLine();
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }

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
