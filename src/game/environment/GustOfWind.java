package game.environment;

import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * A wind environment called GustOfWind, so windy!
 * @author Yeoh Ming Wei
 */
public class GustOfWind extends Ground {
    
	/**
	 * A constructor for the GustOfWind class
	 */
    public GustOfWind() {
		// The display character for GustofWind
		super('&');
	}

	/**
	 * A method that manaage the enemy spawning every turn. The "if" conditional
	 * will determine if it is east side or west side. 
	 */
	@Override	
	public void tick(Location location) {
		super.tick(location) ;

		int mid = location.map().getXRange().max() / 2 ;
		
		if (location.x() < mid) {
			WestMapEnemyFactory.getInstance().addEnemy(this, location) ;
		} else {
			EastMapEnemyFactory.getInstance().addEnemy(this, location) ;
		}
			
	}
}

