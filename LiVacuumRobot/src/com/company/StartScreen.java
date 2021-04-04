package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import static java.awt.Font.PLAIN;

public class StartScreen {
    JFrame frame;
    JFrame frame2;
    JFrame frame3;
    JLabel label;
    JLabel label2;
    ImageIcon house1Image=new ImageIcon("house1.jpg");
    JLabel house1ImageLabel;
    JButton startButton;
    JButton exitButton;
    JButton house1Button;
    JButton house2Button;
    JButton startCleanButton;
    Font titleFont=new Font("Times New Roman", PLAIN, 35);

    public StartScreen(){
        System.out.println("Welcome to virtual robot vacuum cleaner!\n" +
                "Press 'Start' button to begin\nPress 'Exit' button to exit.\n ");
//------LABEL--------------------------------------------------------
        //label in frame
        label =new JLabel("Welcome to virtual robot vacuum cleaner!");
        label.setForeground(Color.white);
        label.setFont(titleFont);
        label.setBounds(90,100,600,150);
        label.setVisible(true);

        //label in frame2
        label2=new JLabel("Which house model you want to test?");
        label2.setForeground(Color.WHITE);
        label2.setFont(titleFont);
        label2.setBounds(90,100,600,150);
        label2.setVisible(true);

        //house1ImageLabel in frame3
        house1ImageLabel=new JLabel(house1Image);
        house1ImageLabel.setForeground(Color.WHITE);
        house1ImageLabel.setFont(titleFont);
        house1ImageLabel.setBounds(100,50,500,330);
        house1ImageLabel.setVisible(true);

//------BUTTON------------------------------------------------------
        //start button in frame
        startButton=new JButton("Start");
        startButton.setBounds(200,300,100,100);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==startButton){
                    System.out.println("> You choose 'start' button.");
                    //Build frame2 when click 'start' button, called house model screen
                    frame2=new JFrame("House model one");
                    frame2.setSize(800,600);
                    frame2.getContentPane().setBackground(Color.black);
                    frame2.setLayout(null);
                    frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame2.add(house1Button);
                    frame2.add(house2Button);
                    frame2.add(label2);
                    frame2.setVisible(true);

                }
            }
        });
        startButton.setBackground(Color.ORANGE);

        //exit button in frame
        exitButton=new JButton("Exit");
        exitButton.setBounds(400,300,100,100);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==exitButton){
                    System.out.println("> You choose 'Exit' button. Quit the program. ");
                    System.exit(0);
                }
            }
        });
        exitButton.setBackground(Color.ORANGE);

        //house 1 button in frame2
        house1Button=new JButton("House 1");
        house1Button.setBounds(170,300,120,120);
        house1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==house1Button){
                    System.out.println("> You choose 'House 1 model.");
                    //Build frame 3 when click house1 button, frame 3 shown house 1 2D graph image
                    frame3=new JFrame("House 1");
                    frame3.setSize(800,600);
                    frame3.setLayout(null);
                    frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame3.getContentPane().setBackground(Color.BLACK);
                    frame3.add(house1ImageLabel);
                    frame3.add(startCleanButton);
                    frame3.setVisible(true);
                }
            }

        });
        house1Button.setBackground(Color.ORANGE);

        //house 2 button in frame2
        house2Button=new JButton("House 2");
        house2Button.setBounds(370,300,120,120);
        house2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==house2Button){
                    System.out.println("> You choose 'House 2 model.");
                    //add house2 when build house2 class
                }

            }
        });
        house2Button.setBackground(Color.ORANGE);

        //start clean button in frame3
        startCleanButton=new JButton("Clean");
        startCleanButton.setBounds(300,400,120,100);
        startCleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==startCleanButton){
                    System.out.println("> Start to clean House 1.");
                    new RobotRoomCleaner().lunch();
                    //Gui performance for robot cleaning in house
                    new House().launch();
                    //then go to end interface see "cleanResult"
                }

            }
        });
        startCleanButton.setBackground(Color.ORANGE);

//-----FRAME-----------------------------------------------------------
        //window for startScreen
        frame = new JFrame("Welcome to Robot Vacuum");
        frame.setSize(800,600);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.add(label);
        frame.add(startButton);
        frame.add(exitButton);
        frame.setVisible(true);

    }

}
