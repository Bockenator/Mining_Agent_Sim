package BackEnd.Environment;

import java.util.Random;
/**
 * Created by tom on 05/06/17.
 */
public class Environment {

    float size;
    Asteroid[] field;

    public Environment(float size, int no_ast) {
        this.size = size;
        this.field = new Asteroid[no_ast];
    }

    //creates random asteroids to populate the environment
    public void initAsteroids(){
        for (int i = 0; i<field.length; i++){
            //initiate the asteroid with random location
            //NOTE: will need some kind of checking in the future to ensure asteroids don't overlap)
            field[i] = new Asteroid(randFloat(),randFloat(),randFloat());
        }
    }


    //a fun little random float generator (used in determining initial asteroid positions)
    //NOTE: at this time the base will always be at (0,0,0) but depending on what happens in the future that may change
    //NOTE AS WELL: should probably be static but fuck it
    public float randFloat() {
        Random rand = new Random();
        float result = rand.nextFloat() * (size - 1) + 1;
        return result;
    }


    //manually set asteroids (will primarily be used for testing)
    public void overwriteAst(Asteroid[] ast){
        this.field = ast;
    }


    //method to convert the asteroid field into a 2d representation (with no z coordinate being used at all)
    public int[][] get2DAsteroids(){
        int[][] result = new int[field.length][2];
        for (int i = 0; i<field.length;i++){
            //casting to int maybe inaccurate but this is used for 2d representation so its not that important
            //NOTE: being able to access the x and y fields is very unsafe (getters/setters?)
            result[i][0] = (int)field[i].x;
            result[i][1] = (int)field[i].y;
        }
        return result;
    }
}