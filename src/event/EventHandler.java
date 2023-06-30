package event;

import entity.Player;
import game.GamePanel;

public class EventHandler {
    GamePanel gp;

    public EventHandler(GamePanel gp){
        this.gp = gp;
    }

    public void entityDead(){
        if (gp.player.HP <= 0){
            gp.player.alive = false;
            gp.player.inBattle = false;
        }
        else if (gp.currentEnemy != null && gp.currentEnemy.HP <= 0){
            gp.currentEnemy.alive = false;
            gp.currentEnemy.inBattle = false;
        }
    }

    public boolean playerWon(){
        if (gp.player.HP > 0 && gp.currentEnemy.HP <= 0){
            return true;
        }
        return false;
    }

    public boolean playerLevelUp(Player player){
        if (player.xp >= gp.levelSystem.xp_for_level1 && !player.level1Reached){
            player.level = 1;
            player.level1Reached = true;
            return true;
        }
        else if (player.xp >= gp.levelSystem.xp_for_level2 && !player.level2Reached){
            player.level = 2;
            player.level2Reached = true;
            return true;
        }
        else if (player.xp >= gp.levelSystem.xp_for_level3 && !player.level3Reached){
            player.level = 3;
            player.level3Reached = true;
            return true;
        }
        else if (player.xp >= gp.levelSystem.xp_for_level4 && !player.level4Reached){
            player.level = 4;
            player.level4Reached = true;
            return true;
        }
        else if (player.xp >= gp.levelSystem.xp_for_level5 && !player.level5Reached){
            player.level = 5;
            player.level5Reached = true;
            return true;
        }
        else if (player.xp >= gp.levelSystem.xp_for_level6 && !player.level6Reached){
            player.level = 6;
            player.level6Reached = true;
            return true;
        }
        return false;
    }

    public void addXpToPlayer(Player player){
        player.xp += 10;
    }

    public void update(){
        entityDead();
    }
}
