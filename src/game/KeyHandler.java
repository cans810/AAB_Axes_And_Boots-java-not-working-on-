package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public char keyPressed;
    public String playerName = "";

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        gp.newCharacterScreenGUI.draw(gp.newCharacterScreenGUI.g2);

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE){

            if (!(playerName.length() <= 0)){
                playerName = playerName.substring(0, playerName.length() - 1);
            }

        }
        else if (e.getKeyCode() == KeyEvent.VK_ENTER){

            gp.player.name = playerName;
            gp.newCharacterScreenGUI.createNewCharacterGUIState = gp.newCharacterScreenGUI.giveStatsState;

        }
        else if (!e.isActionKey()){

            keyPressed = e.getKeyChar();
            playerName += keyPressed;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
