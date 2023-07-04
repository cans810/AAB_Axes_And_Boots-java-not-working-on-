package UI;

import game.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelUpGUI extends AABGUI {

    GamePanel gp;
    public Graphics2D g2;

    public BufferedImage attribute_up;
    public BufferedImage attribute_down;
    public BufferedImage attribute_up_hovered;
    public BufferedImage attribute_down_hovered;

    public boolean justEnteredState;

    String totalPointsTitle = "Total Points";
    int totalPointsTitleX;
    int totalPointsTitleY;
    public int totalPoints;

    String strengthTitle = "Strength";
    public static double strengthPoint;
    public double baseStrengthPoint;
    public AABButton increaseStrengthButton;
    public AABButton decreaseStrengthButton;
    public int increaseStrengthButtonHit = -1;
    public boolean hoveringIncreaseStrengthButton;
    public boolean hoveringDecreaseStrengthButton;

    String vitalityTitle = "Vitality";
    public static double vitalityPoint;
    public double baseVitalityPoint;
    public AABButton increaseVitalityButton;
    public AABButton decreaseVitalityButton;
    public int increaseVitalityButtonHit = -1;
    public boolean hoveringIncreaseVitalityButton;
    public boolean hoveringDecreaseVitalityButton;

    String dexterityTitle = "Dexterity";
    public static double dexterityPoint;
    public double baseDexterityPoint;
    public AABButton increaseDexterityButton;
    public AABButton decreaseDexterityButton;
    public int increaseDexterityButtonHit = -1;
    public boolean hoveringIncreaseDexterityButton;
    public boolean hoveringDecreaseDexterityButton;

    public Rectangle proceedButton = new Rectangle();

    public LevelUpGUI(GamePanel gp) {
        super(gp);
        this.gp = gp;

        totalPoints = 2;

        justEnteredState = false;

        baseStrengthPoint = 0;
        baseVitalityPoint = 0;
        strengthPoint = 0;
        vitalityPoint = 0;

        // title total points
        totalPointsTitleX = gp.screenWidth/2;
        totalPointsTitleY = gp.screenHeight/2;

        setImages();

        increaseStrengthButton = new AABButton(gp.screenWidth/2-425,gp.screenHeight/2-155,40,40,gp);
        increaseStrengthButton.button_image = attribute_up;
        increaseStrengthButton.hovering_button_image = attribute_up_hovered;

        decreaseStrengthButton = new AABButton(gp.screenWidth/2-365,gp.screenHeight/2-155,40,40,gp);
        decreaseStrengthButton.button_image = attribute_down;
        decreaseStrengthButton.hovering_button_image = attribute_down_hovered;

        increaseVitalityButton = new AABButton(gp.screenWidth/2-425,gp.screenHeight/2-95,40,40,gp);
        increaseVitalityButton.button_image = attribute_up;
        increaseVitalityButton.hovering_button_image = attribute_up_hovered;

        decreaseVitalityButton = new AABButton(gp.screenWidth/2-365,gp.screenHeight/2-95,40,40,gp);
        decreaseVitalityButton.button_image = attribute_down;
        decreaseVitalityButton.hovering_button_image = attribute_down_hovered;

        increaseDexterityButton = new AABButton(gp.screenWidth/2-425,gp.screenHeight/2-35,40,40,gp);
        increaseDexterityButton.button_image = attribute_up;
        increaseDexterityButton.hovering_button_image = attribute_up_hovered;

        decreaseDexterityButton = new AABButton(gp.screenWidth/2-365,gp.screenHeight/2-35,40,40,gp);
        decreaseDexterityButton.button_image = attribute_down;
        decreaseDexterityButton.hovering_button_image = attribute_down_hovered;
    }

    public void setBaseAttributes(){
        baseStrengthPoint = gp.player.strength;
        baseVitalityPoint = gp.player.vitality;
        baseDexterityPoint = gp.player.dexterity;
    }

    @Override
    public void setImages() {
        attribute_up = setupImage("/attribute_up",40,40);
        attribute_down = setupImage("/attribute_down",40,40);
        attribute_up_hovered = setupImage("/attribute_up_hovered",40,40);
        attribute_down_hovered = setupImage("/attribute_down_hovered",40,40);
    }

    @Override
    public void update() {
        // strength
        if (isMouseWithinComponent(gp,increaseStrengthButton.getBounds())){
            hoveringIncreaseStrengthButton = true;
        }
        else if (!isMouseWithinComponent(gp,increaseStrengthButton.getBounds())){
            hoveringIncreaseStrengthButton = false;
        }

        if (isMouseWithinComponent(gp,decreaseStrengthButton.getBounds())){
            hoveringDecreaseStrengthButton = true;
        }
        else if (!isMouseWithinComponent(gp,decreaseStrengthButton.getBounds())){
            hoveringDecreaseStrengthButton = false;
        }

        // vitality
        if (isMouseWithinComponent(gp,increaseVitalityButton.getBounds())){
            hoveringIncreaseVitalityButton = true;
        }
        else if (!isMouseWithinComponent(gp,increaseVitalityButton.getBounds())){
            hoveringIncreaseVitalityButton = false;
        }

        if (isMouseWithinComponent(gp,decreaseVitalityButton.getBounds())){
            hoveringDecreaseVitalityButton = true;
        }
        else if (!isMouseWithinComponent(gp,decreaseVitalityButton.getBounds())){
            hoveringDecreaseVitalityButton = false;
        }

        // dexterity
        if (isMouseWithinComponent(gp,increaseDexterityButton.getBounds())){
            hoveringIncreaseDexterityButton = true;
        }
        else if (!isMouseWithinComponent(gp,increaseDexterityButton.getBounds())){
            hoveringIncreaseDexterityButton = false;
        }

        if (isMouseWithinComponent(gp,decreaseDexterityButton.getBounds())){
            hoveringDecreaseDexterityButton = true;
        }
        else if (!isMouseWithinComponent(gp,decreaseDexterityButton.getBounds())){
            hoveringDecreaseDexterityButton = false;
        }

        // update attributes of player if just entered the state
        if (justEnteredState){
            totalPoints = 2;
            setBaseAttributes();
            strengthPoint = gp.player.strength;
            vitalityPoint = gp.player.vitality;
            dexterityPoint = gp.player.dexterity;
            justEnteredState = false;
        }

        if (totalPoints > 0){
            // strength
            if (increaseStrengthButtonHit == 1){
                totalPoints -= 1;
                strengthPoint += 1;

                System.out.println("str point: " + strengthPoint);
                System.out.println("base str point: " + baseStrengthPoint);
            }

            // vitality
            else if (increaseVitalityButtonHit == 1){
                totalPoints -= 1;
                vitalityPoint += 1;

                System.out.println("vt point: " + vitalityPoint);
                System.out.println("base vt point: " + baseVitalityPoint);
            }

            // dexterity
            else if (increaseDexterityButtonHit == 1){
                totalPoints -= 1;
                dexterityPoint += 20;

                System.out.println("dx point: " + dexterityPoint);
                System.out.println("base dx point: " + baseDexterityPoint);
            }
        }

        if (increaseStrengthButtonHit == 0){
            // strength
            if (strengthPoint > baseStrengthPoint){
                totalPoints += 1;
                strengthPoint -= 1;

                System.out.println("str point: " + strengthPoint);
                System.out.println("base str point: " + baseStrengthPoint);
            }
        }

        else if (increaseVitalityButtonHit == 0){
            // vitality
            if (vitalityPoint > baseVitalityPoint){
                totalPoints += 1;
                vitalityPoint -= 1;

                System.out.println("vt point: " + vitalityPoint);
                System.out.println("base vt point: " + baseVitalityPoint);
            }
        }

        else if (increaseDexterityButtonHit == 0){
            // dexterity
            if (dexterityPoint > baseDexterityPoint){
                totalPoints += 1;
                dexterityPoint -= 1;

                System.out.println("vt point: " + dexterityPoint);
                System.out.println("base vt point: " + baseDexterityPoint);
            }
        }

        increaseStrengthButtonHit = -1;
        increaseVitalityButtonHit = -1;
        increaseDexterityButtonHit = -1;
    }

    public void setAttributesFinal(){
        gp.player.strength = strengthPoint;
        gp.player.vitality = vitalityPoint;
        gp.player.dexterity = dexterityPoint;

        // adjust player size here because of torso being drawn downward when entered to battle
        gp.player.updateEntitySize(baseStrengthPoint,strengthPoint);
    }

    @Override
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR); // more smooth for images

        g2.setColor(new Color(0,150,200));
        g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);

        g2.setFont(new Font("Tahoma", Font.BOLD, 20));
        g2.setColor(new Color(100,20,40));
        g2.drawString(totalPointsTitle,totalPointsTitleX-70,totalPointsTitleY-300);
        g2.drawString(Integer.toString(totalPoints),totalPointsTitleX-20,totalPointsTitleY-270);

        // strength
        g2.setFont(new Font("Tahoma", Font.BOLD, 20));
        g2.setColor(new Color(100,20,40));
        g2.drawString(strengthTitle,gp.screenWidth/2-550,gp.screenHeight/2-126);
        g2.drawString(Integer.toString((int) strengthPoint),gp.screenWidth/2-450,gp.screenHeight/2-126);

        if (hoveringIncreaseStrengthButton){
            g2.drawImage(increaseStrengthButton.hovering_button_image,increaseStrengthButton.getBounds().x,increaseStrengthButton.getBounds().y,null);
        }
        else {
            g2.drawImage(increaseStrengthButton.button_image,increaseStrengthButton.getBounds().x,increaseStrengthButton.getBounds().y,null);
        }

        if (hoveringDecreaseStrengthButton){
            g2.drawImage(decreaseStrengthButton.hovering_button_image,decreaseStrengthButton.getBounds().x,decreaseStrengthButton.getBounds().y,null);
        }
        else {
            g2.drawImage(decreaseStrengthButton.button_image,decreaseStrengthButton.getBounds().x,decreaseStrengthButton.getBounds().y,null);
        }

        // vitality
        g2.setFont(new Font("Tahoma", Font.BOLD, 20));
        g2.setColor(new Color(100,20,40));
        g2.drawString(vitalityTitle,gp.screenWidth/2-550,gp.screenHeight/2-66);
        g2.drawString(Integer.toString((int) vitalityPoint),gp.screenWidth/2-450,gp.screenHeight/2-66);

        if (hoveringIncreaseVitalityButton){
            g2.drawImage(increaseVitalityButton.hovering_button_image,increaseVitalityButton.getBounds().x,increaseVitalityButton.getBounds().y,null);
        }
        else {
            g2.drawImage(increaseVitalityButton.button_image,increaseVitalityButton.getBounds().x,increaseVitalityButton.getBounds().y,null);
        }

        if (hoveringDecreaseVitalityButton){
            g2.drawImage(decreaseVitalityButton.hovering_button_image,decreaseVitalityButton.getBounds().x,decreaseVitalityButton.getBounds().y,null);
        }
        else {
            g2.drawImage(decreaseVitalityButton.button_image,decreaseVitalityButton.getBounds().x,decreaseVitalityButton.getBounds().y,null);
        }

        // dexterity
        g2.setFont(new Font("Tahoma", Font.BOLD, 20));
        g2.setColor(new Color(100,20,40));
        g2.drawString(dexterityTitle,gp.screenWidth/2-550,gp.screenHeight/2-6);
        g2.drawString(Integer.toString((int) dexterityPoint),gp.screenWidth/2-450,gp.screenHeight/2-6);

        if (hoveringIncreaseDexterityButton){
            g2.drawImage(increaseDexterityButton.hovering_button_image,increaseDexterityButton.getBounds().x,increaseDexterityButton.getBounds().y,null);
        }
        else {
            g2.drawImage(increaseDexterityButton.button_image,increaseDexterityButton.getBounds().x,increaseDexterityButton.getBounds().y,null);
        }

        if (hoveringDecreaseDexterityButton){
            g2.drawImage(decreaseDexterityButton.hovering_button_image,decreaseDexterityButton.getBounds().x,decreaseDexterityButton.getBounds().y,null);
        }
        else {
            g2.drawImage(decreaseDexterityButton.button_image,decreaseDexterityButton.getBounds().x,decreaseDexterityButton.getBounds().y,null);
        }

        // button proceed
        proceedButton.x = gp.screenWidth/2+575;
        proceedButton.y = gp.screenHeight/2+300;
        proceedButton.width = 100;
        proceedButton.height = 50;
        g2.setColor(Color.orange);
        g2.fill(proceedButton);
    }
}
