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

public class Register extends JFrame implements ActionListener{
    public String nameS,mobileS,emailS,roleS,empS="",usernameS,passwordS;

    CustomJPanel main,regisForm;

    CustomLabel registxt,name,mobile,email,role,empId,userName,pass,rePass;
    CustomTextField nameF,mobileF,emailF,empF,userF;
    JPasswordField passF,rePassF;

    JComboBox<String> roleF;

    CustomButton register,back,reset;

    Database_lms db;


    public Register(){
        super("Register");
        setBounds(500, 250, 550, 480);
        setResizable(false);
        setIconImage(GUI.icon);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        String roles[]={"Librarian","Issuer"};

        main=new CustomJPanel();

        registxt=new CustomLabel("ENTER YOUR REGISTRATION DETAILS",CustomLabel.CENTER,25);

        name=new CustomLabel("Name");
        mobile=new CustomLabel("Mobile No.");
        email=new CustomLabel("Email Id");
        role=new CustomLabel("Role");
        empId = new CustomLabel("Emp. ID");
        userName=new CustomLabel("User Name");
        pass=new CustomLabel("Password");
        rePass=new CustomLabel("Re-Enter Password");

        nameF=new CustomTextField(15);
        mobileF=new CustomTextField(15);
        emailF=new CustomTextField(15);
        empF=new CustomTextField();
        userF=new CustomTextField(15);
        passF=new JPasswordField(15);
        rePassF=new JPasswordField(15);

        roleF=new JComboBox<String>(roles); 
        roleF.setSelectedIndex(-1);
        roleF.setForeground(GUI.text);

        register=new CustomButton("REGISTER");
        reset=new CustomButton("RESET");
        back=new CustomButton("BACK");


        
        Border b=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"ENTER DETAILS");
        regisForm=new CustomJPanel(new GridLayout(10, 2, 10, 10));
        regisForm.setBorder(b);

        regisForm.add(name);regisForm.add(nameF);
        regisForm.add(mobile);regisForm.add(mobileF);
        regisForm.add(email);regisForm.add(emailF);
        regisForm.add(role);regisForm.add(roleF);
        regisForm.add(empId);regisForm.add(empF);
        regisForm.add(userName);regisForm.add(userF);
        regisForm.add(pass);regisForm.add(passF);
        regisForm.add(rePass);regisForm.add(rePassF);
        regisForm.add(register);regisForm.add(reset);
        regisForm.add(back) ;


        main.add(regisForm);

        add(registxt,BorderLayout.NORTH);
        add(main,BorderLayout.CENTER);

        register.addActionListener(this);
        reset.addActionListener(this);
        back.addActionListener(this);

    }

    void reset(){
        nameF.setText("");
        mobileF.setText("");
        emailF.setText("");
        empF.setText("");
        userF.setText("");
        passF.setText("");
        rePassF.setText("");
        roleF.setSelectedIndex(-1);
    }


    /** 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==register){
            
            if(String.valueOf(passF.getPassword()).equals(String.valueOf(rePassF.getPassword()))){
                //database insertion
                nameS=nameF.getText().toString();
                mobileS =mobileF.getText().toString();
                emailS =emailF.getText().toString();
                empS =empF.getText().toString();
                usernameS =userF.getText().toString();
                passwordS=String.valueOf(passF.getPassword());
                roleS=(String)roleF.getSelectedItem();

                try{
                    int done=-1;
                    if(roleS==null){
                        JOptionPane.showMessageDialog(null,"Enter a role","",JOptionPane.ERROR_MESSAGE);
                    }
                    if(!nameS.equals("") && !mobileS.equals("") && !emailS.equals("")){
                        db=new Database_lms();
                        done=db.register(usernameS,nameS,mobileS,emailS,roleS,empS,passwordS);
                    }
                    if(done==1){
                        GUI.welcomeFrame.setVisible(true);
                        setVisible(false);
                        dispose();
                        reset();
                        JOptionPane.showMessageDialog(null,"Registered Successfully!!");
                    }
                    else if(done==0){
                        JOptionPane.showMessageDialog(null,"UserName Exists!!","INVALID",JOptionPane.ERROR_MESSAGE);
                        userF.setText("");
                    }
                    else if(done==-1){
                        JOptionPane.showMessageDialog(null,"All fields are mandatory.","INVALID",JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Enter a valid Emp. ID","INVALID",JOptionPane.ERROR_MESSAGE);
                        empF.setText("");
                    }
                }
                catch(Exception ee){}
                

            }
            else{
                JOptionPane.showMessageDialog(null,"Passwords do not match");
                passF.setText("");
                rePassF.setText("");
            }
        }
        else if(e.getSource()==reset){
            reset();
        }
        else{
            GUI.welcomeFrame.setVisible(true);
            setVisible(false);
            dispose();
        }
    }
    
}
