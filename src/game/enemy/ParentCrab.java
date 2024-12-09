package game.enemy;


/**
 * Crab Rave!
 * @author Lee Sing Yuan
 */
public abstract class ParentCrab extends Enemy {

    /**
     * The constructor for Crab parent
     * @param initName name
     * @param initDisplay UI char
     * @param initHp hit points
     */
    public ParentCrab(String initName, char initDisplay, int initHp) {
        super(initName,initDisplay,initHp);

        // adding the type
        this.addCapability(ActorTypes.PARENTCRAB);
    }
}