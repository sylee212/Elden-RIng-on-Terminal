package game;

import edu.monash.fit2099.engine.positions.GameMap;
import java.util.ArrayList;
import java.util.List;

/**
 * A reset manager class that manages a list of resettables.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 *
 */
public class ResetManager {
    private static List<Resettable> resettables;
    private static ResetManager instance;
    public static GameMap map;



    /**
     * HINT 1: where have we seen a private constructor before?
     * HINT 2: see the instance attribute above.
     */
    private ResetManager() {
        resettables = new ArrayList<>();
    }

    public static void run(GameMap map) {
        for (Resettable resettable : resettables){
            if (resettable != null) {
                resettable.reset(map);
            }
        }

    }

    public static void registerResettable(Resettable resettable) {
        if (resettables == null) {
            instance = new ResetManager();
        }
        resettables.add(resettable);
    }

    public static void removeResettable(Resettable resettable) {
        resettables.remove(resettable);
    }

}
