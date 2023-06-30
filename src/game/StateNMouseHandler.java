package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StateNMouseHandler implements MouseListener {

    GamePanel gp;
    public boolean played;

    public StateNMouseHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        if (gp.gameState == gp.battleState){
            if (gp.turn.equals("player")){
                if (gp.actionsGUI.forward.intersects(x, y, 1, 1)){
                    gp.player.moveForwardClicked = true;

                    //gp.player.played = true;
                }

                else if (gp.actionsGUI.backward.intersects(x, y, 1, 1)){
                    gp.player.moveBackwardClicked = true;

                    //gp.player.played = true;
                }

                else if (gp.actionsGUI.attackLight.intersects(x, y, 1, 1) && gp.player.canAttack){
                    gp.player.lightAttackClicked = true;

                    //gp.player.played = true;
                }

                else if (gp.actionsGUI.attackLeaping.intersects(x, y, 1, 1)){
                    gp.player.leapAttackClicked = true;

                    //gp.player.played = true;
                }

                else {
                    System.out.println(x + " " + y);
                    gp.player.played = false;
                }
            }
        }
        else if (gp.gameState == gp.titleState){

            if (gp.titleGUI.newGameTitleArea.intersects(x, y, 1, 1)){
                gp.gameState = gp.createNewCharacterState;
            }

            else if (gp.titleGUI.exitTitleArea.intersects(x, y, 1, 1)){
                System.exit(0);
            }

            else {
                System.out.println("What are you clicking on?");
            }

        }
        else if (gp.gameState == gp.createNewCharacterState){

            if (gp.newCharacterScreenGUI.createNewCharacterGUIState == gp.newCharacterScreenGUI.giveStatsState){

                // if proceed button is clicked, enter in battle
                if (gp.newCharacterScreenGUI.proceedButton.intersects(x, y, 1, 1)){
                    gp.gameState = gp.battleState;
                    gp.justEnteredBattle = true;
                    gp.setEntitiesBattleState(true);
                }

                // strength button
                if (gp.newCharacterScreenGUI.increaseStrengthButton.intersects(x, y, 1, 1)){
                    gp.newCharacterScreenGUI.increaseStrengthButtonHit = 1;
                }
                else if (gp.newCharacterScreenGUI.decreaseStrengthButton.intersects(x, y, 1, 1)){
                    gp.newCharacterScreenGUI.increaseStrengthButtonHit = 0;
                }

                // vitality button
                if (gp.newCharacterScreenGUI.increaseVitalityButton.intersects(x, y, 1, 1)){
                    gp.newCharacterScreenGUI.increaseVitalityButtonHit = 1;
                }
                else if (gp.newCharacterScreenGUI.decreaseVitalityButton.intersects(x, y, 1, 1)){
                    gp.newCharacterScreenGUI.increaseVitalityButtonHit = 0;
                }
            }
        }
        else if (gp.gameState == gp.levelUpState){
            // if proceed button is clicked, go to town
            if (gp.levelUpGUI.proceedButton.intersects(x, y, 1, 1)){
                gp.levelUpGUI.setAttributesFinal();
                gp.gameState = gp.inVillageState;
            }

            // strength button
            if (gp.levelUpGUI.increaseStrengthButton.intersects(x, y, 1, 1)){
                gp.levelUpGUI.increaseStrengthButtonHit = 1;
            }
            else if (gp.levelUpGUI.decreaseStrengthButton.intersects(x, y, 1, 1)){
                gp.levelUpGUI.increaseStrengthButtonHit = 0;
            }

            // vitality button
            if (gp.levelUpGUI.increaseVitalityButton.intersects(x, y, 1, 1)){
                gp.levelUpGUI.increaseVitalityButtonHit = 1;
            }
            else if (gp.levelUpGUI.decreaseVitalityButton.intersects(x, y, 1, 1)){
                gp.levelUpGUI.increaseVitalityButtonHit = 0;
            }
        }
        else if (gp.gameState == gp.inVillageState){
            if (gp.villageArea.newBattleButton.intersects(x, y, 1, 1)) {
                gp.gameState = gp.battleState;
                gp.justEnteredBattle = true;
            }
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
