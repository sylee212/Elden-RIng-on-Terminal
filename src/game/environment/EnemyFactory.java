package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * An interface class for EnemyFactory to manage the enemies
 * spawn. The EastMapEnemyFactory and WestMapEnemyFactory 
 * will be the one implementing this interface.
 * @author Yeoh Ming Wei
 */
public interface EnemyFactory {

    /**
     * A method to add enemy into the map. 
     * @param ground The specific environement. 
     * @param location The location of the environment.
     */
     void addEnemy(Ground ground, Location location) ;

}
