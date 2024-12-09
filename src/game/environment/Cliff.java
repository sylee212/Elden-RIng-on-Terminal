package game.environment;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.Status;
import game.action.DeathAction;
import game.enemy.ActorTypes;
import game.player.Player;

public class Cliff extends Ground {
    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     */
    public Cliff() {
        super('+');
        addCapability(Status.ITEM_NOT_SPAWNABLE) ;
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return actor.hasCapability(ActorTypes.PLAYER);
    }

    @Override
    public void tick(Location location){
        Player player = Player.getInstance();
        if(location.containsAnActor()){
            //call death action
            Actor deadActor = location.getActor();
            DeathAction deathAction = new DeathAction(player, deadActor);
            String result = deathAction.execute(player, location.map());
            System.out.println(result);
            System.out.println(player + " fell off the cliff and died.");
        }
    }
}
