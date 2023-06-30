package UI;

import entity.Entity;
import game.GamePanel;

import java.awt.*;

public class EntityGUI extends AABGUI {
    GamePanel gp;

    Entity entity;

    String entityHP;
    String entityName;

    int entityHPx;
    int entityHPy;

    int entityNamex;
    int entityNamey;

    public EntityGUI(GamePanel gp,Entity entity,String type){
        super(gp);
        this.gp = gp;
        this.entity = entity;
        entityHP = Integer.toString(entity.HP);
        entityName = entity.name;

        if (type.equals("player")){
            entityHPx = gp.tileSize*8;
            entityHPy = gp.tileSize*8;
            entityNamex = gp.tileSize*8;
            entityNamey = gp.tileSize*12;
        }
        else if (type.equals("enemy")){
            entityHPx = gp.tileSize*216;
            entityHPy = gp.tileSize*8;
            entityNamex = gp.tileSize*216;
            entityNamey = gp.tileSize*12;
        }
    }

    public void update(){
        entityHP = Integer.toString(entity.HP);
        entityName = entity.name;
    }

    public void draw(Graphics2D g2){

        g2.setFont(new Font("Tahoma", Font.BOLD, 20));
        g2.setColor(new Color(240,200,200));
        g2.drawString(entityHP,entityHPx,entityHPy);
        g2.drawString(entityName,entityNamex,entityNamey);

    }
}
