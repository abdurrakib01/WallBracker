package com.company;

import java.awt.*;
import java.awt.BasicStroke;
public class wall_map {
    public int map[][];
    public int wall_width;
    public int wall_height;
    public wall_map(int row, int col){
        map = new int[row][col];
        for(int i = 0; i < map.length; i++){
           for (int j = 0; j< map[0].length; j++){
                map[i][j] = 1;
           }
       }
        wall_width = 540/col;
        wall_height = 150/row;
    }
    public void draw(Graphics2D g){
        for(int i = 0; i < map.length; i++){
            for (int j = 0; j< map[0].length; j++){
                if(map[i][j]>0){
                    g.setColor(Color.white);
                    g.fillRect(j* wall_width +80, i* wall_height +50, wall_width, wall_height);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j* wall_width +80, i* wall_height +50, wall_width, wall_height);


                }
            }
        }
    }
   public void set_wall_value(int value, int row, int col){
        map[row][col] = value;
    }
}
