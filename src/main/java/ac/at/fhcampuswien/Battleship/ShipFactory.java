
package ac.at.fhcampuswien.Battleship;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;


import java.util.ArrayList;
import java.util.Iterator;

import static com.almasb.fxgl.dsl.FXGL.*;



/**
 * This class is not functional at the moment. Logic for spawning needs to be written and hooked up.
 *
 * Ships get a graphical look for different levels like horizontal and vertical.
 */

public class ShipFactory implements EntityFactory {

    public enum Type {
        SHIP
    }

    private static String nextShipSprite;

    /**
     * resolves a given ship type given by its length/typefield and its orientation to a sprite name saved on disk
     *
     * Note: vertical ship names should be horizontal, files are named wrong
     *
     * @param type int
     * @param horizontal boolean
     * @return String
     */

    private static String spriteResolver(int type, boolean horizontal){
        String sprite = null;


        if (!horizontal){
            switch (type){
                case 1 -> sprite = "ship_1x1.png";
                case 2 -> sprite = "ship_1x2_vertical.png";
                case 3 -> sprite = "ship_1x3_vertical.png";
                case 4 -> sprite = "ship_1x4_vertical.png";
                case 5 -> sprite = "ship_1x5_vertical.png";
            }
        }else{
            switch (type){
                case 1 -> sprite = "ship_1x1.png";
                case 2 -> sprite = "ship_1x2.png";
                case 3 -> sprite = "ship_1x3.png";
                case 4 -> sprite = "ship_1x4.png";
                case 5 -> sprite = "ship_1x5.png";
            }
        }
        return sprite;
    }

    /**
     * spawns ships
     * @param player Player
     */

    public static void updateShipSpawns(Player player) {
        Ship ship;
        ArrayList<Ship> shipsList = player.getShipInstances();

        if (shipsList.size() != 0) {
            Iterator<Ship> iterator = shipsList.iterator();

            for (int i = 0; i < shipsList.size(); i++) {
                ship = iterator.next();
                nextShipSprite = spriteResolver(ship.getType(), ship.isVertical());
                spawn("ship", ship.getX(), ship.getY());
            }
        }
    }

    @Spawns("ship")
    public Entity newShip(SpawnData data){

       /* The code inside here is redundant - still, keeping it for reference how javaFX handles asset loading

        File test = new File(nextShipSpriteLocation);
        FileInputStream input = new FileInputStream(test.getAbsolutePath());
        Image img = new Image(input);
        ImageView view = new ImageView(img);
        */

        var ship = FXGL.entityBuilder(data)
                .view(nextShipSprite)
                .type(Type.SHIP)
                .build();
        return ship;
    }
}
