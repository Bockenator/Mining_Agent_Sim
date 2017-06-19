package BackEnd.Agent;

import BackEnd.Environment.Asteroid;
import BackEnd.Logic.*;
import java.util.*;
/**
 * Created by tom on 05/06/17.
 * Contains the Agent architecture and logic stuff for a basic mining agent
 */
public class Agent {
    //should probably all be private
    //positions
    private float x,y,z;
    //directional speeds
    private float x_sp,y_sp,z_sp;
    private float speed;
    private float [] target_pos;


    private float sense_range;
    private float mining_range;
    private float max_cargo;
    private float cargo;

    //this is the plan (we take the head of the queue every time to get the next action in the plan)
    private ArrayList<Action> action_queue;

    //array for beliefs for logic inference
    private ArrayList<String> beliefs;

    //array for desires to be turned into intentions
    private ArrayList<String> desires;

    //these map actual percepts
    HashMap<String, float []> world_objects;


    public Agent() {
        init();
    }

    //init method
    private void init(){
        this.action_queue = new ArrayList<>();
        this.beliefs = new ArrayList<>();
        this.desires = new ArrayList<>();
        this.world_objects = new HashMap<>();
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.speed = 1;
        this.x_sp = 0;
        this.y_sp = 0;
        this.z_sp = 0;
        this.sense_range = 10;
        this.mining_range = 5;
        this.cargo = 0;
        this.max_cargo = 100;
    }

    //we move by trying to go to target position (obstacle avoidance and collision detection not implemented)
    private void move(){
        //get total distance
        float distance = (float)Math.sqrt(((target_pos[0]-x)*(target_pos[0]-x)) + ((target_pos[1]-y)*(target_pos[1]-y))
                + ((target_pos[2]-z)*(target_pos[2]-z)));

        //get x y z distances (we need actual values to determine directions)
        float x_d = (target_pos[0]-x);
        float y_d = (target_pos[1]-y);
        float z_d = (target_pos[2]-z);

        //adjust based on distance
        x_sp = (x_d/distance)*speed;
        y_sp = (y_d/distance)*speed;
        z_sp = (z_d/distance)*speed;

        //move
        x+=x_sp;
        y+=y_sp;
        z+=z_sp;
    }

    //NOTE: see(Asteroid) can be put here or when we add the object to the arraylist in getPercepts
    //check if any asteroids are in range to be mined
    private void checkRanges(float [] obj_pos){
        //Euclidean distance
        float distance = (float)Math.sqrt(((obj_pos[0]-x)*(obj_pos[0]-x)) + ((obj_pos[1]-y)*(obj_pos[1]-y)) +
                ((obj_pos[2]-z)*(obj_pos[2]-z)));
        if (distance <= mining_range){
            addBelief("within_range(Asteroid)");

        }
    }

    private void mine(){

    }

    private void dumpCargo(){
        cargo = 0;
        removeBelief("have_cargo");
        removeBelief("cargo_full");
    }

    private void returnToBase(){
        target_pos = new float [] {0,0,0};
        move();
    }

    private void goTo(){
        String [] keys = (String [])world_objects.keySet().toArray();
        target_pos = world_objects.get(keys[0]);
        move();
    }

    private void defaultMove(){
        target_pos = new float [] {100, 100, 100};
        move();
    }

    //used for doing actions (from action queue)
    private void doAct(Action a){
        switch(a){
            case RETURN_TO_BASE:
                returnToBase();
                break;

            case GO_TO:
                goTo();
                break;

            case MOVE:
                move();
                break;

            case MINE:
                mine();
                break;

            case DUMP_CARGO:
                dumpCargo();
                break;

            default:
                defaultMove();
                break;
        }
    }

    //percepts come in the form a list of object name (in the future might turn into unique ids) and positions
    public void getPercepts(HashMap<String, float []> world_obj){
        this.world_objects = world_obj;

        //NOTE: this should technically be in the "see" method but fits in here too
        //if we see an object we also believe that we see it
        if (world_objects.size() > 0){
            String [] object_names = (String[]) (world_objects.keySet()).toArray();
            for (int i = 0; i < object_names.length; i++){
                addBelief("see("+object_names[i]+")");
            }
        }
    }

    //convert percepts to beliefs
    private void see(){
        //cargo beliefs
        if (cargo > 0) {
            addBelief("have_cargo");
            if (cargo == max_cargo) {
                addBelief("cargo_full");
            }
        }

        //object beliefs
        if (world_objects.size() > 0){
            String [] object_names = (String[]) (world_objects.keySet()).toArray();
            for (int i = 0; i < world_objects.size(); i++){
               checkRanges(world_objects.get(object_names[i]));
            }
        }

        //position beliefs
        if ((x == 0) && (y == 0) && (z == 0)){
            addBelief("at_base");
        }

    }

    //method for adding world beliefs (we only add a belief if it isn't there already)
    private void addBelief(String belief){
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


    //if we have an item in the action queue the agent acts otherwise we replan (getting new percepts etc)
    public void agentBehave(HashMap<String, float []> world_obj){
        if (action_queue.size() < 1){
            getPercepts(world_obj);
            beliefRevision();
            //once plan generation works we will be able to generate entire plans using (generatePlan)
            action_queue.add(Inference.infer(beliefs));
            System.out.println("Planning");
        }
        else {
            doAct(action_queue.get(0));
            System.out.println("X: "+x+" Y: "+y+" Z: "+z+" ACT: "+action_queue.get(0));
            action_queue.remove(0);
        }

    }

    //for now this seems superfluous but as more different kind of objects are added this makes it easy to extract
    //the type of objects
    public String extractObj(String object_name){
        if(object_name.substring(0,8).equals("Asteroid")){
            return "Asteroid";
        }
        return null;
    }

    public float[] getPosition(){
        return new float [] {x,y,z};
    }

    //get 2d representation for the front end
    public int[] get2DAgent(){
        return new int [] {(int)x,(int)y};
    }
}