package game.enemy;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.WeaponItem;
import game.weapon.WeaponStatus;

/**
 * Spooky, spooky skeleton
 * @author Lee Sing Yuan
 */
public abstract class ParentSkeleton extends Enemy {
    /**
     * The pile of bones counter
     */
    private int counter;
    private final int counterReset;
    private final int counterMax;

    /**
     * The runes that this actor can drop
     */
    private final int SKELETON_MIN_RUNE = 35 ;
    private final int SKELETON_MAX_RUNE = 892 ;


    public ParentSkeleton(String initName, char initDisplay, int initHp) {
        super(initName,initDisplay,initHp);

        // adding the skill
        this.addCapability(PileOfBones.PILE_OF_BONES);

        // adding the type
        this.addCapability(ActorTypes.PARENTSKELETON);

        counterReset = -1;
        counterMax = 3;

        // -1 cause immediate increment at playTurn if want actual 3 rounds of visible pileOfBones
        counter = counterReset;
    }

    /**
     * Select and return an action to perform on the current turn.
     *
     * Approach description:
     *      1) check if the actor does not has the ability pile of bones
     *              1.1) if no, then start counter and change to pile of bones
     *                  1.1.1) if counter == max number of counter,
     *                         1.1.1.1) revive
     *              1.2) if have, do normal play turn
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to be performed
     */

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        // because to use weapons' get allowableActions,
        // it needs to know the current locations
        // so need to tick first
        for ( WeaponItem w : this.getWeaponInventory() )
        {
            if ( w.hasCapability(WeaponStatus.HAVE_NOT_TICKED) ) {
                w.tick(map.locationOf(this), this);
                actions.add(w.getAllowableActions());
            }
        }

        // because to use items' get allowableActions,
        // it needs to know the current locations
        // so need to tick first
        for ( Item i: this.getItemInventory() )
        {
            if ( i.hasCapability(WeaponStatus.HAVE_NOT_TICKED) ) {
                i.tick(map.locationOf(this), this);
                actions.add(i.getAllowableActions());
            }
        }

        // this means that, the HSS has used its skill of pile of bones
        // so it's removed to allow checking of is it dead or should he become a pile of bones
        if (!this.hasCapability(PileOfBones.PILE_OF_BONES)) {

            // change the display and start the counter
            this.setDisplayChar('X');
            counter++;

            // if the HSS has been un - attacked for 3 rounds, revive
            if (counter == counterMax) {

                // give the HSS his skill back so that can be used again if he died again
                this.addCapability(PileOfBones.PILE_OF_BONES);

                // reset his display
                this.setDisplayChar('q');

                //resetting his HP
                this.resetMaxHp(getMaxHp());

                // reset the counter so can be used again
                counter = counterReset;

            }

        }

        // if the HSS has the skill, means he hasnt died and should perform one of the following actions
        else {
            return super.playTurn(actions,lastAction,map,display);
        }
        return new DoNothingAction();
    }

    public int getMinRune() {
        return SKELETON_MIN_RUNE ;
    }

    public int getMaxRune() {
        return SKELETON_MAX_RUNE ;
    }

}

