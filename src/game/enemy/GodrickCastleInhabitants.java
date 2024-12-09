package game.enemy;

/**
 * Castle inhabitants
 * @author Lee Sing Yuan
 */
public abstract class GodrickCastleInhabitants extends Enemy {

    /**
     * The constructor for Castle inhabitants' parent
     * @param initName name
     * @param initDisplay UI char
     * @param initHp hit points
     */
    public GodrickCastleInhabitants(String initName, char initDisplay, int initHp) {
        super(initName,initDisplay,initHp);

        // add the type
        this.addCapability(ActorTypes.GODRICK);
    }

}
