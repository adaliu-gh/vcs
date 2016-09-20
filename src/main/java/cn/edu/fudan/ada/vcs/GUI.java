package cn.edu.fudan.ada.vcs;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class GUI{
    String url="jdbc:mysql://localhost/vcs?useSSL=no";
    String user="root";
    String password="";
    JTextField idText;
    JTextField passText;
    Label warning;
    public void createGUI(){
        JFrame frame =new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel =new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
       

        Box textBox=new Box(BoxLayout.Y_AXIS);
        Box buttonBox=new Box(BoxLayout.X_AXIS);

        JButton login=new JButton("Login");
        login.addActionListener(new LoginListener());
        buttonBox.add(login);

        JButton quit=new JButton("Quit");
        quit.addActionListener(new QuitListener());
        buttonBox.add(quit);

        idText=new JTextField(30);
        passText=new JTextField(30);
     
        textBox.add(idText);
        textBox.add(passText);
        warning=new Label("");
        panel.add(warning);
        
        boolean con=Database.connect(url,user,password);
        if (!con){
            warning.setText("Sorry, cannot connect to database");
            
            frame.getContentPane().add(panel);
            System.exit(-1);
        }
        warning.setText("Welcome to Course System ^_^ ~");
        panel.add(textBox);
        panel.add(buttonBox);
        frame.getContentPane().add(panel);
        frame.setSize(300,300);
        frame.pack();
        frame.setVisible(true);
    }

    public class LoginListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            String idString=idText.getText();
            String passString=passText.getText();
            String identity=Database.checkIdentity(idString,passString);

            switch (identity){
            case "f":
                warning.setText("Invalid ID & Pass");
                break;
            case "s":
                Student stu=new Student(idString,passString);
                stu.enterSystem();
                break;
            case "t":
                Instructor ins=new Instructor(idString,passString);
                ins.enterSystem();
                break;
            case "a":
                Administrator admin=new Administrator(idString,passString);
                admin.enterSystem();
        }
    }
}
    public class QuitListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
        	warning.setText("BYE~");
        	System.exit(0);
        }
    }
}