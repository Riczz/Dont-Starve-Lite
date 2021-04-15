package dslite.world.entity;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Objects;

/**
 * A játékban használt textúrák betöltését végrehajtó Singleton osztály.
 * A WorldMap osztály használja fel.
 *
 * @see dslite.world.WorldMap
 */
public final class Textures {

    /**
     * A útvonalakat, és az indexeket tartalmazó HashMap
     */
    private static HashMap<Integer, String> texturePaths;

    /**
     * A kéepeket, és az indexeket tartalmazó HashMap
     */
    private static HashMap<Integer, Image> textures;

    private static Textures instance = null;

    /**
     * A textúrákhoz tartozó útvonalakhoz indexet rendel,
     * majd ezekhez hozzátársítja a képeket.
     */
    private Textures() {

        texturePaths = new HashMap<>();
        textures = new HashMap<>();

        //GAME OBJECTS
        texturePaths.put(1, "/dslite/textures/objects/wilson.png");
        texturePaths.put(2, "/dslite/textures/objects/boulder.png");
        texturePaths.put(3, "/dslite/textures/objects/boulder_flintless.png");
        texturePaths.put(4, "/dslite/textures/objects/bush.png");
        texturePaths.put(5, "/dslite/textures/objects/bush_picked.png");
        texturePaths.put(6, "/dslite/textures/objects/evergreen.png");
        texturePaths.put(7, "/dslite/textures/objects/evergreen2.png");
        texturePaths.put(8, "/dslite/textures/objects/flower.png");
        texturePaths.put(9, "/dslite/textures/objects/flower2.png");
        texturePaths.put(10, "/dslite/textures/objects/flower3.png");
        texturePaths.put(11, "/dslite/textures/objects/flower4.png");
        texturePaths.put(12, "/dslite/textures/objects/flower5.png");
        texturePaths.put(13, "/dslite/textures/objects/flower6.png");
        texturePaths.put(14, "/dslite/textures/objects/flower7.png");
        texturePaths.put(15, "/dslite/textures/objects/flower8.png");
        texturePaths.put(16, "/dslite/textures/objects/goldvein.png");
        texturePaths.put(17, "/dslite/textures/objects/grass.png");
        texturePaths.put(18, "/dslite/textures/objects/grass_picked.png");
        texturePaths.put(19, "/dslite/textures/objects/sapling.png");
        texturePaths.put(20, "/dslite/textures/objects/sapling_picked.png");
        texturePaths.put(21, "/dslite/textures/objects/spikybush.png");
        texturePaths.put(22, "/dslite/textures/objects/spikybush_picked.png");
        texturePaths.put(23, "/dslite/textures/objects/spikytree.png");

        //CRAFTING
        texturePaths.put(24, "/dslite/textures/crafting/cut_grass.png");
        texturePaths.put(25, "/dslite/textures/crafting/flint.png");
        texturePaths.put(26, "/dslite/textures/crafting/gold.png");
        texturePaths.put(27, "/dslite/textures/crafting/log.png");
        texturePaths.put(28, "/dslite/textures/crafting/petals.png");
        texturePaths.put(29, "/dslite/textures/crafting/rocks.png");
        texturePaths.put(30, "/dslite/textures/crafting/twigs.png");

        //FOOD
        texturePaths.put(31, "/dslite/textures/food/berries.png");
        texturePaths.put(32, "/dslite/textures/food/berries_cooked.png");
        texturePaths.put(33, "/dslite/textures/food/carrot.png");
        texturePaths.put(34, "/dslite/textures/food/carrot_cooked.png");
        texturePaths.put(35, "/dslite/textures/food/bluecap.png");
        texturePaths.put(36, "/dslite/textures/food/greencap.png");
        texturePaths.put(37, "/dslite/textures/food/redcap.png");

        //SURVIVAL
        texturePaths.put(38, "/dslite/textures/survival/campfire.png");
        texturePaths.put(39, "/dslite/textures/survival/garland.png");

        //TOOLS
        texturePaths.put(40, "/dslite/textures/tools/axe.png");
        texturePaths.put(41, "/dslite/textures/tools/pickaxe.png");

        for (Integer key : texturePaths.keySet()) {
            textures.put(key, getImage(key));
        }

        instance = this;
    }

    private Image getImage(Integer key) {
        String path = texturePaths.get(key);
        if (path != null) {
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        }

        //Ha nem jó az elérési útvonal megpróbáljuk az elsőt visszaadni.
        return new Image(texturePaths.get(0));
    }

    /**
     * Spritehoz tartozó kép visszaadása
     *
     * @param sprite A sprite
     * @return A Spritehoz tartozó kép
     * @see Sprite
     */
    public static Image getTexture(Sprite sprite) {
        return textures.get(sprite.getIndex());
    }

    public static Textures getInstance() {
        return instance == null ? new Textures() : instance;
    }
}
