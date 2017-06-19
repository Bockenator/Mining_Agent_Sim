package BackEnd.Agent;

import BackEnd.Environment.Asteroid;
import BackEnd.Logic.*;
import java.util.*;
/**
 * Created by tom on 05/06/17.
 */
public class Agent {
    //should probably all be private
    //positions
    float x,y,z;
    //directional speeds
    float x_sp,y_sp,z_sp;
    float speed;
    float [] target_pos;


    float sense_range;
    float mining_range;
    float max_cargo;
    float cargo;

    //this is the plan (we take the head of the queue every time to get the next action in the plan)
    ArrayList<Action> action_queue;

    //array for beliefs for logic inference
    ArrayList<String> beliefs;

    //array for desires to be turned into intentions
    ArrayList<String> desires;

    //these map actual percepts
    ArrayList<float []> obj_positions;
    ArrayList<String> obj_names;

    public Agent() {
        init();
    }

    //init method
    private void init(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.speed = 10;
        this.x_sp = 0;
        this.y_sp = 0;
        this.z_sp = 0;
        this.sense_range = 10;
        this.mining_range = 5;
        this.cargo = 0;
        this.max_cargo = 100;
    }

    //we move by trying to go to target position (obstacle avoidance and collision detection not implemented)
    public void move(){
        //get total distance
        float distance = (float)Math.sqrt(((target_pos[0]-x)*(target_pos[0]-x)) + ((target_pos[1]-y)*(target_pos[1]-y))
                + ((target_pos[2]-z)*(target_pos[2]-z)));

        //get x y z distances
        float x_d = (float)Math.sqrt((target_pos[0]-x)*(target_pos[0]-x));
        float y_d = (float)Math.sqrt((target_pos[1]-y)*(target_pos[1]-y));
        float z_d = (float)Math.sqrt((target_pos[2]-z)*(target_pos[2]-z));

        //adjust based on distance
        x_sp = (x_d/distance)*speed;
        y_sp = (y_d/distance)*speed;
        y_sp = (z_d/distance)*speed;

        //move
        x+=x_sp;
        y+=y_sp;
        z+=z_sp;
    }

    //NOTE: see(Asteroid) can be put here or when we add the object to the arraylist in getPercepts
    //check if any asteroids are in range to be mined
    public boolean checkRanges(float [] obj_pos){
        //Euclidean distance
        float distance = (float)Math.sqrt(((obj_pos[0]-x)*(obj_pos[0]-x)) + ((obj_pos[1]-y)*(obj_pos[1]-y)) +
                ((obj_pos[2]-z)*(obj_pos[2]-z)));
        if (distance <= mining_range){
            addBelief("within_range(Asteroid)");
            return true;
        }
        return false;
    }

    //used for doing actions (from action queue)
    public void doAct(Action a){
        switch(a){
            case MOVE:
                move();
                break;

            case MINE:
                break;

            case DUMP_CARGO:
                break;

            default:
                //idle
                break;
        }
    }

    //percepts come in the form a list of object name (in the future might turn into unique ids) and positions
    public void getPercepts(ArrayList<String> objects, ArrayList<float []> positions){
        this.obj_names = objects;
        this.obj_positions = positions;

        //NOTE: this should technically be in the "see" method but fits in here too
        //if we see an object we also believe that we see it
        if (obj_names.size() > 0){
            for (int i = 0; i < obj_names.size(); i++){
                addBelief("see("+obj_names.get(i)+")");
            }
        }
    }

    //convert percepts to beliefs
    public void see(){
        //cargo beliefs
        if (cargo > 0) {
            addBelief("have_cargo");
            if (cargo == max_cargo) {
                addBelief("cargo_full");
            }
        }

        //object beliefs
        if (obj_positions.size() > 0){
            for (int i = 0; i < obj_positions.size(); i++){
               checkRanges(obj_positions.get(i));
            }
        }

        //position beliefs
        if ((x == 0) && (y == 0) && (z == 0)){
            addBelief("at_base");
        }

    }

    //method for adding world beliefs (we only add a belief if it isn't there already)
    public void addBelief(String belief){
        if (!(beliefs.contains(belief))){
            beliefs.add(belief);
        }
    }

    //method for removing beliefs (just for clarity)
    public void removeBelief(String belief){
        //if condition is just a precaution to avoid null-pointer errors
        if (beliefs.contains(belief)) {
            beliefs.remove(belief);
        }
    }

    //gets new beliefs. Removes all previous beliefs so no object permanence is possible
    public void beliefRevision(){
        beliefs.clear();
        see();
    }

    //get 2d representation for the front end
    public int[] get2DAgent(){
        int[] agent =  {(int)x,(int)y};
        return agent;
    }
}