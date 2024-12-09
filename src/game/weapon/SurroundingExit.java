package game.weapon;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;

/**
 * An extra class to hold information
 * Note: this class is created to store information used in NearMe
 * @author Lee Sing Yuan
 * @see game.action.NearMe
 */
public class SurroundingExit {
    private Exit exit;
    private Actor target;

    /**
     * Store data directly
     * @param target the target
     * @param exit the exit information
     */
    public SurroundingExit(Actor target , Exit exit){
        setTarget(target);
        setExit(exit);
    }

    public Exit getExit() {
        return exit;
    }

    public void setExit(Exit exit) {
        this.exit = exit;
    }

    public Actor getTarget() {
        return target;
    }

    public void setTarget(Actor target) {
        this.target = target;
    }
}
