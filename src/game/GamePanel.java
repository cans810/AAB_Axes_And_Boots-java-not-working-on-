package game;

import UI.*;
import area.BattleArea;
import area.VillageArea;
import entity.Enemy;
import entity.Player;
import event.EventHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // 16x3 = 48

    public final int tileSize = originalTileSize/8 * scale; // 6
    public final int maxScreenCol = 240;
    public final int maxScreenRow = 128;
    public final int screenWidth = tileSize * maxScreenCol; // 1440 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 768 pixels

    // ARENA SETTINGS
    public final int arenaMaxScreenCol = 480; // 2880
    public final int arenaMaxScreenRow = 320; // 1920

    public final int arenaScreenWidth = tileSize * arenaMaxScreenCol;
    public final int arenaScreenHeight = tileSize * arenaMaxScreenRow;

    // CAMERA
    private static final double ZOOM_FACTOR = 0.95; // Zoom factor for each border hit
    private static final double MIN_ZOOM_LEVEL_1 = 0.68;
    private static final double MIN_ZOOM_LEVEL_2 = 0.52;
    private double zoomLevel = 1.0;

    public boolean hittingBorder1_player;
    public boolean hittingBorder2_player;
    public boolean hittingBorder1_enemy;
    public boolean hittingBorder2_enemy;

    public boolean zoomedInOnPlayer;

    // FPS
    int FPS = 144;

    // handlers and thread
    public KeyHandler keyH = new KeyHandler(this);
    public StateNMouseHandler mouseH = new StateNMouseHandler(this);
    public EventHandler eventH = new EventHandler(this);
    Thread gameThread;
    public Graphics2D g2;

    // GUI
    TitleGUI titleGUI;
    CreateNewCharacterScreenGUI newCharacterScreenGUI;
    ActionsGUI actionsGUI;
    LevelUpGUI levelUpGUI;

    // GAME
    public VillageArea villageArea;
    public BattleArea battleArea;
    public Player player;
    public Enemy currentEnemy;
    public ArrayList<Enemy> pastEnemies = new ArrayList<>();

    // enum-abstract class
    public LevelSystem levelSystem;

    // game state
    public int gameState;
    public final int titleState = 0;
    public final int createNewCharacterState = 1;
    public final int battleState = 2;
    public final int pauseState = 3;
    public final int dialogueState = 4;
    public final int inVillageState = 5;
    public final int levelUpState = 6;
    public String turn;

    // GENERATORS
    public BattleCreator battleCreator;
    public boolean justEnteredBattle = false;

    // FONT
    public GameFont gameFont;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // enabling this can improve games rendering performance
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.setFocusable(true);

        titleGUI = new TitleGUI(this);
    }

    public void setupGame(){

        gameFont = new GameFont(this);

        battleCreator = new BattleCreator(this);

        player = new Player(this);

        newCharacterScreenGUI = new CreateNewCharacterScreenGUI(this);

        actionsGUI = new ActionsGUI(this);

        levelUpGUI = new LevelUpGUI(this);

        villageArea = new VillageArea(this);

        battleArea = new BattleArea(this);

        levelSystem = new LevelSystem();

        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start(); // this calls method 'run'
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0; // for fps displaying purposes
        int drawCount = 0; // for fps displaying purposes

        while (gameThread != null){ // as long as gameThread exists

            // for example, if FPS is n, the program does steps 1 and 2 n times
            // per second.
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime); // for fps displaying purposes
            lastTime = currentTime;

            // ACTUAL GAME LOOP
            if (delta >= 1){
                // 1 update info such as character position
                update();

                // 2 draw the screen with the updated information
                repaint();

                delta--;
                drawCount++; // for fps displaying purposes
            }

            // FPS DISPLAYING
            if (timer >= 1000000000){
                //System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    private double getDistance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void update(){
        // always update player attributes (not update method, updateAttributes.)
        player.updateAttributes();

        if (gameState == titleState){
            titleGUI.update();
        }

        else if (gameState == createNewCharacterState){
            newCharacterScreenGUI.update();
        }

        else if (gameState == battleState){

            if (areInBattle()){
                // camera
                zoomOutOnHittingScreenBorder();

                eventH.update();

                if (!battleOngoing()){
                    // if enough xp to level up, go to level up screen
                    // else go directly to town screen
                    if (eventH.playerWon()){
                        pastEnemies.add(currentEnemy);
                        currentEnemy = null;
                        eventH.addXpToPlayer(player);

                        System.out.println(player.level);
                        System.out.println(player.xp);

                        if (eventH.playerLevelUp(player)){
                            gameState = levelUpState;
                            levelUpGUI.justEnteredState = true;
                        }
                        else {
                            gameState = inVillageState;
                        }

                    }
                    else if (!eventH.playerWon()){
                        System.exit(0);
                    }
                }
            }

            // create the new enemy once battleState has been entered first time
            if (justEnteredBattle){
                turn = "player";

                currentEnemy = battleCreator.setupEnemy();

                // reset player position
                player.resetRightBeforeBattle();

                justEnteredBattle = false;
            }

            player.update();
            player.gui.update();
            actionsGUI.update();

            if (currentEnemy != null){
                currentEnemy.update();
                currentEnemy.gui.update();
            }

            eventH.update();

        }

        else if (gameState == levelUpState){
            levelUpGUI.update();
        }

        else if (gameState == inVillageState){
            villageArea.update();
        }

    }

    public void paintComponent(Graphics g){ // imagine the graphics object as a pencil, or a paint brush
        super.paintComponent(g);

        // Graphics2D class extends the graphics class to provide more sophisticated
        // control over geometry, coordinate transformations, color management,
        // and text layout.

        g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR); // more smooth for images

        AffineTransform originalTransform = g2.getTransform();


        if (gameState == titleState){
            titleGUI.draw(g2);
        }

        else if (gameState == createNewCharacterState){
            newCharacterScreenGUI.draw(g2);
        }

        else if (gameState == battleState){
            // for black parts at the edge of the screen
            g.setColor(Color.white);
            g.fillRect(0, 0, getWidth(), getHeight());
            // Calculate the translation to center the screen
            /*int translationX = (int) ((getWidth() - (screenWidth * zoomLevel)) / 2);
            int translationY = (int) ((getHeight() - (screenHeight * zoomLevel)) / 2);*/

            int translationX = 0;
            int translationY = 0;

            System.out.println(zoomedInOnPlayer);

            if (zoomLevel < 1){
                translationX = (int) ((getWidth() - (arenaScreenWidth * zoomLevel)) / 2);
                translationY = (int) ((getHeight() - (arenaScreenHeight * zoomLevel)));
            }
            else if (zoomLevel == 1 && zoomedInOnPlayer){
                translationX = (int) ((getWidth() - ((player.torso.x + currentEnemy.torso.x) * zoomLevel)) / 2);
                translationY = (int) ((getHeight() - (arenaScreenHeight-200 * zoomLevel)));
            }
            else{
                translationX = (int) ((getWidth() - (arenaScreenWidth * zoomLevel)) / 2);
                translationY = (int) ((getHeight() - (arenaScreenHeight-200 * zoomLevel)));
            }

            // Apply the translation
            g2.translate(translationX, translationY);

            g2.scale(zoomLevel, zoomLevel);

            battleArea.draw(g2);

            //draw back line for ground
            /*g2.setStroke(new BasicStroke(4.0f));
            g2.setColor(Color.black);
            g2.drawLine(0,tileSize*84,tileSize*240,tileSize*84);
            g2.setStroke(new BasicStroke(1.0f));*/

            //entities
            // if its player's turn, draw player on top
            if (turn.equals("player")){
                currentEnemy.draw(g2);
                player.draw(g2);
            }
            // if its enemy's turn, draw enemy on top
            else if (turn.equals("enemy")){
                player.draw(g2);
                currentEnemy.draw(g2);
            }

            g2.setColor(Color.red);
            g2.drawLine(0,1550,tileSize*480,1550);

            //draw front line for ground
            /*g2.setStroke(new BasicStroke(4.0f));
            g2.setColor(Color.black);
            g2.drawLine(0,tileSize*112,tileSize*240,tileSize*112);
            g2.setStroke(new BasicStroke(1.0f));*/

            g2.scale(1 / zoomLevel, 1 / zoomLevel);
            g2.translate(-translationX, -translationY);

            g2.setTransform(originalTransform);

            //entity GUI
            //actions GUI
            if (turn.equals("player")){
                actionsGUI.draw(g2);
            }

            player.gui.draw(g2);

            currentEnemy.gui.draw(g2);
        }

        else if (gameState == levelUpState){
            levelUpGUI.draw(g2);
        }

        else if (gameState == inVillageState){
            villageArea.draw(g2);
        }

        g2.dispose(); // dispose of this graphics context and release any system resources that it is using.
    }

    public void zoomOutOnHittingScreenBorder(){
        if (player.torso.x <= 700 || player.torso.x >= screenWidth + 630 /*|| player.torso.y < 0 || player.torso.y >= screenHeight*/) {
            if (zoomLevel > MIN_ZOOM_LEVEL_1) {
                zoomLevel *= ZOOM_FACTOR; // Adjust the zoom level
                hittingBorder1_player = true;
            }
            else{
                hittingBorder1_player = false;
            }
        }
        if (player.torso.x <= 350 || player.torso.x >= screenWidth + 1000/*|| player.torso.y < 0 || player.torso.y >= screenHeight*/) {
            if (zoomLevel > MIN_ZOOM_LEVEL_2) {
                zoomLevel *= ZOOM_FACTOR; // Adjust the zoom level
                hittingBorder2_player = true;
            }
            else{
                hittingBorder2_player = false;
            }
        }

        if (currentEnemy.torso.x <= 700 || currentEnemy.torso.x >= screenWidth + 630 /*|| currentEnemy.torso.y < 0 || currentEnemy.torso.y >= screenHeight*/) {
            if (zoomLevel > MIN_ZOOM_LEVEL_1) {
                zoomLevel *= ZOOM_FACTOR; // Adjust the zoom level
                hittingBorder1_enemy = true;
            }
            else{
                hittingBorder1_enemy = false;
            }
        }
        if (currentEnemy.torso.x <= 350 || player.torso.x >= screenWidth + 1000/*|| player.torso.y < 0 || player.torso.y >= screenHeight*/) {
            if (zoomLevel > MIN_ZOOM_LEVEL_2) {
                zoomLevel *= ZOOM_FACTOR; // Adjust the zoom level
                hittingBorder2_enemy = true;
            }
            else{
                hittingBorder2_enemy = false;
            }
        }

        if (getDistance(player.torso.x,player.torso.y,currentEnemy.torso.x,currentEnemy.torso.y) <= 400){
            if (!hittingBorder1_player){
                zoomLevel = 1.0;
                zoomedInOnPlayer = true;
            }
            else if (!hittingBorder2_player){
                zoomLevel = 1.0;
                zoomedInOnPlayer = true;
            }

            if (!hittingBorder1_enemy){
                zoomLevel = 1.0;
                zoomedInOnPlayer = true;
            }
            else if (!hittingBorder2_enemy){
                zoomLevel = 1.0;
                zoomedInOnPlayer = true;
            }
        }
        else if(zoomLevel == 1 && zoomedInOnPlayer){
            zoomedInOnPlayer = false;
        }
    }

    public void setEntitiesBattleState(boolean areBattling){
        if (areBattling){
            player.setupEntityInBattle();
        }
        else{
            player.setupEntityOffBattle();
        }
    }

    public boolean areInBattle(){
        return player.inBattle && currentEnemy != null && currentEnemy.inBattle;
    }

    public boolean battleOngoing(){
        if (areInBattle()){
            return player.alive && currentEnemy != null && currentEnemy.alive;
        }
        return false;
    }

    public BufferedImage scaleImage(BufferedImage original, int width, int height){
        // to scale the image beforehand
        BufferedImage scaledImage = new BufferedImage(width,height,original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.drawImage(original,0,0,width,height,null);
        g2.dispose();

        return scaledImage;
    }

    public void determineTurn(){
        if (player.played){
            turn = "enemy";
            player.played = false;
        }
        else if (currentEnemy.played){
            turn = "player";
            currentEnemy.played = false;
        }
    }

}
