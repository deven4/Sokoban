package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Singleton class.
 **/
public class Assets {

    private static Assets assets;
    public final Image grass, brickWall, crate, ground,
            truePlace, character, wallTextureBG, padlock,
            trueCrate, winBG;

    private Assets() {
        grass = loadImage("/grass_bg.png");
        brickWall = loadImage("/wall_bg.png");
        crate = loadImage("/crate_bg.png");
        ground = loadImage("/ground.png");
        truePlace = loadImage("/true_pos.png");
        character = loadImage("/player.png");
        wallTextureBG = loadImage("/texture_wall.png");
        padlock = loadImage("/lock.png");
        trueCrate = loadImage("/placed_crate.png");
        winBG = loadImage("/win_bg_1.png");
    }

    public static Assets getInstance() {
        if (assets == null)
            return new Assets();
        return assets;
    }

    public static void setAssets(Assets assets) {
        Assets.assets = assets;
    }

    private Image loadImage(String path) {
        try {
            return ImageIO.read(Assets.class.getResource(path));
        } catch (IOException exception) {
            System.err.println("Exception: " + exception.getMessage());
        }
        return null;
    }
}
