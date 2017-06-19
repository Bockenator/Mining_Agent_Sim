/**
 * Created by tom on 05/06/17.
 */
import FrontEnd.*;
import BackEnd.Agent.*;
import BackEnd.Environment.*;
import BackEnd.Logic.*;

public class Main {

    public static void main(String [ ] args) {
        //init the rules
        Inference.init_rules();

        int[][] agents2d = new int[1][2];
        Agent x = new Agent();
        Environment tom_e = new Environment(800, 20);
        tom_e.initAsteroids();

        agents2d[0] = x.get2DAgent();
        Base bs = new Base(800, tom_e.get2DAsteroids(),agents2d);
        while(true) {
            agents2d[0] = x.get2DAgent();
            bs.update(tom_e.get2DAsteroids(), agents2d);
            x.agentBehave(tom_e.getAllInRange(10,x.getPosition()));
        }
    }

}