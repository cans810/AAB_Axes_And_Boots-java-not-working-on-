package UI;

import game.GamePanel;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class AABButton extends Rectangle2D.Double{
    GamePanel gp;

    public BufferedImage button_image;
    public BufferedImage hovering_button_image;

    public AABButton(double x,double y,double width,double height,GamePanel gp){
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setButtonImage(BufferedImage buttonImage) {
        this.button_image = gp.scaleImage(buttonImage,this.getBounds().width,this.getBounds().height);
    }

    public void setHoveringButtonImage(BufferedImage hovering_button_image) {
        this.hovering_button_image = gp.scaleImage(hovering_button_image,this.getBounds().width,this.getBounds().height);
    }

}
