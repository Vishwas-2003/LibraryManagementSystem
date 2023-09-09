/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.vishwas.librarymanagementsystem;

/**
 *
 * @author Vishwas
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;




public class Login extends JFrame implements ActionListener{
    public String username;
    public String password;

    CustomLabel user,pass,logTxt;
    CustomTextField userTxt;
    JPasswordField passTxt;
    CustomButton loginBtn,resetBtn,backBtn;

    CustomJPanel form,main;

    public Login(){
        super("Login");
        setBounds(500, 250, 400, 300);
        setResizable(false);
        setIconImage(GUI.icon);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        main=new CustomJPanel();

        form=new CustomJPanel();
        Border b=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"ENTER DETAILS");
        form.setBorder(b);
        form.setLayout(new GridLayout(4, 2, 20, 20));

        user=new CustomLabel("USERNAME",CustomLabel.LEFT);
        pass=new CustomLabel("PASSWORD",CustomLabel.LEFT);
        logTxt = new CustomLabel("ENTER YOUR LOGIN DETAILS",SwingConstants.CENTER,20 );

        userTxt=new CustomTextField(15);
        passTxt=new JPasswordField(15);

        loginBtn=new CustomButton("LOGIN");
        resetBtn=new CustomButton("RESET");
        backBtn=new CustomButton("BACK");

        form.add(user);
        form.add(userTxt);
        form.add(pass);
        form.add(passTxt);
        form.add(loginBtn);
        form.add(resetBtn);
        form.add(backBtn);

        main.add(form);
        
        add(logTxt,BorderLayout.NORTH);
        add(main,BorderLayout.CENTER);

        loginBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        backBtn.addActionListener(this);
    }

    void reset(){
        userTxt.setText("");
        passTxt.setText("");
    }

    
    /** 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==loginBtn){
            username=(String)(userTxt.getText());
            password=String.valueOf(passTxt.getPassword());
            try{
                int ifLogged=new Database_lms().login(username,password);

                if(ifLogged==1){
                    new DashboardIssuer(username).setVisible(true);
                    setVisible(false);
                    dispose();
                }
                else if(ifLogged==2){
                    new DashboardLibrarian(username).setVisible(true);
                    setVisible(false);
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Bad Credentials");
                }
            }
            catch(Exception ee){}
        }
        else if(e.getSource()==resetBtn){
            reset();
        }
        else{
            GUI.welcomeFrame.setVisible(true);
            setVisible(false);
            dispose();
        }
        reset();

    }
    
}

