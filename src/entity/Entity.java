package entity;

import UI.EntityGUI;
import entity.entity_misc.EntityPart;
import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Entity {
    GamePanel gp;

    public EntityGUI gui;

    public String side;
    public boolean alive;

    public float screenX;
    public float screenY;

    // ENTITY PARTS
    public ArrayList<Shape> bodyParts = new ArrayList<>();

    public EntityPart head;

    public EntityPart torso;

    public EntityPart arm_right;

    public EntityPart forearm_right;

    public EntityPart arm_left;

    public EntityPart forearm_left;

    public EntityPart torso_lower;

    public EntityPart leg_right;

    public EntityPart calf_right;

    public EntityPart leg_left;

    public EntityPart calf_left;

    public EntityPart foot_right;

    public EntityPart foot_left;

    public Rectangle hitbox;

    // stats
    public int level;

    public int strength;
    public int magic;
    public int dexterity;
    public int vitality;
    public int offense;
    public int protection;
    public int charisma;
    public int stamina;

    // attributes
    public String name;

    public int baseHP = 20;
    public int HP;

    public int baseHitDamage = 2;
    public int hitDamage;

    public float baseStepSize;
    public float stepSize;

    public double size_multiplier = 1;
    public double heightChange = 0;

    // boolean values
    public boolean played;

    public boolean canMoveForward;
    public boolean canMoveBackward;
    public boolean canAttack;
    public boolean canAttackLeaping;

    public boolean inBattle;

    // ANIMATION
    public long thinkTimer = 0L;
    public long thinkTime = 200L;

    public double walkingAnim_ArmsSwingBackwardStartingPosAngle = -6;
    public double walkingAnim_ArmsSwingForwardStartingPosAngle = 6;

    public double walkingAnim_LegsSwingBackwardStartingPosAngle = 2;
    public double walkingAnim_LegsSwingForwardStartingPosAngle = 10;

    public float destinationX;

    public boolean inForwardSwingArm;
    public boolean inBackwardSwingArm;

    public boolean inForwardSwingLeg;
    public boolean inBackwardSwingLeg;

    public boolean inGoUpArmLightAttack;
    public boolean inGoDownArmLightAttack;

    public double lightAttackAnim_LegLeftRotateForwardUpStartingPosAngle = 8;
    public double lightAttackAnim_LegLeftRotateBackwardDownStartingPosAngle = 20;

    public boolean inForwardLeanTorso;
    public boolean inBackwardLeanTorso;

    public boolean inArmLeapAttackDown;
    public boolean inArmLeapAttackUp;

    public boolean inLegLeftRotateForwardUpAttackLight;
    public boolean inLegLeftRotateBackwardDownAttackLight;

    public boolean moveForwardProcessJustStarted;
    public boolean inForwardWalkingProcess;

    public static boolean moveBackwardProcessJustStarted;
    public boolean inBackwardWalkingProcess;

    public boolean lightAttackProcessJustStarted;
    public boolean inLightAttackProcess;


    public boolean leapAttackProcessJustStarted;
    public boolean inLeapAttackProcess;

    public Entity(GamePanel gp){
        this.gp = gp;
        screenX = gp.screenWidth/2 - (gp.tileSize/2); // center of the screen showing currently
        screenY = gp.screenHeight/2 - (gp.tileSize/2); // center of the screen showing currently

        // strength defines hitDamage
        strength = 1;
        hitDamage = baseHitDamage + strength;

        // magicka defines magicDamage
        // şimdilik atla
        magic = 1;

        // agility defines stepSize
        dexterity = 1;
        baseStepSize = gp.tileSize*26;
        stepSize = (int) (baseStepSize + (float) (dexterity*gp.tileSize)/12);

        // vitality defines HP
        vitality = 1;
        HP = vitality*2 + baseHP;

        // attack defines chances of attack action land
        // şimdilik atla
        offense = 1;

        // defence defines chances of blocking enemy attack
        // şimdilik atla
        protection = 1;

        // charisma reduces items prices that are in market,
        // şimdilik atla
        charisma = 1;

        // stamina defines stamina point
        // şimdilik atla
        stamina = 1;

        name = "Can";
        canAttack = false;
        inBattle = false;
        alive = false;
    }

    public void setImages(){

    }

    public void setupEntityInBattle(){
        alive = true;
        inBattle = true;
    }

    public void setupEntityOffBattle(){
        alive = false;
        inBattle = false;
    }

    public void update(){

    }

    public void draw(Graphics2D g2){

    }

    public void updateAttributes(){
        hitDamage = baseHitDamage + strength;

        //stepSize = (int) (baseStepSize + (float) (agility*gp.tileSize)/12);

        if (!inBattle){
            HP = vitality*2 + baseHP;
        }
    }

    /*public void attackLight(Entity entity){
        entity.HP -= hitDamage;
    }*/

    public void moveForwardProcessStart(float destinationX){

    }

    public void moveBackwardProcessStart(float destinationX){

    }

    public void attackLeapingProcessStart(Entity entity, float destinationX){

    }


    public void fixPosWhenCollide(Entity entity){

    }

    public void update_entityPart_positions(){

    }

    public void update_pivotPoints(){


    }

    public void animateArmWalking(){

    }

    public void animateLegWalking(){

    }

    public void growEntity(double sizeMultiplier){
        double initialHeight = torso.height;

        head.height *= sizeMultiplier;
        head.width *= sizeMultiplier;

        torso.height *= sizeMultiplier;
        torso.width *= sizeMultiplier;

        //System.out.println("torso y:" + torso.y);
        heightChange = (torso.height - initialHeight);
        //System.out.println("height change: " + heightChange);
        torso.y = (torso.y - heightChange*2.67); // 2.58 -> y değişmiyor // 2.67 -> yerinde durması için
        //System.out.println("torso y:" + torso.y);
        //System.out.println("foot y:" + foot_right.y);

        torso_lower.height *= sizeMultiplier;
        torso_lower.width *= sizeMultiplier;

        arm_right.height *= sizeMultiplier;
        arm_right.width *= sizeMultiplier;

        forearm_right.height *= sizeMultiplier;
        forearm_right.width *= sizeMultiplier;

        arm_left.height *= sizeMultiplier;
        arm_left.width *= sizeMultiplier;

        forearm_left.height *= sizeMultiplier;
        forearm_left.width *= sizeMultiplier;

        leg_right.height *= sizeMultiplier;
        leg_right.width *= sizeMultiplier;

        calf_right.height *= sizeMultiplier;
        calf_right.width *= sizeMultiplier;

        leg_left.height *= sizeMultiplier;
        leg_left.width *= sizeMultiplier;

        calf_left.height *= sizeMultiplier;
        calf_left.width *= sizeMultiplier;

        foot_right.height *= sizeMultiplier;
        foot_right.width *= sizeMultiplier;

        foot_left.height *= sizeMultiplier;
        foot_left.width *= sizeMultiplier;

        setImages();
    }

    public EntityPart rotate_limb(EntityPart limbToRotate, double rotationAngle,double pivotPointX, double pivotPointY) {
        // Create a rotation transformation
        double angle = Math.toRadians(rotationAngle);

        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, pivotPointX, pivotPointY);

        // Apply the rotation to the pivot point
        Point2D.Double rotatedPivot = (Point2D.Double) transform.transform(limbToRotate.pivotPoint, null);

        // Update the properties of the limb
        limbToRotate.pivotPoint = new Point2D.Double(rotatedPivot.getX(), rotatedPivot.getY());
        limbToRotate.rotationAngle += rotationAngle;

        return limbToRotate;
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

    int torsoWidthInitial;
    int torsoHeightInitial;

    int headWidthInitial;
    int headHeightInitial;

    int torso_lowerWidthInitial;
    int torso_lowerHeightInitial;

    int arm_rightWidthInitial;
    int arm_rightHeightInitial;

    int forearm_rightWidthInitial;
    int forearm_rightHeightInitial;

    int arm_leftWidthInitial;
    int arm_leftHeightInitial;

    int forearm_leftWidthInitial;
    int forearm_leftHeightInitial;

    int leg_rightWidthInitial;
    int leg_rightHeightInitial;

    int calf_rightWidthInitial;
    int calf_rightHeightInitial;

    int foot_rightWidthInitial;
    int foot_rightHeightInitial;

    int leg_leftWidthInitial;
    int leg_leftHeightInitial;

    int calf_leftWidthInitial;
    int calf_leftHeightInitial;

    int foot_leftWidthInitial;
    int foot_leftHeightInitial;
}
