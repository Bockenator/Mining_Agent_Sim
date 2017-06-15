package BackEnd.Logic;

/**
 * Created by tom on 14/06/17.
 */
public class Rule {
    String[] precond;
    String[] postcond;
    Action act;

    public Rule(String[] precond, String[] postcond, Action act) {
        this.precond = precond;
        this.postcond = postcond;
        this.act = act;
    }

    //PLEASE FOR THE LOVE OF GOD CHANGE THIS TO BE SEND THE RULE AS A QUERY TO THE KB RATHER THAN THIS
   /* public String[] check_cond(String[] kb){
        for (int i = 0; i<precond.length; i++){
            kb.contains(precond[i]);
        }

        return postcond;
    }*/
}
