package BackEnd.Environment;

import BackEnd.Agent.Agent;
import BackEnd.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
/**
 * Created by tom on 05/06/17.
 */
public class Environment extends Observer{

    float size;
    //THIS NEEDS TO BE A HASHMAP SO THAT ASTEROIDS ALWAYS HAVE UNIQUE IDS
    Asteroid[] field;

    public Environment(float size, int no_ast, Agent agent) {
        this.size = size;
        this.field = new Asteroid[no_ast];
        this.agent = agent;
        this.agent.setObs(this);
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


    //get list of objects in world (just asteroids for now)
    public ArrayList<String> getObjects(){
        ArrayList<String> objs = new ArrayList<>();
        for (int i = 0; i < field.length; i++){
            objs.add("Asteroid");
        }
        return objs;
    }

    //get list of the positions of the objects in the world
    public ArrayList<float []> getObjectPos(){
        ArrayList<float []> o_pos = new ArrayList<>();
        float [] pos = new float [3];
        for (int i = 0; i < field.length; i++){
            pos[0] = field[i].x;
            pos[1] = field[i].y;
            pos[2] = field[i].z;
            o_pos.add(pos);
        }
        return o_pos;
    }

    //gets all objects within an agents sense range
    public HashMap<String, float[]> getAllInRange(float range, float [] obj_pos){
        HashMap<String, float[]> objs = new HashMap<>();
        float [] pos = new float [3];
        String name;
        float distance;
        for (int i = 0; i < field.length; i++){
            //euclidean distance and only add those in range
            //NOTE: could replace with (x<range && y<range && z<range)
            distance = (float)Math.sqrt(((obj_pos[0]-field[i].x)*(obj_pos[0]-field[i].x)) + ((obj_pos[1]-field[i].y)
            *(obj_pos[1]-field[i].y)) + ((obj_pos[2]-field[i].z)*(obj_pos[2]-field[i].z)));
            if (distance <= range) {
                name = "Asteroid" + i;
                pos[0] = field[i].x;
                pos[1] = field[i].y;
                pos[2] = field[i].z;
                objs.put(name, pos);
            }
        }
        return objs;
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

    //update method from observer
    @Override
    public void update(String id){
        //THIS HASH SHOULD BE THE USUAL ID HASH
        HashMap<String, float[]> objs = new HashMap<>();
        objs.remove(id);
    }
}