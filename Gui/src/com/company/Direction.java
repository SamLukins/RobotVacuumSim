package com.company;

public enum Direction {
    L,U,R,D;
}


/*package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.PortUnreachableException;
import java.security.PublicKey;

public class Robot {
    Node head=null;
    Node tail=null;
    private MoveRobot m;
    private Node n=new Node(1,1,Direction.R);

    public Robot(MoveRobot m)
    {
        head=n;
        tail=n;
        this.m=m;
    }

    public void addToTail()
    {
        Node node=null;
        switch (tail.dir)
        {
            case L:
                node=new Node(tail.row,tail.col+1,tail.dir);
                break;
            case R:
                node=new Node(tail.row,tail.col-1,tail.dir);
                break;
            case U:
                node=new Node(tail.row+1,tail.col,tail.dir);
                break;
            case D:
                node=new Node(tail.row-1,tail.col,tail.dir);
                break;
        }
        tail.next=node;
        node.prev=tail;
        tail=node;

    }

    public void addToHead()
    {
        Node node=null;
        switch (head.dir)
        {
            case L:
                node=new Node(head.row,head.col-1,head.dir);
                break;
            case R:
                node=new Node(head.row,head.col+1,head.dir);
                break;
            case U:
                node=new Node(head.row-1,head.col,head.dir);
                break;
            case D:
                node=new Node(head.row+1,head.col,head.dir);
                break;
        }
        node.next=head;
        head.prev=node;
        head=node;


    }

    private Rectangle getRect()
    {
        return new Rectangle(MoveRobot.BLOCK_SIZE*head.col,MoveRobot.BLOCK_SIZE*head.row,head.w,head.h);
    }
    public void draw(Graphics g)
    {
        for(Node n=head;n!=null;n=n.next)
        {
            n.draw(g);
        }
        move();

    }

    public void add()
    {
        this.addToTail();
        m.setBatteryLife(m.getBatteryLife()-1); //show -1 battery life
    }


    private void move() {
        addToHead();
        deleteFromTail();
        checkWall();

    }


    private void checkWall() {
        if(head.row<=0||head.col<=0||head.row>MoveRobot.ROW||head.col>MoveRobot.COL)
        {
           m.stop();

        }


    }


    private void deleteFromTail() {

        tail=tail.prev;
        tail.next=null;
    }


    private class Node
    {
        int w=MoveRobot.BLOCK_SIZE;
        int h=MoveRobot.BLOCK_SIZE;
        int row, col;
        Direction dir=Direction.L;
        Node next=null;
        Node prev=null;


        Node(int row,int col, Direction dir)
        {
            this.row=row;
            this.col=col;
            this.dir=dir;
        }
        private

        //draw robot
        void draw(Graphics g)
        {
            Color c=g.getColor();
            g.setColor(Color.ORANGE);
            g.fillOval(MoveRobot.BLOCK_SIZE*col,MoveRobot.BLOCK_SIZE*row,w,h);
            g.setColor(c);
        }

    }
}
*/