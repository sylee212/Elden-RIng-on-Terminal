package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.RandomNumberGenerator;
import game.enemy.GiantCrayFish;
import game.enemy.GiantDog;
import game.enemy.SkeletalBandit;

/**
 * This Enemy Factory helps to spawn enemies that belongs to East side
 * of the map. 
 * @author Yeoh Ming Wei
 */
public class EastMapEnemyFactory implements EnemyFactory {
    
    /**
     * An null assignment for eastFactory.
     */
    private static EastMapEnemyFactory eastFactory = null;

    /**
     * The private constructor for eastFactory.
     */
    private EastMapEnemyFactory() {}

    /**
     * A method to assign new EastMapEnemyFactory.
     * @return A new EastMapEnemFactory class
     */
    public static EastMapEnemyFactory getInstance() {
        if (eastFactory == null) 
            eastFactory = new EastMapEnemyFactory() ;
        return eastFactory ;
    }

    /**
     * A method to add new East side enemy into the map.
     */
    public void addEnemy(Ground ground, Location location) {
        int p = RandomNumberGenerator.getRandomInt(100) ;

        if (p < 27 && !location.map().isAnActorAt(location) && ground.getDisplayChar() == 'n') {
            location.map().at(location.x(), location.y()).addActor(new SkeletalBandit()) ;
        }
        
        if (p < 4 && !location.map().isAnActorAt(location) && ground.getDisplayChar() == '&') {
            location.map().at(location.x(), location.y()).addActor(new GiantDog()) ;
        }

        if (p < 1 && !location.map().isAnActorAt(location) && ground.getDisplayChar() == '~') {
            location.map().at(location.x(), location.y()).addActor(new GiantCrayFish()) ;
        }       
    }
}
