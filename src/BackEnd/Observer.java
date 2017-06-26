package BackEnd;

import BackEnd.Agent.Agent;

/**
 * Created by tom on 25/06/17.
 */
public abstract class Observer {
    protected Agent agent;
    public abstract void update(String id);
}
