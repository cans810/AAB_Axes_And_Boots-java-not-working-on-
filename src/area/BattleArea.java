package area;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BattleArea {
    GamePanel gp;
    BufferedImage background_arena1;

    public BattleArea(GamePanel gp){
        this.gp = gp;
        setImages();
    }

    public void setImages(){
        //background_arena1 = setupImage("/background_arena1",gp.screenWidth,gp.screenHeight);
        background_arena1 = setupImage("/arenabig",gp.arenaScreenWidth,gp.arenaScreenHeight);
    }

    public void update(){

    }

    public void draw(Graphics2D g2){

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR); // more smooth
        //g2.drawImage(background_arena1,-gp.player.torso.getBounds().x + gp.screenWidth/2,0,null);
        g2.drawImage(background_arena1,0,0,null);

    }

    public BufferedImage setupImage(String path,int width,int height){
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(path +".png"));
            image = gp.scaleImage(image,width,height);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
