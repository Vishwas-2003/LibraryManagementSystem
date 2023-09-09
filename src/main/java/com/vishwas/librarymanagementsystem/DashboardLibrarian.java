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
import javax.swing.border.TitledBorder;

public class DashboardLibrarian extends JFrame implements ActionListener{
    //database
    Connection con,con1;
    Statement stmt,stmt1;


    //dashboard labels
    CustomLabel dashboard,bookIssue,finePaid,dateTime;

    //profile
    CustomLabel name,mobile,email,role,usernameLabel,nameVal,mobileVal,emailVal,roleVal,usernameVal;
    String nameS,mobileS,emailS,roleS;
    CustomJPanel profileMain;
    CustomButton logout;


    //Books
    String b_id[]=new String[100];
    String b_name[]=new String[100];
    String b_publisher[]=new String[100];
    String b_author[]=new String[100];
    String b_edition[]=new String[100];
    String b_status[]=new String[100];

    CustomLabel sNo[]=new CustomLabel[100];
    CustomLabel bId[]=new CustomLabel[100];
    CustomLabel bName[]=new CustomLabel[100];
    CustomLabel bPub[]=new CustomLabel[100];
    CustomLabel bAuth[]=new CustomLabel[100];
    CustomLabel bEdit[]=new CustomLabel[100];
    CustomLabel bStatus[]=new CustomLabel[100];
    CustomLabel bDelete=new CustomLabel();

    CustomButton deleteBook[]=new CustomButton[100];


    //issuer
    String issuerName[]=new String[100];
    String issuerMobile[]=new String[100];
    String issuerEmail[]=new String[100];
    String issuerFine[]=new String[100];

    CustomLabel iSno[]=new CustomLabel[100];
    CustomLabel iName[]=new CustomLabel[100];
    CustomLabel iMobile[]=new CustomLabel[100];
    CustomLabel iEmail[]=new CustomLabel[100];
    CustomLabel iFine[]=new CustomLabel[100];

    //issueRequest
    String iB_id[]=new String[100];
    String iB_name[]=new String[100];
    String iB_author[]=new String[100];

    String requesterName[]=new String[100];
    String requesterMobile[]=new String[100];
    String requesterEmail[]=new String[100];
    String requesterFine[]=new String[100];
    String requesterUserName[]=new String[100];

    CustomLabel iS_no[]=new CustomLabel[100];
    CustomLabel iB_Id[]=new CustomLabel[100];
    CustomLabel iB_Name[]=new CustomLabel[100];
    CustomLabel iB_Auth[]=new CustomLabel[100];

    CustomLabel iRequesterName[]=new CustomLabel[100];
    CustomLabel iRequesterMobile[]=new CustomLabel[100];
    CustomLabel iRequesterEmail[]=new CustomLabel[100];
    CustomLabel iRequesterFine[]=new CustomLabel[100];
    CustomLabel iApproveLabel=new CustomLabel();
    CustomLabel iRejectLabel=new CustomLabel();

    CustomButton iApprove[]=new CustomButton[100];
    CustomButton iReject[]=new CustomButton[100];

    //issueRequest
    String sB_id[]=new String[100];
    String sB_name[]=new String[100];
    String sB_date[]=new String[100];

    String submitterName[]=new String[100];
    String submitterMobile[]=new String[100];
    String submitterEmail[]=new String[100];
    String submitterFine[]=new String[100];
    String submitterUserName[]=new String[100];

    CustomLabel sS_no[]=new CustomLabel[100];
    CustomLabel sB_Id[]=new CustomLabel[100];
    CustomLabel sB_Name[]=new CustomLabel[100];
    CustomLabel sB_Date[]=new CustomLabel[100];

    CustomLabel iSubmitterName[]=new CustomLabel[100];
    CustomLabel iSubmitterMobile[]=new CustomLabel[100];
    CustomLabel iSubmitterEmail[]=new CustomLabel[100];
    CustomLabel iSubmitterFine[]=new CustomLabel[100];
    CustomLabel sApproveLabel=new CustomLabel();
    CustomLabel sRejectLabel=new CustomLabel();

    CustomButton sApprove[]=new CustomButton[100];
    CustomButton sReject[]=new CustomButton[100];

    //add book
    CustomLabel addBookLabel,bookID,bookName,bookPublisher,bookAuthor,bookEdition;
    JTextField bookIDF,bookNameF,bookPublisherF,bookAuthorF,bookEditionF;
    CustomButton addBookBtn,addBookRstBtn;
    CustomJPanel addBookMain,addBookInternal,addBookMainI;



    JTabbedPane dashboardPane;
    CustomJPanel dash,dashDetails,prof,profMain,books,booksInternal,booksMain,addBook,issuers,issuersInternal,issuersMain;
    CustomJPanel issueRequest,issueRequestInternal,issueRequestMain,submitRequest,submitRequestInternal,submitRequestMain;

    Border b=BorderFactory.createLoweredBevelBorder();

    String username;
    
    String totalAvailBooks="",totalBooks="";
    int count=0,countIssuers=0,countIssueRequest=0,countSubmitRequest=0,fine=0;

    LocalDate dt=LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd YYYY");
    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    String date=formatter.format(dt);
    String dateSQL=formatter1.format(dt);

    DashboardLibrarian(String username) throws Exception{
        super("Dashboard(Librarian)");
        setResizable(true);
        setIconImage(GUI.icon);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000, 500));
        roleS="Librarian";

        this.username = username;
        

        Database();

        dashboard();

        profile();
        
        book();

        addBook();

        issuer();

        issuerequest();

        submitrequest();

        //main tabbed panel
        dashboardPane=new JTabbedPane();
        dashboardPane.addTab("DASHBOARD",dash);
        dashboardPane.addTab("PROFILE",profMain);
        dashboardPane.addTab("BOOKS",books);
        dashboardPane.addTab("ADD BOOK",addBook);
        dashboardPane.addTab("ISSUERS",issuers);
        dashboardPane.addTab("ISSUE REQUESTS",issueRequest);
        dashboardPane.addTab("SUBMIT REQUESTS",submitRequest);

        dashboardPane.setBackground(GUI.button);
        dashboardPane.setForeground(GUI.text);
        
        dashboardPane.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //main panel
        add(new JScrollPane(dashboardPane));
    }


    void updateGUI(int index){
        //update dashboard
        dashboard();
        
        //update book
        book();

        //issuer update
        issuer();

        dashboardPane.removeAll();
        dashboardPane.addTab("DASHBOARD",dash);
        dashboardPane.addTab("PROFILE",profMain);
        dashboardPane.addTab("BOOKS",books);
        dashboardPane.addTab("ADD BOOK",addBook);
        dashboardPane.addTab("ISSUERS",issuers);
        dashboardPane.addTab("ISSUE REQUESTS",issueRequest);
        dashboardPane.addTab("SUBMIT REQUESTS",submitRequest);

        dashboardPane.setSelectedIndex(index);
    }


    void Database(){
        count=0;
        countIssuers=0;

        try{
            //database connection
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/library_ms","root","vishwas");
            stmt=con.createStatement();

            ResultSet rs=stmt.executeQuery("select * from user where user_name='"+username+"'");
            while(rs.next()){
                nameS=rs.getString(2);
                mobileS=rs.getString(3);
                emailS=rs.getString(4);
            }
            ResultSet rs1=stmt.executeQuery("select * from book as b");
            while(rs1.next()){
                b_id[count]=rs1.getString(1);
                b_name[count] = rs1.getString(2);
                b_publisher[count]=rs1.getString(3);
                b_author[count]=rs1.getString(4);
                b_edition[count]=rs1.getString(5);
                count++;
            }
            ResultSet rs2=stmt.executeQuery("select count(b_id) from book as b where b_id not in (select b_id from issue)");
            while(rs2.next()){
                totalAvailBooks=rs2.getString(1);
            }
            ResultSet rs3=stmt.executeQuery("select count(b_id) from book");
            while(rs3.next()){
                totalBooks=rs3.getString(1);
            }
            ResultSet rs4=stmt.executeQuery("select name,mobile,email,fine from user where emp_id=''");
            while(rs4.next()){
                issuerName[countIssuers] = rs4.getString(1);
                issuerMobile[countIssuers]=rs4.getString(2);
                issuerEmail[countIssuers]=rs4.getString(3);
                issuerFine[countIssuers]=rs4.getString(4);
                countIssuers++;
            }
            ResultSet rs5=stmt.executeQuery("select i.b_id,b_name,b_author,name,mobile,email,fine,u.user_name from issue_request as i,user as u,book as b where b.b_id=i.b_id and i.user_name=u.user_name");
            while(rs5.next()){
                iB_id[countIssueRequest] = rs5.getString(1);
                iB_name[countIssueRequest] = rs5.getString(2);
                iB_author[countIssueRequest]=rs5.getString(3);
                requesterName[countIssueRequest]=rs5.getString(4);
                requesterMobile[countIssueRequest]=rs5.getString(5);
                requesterEmail[countIssueRequest]=rs5.getString(6);
                requesterFine[countIssueRequest]=rs5.getString(7);
                requesterUserName[countIssueRequest]=rs5.getString(8);
                countIssueRequest++;
            }
            ResultSet rs6=stmt.executeQuery("select i.b_id,b_name,submit_date,name,mobile,email,fine,u.user_name from submit_request as i,user as u,book as b where b.b_id=i.b_id and i.user_name=u.user_name");
            while(rs6.next()){
                sB_id[countSubmitRequest] = rs6.getString(1);
                sB_name[countSubmitRequest] = rs6.getString(2);
                sB_date[countSubmitRequest]=rs6.getString(3);
                submitterName[countSubmitRequest]=rs6.getString(4);
                submitterMobile[countSubmitRequest]=rs6.getString(5);
                submitterEmail[countSubmitRequest]=rs6.getString(6);
                submitterFine[countSubmitRequest]=rs6.getString(7);
                submitterUserName[countSubmitRequest]=rs6.getString(8);
                countSubmitRequest++;
            }
        }
        catch(Exception e){System.out.println(e);}
    }



    void dashboard(){
        //dashboard items
        dashboard=new CustomLabel("My Dashboard", CustomLabel.LEFT,80,Font.ITALIC);
        bookIssue=new CustomLabel("Total Available Books:"+totalAvailBooks, CustomLabel.CENTER,35);
        finePaid=new CustomLabel("Total Books:"+totalBooks, CustomLabel.CENTER,35);

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
        emailVal = new CustomLabel(emailS,CustomLabel.LEFT,40 );
        role = new CustomLabel("Role",CustomLabel.LEFT ,40);
        roleVal = new CustomLabel(roleS,CustomLabel.LEFT ,40);
        usernameLabel = new CustomLabel("Username",CustomLabel.LEFT ,40);
        usernameVal = new CustomLabel(username,CustomLabel.LEFT ,40);
        
        //logout
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


    void book(){
        books=new CustomJPanel();
        booksInternal=new CustomJPanel(new GridLayout(2, 1, 10, 10));

        booksMain=new CustomJPanel(new GridLayout(count+1, 8, 10, 10),1);
        booksMain.setBorder(b);
        
        CustomLabel lBooks=new CustomLabel("Library Books:", CustomLabel.LEFT);
        lBooks.setFont(new Font("Times New Roman",Font.BOLD,30));

        //Table Headings
        sNo[0]=new CustomLabel("S.No.",CustomLabel.LEFT,20);
        bId[0]=new CustomLabel("Book ID",CustomLabel.LEFT,20);
        bName[0]=new CustomLabel("Book Name",CustomLabel.LEFT,20);
        bPub[0]=new CustomLabel("Publisher Name",CustomLabel.LEFT,20);
        bAuth[0]=new CustomLabel("Author's Name",CustomLabel.LEFT,20);
        bEdit[0]=new CustomLabel("Edition Year",CustomLabel.LEFT,20);
        bStatus[0]=new CustomLabel("Status",CustomLabel.LEFT,20);
        bDelete=new CustomLabel("Action",CustomLabel.LEFT,20);

        
        //adding table headings
        booksMain.add(sNo[0]);
        booksMain.add(bId[0]);
        booksMain.add(bName[0]);
        booksMain.add(bPub[0]);
        booksMain.add(bAuth[0]);
        booksMain.add(bEdit[0]);
        booksMain.add(bStatus[0]);
        booksMain.add(bDelete);


        //adding data
        for(int i=0;i<count;i++){
            sNo[i]=new CustomLabel(String.valueOf(i+1),CustomLabel.LEFT,15);
            bId[i]=new CustomLabel(b_id[i],CustomLabel.LEFT,15);
            bName[i]=new CustomLabel(b_name[i],CustomLabel.LEFT,15);
            bPub[i]=new CustomLabel(b_publisher[i],CustomLabel.LEFT,15);
            bAuth[i]=new CustomLabel(b_author[i],CustomLabel.LEFT,15);
            bEdit[i]=new CustomLabel(b_edition[i],CustomLabel.LEFT,15);
            
            try{
                ResultSet rs5=stmt.executeQuery("select b_id from book where b_id='"+b_id[i]+"' and b_id not in (select b_id from issue) and b_id not in (select b_id from issue_request)");
                if(rs5.next()){
                    bStatus[i]=new CustomLabel("Available",CustomLabel.LEFT,15);
                }
                else{
                    ResultSet rs6=stmt.executeQuery("select b_id from issue_request where b_id='"+b_id[i]+"'");
                    if(rs6.next()){
                        bStatus[i]=new CustomLabel("Requested",CustomLabel.LEFT,15);

                    }
                    else{
                        bStatus[i]=new CustomLabel("Issued",CustomLabel.LEFT,15);
                    }
                }
            }
            catch(Exception e){System.out.println(e);}
            
            deleteBook[i]=new CustomButton("Delete Book");
            deleteBook[i].setBackground(Color.RED);
            deleteBook[i].setForeground(Color.WHITE);

            booksMain.add(sNo[i]);
            booksMain.add(bId[i]);
            booksMain.add(bName[i]);
            booksMain.add(bPub[i]);
            booksMain.add(bAuth[i]);
            booksMain.add(bEdit[i]);
            booksMain.add(bStatus[i]);
            booksMain.add(deleteBook[i]);
            
            deleteBook[i].addActionListener(this);
        }

        
        booksInternal.add(lBooks);
        booksInternal.add(booksMain);

        books.add(booksInternal);

    } 
    

    void addBook(){
        addBookLabel=new CustomLabel("Add New Book",CustomLabel.CENTER,50);

        addBookInternal=new CustomJPanel(new GridLayout(2, 1, 15, 10));

        addBookMain=new CustomJPanel(new GridLayout(6, 2, 15, 15),1);

        Border b1=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Book Details",TitledBorder.LEFT,TitledBorder.DEFAULT_POSITION,new Font("Times New Roman",Font.BOLD, 20));
        addBookMain.setBorder(b1);

        bookID=new CustomLabel("ID:",CustomLabel.CENTER);
        bookName=new CustomLabel("NAME:",JTextField.CENTER);
        bookAuthor=new CustomLabel("AUTHOR:",JTextField.CENTER);
        bookPublisher=new CustomLabel("PUBLISHER:",JTextField.CENTER);
        bookEdition=new CustomLabel("EDITION:",JTextField.CENTER);

        bookIDF=new JTextField(50);
        bookNameF=new JTextField(15);
        bookAuthorF=new JTextField(15);
        bookPublisherF=new JTextField(15);
        bookEditionF=new JTextField(15);

        addBookBtn=new CustomButton("ADD");
        addBookRstBtn=new CustomButton("RESET");

        addBookBtn.addActionListener(this);
        addBookRstBtn.addActionListener(this);

        addBookMain.add(bookID);
        addBookMain.add(bookIDF);
        addBookMain.add(bookName);
        addBookMain.add(bookNameF);
        addBookMain.add(bookAuthor);
        addBookMain.add(bookAuthorF);
        addBookMain.add(bookPublisher);
        addBookMain.add(bookPublisherF);
        addBookMain.add(bookEdition);
        addBookMain.add(bookEditionF);
        addBookMain.add(addBookBtn);
        addBookMain.add(addBookRstBtn);

        addBookMainI=new CustomJPanel();
        addBookMainI.add(addBookMain,BorderLayout.CENTER);

        addBook=new CustomJPanel(new GridLayout(2, 1, 0, 0));
        addBookInternal.add(addBookLabel);
        addBookInternal.add(addBookMainI);

        addBook.add(addBookInternal);
    }
    

    void issuer(){
        issuers=new CustomJPanel();
        issuersInternal=new CustomJPanel(new GridLayout(2, 1, 10, 10));
        
        issuersMain=new CustomJPanel(new GridLayout(countIssuers+1, 6, 100, 10),1);
        issuersMain.setBorder(b);
        
        CustomLabel issuersLabel=new CustomLabel("Registered Issuers Details:", CustomLabel.CENTER,30);

        //Table Headings
        iSno[0]=new CustomLabel("S.No.",CustomLabel.LEFT,20);
        iName[0]=new CustomLabel("Name",CustomLabel.LEFT,20);
        iMobile[0]=new CustomLabel("Mobile No.",CustomLabel.LEFT,20);
        iEmail[0]=new CustomLabel("E-Mail",CustomLabel.LEFT,20);
        iFine[0]=new CustomLabel("Fine Due",CustomLabel.LEFT,20);


        //adding table headings
        issuersMain.add(iSno[0]);
        issuersMain.add(iName[0]);
        issuersMain.add(iMobile[0]);
        issuersMain.add(iEmail[0]);
        issuersMain.add(iFine[0]);

        //adding data
        for(int i=0;i<countIssuers;i++){
            iSno[i]=new CustomLabel(String.valueOf(i+1),CustomLabel.LEFT,15);
            iName[i]=new CustomLabel(issuerName[i],CustomLabel.LEFT,15);
            iMobile[i]=new CustomLabel(issuerMobile[i],CustomLabel.LEFT,15);
            iEmail[i]=new CustomLabel(issuerEmail[i],CustomLabel.LEFT,15);
            iFine[i]=new CustomLabel(issuerFine[i],CustomLabel.LEFT,15);
           
            issuersMain.add(iSno[i]);
            issuersMain.add(iName[i]);
            issuersMain.add(iMobile[i]);
            issuersMain.add(iEmail[i]);
            issuersMain.add(iFine[i]);
            
        }

        issuersInternal.add(issuersLabel);
        issuersInternal.add(issuersMain);

        issuers.add(issuersInternal);

    }

    void issuerequest(){
        issueRequest = new CustomJPanel();

        issueRequestInternal=new CustomJPanel(new GridLayout(2, 1, 10, 10));
        
        issueRequestMain=new CustomJPanel(new GridLayout(countIssueRequest+1, 6, 10, 10),1);
        issueRequestMain.setBorder(b);
        
        CustomLabel issueRequestLabel=new CustomLabel("Issue Requests:", CustomLabel.CENTER,30);

        
        //Table Headings
        iS_no[0]=new CustomLabel("S.No.",CustomLabel.LEFT,20);
        iB_Id[0]=new CustomLabel("Book ID",CustomLabel.LEFT,20);
        iB_Name[0]=new CustomLabel("Book Name",CustomLabel.LEFT,20);
        iB_Auth[0]=new CustomLabel("Book Author",CustomLabel.LEFT,20);
        iRequesterName[0]=new CustomLabel("Issuer Name",CustomLabel.LEFT,20);
        iRequesterMobile[0]=new CustomLabel("Issuer Mobile",CustomLabel.LEFT,20);
        iRequesterEmail[0]=new CustomLabel("Issuer E-Mail",CustomLabel.LEFT,20);
        iRequesterFine[0]=new CustomLabel("Issuer Fine",CustomLabel.LEFT,20);
        iApproveLabel=new CustomLabel("");
        iRejectLabel=new CustomLabel("");


        //adding table headings
        issueRequestMain.add(iS_no[0]);
        issueRequestMain.add(iB_Id[0]);
        issueRequestMain.add(iB_Name[0]);
        issueRequestMain.add(iB_Auth[0]);
        issueRequestMain.add(iRequesterName[0]);
        issueRequestMain.add(iRequesterMobile[0]);
        issueRequestMain.add(iRequesterEmail[0]);
        issueRequestMain.add(iRequesterFine[0]);
        issueRequestMain.add(iApproveLabel);
        issueRequestMain.add(iRejectLabel);


        for(int i=0;i<countIssueRequest;i++){
            iS_no[i]=new CustomLabel(String.valueOf(i+1),CustomLabel.LEFT,15);
            iB_Id[i]=new CustomLabel(iB_id[i],CustomLabel.LEFT,15);
            iB_Name[i]=new CustomLabel(iB_name[i],CustomLabel.LEFT,15);
            iB_Auth[i]=new CustomLabel(iB_author[i],CustomLabel.LEFT,15);
            iRequesterName[i]=new CustomLabel(requesterName[i],CustomLabel.LEFT,15);
            iRequesterMobile[i]=new CustomLabel(requesterMobile[i],CustomLabel.LEFT,15);
            iRequesterEmail[i]=new CustomLabel(requesterEmail[i],CustomLabel.LEFT,15);
            iRequesterFine[i]=new CustomLabel(requesterFine[i],CustomLabel.LEFT,15);

            iApprove[i]=new CustomButton("APPROVE");
            iApprove[i].setBackground(Color.green);
            iApprove[i].setForeground(Color.WHITE);
            iReject[i]=new CustomButton("REJECT");
            iReject[i].setBackground(Color.RED);
            iReject[i].setForeground(Color.WHITE);

            iApprove[i].addActionListener(this);
            iReject[i].addActionListener(this);

           
            issueRequestMain.add(iS_no[i]);
            issueRequestMain.add(iB_Id[i]);
            issueRequestMain.add(iB_Name[i]);
            issueRequestMain.add(iB_Auth[i]);
            issueRequestMain.add(iRequesterName[i]);
            issueRequestMain.add(iRequesterMobile[i]);
            issueRequestMain.add(iRequesterEmail[i]);
            issueRequestMain.add(iRequesterFine[i]);
            issueRequestMain.add(iApprove[i]);
            issueRequestMain.add(iReject[i]);
            
        }

        issueRequestInternal.add(issueRequestLabel);
        issueRequestInternal.add(issueRequestMain);

        issueRequest.add(issueRequestInternal);

    }

    void submitrequest(){
        submitRequest = new CustomJPanel();

        submitRequestInternal=new CustomJPanel(new GridLayout(2, 1, 10, 10));
        
        submitRequestMain=new CustomJPanel(new GridLayout(countSubmitRequest+1, 6, 10, 10),1);
        submitRequestMain.setBorder(b);
        
        CustomLabel submitRequestLabel=new CustomLabel("Submit Requests:", CustomLabel.CENTER,30);

        
        //Table Headings
        sS_no[0]=new CustomLabel("S.No.",CustomLabel.LEFT,20);
        sB_Id[0]=new CustomLabel("Book ID",CustomLabel.LEFT,20);
        sB_Name[0]=new CustomLabel("Book Name",CustomLabel.LEFT,20);
        sB_Date[0]=new CustomLabel("Application Date",CustomLabel.LEFT,20);
        iSubmitterName[0]=new CustomLabel("Submitter Name",CustomLabel.LEFT,20);
        iSubmitterMobile[0]=new CustomLabel("Submitter Mobile",CustomLabel.LEFT,20);
        iSubmitterEmail[0]=new CustomLabel("Submitter E-Mail",CustomLabel.LEFT,20);
        iSubmitterFine[0]=new CustomLabel("Submitter Fine",CustomLabel.LEFT,20);
        sApproveLabel=new CustomLabel("");
        sRejectLabel=new CustomLabel("");


        //adding table headings
        submitRequestMain.add(sS_no[0]);
        submitRequestMain.add(sB_Id[0]);
        submitRequestMain.add(sB_Name[0]);
        submitRequestMain.add(iSubmitterName[0]);
        submitRequestMain.add(iSubmitterMobile[0]);
        submitRequestMain.add(iSubmitterEmail[0]);
        submitRequestMain.add(iSubmitterFine[0]);
        submitRequestMain.add(sB_Date[0]);
        submitRequestMain.add(sApproveLabel);
        submitRequestMain.add(sRejectLabel);


        for(int i=0;i<countSubmitRequest;i++){
            sS_no[i]=new CustomLabel(String.valueOf(i+1),CustomLabel.LEFT,15);
            sB_Id[i]=new CustomLabel(sB_id[i],CustomLabel.LEFT,15);
            sB_Name[i]=new CustomLabel(sB_name[i],CustomLabel.LEFT,15);
            sB_Date[i]=new CustomLabel(sB_date[i],CustomLabel.LEFT,15);
            iSubmitterName[i]=new CustomLabel(submitterName[i],CustomLabel.LEFT,15);
            iSubmitterMobile[i]=new CustomLabel(submitterMobile[i],CustomLabel.LEFT,15);
            iSubmitterEmail[i]=new CustomLabel(submitterEmail[i],CustomLabel.LEFT,15);
            iSubmitterFine[i]=new CustomLabel(submitterFine[i],CustomLabel.LEFT,15);

            sApprove[i]=new CustomButton("APPROVE");
            sApprove[i].setBackground(Color.GREEN);
            sApprove[i].setForeground(Color.WHITE);
            sReject[i]=new CustomButton("REJECT");
            sReject[i].setBackground(Color.RED);
            sReject[i].setForeground(Color.WHITE);

            sApprove[i].addActionListener(this);
            sReject[i].addActionListener(this);

           
            submitRequestMain.add(sS_no[i]);
            submitRequestMain.add(sB_Id[i]);
            submitRequestMain.add(sB_Name[i]);
            submitRequestMain.add(iSubmitterName[i]);
            submitRequestMain.add(iSubmitterMobile[i]);
            submitRequestMain.add(iSubmitterEmail[i]);
            submitRequestMain.add(iSubmitterFine[i]);
            submitRequestMain.add(sB_Date[i]);
            submitRequestMain.add(sApprove[i]);
            submitRequestMain.add(sReject[i]);
            
        }

        submitRequestInternal.add(submitRequestLabel);
        submitRequestInternal.add(submitRequestMain);

        submitRequest.add(submitRequestInternal);

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

        //delete book
        for(int i=0;i<count;i++){
            if(e.getSource()==deleteBook[i]){
                int confirm=JOptionPane.showOptionDialog(this, "Confirm Deletion?", "Select an Option",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,null,1);
                String delete="delete from book where b_id='"+b_id[i]+"'";

                if(confirm==0){
                    try{
                        // System.out.println(delete);
                        stmt.executeUpdate(delete);
                        JOptionPane.showMessageDialog(null,"BOOK DELETED SUCCESSFULLY.");
                        
                        //to update whole data
                        this.Database();
                        updateGUI(dashboardPane.getSelectedIndex());

                    }
                    catch(Exception ee){
                        System.out.println(ee);
                        JOptionPane.showMessageDialog(null,"BOOK IS ISSUED OR REQUESTED!!");
                    }
                }
            }
        }


        //approve issue
        for(int i=0;i<countIssueRequest;i++){
            if(e.getSource()==iApprove[i]){
                String insert="insert into issue values('"+iB_id[i]+"','"+requesterUserName[i]+"','"+dateSQL+"')";
                String delete="delete from issue_request where b_id='"+iB_id[i]+"' and user_name='"+requesterUserName[i]+"'";
                String ifAlreadyIssued="select b_id from issue where b_id='"+iB_id[i]+"'";
                ResultSet rs;
                try{
                    rs=stmt.executeQuery(ifAlreadyIssued);

                    if(rs.next()){
                        JOptionPane.showMessageDialog(null, "Book Already Issued!");
                    }
                    else{
                        try{
                            if(iReject[i].getText().equals("REJECTED")){
                                if(iApprove[i].getText().equals("APPROVED")){
                                    JOptionPane.showMessageDialog(null,"Already Approved");
                                }
                                else{
                                    JOptionPane.showMessageDialog(null,"Already Rejected");
                                }
                            }
                            else{
                                stmt.executeUpdate(insert);
                                stmt.executeUpdate(delete);
        
                                //to update whole data
                                this.Database();
                                updateGUI(dashboardPane.getSelectedIndex());
        
                                iApprove[i].setText("APPROVED");
                            }
                        }
                        catch(Exception SQLIntegrityConstraintViolationException){
                            System.out.println(SQLIntegrityConstraintViolationException);
                            JOptionPane.showMessageDialog(null,"Already Approved");
                            iApprove[i].setText("APPROVED");
                        }
                    }
                }
                catch(Exception ee){}

            }
        }

        //decline request
        for(int i=0;i<countIssueRequest;i++){
            if(e.getSource()==iReject[i]){
                String delete="delete from issue_request where b_id='"+iB_id[i]+"' and user_name='"+requesterUserName[i]+"'";
                try{
                    if(iApprove[i].getText().equals("APPROVED")){
                        if(iReject[i].getText().equals("REJECTED")){
                            JOptionPane.showMessageDialog(null,"Already Rejected");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Already Approved");
                        }
                    }
                    else{
                        if(iReject[i].getText().equals("REJECTED")){
                            JOptionPane.showMessageDialog(null,"Already Rejected");
                        }
                        else{
                            stmt.executeUpdate(delete);

                            iReject[i].setText("REJECTED");
                        }
                    }
                }
                catch(Exception SQLIntegrityConstraintViolationException){System.out.println(SQLIntegrityConstraintViolationException);}
            }
        }



        //approve submission
        for(int i=0;i<countSubmitRequest;i++){
            fine=0;
            //if submit approve button is clicked 
            if(e.getSource()==sApprove[i]){
                int days=0;

                //true if approve and reject button is unclicked
                if(!sApprove[i].getText().equals("APPROVED") && !sReject[i].getText().equals("REJECTED")){
                    try{
                        String issueDate="(select issue_date from issue where b_id='"+sB_id[i]+"' and user_name='"+submitterUserName[i]+"')";
                        String submitDate="(select submit_date from submit_request where b_id='"+sB_id[i]+"' and user_name='"+submitterUserName[i]+"')";
                        String dateDiff="SELECT DATEDIFF("+submitDate+","+issueDate+") AS DateDiff";
                        ResultSet rs=stmt.executeQuery(dateDiff);
                        while(rs.next()){
                            System.out.println(rs.getString(1));
                            days = rs.getInt(1);
                        }
                    }
                    catch(Exception ee){System.out.println(ee);}

                    if(days>10){//check for fine
                        fine=(days-10)*2;
                    }

                    if(fine!=0){//update fine
                        String fineUpdate="update user set fine='"+fine+"' where user_name='"+submitterUserName[i]+"'";
                        try{
                            stmt.executeUpdate(fineUpdate);
                        }catch(Exception ee){System.out.println(ee);}
                    }

                    //deleting submit request and issue request
                    String delete="delete from submit_request where b_id='"+sB_id[i]+"' and user_name='"+submitterUserName[i]+"'";
                    String delete1="delete from issue where b_id='"+sB_id[i]+"' and user_name='"+submitterUserName[i]+"'";
                    try{
                        stmt.executeUpdate(delete);
                        stmt.executeUpdate(delete1);
                            
                        //to update whole data
                        this.Database();
                        updateGUI(dashboardPane.getSelectedIndex());

                        sApprove[i].setText("APPROVED");
                    }
                    catch(Exception SQLIntegrityConstraintViolationException){
                        System.out.println(SQLIntegrityConstraintViolationException);
                        JOptionPane.showMessageDialog(null,"Already Approved");
                        sApprove[i].setText("APPROVED");
                    }
                }
                else{
                    if(sReject[i].getText().equals("REJECTED")){
                            JOptionPane.showMessageDialog(null,"Already Rejected");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Already Approved");
                        }
                }
            }
        }


        //reject submission
        for(int i=0;i<countSubmitRequest;i++){
            if(e.getSource()==sReject[i]){
                String delete="delete from submit_request where b_id='"+sB_id[i]+"' and user_name='"+submitterUserName[i]+"'";
                try{
                    if(sReject[i].getText().equals("REJECTED")){//check for already approved or rejected
                        JOptionPane.showMessageDialog(null,"Already Rejected");
                    }
                    else if(sApprove[i].getText().equals("APPROVED")){
                        JOptionPane.showMessageDialog(null,"Already Approved");
                    }
                    else{
                        stmt.executeUpdate(delete);

                        sReject[i].setText("REJECTED");
                    }
                }
                catch(Exception SQLIntegrityConstraintViolationException){System.out.println(SQLIntegrityConstraintViolationException);}
            }

        }


        //add books
        if(e.getSource()==addBookRstBtn){
            bookIDF.setText("");
            bookNameF.setText("");
            bookPublisherF.setText("");
            bookAuthorF.setText("");
            bookEditionF.setText("");
        }
        if(e.getSource()==addBookBtn){
            String id=bookIDF.getText();
            String name=bookNameF.getText();
            String publisher=bookPublisherF.getText();
            String author=bookAuthorF.getText();
            String edition=bookEditionF.getText();

            if(!id.equals("") && !name.equals("") && !publisher.equals("") && !author.equals("") && !edition.equals("")){
                try{
                    String add = "insert into book values('"+id+"','"+name+"','"+publisher+"','"+author+"','"+edition+"')";
                    stmt.executeUpdate(add);
                            
                    //to update whole data
                    this.Database();
                    updateGUI(dashboardPane.getSelectedIndex());

                    JOptionPane.showMessageDialog(null,"ADDED SUCCESSFULLY.");

                    bookIDF.setText("");
                    bookNameF.setText("");
                    bookPublisherF.setText("");
                    bookAuthorF.setText("");
                    bookEditionF.setText("");

                }
                catch(SQLSyntaxErrorException se){JOptionPane.showMessageDialog(null,"FORMAT IS NOT VALID");}
                catch(SQLIntegrityConstraintViolationException ie){
                    System.out.println(ie);
                    JOptionPane.showMessageDialog(null,"DATA ALREADY ADDED");
                }
                catch(Exception ee){System.out.println(ee);}
            }
            else{
                JOptionPane.showMessageDialog(null,"ALL FIELDS ARE COMPULSORY.");
            }

        }


    }

}


