package com.company;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class House extends Frame {

    public static final int ROW=20;
    public static final int COL=20;
    public static final int block_size=50;
    private boolean flag=true;
    public void launch()
    {
        setSize(block_size*COL, block_size*ROW);
        setLocation(50,50);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
        // new Thread(new PaintThread()).start();

        //then clean finish, call cleanResult interface class show result and end program.
    }

    public void paint(Graphics g)
    {
        Color c=g.getColor();
        g.setColor(Color.GREEN);
        g.fillRect(0,0,block_size*COL,block_size*ROW);  //paint house

        //set the line color now as orange, so we can clearly see the lines
        // after we finish to build the robot, we can set the line as the house background color
        g.setColor(Color.ORANGE);
        for(int i=0; i<ROW-1;i++)
        {
            g.drawLine(0,block_size*i,COL*block_size,i*block_size);
        }
        for(int i=0; i<COL-1;i++)
        {
            g.drawLine(block_size*i,0,i*block_size,ROW*block_size);
        }

        //Here is two rooms and their walls
        g.setColor(Color.cyan);
        g.fillRect(0,300,block_size*7,block_size);
        g.fillRect(0,0,block_size*6,block_size);
        g.fillRect(300,0,block_size,block_size*4);

        g.fillRect(600,0,block_size*8,block_size);
        g.fillRect(550,500,block_size*9,block_size);
        g.fillRect(550,0,block_size,block_size*8);

    }


    //class for run the robot and set the running speed
    // wait until we build the robot
    private class PaintThread implements Runnable{
        public void run()
        {

            while(flag)
            {
                repaint();
                try{
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }


    }

}

