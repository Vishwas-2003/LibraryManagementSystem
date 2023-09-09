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


public class Welcome extends JFrame implements ActionListener{
    CustomLabel wel,or;
    CustomButton log,regis;
    CustomJPanel jp,jpin; 

    public Welcome(){
        super("Welcome");
        setBounds(500, 250, 500, 200);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(GUI.icon);
        getContentPane().setBackground(GUI.background);


        wel=new CustomLabel("Welcome!",CustomLabel.CENTER,50,Font.ITALIC);
        or=new CustomLabel("                or                ",CustomLabel.CENTER,15,Font.ITALIC);

        log=new CustomButton("LOGIN");
        regis=new CustomButton("REGISTER");
        
        
        jp=new CustomJPanel();
        
        jpin=new CustomJPanel();

        jpin.add(log);
        jpin.add(or);
        jpin.add(regis);
        jpin.setLayout(new FlowLayout());

        jp.add(jpin);
        add(wel,BorderLayout.NORTH);
        add(jp,BorderLayout.CENTER);

        log.addActionListener(this);
        regis.addActionListener(this);

    }

    /** 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==log){
            GUI.loginFrame.setVisible(true);
            setVisible(false);
            dispose();
            
        }
        else{
            GUI.registerFrame.setVisible(true);
            setVisible(false);
            dispose();
        }
    }
    
}

