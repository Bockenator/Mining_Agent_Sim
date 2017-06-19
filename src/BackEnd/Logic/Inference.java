package BackEnd.Logic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by tom on 14/06/17.
 */

/*We need only one instance of this class for this simulation. Given that the rules for all agents are the same we can
  instancise the rules here. But given that all agents beliefs are different they will have to pass their own queries
  and kbs*/
public class Inference {

    //The list of rules that an agent can take <- as the sim increases in scope this will need to be increased
    static ArrayList<Rule> rules = new ArrayList<Rule>();


    //rules will be init-ed here (might turn into a reading in of external rules in .txt format)
    public static void init_rules(){
        rules.add(new Rule(new String[] {"at_base","have_cargo"}, new String[] {}, Action.DUMP_CARGO));
        rules.add(new Rule(new String[] {"cargo_full"}, new String[] {}, Action.RETURN_TO_BASE));
        rules.add(new Rule(new String[] {"within_range(Asteroid)"}, new String[] {}, Action.MINE));
        rules.add(new Rule(new String[] {"see(Asteroid"}, new String[] {}, Action.GO_TO));
    }

    //basic exhaustive kb query-ing
    public static boolean queryKB(String[] query, ArrayList<String> kb){
        for (int i = 0; i<query.length; i++){
            if (!(kb.contains(query[i]))){
                return false;
            }
        }
        return true;
    }


    //Basically just a case switch -> query the kb on all rules and return the appropriate act (or return move)
    public static Action infer(ArrayList<String> kb){
        //check if any rule can be infered
        for (int i = 0; i<rules.size(); i++){
            //query the kb on all pre-conditions for all rules
            //if any can be satisfied return the action
            if (queryKB(rules.get(i).precond, kb)){
                return rules.get(i).act;
            }
        }
        return Action.MOVE;
    }

    //generate plans
    public static ArrayList<Action> gen_plan(String[] kb){
        ArrayList<Action> plan = new ArrayList<Action>();

        return plan;
    }
}
