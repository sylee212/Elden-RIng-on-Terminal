package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.enemy.GiantCrab;
import game.enemy.HeavySkeletonSwordsman;
import game.enemy.LoneWolf;

/**
 * This Enemy Factory helps to spawn enemies that belongs to West side
 * of the map. 
 * @author Yeoh Ming Wei
 */
public class WestMapEnemyFactory implements EnemyFactory {
    
    /**
     * An null assignment for westFactory.
     */
    private static WestMapEnemyFactory westFactory = null;

    /**
     * The private constructor for westFactory.
     */
    private WestMapEnemyFactory() {
    }

    /**
     * A method to assign new WestMapEnemyFactory.
     * @return A new WestMapEnemFactory class
     */
    public static WestMapEnemyFactory getInstance() {
        if (westFactory == null) 
            westFactory = new WestMapEnemyFactory() ;
        return westFactory ;
    }

    /**
     * A method to add new West side enemy into the map.
     */
    public void addEnemy(Ground ground, Location location) {

        int p = RandomNumberGenerator.getRandomInt(100) ;

        if (p < 27 && !location.map().isAnActorAt(location) && ground.getDisplayChar() == 'n') {
            location.map().at(location.x(), location.y()).addActor(new HeavySkeletonSwordsman()) ;
        }
        
        if (p < 33 && !location.map().isAnActorAt(location) && ground.getDisplayChar() == '&') {
            location.map().at(location.x(), location.y()).addActor(new LoneWolf()) ;
        }

        if (p < 2 && !location.map().isAnActorAt(location) && ground.getDisplayChar() == '~') {
            location.map().at(location.x(), location.y()).addActor(new GiantCrab()) ;
        } 

    }
}


