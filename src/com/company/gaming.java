package com.company;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class gaming extends JPanel implements ActionListener, KeyListener{
    private boolean play = false;
    private int score = 0;

    private int total_wall = 21;

    private Timer time;
    private int delay = 8;

    private int player_pos = 310;

    private int ball_pos_x = 120;
    private int ball_pos_y = 350;
    private int ball_dir_x = 5;
    private int ball_dir_y = 5;
    private wall_map map;
    public gaming(){
        map = new wall_map(3, 7);
        addKeyListener(this);
        setFocusable(true);
        time = new Timer(delay, this);
        time.start();
    }
    public void paint(Graphics g){
        // background
        g.setColor(Color.black);
        g.fillRect(1,1,692, 592);

        //drawing map
        map.draw((Graphics2D)g);

        // borders
        g.setColor(Color.darkGray); // adding border background
        g.fillRect(0,0,8,592);
        g.fillRect(0,0,692,8);
        g.fillRect(677,0,8,592);

        //scores
        g.setColor(Color.LIGHT_GRAY); // adding score font color
        g.setFont(new Font("",Font.ITALIC, 25));
        g.drawString(""+score,630, 30);

        //paddle
        g.setColor(Color.magenta);
        g.fillRect(player_pos,550,100,10);

        //the ball
        g.setColor(Color.yellow); // adding color for ball
        g.fillOval(ball_pos_x, ball_pos_y,20, 20);

        if(total_wall <=0){
            play = false;
            ball_dir_x = 0;
            ball_dir_y = 0;
            g.setColor(Color.ORANGE);
            g.setFont(new Font("",Font.ITALIC, 30));
            g.drawString("you won!",260, 300);

            g.setFont(new Font("",Font.ITALIC, 20));
            g.drawString("Press inter to Restate",230, 340);
        }

        if(ball_pos_y > 570){
            play = false;
            ball_dir_x = 0;
            ball_dir_y = 0;
            g.setColor(Color.orange);
            g.setFont(new Font("",Font.ITALIC, 30));
            g.drawString("Game over, Score: "+score,190, 300);

            g.setFont(new Font("",Font.ITALIC, 20));
            g.drawString("Press inter to Restate",230, 340);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();
        if(play){
            if(new Rectangle(ball_pos_x, ball_pos_y,20, 20).intersects(new Rectangle(player_pos, 550,100,8))){
                ball_dir_y = -ball_dir_y;
            }
            A: for(int i= 0; i<map.map.length; i++){
                for(int j=0; j<map.map[0].length; j++){
                    if(map.map[i][j] > 0){
                        int wall_pos_x =j* map.wall_width +80;
                        int wall_pos_y =i*map.wall_height + 50;
                        int wall_width = map.wall_width;
                        int wall_height = map.wall_height;

                        Rectangle rect = new Rectangle(wall_pos_x,wall_pos_y,wall_width, wall_height);
                        Rectangle ball_rect = new Rectangle(ball_pos_x, ball_pos_y, 20, 20);
                        Rectangle wall_rect = rect;
                        if(ball_rect.intersects(wall_rect)){
                            map.set_wall_value(0,i,j);
                            total_wall -= 1;
                            score += 5;
                            if(ball_pos_x +19 <= wall_rect.x || ball_pos_x +1 >= wall_rect.x+wall_rect.width){
                                ball_dir_x = -ball_dir_x;
                            }
                            else {
                                ball_dir_y =-ball_dir_y;
                            }
                            break A;
                        }
                    }
                }
            }

            ball_pos_x += ball_dir_x;
            ball_pos_y += ball_dir_y;
            if(ball_pos_x < 0){
                ball_dir_x = -ball_dir_x;
            }
            if(ball_pos_y < 0){
                ball_dir_y = -ball_dir_y;
            }
            if(ball_pos_x > 665){
                ball_dir_x = -ball_dir_x;
            }
        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (player_pos > 600) {
                player_pos = 600;
            }
            else {
                moveRight();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if (player_pos < 10) {
                player_pos = 10;
            }
            else{
                moveLeft();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ball_pos_x = 120;
                ball_pos_y =  350;
                ball_dir_x = 5;
                ball_dir_y = 5;
                player_pos = 310;
                score = 0;
                total_wall = 21;
                map = new wall_map(3,7);
                repaint();
            }
        }

    }

    private void moveLeft() {
        play = true;
        player_pos -= 20;
    }

    private void moveRight() {
        play = true;
        player_pos += 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
