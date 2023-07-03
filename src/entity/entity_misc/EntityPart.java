package entity.entity_misc;

import game.GamePanel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class EntityPart extends Rectangle2D.Double {
    GamePanel gp;
    public String entityDirection;

    public BufferedImage image;

    public Point2D.Double pivotPoint;

    public Shape attachedPart;
    public Point2D.Double attachPoint;

    public double rotationAngle;

    public EntityPart(double x,double y,int width,int height,GamePanel gp){
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        pivotPoint = new Point2D.Double();
        attachPoint = new Point2D.Double();

        pivotPoint.setLocation(x,y);
    }

    public void drawPivotPoint(Graphics2D g2){

        g2.setColor(Color.white);
        g2.drawLine((int)pivotPoint.x, (int)pivotPoint.y, (int)pivotPoint.x, (int)pivotPoint.y);
    }

}
