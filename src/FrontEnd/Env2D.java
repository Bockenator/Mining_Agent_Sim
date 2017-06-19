package FrontEnd;

import javax.swing.*;
import java.awt.*;

/**
 * Created by tom on 12/06/17.
 */
//this JPanel will house the 2D overview of the environment. As such it houses incomplete information
public class Env2D extends JPanel {
    int size;
    int[][] asteroids;
    int[][] agents;


    public Env2D(int size, int[][] asteroids, int[][] agents){
        this.size = size;
        this.asteroids = asteroids;
        this.agents = agents;

        //set our preffered dimension in the constructor too
        //NOTE: ONLY TO BE DONE IN TESTING AS OTHERWISE MIGHT RUN INTO WINDOW SIZE ISSUES
        this.setPreferredSize(new Dimension(size,size));

        setBackground(Color.BLACK);
    }

    public void update(int[][] asteroids, int[][] agents){
        this.asteroids = asteroids;
        this.agents = agents;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.green);
        //g.fillOval(50,50,10,10);
        drawAgents(g);
        drawAsteroids(g);
    }

    public void drawAgents(Graphics g){
        g.setColor(Color.green);
        for(int i = 0; i<agents.length;i++){
            g.fillOval(agents[i][0], agents[i][1],10,10);
        }
    }

    public void drawAsteroids(Graphics g){
        g.setColor(Color.red);
        for(int i = 0; i<asteroids.length;i++){
            g.fillOval(asteroids[i][0], asteroids[i][1],10,10);
        }
    }
}