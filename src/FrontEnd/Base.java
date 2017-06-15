package FrontEnd;

import javax.swing.*;

/**
 * Created by tom on 12/06/17.
 */
public class Base extends JFrame {
    Env2D panel2D;

    //NOTE: might be an idea to force a set size for the JFrames and Jpanels (especially for large areas)
    public Base(int size, int[][] asteroids, int[][] agents){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create a new 2D representation
        panel2D = new Env2D(size, asteroids, agents);

        //add the representations
        this.getContentPane().add(panel2D);
        this.pack();
        this.setVisible(true);
    }

    public void update(){
        panel2D.repaint();
    }
}