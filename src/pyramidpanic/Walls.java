/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pyramidpanic;

import java.awt.Graphics;

/**
 *
 * @author A
 */
public class Walls extends GameObject{
    GamePhysics gp;
    public Walls(int x, int y, int objectType) {
        super(x, y, objectType);
    }
    public void update(GameWorld game){
        this.gp = new GamePhysics();
        if(getObjectType() == 1);
        if(gp.noWallCollision(game, this)){
            setMove(false);      
        }
        
    }

}
