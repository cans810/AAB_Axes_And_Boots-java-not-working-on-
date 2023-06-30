package game;

import entity.Enemy;

public class BattleCreator {

    GamePanel gp;

    public BattleCreator(GamePanel gp){
        this.gp = gp;

    }

    public Enemy setupEnemy(){
        return new Enemy(gp);
    }
}
