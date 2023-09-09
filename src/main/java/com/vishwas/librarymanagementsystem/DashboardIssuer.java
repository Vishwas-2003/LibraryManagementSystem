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
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.Border;

public class DashboardIssuer extends JFrame implements ActionListener{
    //database
    Connection con,con1;
    Statement stmt,stmt1;


    //dashboard labels
    CustomLabel dashboard,bookIssue,finePaid,dateTime;
    

    //profile
    CustomLabel name,mobile,email,role,usernameLabel,nameVal,mobileVal,emailVal,roleVal,usernameVal;
    String nameS,mobileS,emailS,roleS,fineS;
    CustomJPanel profileMain;
    CustomButton logout;
    
    //My Books
    String b_id[]=new String[10];
    String b_name[]=new String[10];
    String b_publisher[]=new String[10];
    String b_author[]=new String[10];
    String b_edition[]=new String[10];
    String b_date[]=new String[10];

    CustomLabel sNo[]=new CustomLabel[10];
    CustomLabel bId[]=new CustomLabel[10];
    CustomLabel bName[]=new CustomLabel[10];
    CustomLabel bPub[]=new CustomLabel[10];
    CustomLabel bAuth[]=new CustomLabel[10];
    CustomLabel bEdit[]=new CustomLabel[10];
    CustomLabel bDate[]=new CustomLabel[10];
    CustomLabel bSubmit=new CustomLabel();

    CustomButton bSubmitButton[]=new CustomButton[9];

    //Issue
    String b_id_avail[]=new String[100];
    String b_name_avail[]=new String[100];
    String b_publisher_avail[]=new String[100];
    String b_author_avail[]=new String[100];
    String b_edition_avail[]=new String[100];

    CustomLabel sNoAvail[]=new CustomLabel[100];
    CustomLabel bNameAvail[]=new CustomLabel[100];
    CustomLabel bPubAvail[]=new CustomLabel[100];
    CustomLabel bAuthAvail[]=new CustomLabel[100];
    CustomLabel bEditAvail[]=new CustomLabel[100];
    CustomLabel bRequest=new CustomLabel();

    CustomButton bRequestButton[]=new CustomButton[100];

    //fine
    CustomJPanel payMain,pay;   
    CustomLabel fineLabel,fineValueLabel;
    CustomButton payBtn;
    
    
    JTabbedPane dashboardPane;
    CustomJPanel dash,dashDetails,prof,profMain,issue,issueInternal,issueMain,book,bookInternal,bookMain;

    Border b=BorderFactory.createLoweredBevelBorder();

    String username;
    
    int count=0,countBook=0,issueIndex=0;
    String fine="",totalBooks="";

    LocalDate dt=LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd YYYY");
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    String date=formatter.format(dt);
    String dateSQL=formatter1.format(dt);

    DashboardIssuer(String username)throws Exception{
        super("Dashboard");
        setResizable(true);
        setIconImage(GUI.icon);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000, 500));
        roleS="Issuer";
        this.username = username;
        
        database();
        
        dashboard();

        profile();
        
        issue();
        
        books();

        payFine();
        
        //main tabbed panel
        dashboardPane=new JTabbedPane();
        dashboardPane.addTab("DASHBOARD",dash);
        dashboardPane.addTab("PROFILE",profMain);
        dashboardPane.addTab("ISSUE NEW BOOK",issue);
        dashboardPane.addTab("MY BOOKS",book);
        dashboardPane.addTab("Pay Fine",payMain);

        dashboardPane.setBackground(GUI.button);
        dashboardPane.setForeground(GUI.text);

        dashboardPane.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //maine panel
        add(new JScrollPane(dashboardPane));

    }

    void updateGUI(int index){
        //update dashboard
        dashboard();
        
        //update fine
        payFine();

        dashboardPane.removeAll();
        dashboardPane.addTab("DASHBOARD",dash);
        dashboardPane.addTab("PROFILE",profMain);
        dashboardPane.addTab("ISSUE NEW BOOK",issue);
        dashboardPane.addTab("MY BOOKS",book);
        dashboardPane.addTab("Pay Fine",payMain);

        dashboardPane.setSelectedIndex(index);

    }

    void database(){
        try{
            //database connection
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms","root","vishwas");
            stmt=con.createStatement();

            ResultSet rs=stmt.executeQuery("select * from user where user_name='"+username+"'");
            while(rs.next()){
                nameS=rs.getString(2);
                mobileS=rs.getString(3);
                emailS=rs.getString(4);
                fineS=rs.getString(8);
            }
            ResultSet rs1=stmt.executeQuery("select b.b_id,b_name,b_publisher,b_author,b_edition,issue_date from book as b,issue as i where b.b_id=i.b_id and user_name='"+username+"'");
            while(rs1.next()){
                b_id[count]=rs1.getString(1);
                b_name[count] = rs1.getString(2);
                b_publisher[count]=rs1.getString(3);
                b_author[count]=rs1.getString(4);
                b_edition[count]=rs1.getString(5);
                b_date[count]=rs1.getString(6);
                count++;
            }
            ResultSet rs2=stmt.executeQuery("select sum(fine) from user where user_name='"+username+"'");
            while(rs2.next()){
                fine=rs2.getString(1);
            }
            ResultSet rs3=stmt.executeQuery("select count(b_id) from issue as i,user as u where u.user_name=i.user_name and i.user_name='"+username+"'");
            while(rs3.next()){
                totalBooks=rs3.getString(1);
            }
            ResultSet rs4=stmt.executeQuery("select * from book as b where b.b_id not in (Select i.b_id from issue as i)");
            while(rs4.next()){
                b_id_avail[countBook] = rs4.getString(1);
                b_name_avail[countBook] = rs4.getString(2);
                b_publisher_avail[countBook]=rs4.getString(3);
                b_author_avail[countBook]=rs4.getString(4);
                b_edition_avail[countBook]=rs4.getString(5);
                countBook++;
            }
        }
        catch(Exception e){System.out.println(e);}

    }

    
    void dashboard(){
        //dashboard items
        dashboard=new CustomLabel("My Dashboard", CustomLabel.LEFT,80,Font.ITALIC);
        bookIssue=new CustomLabel("Total Books Issued:"+totalBooks, CustomLabel.CENTER,35);
        finePaid=new CustomLabel("Total Fine Due:"+fine, CustomLabel.CENTER,35);
        
        dateTime=new CustomLabel(date,CustomLabel.CENTER);

        //dashboard inside details
        dashDetails=new CustomJPanel(new BorderLayout(0, 50));
        dashDetails.setBorder(b);
        dashDetails.setBounds(50, 50, 1000, HEIGHT);
        dashDetails.add(bookIssue,BorderLayout.CENTER);
        dashDetails.add(finePaid,BorderLayout.SOUTH);

        //dashboard container
        dash=new CustomJPanel();
        dash.setLayout(new GridLayout(3,1,10,10));
        dash.add(dashboard);
        dash.add(dashDetails);
        dash.add(dateTime);
    }
    
    void profile(){
        //profile container
        profMain=new CustomJPanel();
        prof=new CustomJPanel(new GridLayout(6, 2, 100,60));
        prof.setBorder(b);
        
        name=new CustomLabel("Name",CustomLabel.LEFT,40);
        nameVal=new CustomLabel(nameS,CustomLabel.LEFT,40);
        mobile=new CustomLabel("Phone No.",CustomLabel.LEFT,40);
        mobileVal=new CustomLabel(mobileS,CustomLabel.LEFT,40);
        email = new CustomLabel("E-mail ID",CustomLabel.LEFT ,40);
        emailVal = new CustomLabel(emailS,CustomLabel.LEFT ,40);
        role = new CustomLabel("Role",CustomLabel.LEFT ,40);
        roleVal = new CustomLabel(roleS,CustomLabel.LEFT ,40);
        usernameLabel = new CustomLabel("Username",CustomLabel.LEFT ,40);
        usernameVal = new CustomLabel(username,CustomLabel.LEFT ,40);

        //logout button
        logout=new CustomButton("LOGOUT");
        logout.addActionListener(this);
        
        //set font color
        name.setForeground(Color.BLUE);
        mobile.setForeground(Color.BLUE);
        email.setForeground(Color.BLUE);
        role.setForeground(Color.BLUE);
        usernameLabel.setForeground(Color.BLUE);
        
        prof.add(usernameLabel);
        prof.add(usernameVal);
        prof.add(name);
        prof.add(nameVal);
        prof.add(mobile);
        prof.add(mobileVal);
        prof.add(email);
        prof.add(emailVal);
        prof.add(role);
        prof.add(roleVal);
        prof.add(logout);
        
        profMain.add(new JScrollPane(prof));
    }

    void issue(){
        //issue book container
        issue=new CustomJPanel();
        issueInternal=new CustomJPanel(new GridLayout(2, 1, 10, 10));

        issueMain=new CustomJPanel(new GridLayout(countBook+1,6, 5,5),1);
        issueMain.setBorder(b);

        CustomLabel Avail=new CustomLabel("Available Books",CustomLabel.CENTER,40);


        //Table Headings
        sNoAvail[0]=new CustomLabel("S.No.",CustomLabel.LEFT,20);
        bNameAvail[0]=new CustomLabel("Book Name",CustomLabel.LEFT,20);
        bPubAvail[0]=new CustomLabel("Publisher Name",CustomLabel.LEFT,20);
        bAuthAvail[0]=new CustomLabel("Author's Name",CustomLabel.LEFT,20);
        bEditAvail[0]=new CustomLabel("Edition Year",CustomLabel.LEFT,20);
        bRequest=new CustomLabel("Request Book",CustomLabel.LEFT,20);

        
        issueMain.add(sNoAvail[0]);
        issueMain.add(bNameAvail[0]);
        issueMain.add(bPubAvail[0]);
        issueMain.add(bAuthAvail[0]);
        issueMain.add(bEditAvail[0]);
        issueMain.add(bRequest);

        for(int i=0;i<countBook;i++){
            sNoAvail[i]=new CustomLabel(String.valueOf(i+1),CustomLabel.LEFT,15);
            bNameAvail[i]=new CustomLabel(b_name_avail[i],CustomLabel.LEFT,15);
            bPubAvail[i]=new CustomLabel(b_publisher_avail[i],CustomLabel.LEFT,15);
            bAuthAvail[i]=new CustomLabel(b_author_avail[i],CustomLabel.LEFT,15);
            bEditAvail[i]=new CustomLabel(b_edition_avail[i],CustomLabel.LEFT,15);
            bRequestButton[i]=new CustomButton("REQUEST");
            bRequestButton[i].setBackground(Color.YELLOW);
            bRequestButton[i].setForeground(Color.BLACK);

            bRequestButton[i].addActionListener(this);

            issueMain.add(sNoAvail[i]);
            issueMain.add(bNameAvail[i]);
            issueMain.add(bPubAvail[i]);
            issueMain.add(bAuthAvail[i]);
            issueMain.add(bEditAvail[i]);
            issueMain.add(bRequestButton[i]);
        }


        issueInternal.add(Avail,BorderLayout.NORTH);
        issueInternal.add(issueMain,BorderLayout.SOUTH);
        
        issue.add(issueInternal);
    }


    void books(){//my books container
        book=new CustomJPanel();
    
        bookInternal=new CustomJPanel(new GridLayout(2, 1, 10, 10));

        bookMain=new CustomJPanel(new GridLayout(count+1, 6, 5, 5),1);
        bookMain.setBorder(b);

        CustomLabel myBook=new CustomLabel("Issued Books",CustomLabel.CENTER);
        myBook.setFont(new Font("Times New Roman",Font.BOLD, 40));

        //Table Headings
        sNo[0]=new CustomLabel("S.No.",CustomLabel.LEFT,20);
        bId[0]=new CustomLabel("Book ID",CustomLabel.LEFT,20);
        bName[0]=new CustomLabel("Book Name",CustomLabel.LEFT,20);
        bPub[0]=new CustomLabel("Publisher Name",CustomLabel.LEFT,20);
        bAuth[0]=new CustomLabel("Author's Name",CustomLabel.LEFT,20);
        bEdit[0]=new CustomLabel("Edition Year",CustomLabel.LEFT,20);
        bDate[0]=new CustomLabel("Issued Date",CustomLabel.LEFT,20);
        bSubmit=new CustomLabel("Submit Request",CustomLabel.LEFT,20);

        //adding table headings
        bookMain.add(sNo[0]);
        bookMain.add(bId[0]);
        bookMain.add(bName[0]);
        bookMain.add(bPub[0]);
        bookMain.add(bAuth[0]);
        bookMain.add(bEdit[0]);
        bookMain.add(bDate[0]);
        bookMain.add(bSubmit);


        //adding data
        for(int i=0;i<count;i++){
            sNo[i]=new CustomLabel(String.valueOf(i+1),CustomLabel.LEFT,15);
            bId[i]=new CustomLabel(b_id[i],CustomLabel.LEFT,15);
            bName[i]=new CustomLabel(b_name[i],CustomLabel.LEFT,15);
            bPub[i]=new CustomLabel(b_publisher[i],CustomLabel.LEFT,15);
            bAuth[i]=new CustomLabel(b_author[i],CustomLabel.LEFT,15);
            bEdit[i]=new CustomLabel(b_edition[i],CustomLabel.LEFT,15);
            bDate[i]=new CustomLabel(b_date[i],CustomLabel.LEFT,15);
            bSubmitButton[i]=new CustomButton("SUBMIT BOOK");
            bSubmitButton[i].setBackground(Color.BLUE);
            bSubmitButton[i].setForeground(Color.WHITE);

            bSubmitButton[i].addActionListener(this);

            bookMain.add(sNo[i]);
            bookMain.add(bId[i]);
            bookMain.add(bName[i]);
            bookMain.add(bPub[i]);
            bookMain.add(bAuth[i]);
            bookMain.add(bEdit[i]);
            bookMain.add(bDate[i]);
            bookMain.add(bSubmitButton[i]);
            
        }

        bookInternal.add(myBook);
        bookInternal.add(bookMain);

        book.add(bookInternal);
    }

    void payFine(){
        //pyaFine container
        payMain=new CustomJPanel();
        pay=new CustomJPanel(new GridLayout(2, 2, 100,60),1);
        pay.setBorder(b);
        
        fineLabel=new CustomLabel("Total fine:",CustomLabel.LEFT,40);
        fineValueLabel=new CustomLabel(fineS,CustomLabel.LEFT,40);

        //logout button
        payBtn=new CustomButton("PAY");
        payBtn.setBackground(Color.BLUE);
        payBtn.setForeground(Color.WHITE);
        payBtn.addActionListener(this);
        
        
        //set font color
        fineLabel.setForeground(Color.BLUE);
        
        pay.add(fineLabel);
        pay.add(fineValueLabel);
        pay.add(payBtn);
        
        payMain.add(new JScrollPane(pay));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==logout){
            int confirm=JOptionPane.showOptionDialog(this, "LOGOUT", "Select an Option",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,null,1);
            if(confirm==0){
                GUI.loginFrame.setVisible(true);
                setVisible(false);
                dispose();
                JOptionPane.showMessageDialog(null,"Logged Out Successfully.");
            }
        }
       

        //for request
        for(int i=0;i<countBook;i++){
            if(e.getSource()==bRequestButton[i]){
                String insert="insert into issue_request values('"+b_id_avail[i]+"','"+username+"')";
                try{
                    stmt.executeUpdate(insert);
                    bRequestButton[i].setText("REQUESTED");
                }
                catch(Exception SQLIntegrityConstraintViolationException){
                    JOptionPane.showMessageDialog(null,"Already Requested");
                    bRequestButton[i].setText("REQUESTED");
                }
                // catch(Exception ee){System.out.println(ee);}
            }
        }

        //for submit request
        for(int i=0;i<count;i++){
            if(e.getSource()==bSubmitButton[i]){
                String insert="insert into submit_request values('"+b_id[i]+"','"+username+"','"+dateSQL+"')";
                try{
                    stmt.executeUpdate(insert);
                    bSubmitButton[i].setText("REQUESTED");
                }
                catch(Exception SQLIntegrityConstraintViolationException){
                    JOptionPane.showMessageDialog(null,"Already Requested");
                    bSubmitButton[i].setText("REQUESTED");
                }
                // catch(Exception ee){System.out.println(ee);}
            }
        }

        if(e.getSource()==payBtn){
            if(!fineValueLabel.getText().equals("0")){
                int confirm=JOptionPane.showOptionDialog(this, "PAY", "Select an Option",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,null,1);
                String update="update user set fine=0 where user_name='"+username+"'";
                try{
                    if(confirm==0){
                        stmt.executeUpdate(update);

                        //to update whole data
                        this.database();
                        updateGUI(dashboardPane.getSelectedIndex());

                        JOptionPane.showMessageDialog(null,"Fine Paid");
                    }
                }
                catch(Exception ee){System.out.println(ee);}
            }
            else{
                JOptionPane.showMessageDialog(null,"No Fine");
            }

        }
    
    }
    
}


