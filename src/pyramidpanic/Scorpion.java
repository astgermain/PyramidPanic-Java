/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pyramidpanic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author A
 */
public class Scorpion extends Enemies{
    GameWorld game;
    GamePhysics gp;
    BufferedImage img;
    private boolean isLeft, isRight, setLeft, setRight;
    public Scorpion(int x, int y, int objectType, int side) {
        super(x, y, objectType);
        if(side == 0){
            setLeft();
        }
        else if(side == 1){
            setRight();
        }
        
    }
    @Override
    public void update(GameWorld game){
        this.gp = new GamePhysics();
        img = game.getBimg(getObjectType());
        if(gp.noScorpionCollision(game, this)){
            
            if(getLeft()){
                setX(getX() - 1);
            }
            else if(getRight()){
                setX(getX() + 1);
            }
        }
        
    }
  
    public void paint(Graphics g, GameWorld game){
        this.game = game;
        g.drawImage(img, getX() - game.getPlayer().getX(), getY() - game.getPlayer().getY(), null);

    }
    public void scorpionMovement(){
        
    }
    

    
}
