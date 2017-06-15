package BackEnd.Agent;

import BackEnd.Environment.Asteroid;
import BackEnd.Logic.*;
import java.util.*;
/**
 * Created by tom on 05/06/17.
 */
public class Agent {
    //should probably all be private
    float x,y,z;
    float x_sp,y_sp,z_sp;
    float sense_range;
    float max_cargo;
    float cargo;
    Action[] action_queue;
    ArrayList<String> beliefs;
    ArrayList<Object> asteroids;

    public Agent() {
        init();
    }

    //init method
    private void init(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.x_sp = 0;
        this.y_sp = 0;
        this.z_sp = 0;
        this.sense_range = 10;
        this.cargo = 0;
        this.max_cargo = 100;
    }

    //method to update positions
    public void move(){
        x+=x_sp;
        y+=y_sp;
        z+=z_sp;
    }

    //get 2d representation for the front end
    public int[] get2DAgent(){
        int[] agent =  {(int)x,(int)y};
        return agent;
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

    //convert percepts to beliefs#
    public void see(){
        if (cargo > 0) {
            add_belief("have_cargo");
            if (cargo == max_cargo) {
                add_belief("cargo_full");
            }
        }

    }

    //method for adding world beliefs (we only add a belief if it isn't there already)
    public void add_belief(String belief){
        if (!(beliefs.contains(belief))){
            beliefs.add(belief);
        }
    }

    //method for removing beliefs (just for clarity)
    public void remove_belief(String belief){
        //if condition is just a precaution to avoid null-pointer errors
        if (beliefs.contains(belief)) {
            beliefs.remove(belief);
        }
    }
}