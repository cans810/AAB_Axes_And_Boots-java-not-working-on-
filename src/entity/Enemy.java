package entity;

import UI.EntityGUI;
import entity.entity_misc.EntityPart;
import game.GamePanel;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends Entity{
    Random random;

    public long thinkTimer = 0L;
    public long thinkTime = 200L;

    public final int baseX = 1580;
    public final int baseY = gp.player.baseY;

    int randomChoice = 0;

    public float destinationX;

    public boolean actionMoveForwardExecuted;
    public boolean actionMoveBackwardExecuted;
    public boolean actionLeapAttackExecuted;
    public boolean actionLightAttackExecuted;

    public boolean inForwardSwingArm;
    public boolean inBackwardSwingArm;

    public boolean inForwardSwingLeg;
    public boolean inBackwardSwingLeg;

    public boolean inGoUpArmLightAttack;
    public boolean inGoDownArmLightAttack;

    public double leapAttackAnim_TorsoLeanForwardStartingPosAngle = 0;
    public double leapAttackAnim_TorsoLeanBackwardStartingPosAngle = -20;
    public double leapAttackAnim_ArmAttackDownStartingPosAngle = 0;
    public double leapAttackAnim_ArmAttackUpStartingPosAngle = 70;

    public double lightAttackAnim_ArmGoUpStartingPosAngle = 20;
    public double lightAttackAnim_ArmGoDownStartingPosAngle = 80;

    public boolean inBreatheIn;
    public boolean inBreatheOut;
    public double breatheInTime = 80;
    public double breatheOutTime = 80;
    public double breathingAnim_torsoY;
    public double breathingAnim_lowerTorsoY;

    public double breathingAnim_armGoUpStartingPosAngle = 1;
    public double breathingAnim_armGoDownStartingPosAngle = 4;
    public boolean inBreatheInArmGoUp;
    public boolean inBreatheOutArmGoDown;


    public Enemy(GamePanel gp) {
        super(gp);

        random = new Random();

        torsoWidthInitial = gp.tileSize*14;
        torsoHeightInitial = gp.tileSize*14;

        headWidthInitial = gp.tileSize*10;
        headHeightInitial = gp.tileSize*10;

        torso_lowerWidthInitial = gp.tileSize*14;
        torso_lowerHeightInitial = gp.tileSize*6;

        arm_rightWidthInitial = gp.tileSize*4;
        arm_rightHeightInitial = gp.tileSize*11;

        forearm_rightWidthInitial = gp.tileSize*4;
        forearm_rightHeightInitial = gp.tileSize*10;

        arm_leftWidthInitial = gp.tileSize*4;
        arm_leftHeightInitial = gp.tileSize*11;

        forearm_leftWidthInitial = gp.tileSize*4;
        forearm_leftHeightInitial = gp.tileSize*10;

        leg_rightWidthInitial = gp.tileSize*5;
        leg_rightHeightInitial = gp.tileSize*16;

        calf_rightWidthInitial = gp.tileSize*4;
        calf_rightHeightInitial = gp.tileSize*9;

        foot_rightWidthInitial = gp.tileSize*5;
        foot_rightHeightInitial = gp.tileSize*3;

        leg_leftWidthInitial = gp.tileSize*5;
        leg_leftHeightInitial = gp.tileSize*16;

        calf_leftWidthInitial = gp.tileSize*4;
        calf_leftHeightInitial = gp.tileSize*9;

        foot_leftWidthInitial = gp.tileSize*5;
        foot_leftHeightInitial = gp.tileSize*3;

        this.torso = new EntityPart(baseX,baseY,torsoWidthInitial,torsoHeightInitial,gp);
        this.head = new EntityPart(torso.x,torso.y,headWidthInitial,headHeightInitial,gp);

        this.torso_lower = new EntityPart(gp.tileSize*16,baseY+torso.height*99/100,torso_lowerWidthInitial,torso_lowerHeightInitial,gp);

        // right arm
        this.arm_right = new EntityPart(torso.x,torso.y,arm_rightWidthInitial,arm_rightHeightInitial,gp);

        this.forearm_right = new EntityPart(torso.x,torso.y,forearm_rightWidthInitial,forearm_rightHeightInitial,gp);

        // left arm
        this.arm_left = new EntityPart(torso.x,torso.y,arm_leftWidthInitial,arm_leftHeightInitial,gp);

        this.forearm_left = new EntityPart(torso.x,torso.y,forearm_leftWidthInitial,forearm_leftHeightInitial,gp);

        // right leg
        this.leg_right = new EntityPart(torso.x,torso.y,leg_rightWidthInitial,leg_rightHeightInitial,gp);

        this.calf_right = new EntityPart(torso.x,torso.y,calf_rightWidthInitial,calf_rightHeightInitial,gp);

        this.foot_right = new EntityPart(torso.x,torso.y,foot_rightWidthInitial,foot_rightHeightInitial,gp);

        // left leg
        this.leg_left = new EntityPart(torso.x,torso.y,leg_leftWidthInitial,leg_leftHeightInitial,gp);

        this.calf_left = new EntityPart(torso.x,torso.y,calf_leftWidthInitial,calf_leftHeightInitial,gp);

        this.foot_left = new EntityPart(torso.x,torso.y,foot_leftWidthInitial,foot_leftHeightInitial,gp);

        this.hitbox = new Rectangle((int) (torso.x-gp.tileSize*18),gp.tileSize*72,gp.tileSize*18,gp.tileSize*4);
        side = "right";

        setImages();

        gui = new EntityGUI(gp, this,"enemy");
        alive = true;
        inBattle = true;

        inBreatheIn = true;
        inBreatheOut = false;
        breathingAnim_torsoY = torso.getBounds().y;
        breathingAnim_lowerTorsoY = torso_lower.getBounds().y;

        update_pivotPoints();
        update_entityPart_positions();
    }

    public void setImages() {
        head.image = setupImage("/newhead1",(int)head.width,(int)head.height);
        torso.image = setupImage("/newtorso1",(int)torso.width,(int)torso.height);
        arm_right.image = setupImage("/newarm1", (int) arm_right.getBounds2D().getWidth(), (int) arm_right.getBounds2D().getHeight());
        arm_left.image = setupImage("/newarm1_rotated", (int) arm_left.getBounds2D().getWidth(), (int) arm_left.getBounds2D().getHeight());
        torso_lower.image = setupImage("/newlowertorso1", (int)torso_lower.width,(int)torso_lower.height);
        forearm_right.image = setupImage("/newforearm1", (int) forearm_right.getBounds2D().getWidth(), (int) forearm_right.getBounds2D().getHeight());
        forearm_left.image = setupImage("/newforearm1_rotated", (int) forearm_left.getBounds2D().getWidth(), (int) forearm_left.getBounds2D().getHeight());
        leg_right.image = setupImage("/newleg1",(int)leg_right.getBounds2D().getWidth(),(int)leg_right.getBounds2D().getHeight());
        leg_left.image = setupImage("/newleg1_rotated",(int)leg_left.getBounds2D().getWidth(),(int)leg_left.getBounds2D().getHeight());
        calf_right.image = setupImage("/newcalf1",(int)calf_right.getBounds2D().getWidth(),(int)calf_right.getBounds2D().getHeight());
        calf_left.image = setupImage("/newcalf1_rotated",(int)calf_left.getBounds2D().getWidth(),(int)calf_left.getBounds2D().getHeight());
        foot_right.image = setupImage("/rightfoot",(int)foot_right.getBounds2D().getWidth(),(int)foot_right.getBounds2D().getHeight());
        foot_left.image = setupImage("/leftfoot",(int)foot_left.getBounds2D().getWidth(),(int)foot_left.getBounds2D().getHeight());
    }

    @Override
    public void update_entityPart_positions() {

        head.x = head.pivotPoint.x - head.width/2;
        head.y = head.pivotPoint.y - head.height;

        // arm right
        arm_right.x = arm_right.pivotPoint.x - arm_right.width*17/20;
        arm_right.y = arm_right.pivotPoint.y;

        arm_right = rotate_limb(arm_right,0,arm_right.pivotPoint.x,arm_right.pivotPoint.y);

        // forearm right
        forearm_right.x = forearm_right.pivotPoint.x - forearm_right.width*8/20;
        forearm_right.y = forearm_right.pivotPoint.y;

        forearm_right = rotate_limb(forearm_right,0,forearm_right.pivotPoint.x,forearm_right.pivotPoint.y);

        // arm left
        arm_left.x = arm_left.pivotPoint.x - arm_left.width*3/20;
        arm_left.y = arm_left.pivotPoint.y;

        arm_left = rotate_limb(arm_left,0,arm_left.pivotPoint.x,arm_left.pivotPoint.y);

        // forearm left
        forearm_left.x = forearm_left.pivotPoint.x - forearm_left.width*12/20;
        forearm_left.y = forearm_left.pivotPoint.y;

        forearm_left = rotate_limb(forearm_left,0,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y);

        // torso lower
        torso_lower.x = torso_lower.pivotPoint.x - torso_lower.width/2;
        torso_lower.y = torso_lower.pivotPoint.y - 1;

        // leg right
        leg_right.x = leg_right.pivotPoint.x - leg_right.width/2;
        leg_right.y = leg_right.pivotPoint.y;

        leg_right = rotate_limb(leg_right,8,leg_right.pivotPoint.x,leg_right.pivotPoint.y);

        // calf right
        calf_right.x = calf_right.pivotPoint.x - calf_right.width/2;
        calf_right.y = calf_right.pivotPoint.y;

        calf_right = rotate_limb(calf_right,0,calf_right.pivotPoint.x,calf_right.pivotPoint.y);

        // foot right
        foot_right.x = foot_right.pivotPoint.x - foot_right.width/2 - foot_right.width/2;
        foot_right.y = foot_right.pivotPoint.y;

        foot_right = rotate_limb(foot_right,0,foot_right.pivotPoint.x,foot_right.pivotPoint.y);

        // leg left
        leg_left.x = leg_left.pivotPoint.x - leg_left.width/2;
        leg_left.y = leg_left.pivotPoint.y;

        leg_left = rotate_limb(leg_left,-8,leg_left.pivotPoint.x,leg_left.pivotPoint.y);

        // calf left
        calf_left.x = calf_left.pivotPoint.x - calf_left.width/2;
        calf_left.y = calf_left.pivotPoint.y;

        calf_left = rotate_limb(calf_left,0,calf_left.pivotPoint.x,calf_left.pivotPoint.y);

        // foot left
        foot_left.x = foot_left.pivotPoint.x;
        foot_left.y = foot_left.pivotPoint.y;

        foot_left = rotate_limb(foot_left,0,foot_left.pivotPoint.x,foot_left.pivotPoint.y);
    }

    @Override
    public void update_pivotPoints() {

        torso.pivotPoint.setLocation(torso.x + torso.width/2,torso.y + torso.height/2);

        head.pivotPoint.setLocation(torso.x + torso.width/2,torso.y + head.height*4/17);

        // right arm
        arm_right.pivotPoint.setLocation(torso.x + arm_right.width/5,torso.y + arm_right.height/6);

        // right forearm
        if (inForwardWalkingProcess || inBackwardWalkingProcess || inLightAttackProcess || inLeapAttackProcess){
            forearm_right.pivotPoint.setLocation(torso.x - forearm_right.width/4,torso.y + forearm_right.height);
        }
        /*else if (inLeapAttackProcess && inArmLeapAttackDown || inArmLeapAttackUp){
            forearm_right.pivotPoint.setLocation(torso.x + forearm_right.width*7/30,torso.y + forearm_right.height);
        }*/
        else {
            forearm_right.pivotPoint.setLocation(torso.x - forearm_right.width/4,torso.y + forearm_right.height);
        }

        // left arm
        arm_left.pivotPoint.setLocation(torso.x + torso.width - arm_left.width/5,torso.y + arm_left.height/6);

        // left forearm
        if (inForwardWalkingProcess || inBackwardWalkingProcess || inLightAttackProcess){
            forearm_left.pivotPoint.setLocation(torso.x + torso.width + forearm_left.width/4,torso.y + forearm_left.height);
        }
        else if (inLeapAttackProcess && inArmLeapAttackDown || inArmLeapAttackUp){
            forearm_left.pivotPoint.setLocation(torso.x + torso.width - forearm_left.width*7/30,torso.y + forearm_left.height);
        }
        else {
            forearm_left.pivotPoint.setLocation(torso.x + torso.width + forearm_left.width/4,torso.y + forearm_left.height);
        }

        // torso lower
        if (inForwardWalkingProcess){
            torso_lower.pivotPoint.setLocation(torso.x + torso_lower.width/2-2,torso.y + torso.height);
        }
        else if (inBackwardWalkingProcess) {
            torso_lower.pivotPoint.setLocation(torso.x + torso_lower.width/2+2,torso.y + torso.height);
        }

        else {
            torso_lower.pivotPoint.setLocation(torso.x + torso_lower.width/2,torso.y + torso.height);
        }

        // right leg
        if (inLeapAttackProcess){
            leg_right.pivotPoint.setLocation(torso.x + leg_right.width*25/20,torso.y + torso.height*9/10);
        }
        else{
            leg_right.pivotPoint.setLocation(torso.x + leg_right.width,torso.y + torso.height);
        }

        // right calf
        if (inForwardWalkingProcess || inBackwardWalkingProcess){
            calf_right.pivotPoint.setLocation(torso.x + calf_right.width + calf_right.width/5,leg_right.y + leg_right.height - leg_right.height/8);
        }
        else if (inLeapAttackProcess){
            calf_right.pivotPoint.setLocation(torso.x + calf_right.width*30/20,leg_right.y + leg_right.height - leg_right.height/8);
        }
        else if (inLegLeftRotateBackwardDownAttackLight || inLegLeftRotateForwardUpAttackLight){
            calf_right.pivotPoint.setLocation(torso.x + calf_right.width*23/20,leg_right.y + leg_right.height - leg_right.height/8);
        }
        else {
            calf_right.pivotPoint.setLocation(torso.x + calf_right.width - calf_right.width/3,leg_right.y + leg_right.height - leg_right.height/8);
        }

        // right foot
        if (inForwardWalkingProcess || inBackwardWalkingProcess){
            foot_right.pivotPoint.setLocation(torso.x + foot_right.width*9/10 + foot_right.width*3/10,calf_right.y + calf_right.height - calf_right.height/8);
        }
        else if (inLeapAttackProcess){
            foot_right.pivotPoint.setLocation(torso.x + foot_right.width*30/20,calf_right.y + calf_right.height - calf_right.height/14);
        }
        else if (inLegLeftRotateBackwardDownAttackLight || inLegLeftRotateForwardUpAttackLight){
            foot_right.pivotPoint.setLocation(torso.x + foot_right.width*25/20,calf_right.y + calf_right.height - calf_right.height*8/30);
        }
        else {
            foot_right.pivotPoint.setLocation(torso.x + foot_right.width*6/7,calf_right.y + calf_right.height - calf_right.height/8);
        }

        // left leg
        leg_left.pivotPoint.setLocation(torso.x + torso.width - leg_left.width,torso.y + torso.height);

        // left calf
        if (inForwardWalkingProcess || inBackwardWalkingProcess || inLeapAttackProcess){
            calf_left.pivotPoint.setLocation(torso.x + torso.width - calf_left.width*6/5,leg_left.y + leg_left.height - leg_left.height/8);
        }
        else {
            calf_left.pivotPoint.setLocation(torso.x + torso.width - calf_left.width*2/3,leg_left.y + leg_left.height - leg_left.height/8);
        }

        // left foot
        if (inForwardWalkingProcess || inBackwardWalkingProcess || inLeapAttackProcess){
            foot_left.pivotPoint.setLocation(torso.x + torso.width - foot_left.width*12/10,calf_left.y + calf_left.height - calf_left.height/8);
        }
        else {
            foot_left.pivotPoint.setLocation(torso.x + torso.width - foot_left.width*6/7,calf_left.y + calf_left.height - calf_left.height/8);
        }

    }

    @Override
    public void update() {
        hittingBorders();

        update_entityPart_positions();
        update_pivotPoints();

        animateBreathing();

        updateAttributes();

        fixPosWhenCollide(gp.player);

        hitbox.x = (int) (torso.x-gp.tileSize*18);
        hitbox.y = gp.tileSize*225;

        if (gp.turn.equals("enemy")){

            if (attackConditionCalculator(gp.player)){
                canAttack = true;
            }
            else{
                canAttack = false;
            }

            /*if (willCollidePlayer(gp.player)){
                canMoveForward = false;
            }
            else{
                canMoveForward = true;
            }*/

            if (!inForwardWalkingProcess && !inBackwardWalkingProcess && !inLightAttackProcess && !inLeapAttackProcess){
                randomChoice = random.nextInt(100);

                if (randomChoice >= 60){
                    if (canMoveForward){
                        actionMoveForwardExecuted = true;
                    }
                    else{
                        System.out.println("Enemy wanted to go forward but couldn't");
                    }
                }
                else if (randomChoice < 60 && randomChoice > 45){
                    if (canMoveBackward){
                        actionMoveBackwardExecuted = true;
                    }
                    else{
                        System.out.println("Enemy wanted to go backward but couldn't");
                    }
                }
                else if (randomChoice < 45 && randomChoice > 35){
                    if (canAttackLeaping){
                        actionLeapAttackExecuted = true;
                    }
                    else{
                        System.out.println("Enemy wanted to leap attack but couldn't");
                    }
                }
                else if (randomChoice < 35){
                    if (canAttack){
                        actionLightAttackExecuted = true;
                    }
                    else{
                        System.out.println("Enemy wanted to light attack but couldn't");
                    }
                }
                else{
                    System.out.println("Enemy stayed in place " + randomChoice);
                    played = true;
                    gp.determineTurn();
                }
            }

            // move forward
            if (actionMoveForwardExecuted){
                inForwardWalkingProcess = true;
                actionMoveForwardExecuted = false;

                moveForwardProcessJustStarted = true;
            }

            if (inForwardWalkingProcess){
                if (moveForwardProcessJustStarted){
                    destinationX = (float) (torso.x - stepSize);
                    moveForwardProcessJustStarted = false;
                }

                moveForwardProcessStart(destinationX);
            }

            // move backward
            if (actionMoveBackwardExecuted){
                inBackwardWalkingProcess = true;
                actionMoveBackwardExecuted = false;

                moveBackwardProcessJustStarted = true;
            }

            if (inBackwardWalkingProcess){
                if (moveBackwardProcessJustStarted){
                    destinationX = (float) (torso.x + stepSize);
                    moveBackwardProcessJustStarted = false;
                }

                moveBackwardProcessStart(destinationX);
            }

            // light attack
            if (actionLightAttackExecuted){
                inLightAttackProcess = true;
                actionLightAttackExecuted = false;

                if (attackConditionCalculator(gp.player)){
                    lightAttackProcessJustStarted = true;
                }
                else{
                    lightAttackProcessJustStarted = false;
                    inLightAttackProcess = false;
                }
            }

            if (inLightAttackProcess){
                if (lightAttackProcessJustStarted){
                    lightAttackProcessJustStarted = false;
                }

                lightAttackProcessStart(lightAttackAnim_ArmGoUpStartingPosAngle,lightAttackAnim_ArmGoDownStartingPosAngle);
            }

            // leap attack
            if (actionLeapAttackExecuted){
                inLeapAttackProcess = true;
                actionLeapAttackExecuted = false;

                leapAttackProcessJustStarted = true;
            }

            if (inLeapAttackProcess){
                if (leapAttackProcessJustStarted){
                    destinationX = (float) (torso.x - stepSize);
                    leapAttackProcessJustStarted = false;
                }

                attackLeapingProcessStart(gp.player,destinationX);
            }

        }

    }

    @Override
    public void draw(Graphics2D g2) {

        //g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR); // more smooth for images

        //g2.setColor(new Color(0,60,35));
        //g2.fill(leg_left);

        //g2.setColor(new Color(0,60,35));
        //g2.fill(arm_right);

        //g2.setColor(new Color(0,10,35));
        //g2.fill(foot_right);

        //g2.setColor(new Color(0,60,35));
        //g2.fill(leg_right);

        // right leg
        if (inForwardSwingLeg){
            drawRotatingLimb(g2,leg_right.pivotPoint.x,leg_right.pivotPoint.y,walkingAnim_LegsSwingForwardStartingPosAngle, leg_right.image, "leg_right");
        }
        else if (inBackwardSwingLeg){
            drawRotatingLimb(g2,leg_right.pivotPoint.x,leg_right.pivotPoint.y,walkingAnim_LegsSwingBackwardStartingPosAngle,leg_right.image,"leg_right");
        }
        else if (inForwardLeanTorso){
            drawRotatingLimb(g2,leg_right.pivotPoint.x,leg_right.pivotPoint.y,-leapAttackAnim_TorsoLeanForwardStartingPosAngle,leg_right.image,"leg_right");
        }
        else if (inBackwardLeanTorso){
            drawRotatingLimb(g2,leg_right.pivotPoint.x,leg_right.pivotPoint.y,-leapAttackAnim_TorsoLeanBackwardStartingPosAngle,leg_right.image,"leg_right");
        }
        else{
            drawRotatingLimb(g2,leg_right.pivotPoint.x,leg_right.pivotPoint.y,8,leg_right.image,"leg_right");
        }

        //g2.setColor(new Color(0,90,35));
        //g2.fill(calf_right);

        // right calf
        if (inForwardSwingLeg){
            drawRotatingLimb(g2,calf_right.pivotPoint.x,calf_right.pivotPoint.y,-walkingAnim_LegsSwingForwardStartingPosAngle, calf_right.image, "calf_right");
        }
        else if (inBackwardSwingLeg){
            drawRotatingLimb(g2,calf_right.pivotPoint.x,calf_right.pivotPoint.y,-walkingAnim_LegsSwingBackwardStartingPosAngle,calf_right.image,"calf_right");
        }
        else if (inForwardLeanTorso){
            drawRotatingLimb(g2,calf_right.pivotPoint.x,calf_right.pivotPoint.y,-leapAttackAnim_TorsoLeanForwardStartingPosAngle,calf_right.image,"calf_right");
        }
        else if (inBackwardLeanTorso){
            drawRotatingLimb(g2,calf_right.pivotPoint.x,calf_right.pivotPoint.y,-leapAttackAnim_TorsoLeanBackwardStartingPosAngle,calf_right.image,"calf_right");
        }
        else{
            drawRotatingLimb(g2,calf_right.pivotPoint.x,calf_right.pivotPoint.y,0,calf_right.image,"calf_right");
        }

        // right foot
        if (inForwardSwingLeg){
            drawRotatingLimb(g2,foot_right.pivotPoint.x,foot_right.pivotPoint.y,-walkingAnim_LegsSwingForwardStartingPosAngle, foot_right.image, "foot_right");
        }
        else if (inBackwardSwingLeg){
            drawRotatingLimb(g2,foot_right.pivotPoint.x,foot_right.pivotPoint.y,-walkingAnim_LegsSwingBackwardStartingPosAngle,foot_right.image,"foot_right");
        }
        else if (inForwardLeanTorso){
            drawRotatingLimb(g2,foot_right.pivotPoint.x,foot_right.pivotPoint.y,-leapAttackAnim_TorsoLeanForwardStartingPosAngle,foot_right.image,"foot_right");
        }
        else if (inBackwardLeanTorso){
            drawRotatingLimb(g2,foot_right.pivotPoint.x,foot_right.pivotPoint.y,-leapAttackAnim_TorsoLeanBackwardStartingPosAngle,foot_right.image,"foot_right");
        }
        else{
            drawRotatingLimb(g2,foot_right.pivotPoint.x,foot_right.pivotPoint.y,0,foot_right.image,"foot_right");
        }

        // right arm
        if (inForwardSwingArm){
            drawRotatingLimb(g2,arm_right.pivotPoint.x,arm_right.pivotPoint.y,walkingAnim_ArmsSwingForwardStartingPosAngle, arm_right.image, "arm_right");
        }
        else if (inBackwardSwingArm){
            drawRotatingLimb(g2,arm_right.pivotPoint.x,arm_right.pivotPoint.y,walkingAnim_ArmsSwingBackwardStartingPosAngle,arm_right.image,"arm_right");
        }
        else if (inForwardLeanTorso){
            drawRotatingLimb(g2,arm_right.pivotPoint.x,arm_right.pivotPoint.y,-leapAttackAnim_TorsoLeanForwardStartingPosAngle,arm_right.image,"arm_right");
        }
        else if (inBackwardLeanTorso){
            drawRotatingLimb(g2,arm_right.pivotPoint.x,arm_right.pivotPoint.y,-leapAttackAnim_TorsoLeanBackwardStartingPosAngle,arm_right.image,"arm_right");
        }
        else if (inBreatheInArmGoUp && !inLeapAttackProcess && !inLightAttackProcess && !inForwardWalkingProcess && !inBackwardWalkingProcess){
            drawRotatingLimb(g2,arm_right.pivotPoint.x,arm_right.pivotPoint.y,breathingAnim_armGoUpStartingPosAngle,arm_right.image,"arm_right");
        }
        else if (inBreatheOutArmGoDown && !inLeapAttackProcess && !inLightAttackProcess && !inForwardWalkingProcess && !inBackwardWalkingProcess){
            drawRotatingLimb(g2,arm_right.pivotPoint.x,arm_right.pivotPoint.y,breathingAnim_armGoDownStartingPosAngle,arm_right.image,"arm_right");
        }
        else{
            g2.drawImage(arm_right.image,arm_right.getBounds().x,arm_right.getBounds().y,null);
        }

        //g2.setColor(new Color(0,90,35));
        //g2.fill(forearm_right);

        // right forearm
        if (inForwardSwingArm){
            drawRotatingLimb(g2,forearm_right.pivotPoint.x,forearm_right.pivotPoint.y,walkingAnim_ArmsSwingForwardStartingPosAngle, forearm_right.image, "forearm_right");
        }
        else if (inBackwardSwingArm){
            drawRotatingLimb(g2,forearm_right.pivotPoint.x,forearm_right.pivotPoint.y,walkingAnim_ArmsSwingBackwardStartingPosAngle, forearm_right.image,"forearm_right");
        }
        else if (inForwardLeanTorso){
            drawRotatingLimb(g2,forearm_right.pivotPoint.x,forearm_right.pivotPoint.y,-leapAttackAnim_TorsoLeanForwardStartingPosAngle,forearm_right.image,"forearm_right");
        }
        else if (inBackwardLeanTorso){
            drawRotatingLimb(g2,forearm_right.pivotPoint.x,forearm_right.pivotPoint.y,-leapAttackAnim_TorsoLeanBackwardStartingPosAngle,forearm_right.image,"forearm_right");
        }
        else if (inBreatheInArmGoUp && !inLeapAttackProcess && !inLightAttackProcess && !inForwardWalkingProcess && !inBackwardWalkingProcess){
            drawRotatingLimb(g2,forearm_right.pivotPoint.x,forearm_right.pivotPoint.y,-breathingAnim_armGoUpStartingPosAngle,forearm_right.image,"forearm_right");
        }
        else if (inBreatheOutArmGoDown && !inLeapAttackProcess && !inLightAttackProcess && !inForwardWalkingProcess && !inBackwardWalkingProcess){
            drawRotatingLimb(g2,forearm_right.pivotPoint.x,forearm_right.pivotPoint.y,-breathingAnim_armGoDownStartingPosAngle,forearm_right.image,"forearm_right");
        }
        else{
            g2.drawImage(forearm_right.image,forearm_right.getBounds().x,forearm_right.getBounds().y,null);
        }

        // left leg
        if (inForwardSwingLeg){
            drawRotatingLimb(g2,leg_left.pivotPoint.x,leg_left.pivotPoint.y,-walkingAnim_LegsSwingForwardStartingPosAngle,leg_left.image,"leg_left");
        }
        else if (inBackwardSwingLeg){
            drawRotatingLimb(g2,leg_left.pivotPoint.x,leg_left.pivotPoint.y,-walkingAnim_LegsSwingBackwardStartingPosAngle,leg_left.image,"leg_left");
        }
        else if (inForwardLeanTorso){
            drawRotatingLimb(g2,leg_left.pivotPoint.x,leg_left.pivotPoint.y,leapAttackAnim_TorsoLeanForwardStartingPosAngle,leg_left.image,"leg_left");
        }
        else if (inBackwardLeanTorso){
            drawRotatingLimb(g2,leg_left.pivotPoint.x,leg_left.pivotPoint.y,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,leg_left.image,"leg_left");
        }
        else if(inLegLeftRotateForwardUpAttackLight){
            drawRotatingLimb(g2,leg_left.pivotPoint.x,leg_left.pivotPoint.y,-lightAttackAnim_LegLeftRotateForwardUpStartingPosAngle,leg_left.image,"leg_left");
        }
        else if (inLegLeftRotateBackwardDownAttackLight){
            drawRotatingLimb(g2,leg_left.pivotPoint.x,leg_left.pivotPoint.y,-lightAttackAnim_LegLeftRotateBackwardDownStartingPosAngle,leg_left.image,"leg_left");
        }
        else{
            drawRotatingLimb(g2,leg_left.pivotPoint.x,leg_left.pivotPoint.y,-8,leg_left.image,"leg_left");
        }

        //g2.setColor(new Color(0,90,35));
        //g2.fill(calf_left);

        // left calf
        if (inForwardSwingLeg){
            drawRotatingLimb(g2,calf_left.pivotPoint.x,calf_left.pivotPoint.y,walkingAnim_LegsSwingForwardStartingPosAngle, calf_left.image, "calf_left");
        }
        else if (inBackwardSwingLeg){
            drawRotatingLimb(g2,calf_left.pivotPoint.x,calf_left.pivotPoint.y,walkingAnim_LegsSwingBackwardStartingPosAngle,calf_left.image,"calf_left");
        }
        else if (inForwardLeanTorso){
            drawRotatingLimb(g2,calf_left.pivotPoint.x,calf_left.pivotPoint.y,leapAttackAnim_TorsoLeanForwardStartingPosAngle,calf_left.image,"calf_left");
        }
        else if (inBackwardLeanTorso){
            drawRotatingLimb(g2,calf_left.pivotPoint.x,calf_left.pivotPoint.y,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,calf_left.image,"calf_left");
        }
        else if(inLegLeftRotateForwardUpAttackLight){
            drawRotatingLimb(g2,calf_left.pivotPoint.x,calf_left.pivotPoint.y,lightAttackAnim_LegLeftRotateForwardUpStartingPosAngle,calf_left.image,"calf_left");
        }
        else if (inLegLeftRotateBackwardDownAttackLight){
            drawRotatingLimb(g2,calf_left.pivotPoint.x,calf_left.pivotPoint.y,lightAttackAnim_LegLeftRotateBackwardDownStartingPosAngle,calf_left.image,"calf_left");
        }
        else{
            drawRotatingLimb(g2,calf_left.pivotPoint.x,calf_left.pivotPoint.y,0,calf_left.image,"calf_left");
        }

        // left foot
        if (inForwardSwingLeg){
            drawRotatingLimb(g2,foot_left.pivotPoint.x,foot_left.pivotPoint.y,walkingAnim_LegsSwingForwardStartingPosAngle, foot_left.image, "foot_left");
        }
        else if (inBackwardSwingLeg){
            drawRotatingLimb(g2,foot_left.pivotPoint.x,foot_left.pivotPoint.y,walkingAnim_LegsSwingBackwardStartingPosAngle,foot_left.image,"foot_left");
        }
        else if (inForwardLeanTorso){
            drawRotatingLimb(g2,foot_left.pivotPoint.x,foot_left.pivotPoint.y,-leapAttackAnim_TorsoLeanForwardStartingPosAngle,foot_left.image,"foot_left");
        }
        else if (inBackwardLeanTorso){
            drawRotatingLimb(g2,foot_left.pivotPoint.x,foot_left.pivotPoint.y,-leapAttackAnim_TorsoLeanBackwardStartingPosAngle,foot_left.image,"foot_left");
        }
        else if(inLegLeftRotateForwardUpAttackLight){
            drawRotatingLimb(g2,foot_left.pivotPoint.x,foot_left.pivotPoint.y,-lightAttackAnim_LegLeftRotateForwardUpStartingPosAngle,foot_left.image,"foot_left");
        }
        else if (inLegLeftRotateBackwardDownAttackLight){
            drawRotatingLimb(g2,foot_left.pivotPoint.x,foot_left.pivotPoint.y,-lightAttackAnim_LegLeftRotateBackwardDownStartingPosAngle,foot_left.image,"foot_left");
        }
        else{
            drawRotatingLimb(g2,foot_left.pivotPoint.x,foot_left.pivotPoint.y,0,foot_left.image,"foot_left");
        }

        //g2.fill(torso);
        if (inForwardLeanTorso){
            drawRotatingLimb(g2,torso.pivotPoint.x,torso.pivotPoint.y,leapAttackAnim_TorsoLeanForwardStartingPosAngle,torso.image,"torso");
        }
        else if (inBackwardLeanTorso){
            drawRotatingLimb(g2,torso.pivotPoint.x,torso.pivotPoint.y,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,torso.image,"torso");
        }
        else if (inBreatheIn && !inLeapAttackProcess && !inLightAttackProcess && !inForwardWalkingProcess && !inBackwardWalkingProcess){
            AffineTransform transform = new AffineTransform();

            transform.translate(torso.getBounds().x, (breathingAnim_torsoY));

            ((Graphics2D) g2).drawImage(torso.image, transform, null);
            //g2.drawImage(torso.image,torso.getBounds().x, (int) (torso.getBounds().y-breathingYChange),null);
        }
        else if (inBreatheOut && !inLeapAttackProcess && !inLightAttackProcess && !inForwardWalkingProcess && !inBackwardWalkingProcess){
            AffineTransform transform = new AffineTransform();

            transform.translate(torso.getBounds().x, (breathingAnim_torsoY));

            ((Graphics2D) g2).drawImage(torso.image, transform, null);

            //g2.drawImage(torso.image,torso.getBounds().x, (int) (torso.getBounds().y+breathingYChange),null);
        }
        else{
            g2.drawImage(torso.image,torso.getBounds().x,torso.getBounds().y,null);
        }

        //g2.fill(torso_lower);
        // torso lower
        if (inForwardLeanTorso){
            drawRotatingLimb(g2,torso_lower.pivotPoint.x,torso_lower.pivotPoint.y,leapAttackAnim_TorsoLeanForwardStartingPosAngle,torso_lower.image,"torso_lower");
        }
        else if (inBackwardLeanTorso){
            drawRotatingLimb(g2,torso_lower.pivotPoint.x,torso_lower.pivotPoint.y,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,torso_lower.image,"torso_lower");
        }
        else if (inBreatheIn && !inLeapAttackProcess && !inLightAttackProcess && !inForwardWalkingProcess && !inBackwardWalkingProcess){
            AffineTransform transform = new AffineTransform();

            transform.translate(torso_lower.getBounds().x, (breathingAnim_lowerTorsoY));

            ((Graphics2D) g2).drawImage(torso_lower.image, transform, null);
            //g2.drawImage(torso.image,torso.getBounds().x, (int) (torso.getBounds().y-breathingYChange),null);
        }
        else if (inBreatheOut && !inLeapAttackProcess && !inLightAttackProcess && !inForwardWalkingProcess && !inBackwardWalkingProcess){
            AffineTransform transform = new AffineTransform();

            transform.translate(torso_lower.getBounds().x, (breathingAnim_lowerTorsoY));

            ((Graphics2D) g2).drawImage(torso_lower.image, transform, null);

            //g2.drawImage(torso.image,torso.getBounds().x, (int) (torso.getBounds().y+breathingYChange),null);
        }
        else{
            g2.drawImage(torso_lower.image,torso_lower.getBounds().x,torso_lower.getBounds().y,null);
        }

        //g2.setColor(new Color(0,60,35));
        //g2.fill(arm_left);

        // left arm
        if (inForwardSwingArm){
            drawRotatingLimb(g2,arm_left.pivotPoint.x,arm_left.pivotPoint.y,-walkingAnim_ArmsSwingForwardStartingPosAngle,arm_left.image,"arm_left");
        }
        else if (inBackwardSwingArm){
            drawRotatingLimb(g2,arm_left.pivotPoint.x,arm_left.pivotPoint.y,-walkingAnim_ArmsSwingBackwardStartingPosAngle,arm_left.image,"arm_left");
        }
        else if (inGoUpArmLightAttack){
            drawRotatingLimb(g2,arm_left.pivotPoint.x,arm_left.pivotPoint.y,lightAttackAnim_ArmGoUpStartingPosAngle,arm_left.image,"arm_left");
        }
        else if (inGoDownArmLightAttack){
            drawRotatingLimb(g2,arm_left.pivotPoint.x,arm_left.pivotPoint.y,lightAttackAnim_ArmGoDownStartingPosAngle,arm_left.image,"arm_left");
        }
        else if (inForwardLeanTorso){
            drawRotatingLimb(g2,arm_left.pivotPoint.x,arm_left.pivotPoint.y,leapAttackAnim_TorsoLeanForwardStartingPosAngle,arm_left.image,"arm_left");
        }
        else if (inBackwardLeanTorso){
            if (inArmLeapAttackDown){
                drawRotatingLimb(g2,arm_left.pivotPoint.x,arm_left.pivotPoint.y,leapAttackAnim_ArmAttackDownStartingPosAngle,arm_left.image,"arm_left");
            }
            else if (inArmLeapAttackUp){
                drawRotatingLimb(g2,arm_left.pivotPoint.x,arm_left.pivotPoint.y,leapAttackAnim_ArmAttackUpStartingPosAngle,arm_left.image,"arm_left");
            }
            else{
                drawRotatingLimb(g2,arm_left.pivotPoint.x,arm_left.pivotPoint.y,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,arm_left.image,"arm_left");
            }
        }
        else if (inBreatheInArmGoUp && !inLeapAttackProcess && !inLightAttackProcess && !inForwardWalkingProcess && !inBackwardWalkingProcess){
            drawRotatingLimb(g2,arm_left.pivotPoint.x,arm_left.pivotPoint.y,-breathingAnim_armGoUpStartingPosAngle,arm_left.image,"arm_left");
        }
        else if (inBreatheOutArmGoDown && !inLeapAttackProcess && !inLightAttackProcess && !inForwardWalkingProcess && !inBackwardWalkingProcess){
            drawRotatingLimb(g2,arm_left.pivotPoint.x,arm_left.pivotPoint.y,-breathingAnim_armGoDownStartingPosAngle,arm_left.image,"arm_left");
        }
        else{
            g2.drawImage(arm_left.image,arm_left.getBounds().x,arm_left.getBounds().y,null);
        }

        //g2.setColor(new Color(0,60,35));
        //g2.fill(forearm_left);

        // left forearm
        if (inForwardSwingArm){
            drawRotatingLimb(g2,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y,-walkingAnim_ArmsSwingForwardStartingPosAngle, forearm_left.image, "forearm_left");
        }
        else if (inBackwardSwingArm){
            drawRotatingLimb(g2,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y,-walkingAnim_ArmsSwingBackwardStartingPosAngle, forearm_left.image,"forearm_left");
        }
        else if (inGoUpArmLightAttack){
            drawRotatingLimb(g2,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y,lightAttackAnim_ArmGoUpStartingPosAngle,forearm_left.image,"forearm_left");
        }
        else if (inGoDownArmLightAttack){
            drawRotatingLimb(g2,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y,lightAttackAnim_ArmGoDownStartingPosAngle,forearm_left.image,"forearm_left");
        }
        else if (inForwardLeanTorso){
            drawRotatingLimb(g2,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y,leapAttackAnim_TorsoLeanForwardStartingPosAngle,forearm_left.image,"forearm_left");
        }
        else if (inBackwardLeanTorso){
            if (inArmLeapAttackDown){
                drawRotatingLimb(g2,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y,leapAttackAnim_ArmAttackDownStartingPosAngle,forearm_left.image,"forearm_left");
            }
            else if (inArmLeapAttackUp){
                drawRotatingLimb(g2,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y,leapAttackAnim_ArmAttackUpStartingPosAngle,forearm_left.image,"forearm_left");
            }
            else{
                drawRotatingLimb(g2,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,forearm_left.image,"forearm_left");
            }
        }
        else if (inBreatheInArmGoUp && !inLeapAttackProcess && !inLightAttackProcess && !inForwardWalkingProcess && !inBackwardWalkingProcess){
            drawRotatingLimb(g2,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y,breathingAnim_armGoUpStartingPosAngle,forearm_left.image,"forearm_left");
        }
        else if (inBreatheOutArmGoDown && !inLeapAttackProcess && !inLightAttackProcess && !inForwardWalkingProcess && !inBackwardWalkingProcess){
            drawRotatingLimb(g2,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y,breathingAnim_armGoDownStartingPosAngle,forearm_left.image,"forearm_left");
        }
        else{
            g2.drawImage(forearm_left.image,forearm_left.getBounds().x,forearm_left.getBounds().y,null);
        }

        //g2.fill(foot_left);

        //g2.setColor(new Color(0,35,0));
        //g2.fill(head);

        g2.drawImage(head.image,head.getBounds().x, head.getBounds().y,null);

        // draw pivot points of EntityParts

        //torso.drawPivotPoint(g2);

        torso_lower.drawPivotPoint(g2);

        head.drawPivotPoint(g2);

        arm_left.drawPivotPoint(g2);

        arm_right.drawPivotPoint(g2);

        leg_left.drawPivotPoint(g2);

        leg_right.drawPivotPoint(g2);

        g2.setColor(Color.white);
        g2.drawLine((int)leg_left.pivotPoint.x,(int)leg_left.pivotPoint.y,(int)leg_left.pivotPoint.x,(int)leg_left.pivotPoint.y);

        g2.setColor(Color.white);
        g2.drawLine((int)calf_left.pivotPoint.x,(int)calf_left.pivotPoint.y,(int)calf_left.pivotPoint.x,(int)calf_left.pivotPoint.y);

        g2.setColor(Color.white);
        g2.drawLine((int)arm_left.pivotPoint.x,(int)arm_left.pivotPoint.y,(int)arm_left.pivotPoint.x,(int)arm_left.pivotPoint.y);

        g2.setColor(Color.white);
        g2.drawLine((int)arm_right.pivotPoint.x,(int)arm_right.pivotPoint.y,(int)arm_right.pivotPoint.x,(int)arm_right.pivotPoint.y);

        g2.setColor(Color.white);
        g2.drawLine((int)forearm_right.pivotPoint.x,(int)forearm_right.pivotPoint.y,(int)forearm_right.pivotPoint.x,(int)forearm_right.pivotPoint.y);

        g2.setColor(Color.white);
        g2.drawLine((int)forearm_left.pivotPoint.x,(int)forearm_left.pivotPoint.y,(int)forearm_left.pivotPoint.x,(int)forearm_left.pivotPoint.y);

        g2.setColor(Color.white);
        g2.drawLine((int)leg_right.pivotPoint.x,(int)leg_right.pivotPoint.y,(int)leg_right.pivotPoint.x,(int)leg_right.pivotPoint.y);

        g2.setColor(Color.white);
        g2.drawLine((int)calf_right.pivotPoint.x,(int)calf_right.pivotPoint.y,(int)calf_right.pivotPoint.x,(int)calf_right.pivotPoint.y);

        g2.drawLine((int)foot_right.pivotPoint.x,(int)foot_right.pivotPoint.y,(int)foot_right.pivotPoint.x,(int)foot_right.pivotPoint.y);

        g2.drawLine((int)foot_left.pivotPoint.x,(int)foot_left.pivotPoint.y,(int)foot_left.pivotPoint.x,(int)foot_left.pivotPoint.y);

        g2.setColor(new Color(255,0,0));
        g2.fill(hitbox);
    }

    public Point2D.Double rotatePoint(Point2D.Double point, Point2D.Double pivot, double angle) {
        // Convert the angle to radians
        double angleRad = Math.toRadians(angle);

        // Calculate the differences from the pivot point
        double dx = point.getX() - pivot.getX();
        double dy = point.getY() - pivot.getY();

        // Calculate the new coordinates after rotation
        double newX = pivot.getX() + dx * Math.cos(angleRad) - dy * Math.sin(angleRad);
        double newY = pivot.getY() + dx * Math.sin(angleRad) + dy * Math.cos(angleRad);

        // Create a new Point with the rotated coordinates
        Point2D.Double rotatedPoint = new Point2D.Double(newX, newY);

        return rotatedPoint;
    }

    public void drawRotatingLimb(Graphics2D g2d, double pivotX, double pivotY, double rotationAngle, BufferedImage image, String limbType) {
        // Save the original transform
        AffineTransform originalTransform = g2d.getTransform();

        // Translate to the pivot point
        g2d.translate(pivotX, pivotY);

        // Rotate around the pivot point
        g2d.rotate(Math.toRadians(rotationAngle));

        if (limbType.equals("arm_right")){
            // Translate back by (-image.getWidth() / 2, -image.getHeight() / 2) to keep the image in place
            g2d.translate(-image.getWidth()*8/10, -image.getHeight() / 100);
        }

        else if (limbType.equals("arm_left")){
            // Translate back by (-image.getWidth() / 2, -image.getHeight() / 2) to keep the image in place
            g2d.translate(-image.getWidth()*10/50 , -image.getHeight() / 100);
        }

        else if (limbType.equals("forearm_right")){
            // Translate back by (-image.getWidth() / 2, -image.getHeight() / 2) to keep the image in place
            g2d.translate(-image.getWidth()*8/20, -image.getHeight() / 100);
        }

        else if (limbType.equals("forearm_left")){
            // Translate back by (-image.getWidth() / 2, -image.getHeight() / 2) to keep the image in place
            g2d.translate(-image.getWidth()*12/20, -image.getHeight() / 100);
        }

        else if (limbType.equals("leg_right")){
            // Translate back by (-image.getWidth() / 2, -image.getHeight() / 2) to keep the image in place
            g2d.translate(-image.getWidth() / 2, -image.getHeight() / 100);
        }

        else if (limbType.equals("leg_left")){
            // Translate back by (-image.getWidth() / 2, -image.getHeight() / 2) to keep the image in place
            g2d.translate(-image.getWidth() / 2, -image.getHeight() / 100);
        }

        else if (limbType.equals("calf_right")){
            // Translate back by (-image.getWidth() / 2, -image.getHeight() / 2) to keep the image in place
            g2d.translate(-image.getWidth() / 2 , -image.getHeight() / 100);
        }

        else if (limbType.equals("calf_left")){
            // Translate back by (-image.getWidth() / 2, -image.getHeight() / 2) to keep the image in place
            g2d.translate(-image.getWidth() / 2 , -image.getHeight() / 100);
        }

        else if (limbType.equals("foot_right")){
            // Translate back by (-image.getWidth() / 2, -image.getHeight() / 2) to keep the image in place
            g2d.translate(-image.getWidth(), -image.getHeight() / 100);
        }

        else if (limbType.equals("foot_left")){
            // Translate back by (-image.getWidth() / 2, -image.getHeight() / 2) to keep the image in place
            g2d.translate(-image.getWidth()/38, -image.getHeight() / 100);
        }

        else if (limbType.equals("torso")){
            // Translate back by (-image.getWidth() / 2, -image.getHeight() / 2) to keep the image in place
            g2d.translate(-image.getWidth()/2, -image.getHeight()/2);
        }

        else if (limbType.equals("torso_lower")){
            // Translate back by (-image.getWidth() / 2, -image.getHeight() / 2) to keep the image in place
            g2d.translate(-image.getWidth()/2, -image.getHeight()/16);
        }

        // Draw the image at the rotated position
        g2d.drawImage(image, 0, 0, null);

        // Restore the original transform
        g2d.setTransform(originalTransform);
    }

    public void breathingAnim_ArmMovement(){
        // reset animation stuff here everyloop when animation is not over.
        if (breathingAnim_armGoUpStartingPosAngle > 4 && breathingAnim_armGoDownStartingPosAngle < 1){
            breathingAnim_armGoUpStartingPosAngle = 1;
            breathingAnim_armGoDownStartingPosAngle = 4;

            inBreatheInArmGoUp = false;
            inBreatheOutArmGoDown = false;
        }

        if (breathingAnim_armGoUpStartingPosAngle <= 4){
            inBreatheInArmGoUp = true;
            breathingAnim_armGoUpStartingPosAngle += 0.045;

            // pivot points
            forearm_right.pivotPoint = rotatePoint(forearm_right.pivotPoint, torso.pivotPoint, breathingAnim_armGoUpStartingPosAngle);
            forearm_left.pivotPoint = rotatePoint(forearm_left.pivotPoint, torso.pivotPoint, -breathingAnim_armGoUpStartingPosAngle);

            // rotate limbs
            arm_right = rotate_limb(arm_right, breathingAnim_armGoUpStartingPosAngle, arm_right.pivotPoint.x,arm_right.pivotPoint.y);
            forearm_right = rotate_limb(forearm_right, breathingAnim_armGoUpStartingPosAngle, forearm_right.pivotPoint.x,forearm_right.pivotPoint.y);
            arm_left = rotate_limb(arm_left, -breathingAnim_armGoUpStartingPosAngle, arm_left.pivotPoint.x,arm_left.pivotPoint.y);
            forearm_left = rotate_limb(forearm_left, -breathingAnim_armGoUpStartingPosAngle, forearm_left.pivotPoint.x,forearm_left.pivotPoint.y);
            //System.out.println("arm going up");
        }

        else if (breathingAnim_armGoDownStartingPosAngle >= 1){
            inBreatheInArmGoUp = false;
            inBreatheOutArmGoDown = true;
            breathingAnim_armGoDownStartingPosAngle -= 0.03;

            forearm_right.pivotPoint = rotatePoint(forearm_right.pivotPoint, torso.pivotPoint, breathingAnim_armGoDownStartingPosAngle);
            forearm_left.pivotPoint = rotatePoint(forearm_left.pivotPoint, torso.pivotPoint, -breathingAnim_armGoDownStartingPosAngle);

            // rotate limbs
            arm_right = rotate_limb(arm_right, breathingAnim_armGoDownStartingPosAngle,arm_right.pivotPoint.x,arm_right.pivotPoint.y);
            forearm_right = rotate_limb(forearm_right, breathingAnim_armGoDownStartingPosAngle,forearm_right.pivotPoint.x,forearm_right.pivotPoint.y);
            arm_left = rotate_limb(arm_left, -breathingAnim_armGoDownStartingPosAngle,arm_left.pivotPoint.x,arm_left.pivotPoint.y);
            forearm_left = rotate_limb(forearm_left, -breathingAnim_armGoDownStartingPosAngle,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y);
            //System.out.println("arm going down");
        }
    }

    public void animateBreathing(){
        breathingAnim_ArmMovement();

        // if a loop of breathing in and out is done then reset stuff
        if (breatheInTime < 0 && breatheOutTime < 0){
            breatheOutTime = 80;
            breatheInTime = 80;
            breathingAnim_torsoY = torso.getBounds().y;
            breathingAnim_lowerTorsoY = torso_lower.getBounds().y;
        }

        if (breatheInTime >= 0 && inBreatheIn){
            breatheInTime -= 1;
            breathingAnim_torsoY -= 0.02;
            breathingAnim_lowerTorsoY -= 0.02;
            //System.out.println("currently breathing in " + breatheInTime);
            if (breatheInTime < 0){
                inBreatheIn = false;
                inBreatheOut = true;
            }
        }

        if (breatheOutTime >= 0 && inBreatheOut){
            breatheOutTime -= 1;
            breathingAnim_torsoY += 0.02;
            breathingAnim_lowerTorsoY += 0.02;
            //System.err.println("currently breathing out " + breatheOutTime);
            if (breatheOutTime < 0){
                inBreatheIn = true;
                inBreatheOut = false;
            }
        }
    }

    public void attackLight(Player enemy){
        if (attackConditionCalculator(enemy)){
            enemy.HP = enemy.HP - hitDamage;
        }
    }

    public void lightAttackProcessStart(double rotUp,double rotDown){
        // kol en tepedeyken vurması için
        if (rotUp >= 80 && rotDown == 77.5){
            attackLight(gp.player);
        }
        // animasyonun bitmesini bekle
        if (rotUp <= 80 || rotDown >= 20){
            animateArmAttackLight();
        }
        else{
            inGoUpArmLightAttack = false;
            inGoDownArmLightAttack = false;
        }
        /*if (lightAttackAnim_LegLeftRotateForwardUpStartingPosAngle <= 20 || lightAttackAnim_LegLeftRotateBackwardDownStartingPosAngle >= 8){
            animateLegAttackLight();
        }*/
        // bittiğinde gerekli ayarları çek
        if (/*rotUp <= -80 && rotDown >= -20*/inGoUpArmLightAttack == false && inGoDownArmLightAttack == false /*&& lightAttackAnim_LegLeftRotateForwardUpStartingPosAngle >= 20 && lightAttackAnim_LegLeftRotateBackwardDownStartingPosAngle <= 8*/){
            inGoUpArmLightAttack = false;
            inGoDownArmLightAttack = false;
            /*inLegLeftRotateForwardUpAttackLight = false;
            inLegLeftRotateBackwardDownAttackLight = false;*/
            lightAttackAnim_ArmGoUpStartingPosAngle = 20;
            lightAttackAnim_ArmGoDownStartingPosAngle = 80;
            /*lightAttackAnim_LegLeftRotateForwardUpStartingPosAngle = 8;
            lightAttackAnim_LegLeftRotateBackwardDownStartingPosAngle = 20;*/
            played = true;
            gp.determineTurn();
            inLightAttackProcess = false;
        }
    }

    public void moveForwardProcessStart(float destinationX){

        if (this.torso.x >= destinationX){
            this.torso.x -= 1;

            animateArmWalking();
            animateLegWalking();
        }
        else{
            inForwardSwingArm = false;
            inBackwardSwingArm = false;
            inForwardSwingLeg = false;
            inBackwardSwingLeg = false;
            walkingAnim_ArmsSwingBackwardStartingPosAngle = -6;
            walkingAnim_ArmsSwingForwardStartingPosAngle = 6;
            walkingAnim_LegsSwingBackwardStartingPosAngle = 2;
            walkingAnim_LegsSwingForwardStartingPosAngle = 10;
            System.out.println("Action Move Forward " + randomChoice);
            played = true;
            gp.determineTurn();
            inForwardWalkingProcess = false;
        }
    }

    public void moveBackwardProcessStart(float destinationX){
        if (this.torso.x <= destinationX){
            this.torso.x += 1;

            animateArmWalking();
            animateLegWalking();
        }
        else{
            inForwardSwingArm = false;
            inBackwardSwingArm = false;
            inForwardSwingLeg = false;
            inBackwardSwingLeg = false;
            walkingAnim_ArmsSwingBackwardStartingPosAngle = -6;
            walkingAnim_ArmsSwingForwardStartingPosAngle = 6;
            walkingAnim_LegsSwingBackwardStartingPosAngle = 2;
            walkingAnim_LegsSwingForwardStartingPosAngle = 10;
            System.out.println("Action Move Backward " + randomChoice);
            played = true;
            gp.determineTurn();
            inBackwardWalkingProcess = false;
        }
    }

    public void animateArmWalking(){

        // reset animation stuff here everyloop when animation is not over.
        if (walkingAnim_ArmsSwingBackwardStartingPosAngle > 6 && walkingAnim_ArmsSwingForwardStartingPosAngle < -6){
            walkingAnim_ArmsSwingBackwardStartingPosAngle = -6;
            walkingAnim_ArmsSwingForwardStartingPosAngle = 6;

            inForwardSwingArm = false;
            inBackwardSwingArm = false;
        }

        if (walkingAnim_ArmsSwingBackwardStartingPosAngle <= 6){
            inBackwardSwingArm = true;
            walkingAnim_ArmsSwingBackwardStartingPosAngle += 0.1;

            // update pivot points
            forearm_right.pivotPoint = rotatePoint(forearm_right.pivotPoint, arm_right.pivotPoint, walkingAnim_ArmsSwingBackwardStartingPosAngle);
            forearm_left.pivotPoint = rotatePoint(forearm_left.pivotPoint, arm_left.pivotPoint,-walkingAnim_ArmsSwingBackwardStartingPosAngle);

            // rotate limbs
            arm_right = rotate_limb(arm_right, walkingAnim_ArmsSwingBackwardStartingPosAngle, arm_right.pivotPoint.x,arm_right.pivotPoint.y);
            forearm_right = rotate_limb(forearm_right, walkingAnim_ArmsSwingBackwardStartingPosAngle, forearm_right.pivotPoint.x,forearm_right.pivotPoint.y);

            arm_left = rotate_limb(arm_left, -walkingAnim_ArmsSwingBackwardStartingPosAngle, arm_left.pivotPoint.x,arm_left.pivotPoint.y);
            forearm_left = rotate_limb(forearm_left, -walkingAnim_ArmsSwingBackwardStartingPosAngle, forearm_left.pivotPoint.x, forearm_left.pivotPoint.y);
        }

        else if (walkingAnim_ArmsSwingForwardStartingPosAngle >= -6){
            inBackwardSwingArm = false;
            inForwardSwingArm = true;
            walkingAnim_ArmsSwingForwardStartingPosAngle -= 0.1;

            // update pivot points
            forearm_right.pivotPoint = rotatePoint(forearm_right.pivotPoint,arm_right.pivotPoint, walkingAnim_ArmsSwingForwardStartingPosAngle);
            forearm_left.pivotPoint = rotatePoint(forearm_left.pivotPoint, arm_left.pivotPoint, -walkingAnim_ArmsSwingForwardStartingPosAngle);

            // rotate limbs
            arm_right = rotate_limb(arm_right, walkingAnim_ArmsSwingForwardStartingPosAngle,arm_right.pivotPoint.x,arm_right.pivotPoint.y);

            forearm_right = rotate_limb(forearm_right, walkingAnim_ArmsSwingForwardStartingPosAngle,forearm_right.pivotPoint.x,forearm_right.pivotPoint.y);

            arm_left = rotate_limb(arm_left, -walkingAnim_ArmsSwingForwardStartingPosAngle, arm_left.pivotPoint.x, arm_left.pivotPoint.y);
            forearm_left = rotate_limb(forearm_left, -walkingAnim_ArmsSwingForwardStartingPosAngle, forearm_left.pivotPoint.x, forearm_left.pivotPoint.y);
        }
    }

    public void animateLegWalking(){

        // reset animation stuff here everyloop when animation is not over.
        if (walkingAnim_LegsSwingBackwardStartingPosAngle > 10 && walkingAnim_LegsSwingForwardStartingPosAngle < 2){
            walkingAnim_LegsSwingBackwardStartingPosAngle = 2;
            walkingAnim_LegsSwingForwardStartingPosAngle = 10;

            inForwardSwingLeg = false;
            inBackwardSwingLeg = false;

            //rotated_forearm_rightPivotPoint.x = (int) rotatePoint(rotated_forearm_rightPivotPoint.x,rotated_forearm_rightPivotPoint.y,walkingAnim_SwingBackwardStartingPosAngle,arm_right).getX();
            //rotated_forearm_rightPivotPoint.y = (int) rotatePoint(rotated_forearm_rightPivotPoint.x,rotated_forearm_rightPivotPoint.y,walkingAnim_SwingBackwardStartingPosAngle,arm_right).getY();
        }

        if (walkingAnim_LegsSwingBackwardStartingPosAngle <= 10){
            inBackwardSwingLeg = true;
            walkingAnim_LegsSwingBackwardStartingPosAngle += 0.1;

            // update pivot points
            calf_right.pivotPoint = rotatePoint(calf_right.pivotPoint, leg_right.pivotPoint, walkingAnim_LegsSwingBackwardStartingPosAngle);
            calf_left.pivotPoint  = rotatePoint(calf_left.pivotPoint, leg_left.pivotPoint, -walkingAnim_LegsSwingBackwardStartingPosAngle);

            foot_right.pivotPoint = rotatePoint(foot_right.pivotPoint, calf_right.pivotPoint, walkingAnim_LegsSwingBackwardStartingPosAngle);
            foot_left.pivotPoint = rotatePoint(foot_left.pivotPoint, calf_left.pivotPoint, -walkingAnim_LegsSwingBackwardStartingPosAngle);

            // rotate limbs
            leg_right = rotate_limb(leg_right, walkingAnim_LegsSwingBackwardStartingPosAngle,leg_right.pivotPoint.x, leg_right.pivotPoint.y);
            calf_right = rotate_limb(calf_right, 0,calf_right.pivotPoint.x, calf_right.pivotPoint.y);
            foot_right = rotate_limb(foot_right, walkingAnim_LegsSwingBackwardStartingPosAngle,foot_right.pivotPoint.x,foot_right.pivotPoint.y);

            leg_left = rotate_limb(leg_left, -walkingAnim_LegsSwingBackwardStartingPosAngle,leg_left.pivotPoint.x, leg_left.pivotPoint.y);
            calf_left = rotate_limb(calf_left, 0,calf_left.pivotPoint.x, calf_left.pivotPoint.y);
            foot_left = rotate_limb(foot_left, -walkingAnim_LegsSwingBackwardStartingPosAngle,foot_left.pivotPoint.x, foot_left.pivotPoint.y);
        }

        else if (walkingAnim_LegsSwingForwardStartingPosAngle >= 2){
            inBackwardSwingLeg = false;
            inForwardSwingLeg = true;
            walkingAnim_LegsSwingForwardStartingPosAngle -= 0.1;

            calf_right.pivotPoint = rotatePoint(calf_right.pivotPoint, leg_right.pivotPoint, walkingAnim_LegsSwingForwardStartingPosAngle);
            calf_left.pivotPoint  = rotatePoint(calf_left.pivotPoint, leg_left.pivotPoint, -walkingAnim_LegsSwingForwardStartingPosAngle);

            foot_right.pivotPoint = rotatePoint(foot_right.pivotPoint, calf_right.pivotPoint, walkingAnim_LegsSwingForwardStartingPosAngle);
            foot_left.pivotPoint = rotatePoint(foot_left.pivotPoint, calf_left.pivotPoint, -walkingAnim_LegsSwingForwardStartingPosAngle);

            // rotate limbs
            leg_right = rotate_limb(leg_right, walkingAnim_LegsSwingForwardStartingPosAngle,leg_right.pivotPoint.x, leg_right.pivotPoint.y);
            calf_right = rotate_limb(calf_right, 0,calf_right.pivotPoint.x, calf_right.pivotPoint.y);
            foot_right = rotate_limb(foot_right, walkingAnim_LegsSwingForwardStartingPosAngle,foot_right.pivotPoint.x,foot_right.pivotPoint.y);

            leg_left = rotate_limb(leg_left, -walkingAnim_LegsSwingForwardStartingPosAngle,leg_left.pivotPoint.x, leg_left.pivotPoint.y);
            calf_left = rotate_limb(calf_left, 0,calf_left.pivotPoint.x, calf_left.pivotPoint.y);
            foot_left = rotate_limb(foot_left, -walkingAnim_LegsSwingForwardStartingPosAngle,foot_left.pivotPoint.x, foot_left.pivotPoint.y);
        }
    }

    public void animateArmAttackLight(){

        if (lightAttackAnim_ArmGoDownStartingPosAngle < 20 && lightAttackAnim_ArmGoUpStartingPosAngle > 80){
            lightAttackAnim_ArmGoDownStartingPosAngle = 80;
            lightAttackAnim_ArmGoUpStartingPosAngle = 20;

            inGoUpArmLightAttack = false;
            inGoDownArmLightAttack = false;
        }

        if (lightAttackAnim_ArmGoUpStartingPosAngle <= 80){
            inGoUpArmLightAttack = true;
            lightAttackAnim_ArmGoUpStartingPosAngle += 2.5;

            // update pivot points
            forearm_left.pivotPoint = rotatePoint(forearm_left.pivotPoint, arm_left.pivotPoint, lightAttackAnim_ArmGoUpStartingPosAngle);

            // rotate limbs
            arm_left = rotate_limb(arm_left, lightAttackAnim_ArmGoUpStartingPosAngle, arm_left.pivotPoint.x, arm_left.pivotPoint.y);
            forearm_left = rotate_limb(forearm_left, lightAttackAnim_ArmGoUpStartingPosAngle, forearm_left.pivotPoint.x, forearm_left.pivotPoint.y);

        }

        else if (lightAttackAnim_ArmGoDownStartingPosAngle >= 20){
            inGoUpArmLightAttack = false;
            inGoDownArmLightAttack = true;
            lightAttackAnim_ArmGoDownStartingPosAngle -= 2.5;

            // update pivot points
            forearm_left.pivotPoint = rotatePoint(forearm_left.pivotPoint, arm_left.pivotPoint, lightAttackAnim_ArmGoDownStartingPosAngle);

            // rotate limbs
            arm_left = rotate_limb(arm_left, lightAttackAnim_ArmGoDownStartingPosAngle, arm_left.pivotPoint.x, arm_left.pivotPoint.y);
            forearm_left = rotate_limb(forearm_left, lightAttackAnim_ArmGoDownStartingPosAngle, forearm_left.pivotPoint.x, forearm_left.pivotPoint.y);

        }
    }

    public void attackLeapingProcessStart(Player entity, float destinationX){

        if (this.torso.x >= destinationX){
            this.torso.x -= 1;

            animateAttackLeap();
        }
        else{
            if (attackConditionCalculator(entity)){
                entity.HP -= hitDamage;
            }
            else{
                System.out.println("Missed leap attack");
            }
            inForwardLeanTorso = false;
            inBackwardLeanTorso = false;
            inArmLeapAttackDown = false;
            inArmLeapAttackUp = false;
            leapAttackAnim_TorsoLeanForwardStartingPosAngle = 0;
            leapAttackAnim_TorsoLeanBackwardStartingPosAngle = -20;
            leapAttackAnim_ArmAttackDownStartingPosAngle = 0;
            leapAttackAnim_ArmAttackUpStartingPosAngle = 70;
            played = true;
            gp.determineTurn();
            inLeapAttackProcess = false;
        }
    }

    public void animateArmAttackLeap(){

        if (leapAttackAnim_ArmAttackDownStartingPosAngle <= 70){
            inArmLeapAttackDown = true;
            leapAttackAnim_ArmAttackDownStartingPosAngle += 2.5;

            // update pivot points
            forearm_left.pivotPoint = rotatePoint(forearm_left.pivotPoint, arm_left.pivotPoint, leapAttackAnim_ArmAttackDownStartingPosAngle);

            // rotate limbs
            arm_left = rotate_limb(arm_left, leapAttackAnim_ArmAttackDownStartingPosAngle, arm_left.pivotPoint.x, arm_left.pivotPoint.y);
            forearm_left = rotate_limb(forearm_left, leapAttackAnim_ArmAttackDownStartingPosAngle, forearm_left.pivotPoint.x, forearm_left.pivotPoint.y);
        }

        else if (leapAttackAnim_ArmAttackUpStartingPosAngle >= 0){
            inArmLeapAttackDown = false;
            inArmLeapAttackUp = true;
            leapAttackAnim_ArmAttackUpStartingPosAngle -= 1;

            // update pivot points
            forearm_left.pivotPoint = rotatePoint(forearm_left.pivotPoint, arm_left.pivotPoint, leapAttackAnim_ArmAttackUpStartingPosAngle);

            // rotate limbs
            arm_left = rotate_limb(arm_left, leapAttackAnim_ArmAttackUpStartingPosAngle, arm_left.pivotPoint.x, arm_left.pivotPoint.y);
            forearm_left = rotate_limb(forearm_left, leapAttackAnim_ArmAttackUpStartingPosAngle, forearm_left.pivotPoint.x, forearm_left.pivotPoint.y);
        }
    }

    public void animateAttackLeap(){

        if (leapAttackAnim_TorsoLeanForwardStartingPosAngle >= -20){
            inForwardLeanTorso = true;
            leapAttackAnim_TorsoLeanForwardStartingPosAngle -= 0.2;

            // update pivot points
            torso.pivotPoint = rotatePoint(torso.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanForwardStartingPosAngle);
            head.pivotPoint = rotatePoint(head.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanForwardStartingPosAngle);
            arm_right.pivotPoint = rotatePoint(arm_right.pivotPoint, arm_right.pivotPoint, -leapAttackAnim_TorsoLeanForwardStartingPosAngle);
            arm_left.pivotPoint = rotatePoint(arm_left.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanForwardStartingPosAngle);
            forearm_right.pivotPoint = rotatePoint(forearm_right.pivotPoint, arm_right.pivotPoint, -leapAttackAnim_TorsoLeanForwardStartingPosAngle);
            forearm_left.pivotPoint = rotatePoint(forearm_left.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanForwardStartingPosAngle);
            torso_lower.pivotPoint = rotatePoint(torso_lower.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanForwardStartingPosAngle);
            leg_right.pivotPoint = rotatePoint(leg_right.pivotPoint, leg_right.pivotPoint, leapAttackAnim_TorsoLeanForwardStartingPosAngle);
            leg_left.pivotPoint = rotatePoint(leg_left.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanForwardStartingPosAngle);
            calf_right.pivotPoint = rotatePoint(calf_right.pivotPoint, leg_right.pivotPoint, -leapAttackAnim_TorsoLeanForwardStartingPosAngle);
            calf_left.pivotPoint = rotatePoint(calf_left.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanForwardStartingPosAngle);
            foot_right.pivotPoint = rotatePoint(foot_right.pivotPoint, leg_right.pivotPoint, -leapAttackAnim_TorsoLeanForwardStartingPosAngle);
            foot_left.pivotPoint = rotatePoint(foot_left.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanForwardStartingPosAngle);

            // rotate limbs
            torso = rotate_limb(torso,leapAttackAnim_TorsoLeanForwardStartingPosAngle,torso.pivotPoint.x,torso.pivotPoint.y);
            torso_lower = rotate_limb(torso_lower,leapAttackAnim_TorsoLeanForwardStartingPosAngle,torso_lower.pivotPoint.x,torso_lower.pivotPoint.y);
            head = rotate_limb(head,leapAttackAnim_TorsoLeanForwardStartingPosAngle,head.pivotPoint.x,head.pivotPoint.y);
            arm_right = rotate_limb(arm_right,-leapAttackAnim_TorsoLeanForwardStartingPosAngle,arm_right.pivotPoint.x,arm_right.pivotPoint.y);
            arm_left = rotate_limb(arm_left,leapAttackAnim_TorsoLeanForwardStartingPosAngle,arm_left.pivotPoint.x,arm_left.pivotPoint.y);
            forearm_right = rotate_limb(forearm_right,leapAttackAnim_TorsoLeanForwardStartingPosAngle,forearm_right.pivotPoint.x,forearm_right.pivotPoint.y);
            forearm_left = rotate_limb(forearm_left,leapAttackAnim_TorsoLeanForwardStartingPosAngle,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y);
            leg_right = rotate_limb(leg_right,-leapAttackAnim_TorsoLeanForwardStartingPosAngle,leg_right.pivotPoint.x,leg_right.pivotPoint.y);
            leg_left = rotate_limb(leg_left,leapAttackAnim_TorsoLeanForwardStartingPosAngle,leg_left.pivotPoint.x,leg_left.pivotPoint.y);
            calf_right = rotate_limb(calf_right,-leapAttackAnim_TorsoLeanForwardStartingPosAngle,calf_right.pivotPoint.x,calf_right.pivotPoint.y);
            calf_left = rotate_limb(calf_left,leapAttackAnim_TorsoLeanForwardStartingPosAngle,calf_left.pivotPoint.x,calf_left.pivotPoint.y);
            foot_right = rotate_limb(foot_right,-leapAttackAnim_TorsoLeanForwardStartingPosAngle,foot_right.pivotPoint.x,foot_right.pivotPoint.y);
            foot_left = rotate_limb(foot_left,leapAttackAnim_TorsoLeanForwardStartingPosAngle,foot_left.pivotPoint.x,foot_left.pivotPoint.y);
        }

        else if (leapAttackAnim_TorsoLeanBackwardStartingPosAngle <= 0){
            inForwardLeanTorso = false;
            inBackwardLeanTorso = true;
            leapAttackAnim_TorsoLeanBackwardStartingPosAngle += 0.2;

            if (leapAttackAnim_TorsoLeanForwardStartingPosAngle <= -20){
                animateArmAttackLeap();
            }

            // update pivot points
            torso.pivotPoint = rotatePoint(torso.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanBackwardStartingPosAngle);
            head.pivotPoint = rotatePoint(head.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanBackwardStartingPosAngle);
            arm_right.pivotPoint = rotatePoint(arm_right.pivotPoint, arm_right.pivotPoint, -leapAttackAnim_TorsoLeanBackwardStartingPosAngle);
            arm_left.pivotPoint = rotatePoint(arm_left.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanBackwardStartingPosAngle);
            forearm_right.pivotPoint = rotatePoint(forearm_right.pivotPoint, arm_right.pivotPoint, -leapAttackAnim_TorsoLeanBackwardStartingPosAngle);
            forearm_left.pivotPoint = rotatePoint(forearm_left.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanBackwardStartingPosAngle);
            torso_lower.pivotPoint = rotatePoint(torso_lower.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanBackwardStartingPosAngle);
            leg_right.pivotPoint = rotatePoint(leg_right.pivotPoint, leg_right.pivotPoint, leapAttackAnim_TorsoLeanBackwardStartingPosAngle);
            leg_left.pivotPoint = rotatePoint(leg_left.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanBackwardStartingPosAngle);
            calf_right.pivotPoint = rotatePoint(calf_right.pivotPoint, leg_right.pivotPoint, -leapAttackAnim_TorsoLeanBackwardStartingPosAngle);
            calf_left.pivotPoint = rotatePoint(calf_left.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanBackwardStartingPosAngle);
            foot_right.pivotPoint = rotatePoint(foot_right.pivotPoint, leg_right.pivotPoint, -leapAttackAnim_TorsoLeanBackwardStartingPosAngle);
            foot_left.pivotPoint = rotatePoint(foot_left.pivotPoint, torso.pivotPoint, leapAttackAnim_TorsoLeanBackwardStartingPosAngle);

            // rotate limbs
            torso = rotate_limb(torso,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,torso.pivotPoint.x,torso.pivotPoint.y);
            torso_lower = rotate_limb(torso_lower,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,torso_lower.pivotPoint.x,torso_lower.pivotPoint.y);
            head = rotate_limb(head,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,head.pivotPoint.x,head.pivotPoint.y);
            arm_right = rotate_limb(arm_right,-leapAttackAnim_TorsoLeanBackwardStartingPosAngle,arm_right.pivotPoint.x,arm_right.pivotPoint.y);
            arm_left = rotate_limb(arm_left,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,arm_left.pivotPoint.x,arm_left.pivotPoint.y);
            forearm_right = rotate_limb(forearm_right,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,forearm_right.pivotPoint.x,forearm_right.pivotPoint.y);
            forearm_left = rotate_limb(forearm_left,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,forearm_left.pivotPoint.x,forearm_left.pivotPoint.y);
            leg_right = rotate_limb(leg_right,-leapAttackAnim_TorsoLeanBackwardStartingPosAngle,leg_right.pivotPoint.x,leg_right.pivotPoint.y);
            leg_left = rotate_limb(leg_left,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,leg_left.pivotPoint.x,leg_left.pivotPoint.y);
            calf_right = rotate_limb(calf_right,-leapAttackAnim_TorsoLeanBackwardStartingPosAngle,calf_right.pivotPoint.x,calf_right.pivotPoint.y);
            calf_left = rotate_limb(calf_left,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,calf_left.pivotPoint.x,calf_left.pivotPoint.y);
            foot_right = rotate_limb(foot_right,-leapAttackAnim_TorsoLeanBackwardStartingPosAngle,foot_right.pivotPoint.x,foot_right.pivotPoint.y);
            foot_left = rotate_limb(foot_left,leapAttackAnim_TorsoLeanBackwardStartingPosAngle,foot_left.pivotPoint.x,foot_left.pivotPoint.y);
        }
    }

    public boolean willCollidePlayer(Player player){
        float xDifference = (float) Math.abs(player.torso.x - torso.x - stepSize);

        if (xDifference <= gp.tileSize*13){
            return true;
        }
        return false;
    }

    public boolean attackConditionCalculator(Player player){
        if (hitbox.intersects(player.torso)){
            return true;
        }
        else {
            return false;
        }
    }

    public void fixPosWhenCollide(Entity entity){

        /*if (torso.x+torso.width > gp.tileSize*gp.maxScreenCol){
            torso.x = gp.tileSize*gp.maxScreenCol - torso.width - gp.tileSize*2;
        }*/
        if (torso.intersects(entity.torso)){
            torso.x += torso.width/2;
        }
    }

    public void hittingBorders(){
        //System.out.println(torso.x);
        if (torso.x > gp.arenaScreenWidth - 120){
            torso.x = gp.arenaScreenWidth - 140;
            canMoveBackward = false;
            canAttackLeaping = false;
            resetWalkingAnimationAndSwitchTurns();
        }
        else{
            canMoveBackward = true;
        }
        System.out.println("can move backward: " + canMoveBackward);

        if (torso.x <= 40){
            torso.x = 50;
            canMoveForward = false;
            canAttackLeaping = false;
            resetWalkingAnimationAndSwitchTurns();
        }
        else{
            canMoveForward = true;
        }
        System.out.println("can move forward: " + canMoveForward);
    }

    public void resetWalkingAnimationAndSwitchTurns(){
        inForwardSwingArm = false;
        inBackwardSwingArm = false;
        inForwardSwingLeg = false;
        inBackwardSwingLeg = false;
        walkingAnim_ArmsSwingBackwardStartingPosAngle = -6;
        walkingAnim_ArmsSwingForwardStartingPosAngle = 6;
        walkingAnim_LegsSwingBackwardStartingPosAngle = 2;
        walkingAnim_LegsSwingForwardStartingPosAngle = 10;
        inForwardWalkingProcess = false;
        inBackwardWalkingProcess = false;

        inForwardLeanTorso = false;
        inBackwardLeanTorso = false;
        inArmLeapAttackDown = false;
        inArmLeapAttackUp = false;
        leapAttackAnim_TorsoLeanForwardStartingPosAngle = 0;
        leapAttackAnim_TorsoLeanBackwardStartingPosAngle = -20;
        leapAttackAnim_ArmAttackDownStartingPosAngle = 0;
        leapAttackAnim_ArmAttackUpStartingPosAngle = 70;
        inLeapAttackProcess = false;

        played = true;
        gp.determineTurn();
    }
}




