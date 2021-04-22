package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MoveRobot extends Frame {

    public static final int ROW=40;
    public static final int COL=40;
    public static final int BLOCK_SIZE=20;
    private boolean flag=true;
    Robot r=new Robot(this);
    int battery=100;

    public void launch()
    {
        this.setSize(COL*BLOCK_SIZE,ROW*BLOCK_SIZE);
        this.setLocation(200,200);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setVisible(true);

        new Thread(new PaintThread()).start();
    }

    @Override
    public void paint(Graphics g) {
        Color c=g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(0,0,COL*BLOCK_SIZE,ROW*BLOCK_SIZE);
        g.setColor(Color.BLACK);
        for(int i=0; i<ROW-1;i++)
        {
            g.drawLine(0,BLOCK_SIZE*i,COL*BLOCK_SIZE,i*BLOCK_SIZE);
        }
        for(int i=0; i<COL-1;i++)
        {
            g.drawLine(BLOCK_SIZE*i,0,i*BLOCK_SIZE,ROW*BLOCK_SIZE);
        }
        g.setColor(Color.red);
        g.drawString("Remaining Battery Life: "+battery, 10,60);
        if(battery<=0)return;
        g.setColor(Color.yellow);
        r.draw(g);
        r.add();



    }
    public void stop()
    {
        flag=false;
    }



    private class PaintThread implements Runnable
    {

        public void run() {
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

    public int getBatteryLife(){
        return battery;

    }
    public void setBatteryLife(int battery){this.battery=battery;}

}
