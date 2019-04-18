/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pyramidpanic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author A
 */
public class PlayerController extends Observable implements KeyListener {

    private Player p;
    private boolean reducePts;
    GameWorld game;
    //private final int up, down, right, left;
    public PlayerController(Player p) {
        this.p = p;
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                reducePts = true;
                

            }
        }, 500, 1000);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        
        switch (keyCode) {
            case VK_UP:
                p.toogleUp();
                
                break;
            case VK_DOWN:
                p.toogleDown();
                break;
            case VK_LEFT:
                p.toogleLeft();
                break;
            case VK_RIGHT:
                p.toogleRight();
                break;
            default:
                break;
        }

        if (keyCode == VK_ENTER) {
            if(p.hLight() == true){
                p.setMapLight(true);
                if(reducePts == true){
                    p.setScore(p.getScore() - 1000);
                    
                }
            }
            reducePts = false;

        }
        if (keyCode == VK_SPACE) {
            p.shoot();

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case VK_UP:
                p.unToogleUp();
                break;
            case VK_DOWN:
                p.unToogleDown();
                break;
            case VK_LEFT:
                p.unToogleLeft();
                break;
            case VK_RIGHT:
                p.unToogleRight();
                break;
            default:
                break;
        }
        if (keyCode == VK_ENTER) {
           
                p.setMapLight(false);
            
            
        }
    }

}
