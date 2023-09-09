package com.vishwas.librarymanagementsystem;

import java.awt.*;
import javax.swing.*;

public class GUI{
    static Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\hp\\OneDrive\\Documents\\NetBeansProjects\\LibraryManagementSystem\\bin\\src\\main\\java\\com\\vishwas\\librarymanagementsystem\\icon.jpeg");
        
    public static Color button=new Color(238, 238, 238);
    public static Color background=new Color(212, 173, 252);
    public static Color text=new Color(92, 70, 156);


    static Welcome welcomeFrame=new Welcome();
    static Login loginFrame=new Login();
    static Register registerFrame=new Register();    
}

class CustomTextField extends JTextField{
    CustomTextField(){
        setForeground(GUI.text);
    }
    CustomTextField(int length){
        super(length);
        setForeground(GUI.text);
    }
}

class CustomButton extends JButton{
    CustomButton(String text){
        super(text);
        setBackground(GUI.button);
        setForeground(GUI.text);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}

class CustomLabel extends JLabel{
    CustomLabel(){
        setForeground(GUI.text);
    }
    CustomLabel(String text){
        super(text);
        setForeground(GUI.text);
    }
    CustomLabel(String text,int horizontalAlignment){
        super(text,horizontalAlignment);
        setForeground(GUI.text);
    }
    CustomLabel(String text,int horizontalAlignment,int size){
        super(text,horizontalAlignment);
        setFont(new Font("Times New Roman", Font.BOLD, size));
        setForeground(GUI.text);
    }
    CustomLabel(String text,int horizontalAlignment,int size,int font){
        super(text,horizontalAlignment);
        setFont(new Font("Times New Roman", font, size));
        setForeground(GUI.text);
    }
}

class CustomJPanel extends JPanel{
    CustomJPanel(){
        this.setBackground(GUI.background);
    }
    CustomJPanel(LayoutManager layout){
        super(layout);
        this.setBackground(GUI.background);

    }
    CustomJPanel(LayoutManager layout,int flag){
        super(layout);
        this.setBackground(GUI.button);

    }
}
