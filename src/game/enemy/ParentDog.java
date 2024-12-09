package game.enemy;

/**
 * BEHOLD, DOG!
 * @author Lee Sing Yuan
 */
public abstract class ParentDog extends Enemy {

    /**
     * The constructor for Dog parent
     * @param initName name
     * @param initDisplay UI char
     * @param initHp hit points
     */
    public ParentDog(String initName, char initDisplay, int initHp) {
        super(initName,initDisplay,initHp);

        // adding the type
        this.addCapability(ActorTypes.PARENTDOG);
    }

}
