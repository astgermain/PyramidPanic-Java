package pyramidpanic;

import java.awt.Rectangle;

public class GamePhysics {

    GameWorld game;
    private boolean collision;
    private Player p;
    private GameObject scorpes, beets, mums, wall;
    private int scoreCalc;

    public boolean noPlayerCollision(GameWorld game) {
        this.game = game;
        this.p = game.getPlayer();
        for (int i = 0; i < game.grid.size(); i++) {
            if (game.grid.get(i).isSolid() && (!(game.grid.get(i).getObjectType() < 23 && game.grid.get(i).getObjectType() > 18))) {
                Rectangle pBox = new Rectangle(this.p.getX(), this.p.getY(), 32, 32);
                Rectangle gameObjectBox = new Rectangle(game.grid.get(i).getX() - game.cameraX , game.grid.get(i).getY() - game.cameraY, 32, 32);
                if (pBox.intersects(gameObjectBox)) {
                   
                    if (game.grid.get(i).getObjectType() == 7) {
                        game.grid.remove(i);
                        scoreCalc = p.getScore() + 2000;
                        p.setScore(scoreCalc);
                        //Can play sound here for getting points
                    }
                    else if (game.grid.get(i).getObjectType() == 7) {
                        game.grid.remove(i);
                        scoreCalc = p.getScore() + 2000;
                        p.setScore(scoreCalc);
                        //Can play sound here for getting points
                    }
                    else if (game.grid.get(i).getObjectType() == 9) {
                        if(p.getLevel() == 1){
                            p.canLight(true);
                            game.setMap2();
                            p.setLevel(2);
                            p.setSpawn(game);
                            break;
                        }
                        
                        
                        //Can play sound here for getting points
                    }
                    else if (game.grid.get(i).getObjectType() == 3) {
                        game.getPlayer().setLives(game);
                        game.getPlayer().setSpawn(game);
                    }
                    else if (game.grid.get(i).getObjectType() == 4) {
                        game.getPlayer().setLives(game);
                        game.getPlayer().setSpawn(game);
                    }
                    else if (game.grid.get(i).getObjectType() == 25) {
                        p.setScore(p.getScore() + (p.getLives() * 10000));
                        game.getPlayer().drawWinBool = true;
                    }
                    else if (game.grid.get(i).getObjectType() == 1) {
                        if (noWallCollision(game, game.grid.get(i))){
                            if(p.getObjectType() == 22){
                                game.grid.get(i).setX(game.grid.get(i).getX() + 1);
                                game.getPlayer().setX(game.getPlayer().getX() - 1);
                            }
                            else if(p.getObjectType() == 21){
                                game.grid.get(i).setX(game.grid.get(i).getX() - 1);
                                game.getPlayer().setX(game.getPlayer().getX() + 1);
                            }
                        }
                    }
                    else if (game.grid.get(i).getObjectType() == 2) {
                        if (noWallCollision(game, game.grid.get(i))){
                            if(p.getObjectType() == 20){
                                game.grid.get(i).setY(game.grid.get(i).getY() + 1);
                                game.getPlayer().setY(game.getPlayer().getY() - 1);
                            }
                            else if(p.getObjectType() == 19){
                                game.grid.get(i).setY(game.grid.get(i).getY() - 1);
                                game.getPlayer().setY(game.getPlayer().getY() + 1);
                            }
                        }
                    }
                    else if (game.grid.get(i).getObjectType() == 11) {
                        game.grid.remove(i);
                        game.getPlayer().setLivesUp(game);
                    }
                    else if (game.grid.get(i).getObjectType() == 12) {
                        game.grid.remove(i);
                        game.getPlayer().setCanShoot();
                    }
                     else if (game.grid.get(i).getObjectType() == 30) {
                        game.getPlayer().setLives(game);
                        game.getPlayer().setSpawn(game);
                    }
                    else if (game.grid.get(i).getObjectType() == 31) {
                        game.getPlayer().setLives(game);
                        game.getPlayer().setSpawn(game);
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean noScorpionCollision(GameWorld game, GameObject go) {
        this.game = game;
        this.scorpes = go;
        
        for (int j = 0; j < game.grid.size(); j++) {
            
            //System.out.println(this.scorpes);
                
                Rectangle sBox = new Rectangle(this.scorpes.getX(), this.scorpes.getY(), 32, 32);
                Rectangle gameObjectBox = new Rectangle(game.grid.get(j).getX(), game.grid.get(j).getY(), 32, 32);
                if (gameObjectBox.intersects(sBox) && (game.grid.get(j).getObjectType() > 18 && game.grid.get(j).getObjectType() < 23)) {
                    game.getPlayer().setLives(game);
                    game.getPlayer().setSpawn(game);
                    return false;
                } 
                else if (gameObjectBox.intersects(sBox) && game.grid.get(j).getObjectType() != 0 && game.grid.get(j) != this.scorpes) {
                    //System.out.println("collide");
                    if (this.scorpes.getObjectType() == 3) {
                        this.scorpes.setObjectType(4);
                        this.scorpes.setRight();
                    } else if (this.scorpes.getObjectType() == 4) {
                        this.scorpes.setObjectType(3);
                        this.scorpes.setLeft();
                       //\ System.out.println(game.grid.get(i).getObjectType());
                    }
                    return false;
                } 
              
                
            }
        
        
        return true;
    }
     public boolean noWallCollision(GameWorld game, GameObject go) {
        this.game = game;
        this.wall = go;
        
        for (int j = 0; j < game.grid.size(); j++) {
            
            //System.out.println(this.scorpes);
                
                Rectangle wBox = new Rectangle(this.wall.getX(), this.wall.getY(), 32, 32);
                Rectangle gameObjectBox = new Rectangle(game.grid.get(j).getX(), game.grid.get(j).getY(), 32, 32);
               
                if (gameObjectBox.intersects(wBox) && game.grid.get(j).getObjectType() != 0 && game.grid.get(j) != this.wall) {
                    
                    
                    return false;
                } 
              
                
            }
        
        
        return true;
    }

    public boolean noBeetleCollision(GameWorld game, GameObject go) {
        this.game = game;
        this.beets = go;
        

        for (int j = 0; j < game.grid.size(); j++) {
            Rectangle bBox = new Rectangle(this.beets.getX(), this.beets.getY(), 32, 32);
            Rectangle gameObjectBox = new Rectangle(game.grid.get(j).getX(), game.grid.get(j).getY(), 32, 32);
            if (gameObjectBox.intersects(bBox) && (game.grid.get(j).getObjectType() > 18 && game.grid.get(j).getObjectType() < 23)) {
                game.getPlayer().setLives(game);
                game.getPlayer().setSpawn(game);
                
            } else if (gameObjectBox.intersects(bBox) && game.grid.get(j).getObjectType() != 0 && game.grid.get(j).getObjectType() != 30 && game.grid.get(j).getObjectType() != 31) {

                if (this.beets.getObjectType() == 30) {
                    this.beets.setObjectType(31);
                    this.beets.setUp();
                } else if (this.beets.getObjectType() == 31) {
                    this.beets.setObjectType(30);
                    this.beets.setDown();
                }
            } else {

            }
        }
        return true;
    }
    // mummys need to move set distance like player, and then reset position when runing into walls 
    
    public boolean noMummyCollision(GameWorld game, GameObject go) {
        this.game = game;
        this.mums = go;
        for (int j = 0; j < game.grid.size(); j++) {
            Rectangle mBox = new Rectangle(this.mums.getX(), this.mums.getY(), 32, 32);
            Rectangle gameObjectBox = new Rectangle(game.grid.get(j).getX(), game.grid.get(j).getY(), 32, 32);
            if (gameObjectBox.intersects(mBox) && (game.grid.get(j).getObjectType() > 18 && game.grid.get(j).getObjectType() < 23)) {
                game.getPlayer().setLives(game);
                game.getPlayer().setSpawn(game);
                return false;
            } 
            else if (gameObjectBox.intersects(mBox) && game.grid.get(j).getObjectType() != 0 && game.grid.get(j) != this.mums) {
                
                return false;
            }      
        }
        return true;
    } 
     

}
