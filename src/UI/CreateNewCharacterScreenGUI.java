package UI;

import game.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CreateNewCharacterScreenGUI extends AABGUI {
    GamePanel gp;
    public Graphics2D g2;

    public BufferedImage attribute_up;
    public BufferedImage attribute_down;
    public BufferedImage attribute_up_hovered;
    public BufferedImage attribute_down_hovered;

    public int createNewCharacterGUIState;
    public final int enterNameState = 0;
    public final int giveStatsState = 1;

    BufferedImage newCharacterBackground;

    BufferedImage enterNameTitle;
    Rectangle enterNameField;
    int enterNameTitleX;
    int enterNameTitleY;

    String totalPointsTitle = "Total Points";
    int totalPointsTitleX;
    int totalPointsTitleY;
    public int totalPoints = 5;

    String strengthTitle = "Strength";
    int strengthTitleX;
    int strengthTitleY;
    public int strengthPoint = 1;
    public AABButton increaseStrengthButton;
    public AABButton decreaseStrengthButton;
    public int increaseStrengthButtonHit = -1;
    public boolean hoveringIncreaseStrengthButton;
    public boolean hoveringDecreaseStrengthButton;

    String vitalityTitle = "Vitality";
    int vitalityTitleX;
    int vitalityTitleY;
    public int vitalityPoint = 1;
    public AABButton increaseVitalityButton;
    public AABButton decreaseVitalityButton;
    public int increaseVitalityButtonHit = -1;
    public boolean hoveringIncreaseVitalityButton;
    public boolean hoveringDecreaseVitalityButton;

    String agilityTitle = "Agility";
    int agilityTitleX;
    int agilityTitleY;

    public Rectangle proceedButton = new Rectangle();

    public CreateNewCharacterScreenGUI(GamePanel gp) {
        super(gp);
        this.gp = gp;


        createNewCharacterGUIState = enterNameState;

        setImages();

        // title total points
        totalPointsTitleX = gp.screenWidth/2;
        totalPointsTitleY = gp.screenHeight/2;

        // stat strength
        strengthTitleX = gp.screenWidth/2;
        strengthTitleY = gp.screenHeight/2;

        // stat vitality
        vitalityTitleX = gp.screenWidth/2;
        vitalityTitleY = gp.screenHeight/2;

        increaseStrengthButton = new AABButton(strengthTitleX-425,strengthTitleY-155,40,40,gp);
        increaseStrengthButton.button_image = attribute_up;
        increaseStrengthButton.hovering_button_image = attribute_up_hovered;

        decreaseStrengthButton = new AABButton(strengthTitleX-365,strengthTitleY-155,40,40,gp);
        decreaseStrengthButton.button_image = attribute_down;
        decreaseStrengthButton.hovering_button_image = attribute_down_hovered;

        increaseVitalityButton = new AABButton(vitalityTitleX-425,vitalityTitleY-95,40,40,gp);
        increaseVitalityButton.button_image = attribute_up;
        increaseVitalityButton.hovering_button_image = attribute_up_hovered;

        decreaseVitalityButton = new AABButton(vitalityTitleX-365,vitalityTitleY-95,40,40,gp);
        decreaseVitalityButton.button_image = attribute_down;
        decreaseVitalityButton.hovering_button_image = attribute_down_hovered;
    }

    @Override
    public void setImages() {
        enterNameTitle = setupImage("/enter_name_title",630,75);
        newCharacterBackground = setupImage("/newcharacter_background",1440,768);
        attribute_up = setupImage("/attribute_up",40,40);
        attribute_down = setupImage("/attribute_down",40,40);
        attribute_up_hovered = setupImage("/attribute_up_hovered",40,40);
        attribute_down_hovered = setupImage("/attribute_down_hovered",40,40);
    }

    @Override
    public void update() {

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

        if (totalPoints > 0){
            if (increaseStrengthButtonHit == 1){
                totalPoints -= 1;
                strengthPoint += 1;
                gp.player.strength += 1;
            }

            else if (increaseVitalityButtonHit == 1){
                totalPoints -= 1;
                vitalityPoint += 1;
                gp.player.vitality += 1;
            }
        }

        if (increaseStrengthButtonHit == 0){
            if (strengthPoint > 1){
                totalPoints += 1;
                strengthPoint -= 1;
                gp.player.strength -= 1;
            }
        }

        else if (increaseVitalityButtonHit == 0){
            if (vitalityPoint > 1){
                totalPoints += 1;
                vitalityPoint -= 1;
                gp.player.vitality -= 1;
            }
        }

        increaseStrengthButtonHit = -1;
        increaseVitalityButtonHit = -1;

    }

    @Override
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR); // more smooth for images

        g2.drawImage(newCharacterBackground,0,0,null);

        if (createNewCharacterGUIState == enterNameState){
            enterNameTitleX = gp.screenWidth/2 - 70;
            enterNameTitleY = gp.screenHeight/2;

            enterNameField = new Rectangle(enterNameTitleX,enterNameTitleY-35,200,60);

            //g2.setFont(new Font("Tahoma", Font.BOLD, 20));
            //g2.setColor(new Color(100,20,40));
            g2.drawImage(enterNameTitle,enterNameTitleX-245,enterNameTitleY-230,null);

            //g2.draw(enterNameField);


            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // more smooth for texts
            g2.setColor(Color.black);
            g2.setFont(gp.gameFont.immortalFont);
            g2.drawString(String.valueOf(gp.keyH.playerName),enterNameField.x,enterNameField.y+35);
        }

        else if (createNewCharacterGUIState == giveStatsState){

            g2.setFont(new Font("Tahoma", Font.BOLD, 20));
            g2.setColor(new Color(100,20,40));
            g2.drawString(totalPointsTitle,totalPointsTitleX-70,totalPointsTitleY-300);
            g2.drawString(Integer.toString(totalPoints),totalPointsTitleX-20,totalPointsTitleY-270);

            g2.setFont(new Font("Tahoma", Font.BOLD, 20));
            g2.setColor(new Color(100,20,40));
            g2.drawString(strengthTitle,strengthTitleX-550,strengthTitleY-126);
            g2.drawString(Integer.toString(strengthPoint),strengthTitleX-450,strengthTitleY-126);

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

            g2.setFont(new Font("Tahoma", Font.BOLD, 20));
            g2.setColor(new Color(100,20,40));
            g2.drawString(vitalityTitle,vitalityTitleX-550,vitalityTitleY-66);
            g2.drawString(Integer.toString(vitalityPoint),vitalityTitleX-450,vitalityTitleY-66);

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

            // button proceed
            proceedButton.x = strengthTitleX+575;
            proceedButton.y = strengthTitleY+300;
            proceedButton.width = 100;
            proceedButton.height = 50;
            g2.setColor(Color.orange);
            g2.fill(proceedButton);
        }
    }
}