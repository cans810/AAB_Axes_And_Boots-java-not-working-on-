package UI;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class AABGUI {
    public GamePanel gp;

    public Point mousePos;

    public AABGUI(GamePanel gp){
        this.gp = gp;
    }

    public void setImages(){

    }

    public void update() {

    }

    public void draw(Graphics2D g2) {

    }

    public boolean isMouseWithinComponent(Component c,Rectangle area)
    {
        mousePos = c.getMousePosition();

        if (mousePos != null){
            return area.intersects(mousePos.x,mousePos.y,1,1);
        }
        else {
            return false;
        }
    }

    public BufferedImage setupImage(String path, int width, int height){
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
