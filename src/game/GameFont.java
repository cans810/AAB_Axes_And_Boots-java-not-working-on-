package game;

import java.awt.*;
import java.io.File;

public class GameFont {
    GamePanel gp;

    public Font immortalFont;

    public GameFont(GamePanel gp){
        this.gp = gp;

        try{
            immortalFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/immortal.ttf"));
            immortalFont = immortalFont.deriveFont(30f);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
