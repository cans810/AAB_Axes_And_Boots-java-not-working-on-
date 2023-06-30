package UI;

import game.GamePanel;

import java.awt.*;

public class ActionsGUI extends AABGUI {

    GamePanel gp;

    int forwardxDistance = 18;
    int forwardyDistance = -16;
    int backwardxDistance = 10;

    public AABButton forward;
    String forwardSymbol;
    public boolean forwardAvailable;

    public AABButton backward;
    String backwardSymbol;
    public boolean backwardAvailable;

    public AABButton attackLight;
    String attackLightSymbol;
    public boolean attackLightAvailable;

    public AABButton attackLeaping;
    String attackLeapingSymbol;
    public boolean attackLeapingAvailable;

    public ActionsGUI(GamePanel gp){
        super(gp);
        this.gp = gp;
        forward = new AABButton(gp.player.torso.x+(forwardxDistance*gp.tileSize),gp.player.torso.y+(forwardyDistance*gp.tileSize),gp.tileSize*8,gp.tileSize*8,gp);
        forwardSymbol = "FRW";
        forwardAvailable = true;

        backward = new AABButton(gp.player.torso.x-(backwardxDistance*gp.tileSize),gp.player.torso.y+(forwardyDistance*gp.tileSize),gp.tileSize*8,gp.tileSize*8,gp);
        backwardSymbol = "BCW";
        backwardAvailable = true;

        attackLight = new AABButton(gp.player.torso.x+(forwardxDistance*gp.tileSize),gp.player.torso.y+(forwardyDistance),gp.tileSize*8,gp.tileSize*8,gp);
        attackLightSymbol = "ATL";
        attackLightAvailable = false;

        attackLeaping = new AABButton(gp.player.torso.x+(forwardxDistance*gp.tileSize),gp.player.torso.y+(-forwardyDistance*gp.tileSize),gp.tileSize*8,gp.tileSize*8,gp);
        attackLeapingSymbol = "ATLEAP";
        attackLeapingAvailable = false;
    }

    public void update(){

        /*forward.x = gp.player.torso.x+(forwardxDistance*gp.tileSize);
        forward.y = gp.player.torso.y+(forwardyDistance*gp.tileSize);

        backward.x = gp.player.torso.x+(backwardxDistance*-gp.tileSize);
        backward.y = gp.player.torso.y+(forwardyDistance*gp.tileSize);

        attackLight.x = gp.player.torso.x+(forwardxDistance*gp.tileSize);
        attackLight.y = gp.player.torso.y+(forwardyDistance);

        attackLeaping.x = gp.player.torso.x+(forwardxDistance*gp.tileSize);
        attackLeaping.y = gp.player.torso.y+(-forwardyDistance*gp.tileSize);*/

        forward.x = 100;
        forward.y = gp.screenHeight - 100;

        backward.x = 200;
        backward.y = gp.screenHeight - 100;

        attackLight.x = 300;
        attackLight.y = gp.screenHeight - 100;

        attackLeaping.x = 400;
        attackLeaping.y = gp.screenHeight - 100;

    }

    public void draw(Graphics2D g2){

        if (!gp.player.inForwardWalkingProcess && !gp.player.inLeapAttackProcess && !gp.player.inBackwardWalkingProcess){

            g2.setColor(new Color(0,150,0));
            g2.draw(backward);
            g2.setColor(new Color(0,0,0));
            g2.drawString(backwardSymbol, (int)backward.x+gp.tileSize/2, (int)backward.y+gp.tileSize*4);

            if (gp.player.canMoveForward){
                g2.setColor(new Color(0,150,0));
                g2.fill(forward);
                g2.setColor(new Color(0,0,0));
                g2.drawString(forwardSymbol, (int)forward.x+gp.tileSize/2, (int)forward.y+gp.tileSize*4);
            }
            else{
                g2.setColor(new Color(150,0,0));
                g2.fill(forward);
                g2.setColor(new Color(0,0,0));
                g2.drawString(forwardSymbol, (int)forward.x+gp.tileSize/2, (int)forward.y+gp.tileSize*4);
            }

            if (gp.player.canAttack){
                g2.setColor(new Color(0,150,0));
                g2.fill(attackLight);
                g2.setColor(new Color(0,0,0));
                g2.drawString(attackLightSymbol, (int)attackLight.x+gp.tileSize/2, (int)attackLight.y+gp.tileSize*4);
            }
            else{
                g2.setColor(new Color(150,0,0));
                g2.fill(attackLight);
                g2.setColor(new Color(0,0,0));
                g2.drawString(attackLightSymbol, (int)attackLight.x+gp.tileSize/2, (int)attackLight.y+gp.tileSize*4);
            }

            if (gp.player.canAttackLeaping){
                g2.setColor(new Color(0,150,0));
                g2.fill(attackLeaping);
                g2.setColor(new Color(0,0,0));
                g2.drawString(attackLeapingSymbol, (int)attackLeaping.x+gp.tileSize/2, (int)attackLeaping.y+gp.tileSize*4);
            }
            else{
                g2.setColor(new Color(150,0,0));
                g2.fill(attackLeaping);
                g2.setColor(new Color(0,0,0));
                g2.drawString(attackLeapingSymbol, (int)attackLeaping.x+gp.tileSize/2, (int)attackLeaping.y+gp.tileSize*4);
            }
        }

    }



}
