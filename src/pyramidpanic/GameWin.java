/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pyramidpanic;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import static pyramidpanic.GameWorld.TITLE;
import static pyramidpanic.GameWorld.gameDim;

/**
 *
 * @author A
 */
public class GameWin extends Canvas implements Runnable {
    Thread t = new Thread();
    private static boolean running = false;
    private boolean victory = false, defeat = false;
    private JFrame frame;
    private BufferedImage win;
    public GameWin(){
        setMinimumSize(gameDim);
        setMaximumSize(gameDim);
        setPreferredSize(gameDim);
        frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        //this.addKeyListener(tc1);
        init();

        requestFocus();
    }
    @Override
    public void run() {
        Thread me = Thread.currentThread();
        while (t == me) {
            //update();
            //paint();
            try {
                t.sleep(1000 / 144);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public synchronized void start() {
        running = true;
        t = new Thread(this);
        t.start();
    }

    public synchronized void stop() {
        running = false;
        System.exit(0);
    }
    public void init(){
        try {
            win = ImageIO.read(new File("src/pyramidpanic/Resources/Congratulation.gif"));
        } catch (IOException ex) {
            System.out.println("image not loaded");
        }
    }
    public void paint(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        drawWin(g);
    }
    public void drawWin(Graphics g){
        g.drawImage(win, 0, 0, null);
    }
}
