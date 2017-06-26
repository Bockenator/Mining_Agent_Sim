package BackEnd.Environment;

/**
 * Created by tom on 12/06/17.
 */
public class Asteroid {
    private String id;
    float x,y,z;
    //The constructor is set up so that all randomisation is done in the env. class (also means that its easier
    //to create test asteroids)
    public Asteroid(float x, float y, float z, int id_num){
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = "Asteroid"+id_num;
    }

    public String getID(){
      return id;
    }
}
