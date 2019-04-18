package pyramidpanic;

import java.awt.Canvas;

/**
 *
 * @author A
 */
public class GameOver extends Canvas implements Runnable {
    Thread t = new Thread();
    private static boolean running = false;
    private boolean victory = false, defeat = false;
    
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
}
