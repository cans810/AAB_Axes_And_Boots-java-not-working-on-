package area;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class VillageArea{
    GamePanel gp;
    BufferedImage background_village;

    public Rectangle newBattleButton = new Rectangle();
    public String newBattleText = "Battle";

    public Rectangle goHomeButton = new Rectangle();

    public VillageArea(GamePanel gp) {
        this.gp = gp;
        setImages();
    }

    public void setImages(){
        background_village = setupImage("/background_village",gp.screenWidth,gp.screenHeight);
    }

    public void update() {



    }

    public void draw(Graphics2D g2) {

        g2.drawImage(background_village,0,0,null);

        newBattleButton.x = gp.screenWidth/3-325;
        newBattleButton.y = gp.screenHeight/8;
        newBattleButton.width = 75;
        newBattleButton.height = 75;

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
        g2.setColor(new Color(100,100,100));
        g2.fill(newBattleButton);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

        g2.setFont(new Font("Tahoma", Font.BOLD, 20));
        g2.setColor(new Color(20,0,150));
        g2.drawString(newBattleText,newBattleButton.x+6,newBattleButton.y+40);
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
