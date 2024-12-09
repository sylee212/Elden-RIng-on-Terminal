package game.enemy;

/**
 * An enum to have all the possible enemy types and behaviours
 *
 * For example, if an actor is player, all enemies will attack it
 * if an actor is Wolf, all enemies besides Wolf will attack it
 * if its trader, all enemies will not attack it
 * @author Lee Sing Yuan
 */
public enum ActorTypes {
    PLAYER,
    PARENTDOG,
    PARENTCRAB,
    PARENTSKELETON,
    TRADER,
    GODRICK,
    ALLY,
    INVADER
}
