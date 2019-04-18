package pyramidpanic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author A
 */
public class Beetle extends Enemies {
    private boolean isUp, isDown;
    private GamePhysics gp;
    BufferedImage img;
    public Beetle(int x, int y, int objectType, int side) {
        super(x, y, objectType);
        if (side == 1) {
            setUp();
        } else if (side == 0) {
            setDown();
        }
    }
    public void update(GameWorld game) {
        this.gp = new GamePhysics();
        img = game.getBimg(getObjectType());
        if (gp.noBeetleCollision(game, this)) {
            if (getUp()) {
                setY(getY() - 1);
            } else if (getDown()) {
                setY(getY() + 1);
            }
        }

    }
    @Override
    public void paint(Graphics g, GameWorld game){
        g.drawImage(img, getX() - game.getPlayer().getX(), getY()  - game.getPlayer().getY(), null);

    }

    
}
