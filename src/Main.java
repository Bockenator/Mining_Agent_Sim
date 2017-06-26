/**
 * Created by tom on 05/06/17.
 */
import FrontEnd.*;
import BackEnd.Agent.*;
import BackEnd.Environment.*;
import BackEnd.Logic.*;

public class Main {

    int unique_id = 0;

    public static void main(String [ ] args) {
        //init the rules
        Inference.init_rules();
        int a = 0;
        int[][] agents2d = new int[1][2];
        Agent x = new Agent(unique_id);
        unique_id++;
        Environment tom_e = new Environment(800, 20,unique_id);
        tom_e.initAsteroids();
        tom_e.overwriteAst(new Asteroid[] {new Asteroid(10,10,10,unique_id)});
        unique_id++;

        agents2d[0] = x.get2DAgent();
        Base bs = new Base(800, tom_e.get2DAsteroids(),agents2d);

        //basic while loop
        while(true) {
            agents2d[0] = x.get2DAgent();
            bs.update(tom_e.get2DAsteroids(), agents2d);
            x.agentBehave(tom_e.getAllInRange(200,x.getPosition()));
            a++;
        }
    }

}
