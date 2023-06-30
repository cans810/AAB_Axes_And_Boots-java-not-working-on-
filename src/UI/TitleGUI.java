package UI;

import game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TitleGUI extends AABGUI {
    GamePanel gp;

    BufferedImage titleBackground;

    BufferedImage gameTitle;
    int gameTitleX;
    int gameTitleY;

    public Rectangle newGameTitleArea;
    BufferedImage newGameTitle;
    BufferedImage newGameTitleHovered;
    boolean hoveringNewGameTitle;

    public Rectangle exitTitleArea;
    BufferedImage exitTitle;
    BufferedImage exitTitleHovered;
    boolean hoveringExitTitle;

    public TitleGUI(GamePanel gp){
        super(gp);
        this.gp = gp;

        gameTitleX = gp.screenWidth/2 - 310;
        gameTitleY = gp.tileSize*20;

        newGameTitleArea = new Rectangle(gameTitleX+188,gameTitleY+199,250,49);

        exitTitleArea = new Rectangle(gameTitleX+267,gameTitleY+341,90,50);

        setImages();
    }

    public void setImages(){
        gameTitle = setupImage("/game_title",656,109);
        titleBackground = setupImage("/title_background",1440,768);
        newGameTitle = setupImage("/new_game_title",271,60);
        exitTitle = setupImage("/exit_title",112,62);
        newGameTitleHovered = setupImage("/new_game_title_hovered",273,62);
        exitTitleHovered = setupImage("/exit_title_hovered",114,64);
    }

    public void update(){

        if (isMouseWithinComponent(gp,newGameTitleArea)){
            hoveringNewGameTitle = true;
        }
        else if (!isMouseWithinComponent(gp,newGameTitleArea)){
            hoveringNewGameTitle = false;
        }

        if (isMouseWithinComponent(gp,exitTitleArea)){
            hoveringExitTitle = true;
        }
        else if (!isMouseWithinComponent(gp,exitTitleArea)){
            hoveringExitTitle = false;
        }

    }

    public void draw(Graphics2D g2){

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR); // more smooth for images
        g2.drawImage(titleBackground,0,0,null);

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR); // more smooth for images
        g2.drawImage(gameTitle,gameTitleX,gameTitleY,null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,36F));
        g2.setColor(new Color(180,40,12));

        if (hoveringNewGameTitle){
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR); // more smooth for images
            g2.drawImage(newGameTitleHovered,gameTitleX+180,gameTitleY+196,null);
        }
        else{
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR); // more smooth for images
            g2.drawImage(newGameTitle,gameTitleX+180,gameTitleY+196,null);
            //g2.draw(newGameTitleArea);
        }

        if (hoveringExitTitle){
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR); // more smooth for images
            g2.drawImage(exitTitleHovered,exitTitleArea.x-10,gameTitleY+339,null);
            //g2.draw(exitTitleArea);
        }
        else{
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR); // more smooth for images
            g2.drawImage(exitTitle,exitTitleArea.x-10,gameTitleY+339,null);
            //g2.draw(exitTitleArea);
        }

    }

}
