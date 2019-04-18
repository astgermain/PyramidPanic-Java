package pyramidpanic;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameWorld extends Canvas implements Runnable{  
    private static final int mapWidth = 39;
    private static final int mapHeight = 21;
    private static final int mapWidthPx = 628;
    private static final int mapHeightPx = 436;
    public static final Dimension gameDim = new Dimension(mapWidthPx, mapHeightPx);
    public static final String TITLE = "Pyramid Panic";
    public int cameraX = 304, cameraY= 208;
    private Thread t = new Thread();
    private BufferedImage[] bimg = new BufferedImage[32];
    private static boolean running = false, canMove = true;
    public ArrayList<GameObject> grid;
    private Scanner sc;
    private GameObject game;
    private JFrame frame;
    private BufferedImage bg, lrWall, udWall, scorpionLeft, scorpionRight, wall1, 
            wall2, treasure1, treasure2, sword, title, scroll, scarab, potion, 
            pistol, mummyUp, mummyDown, mummyLeft, mummyRight, lives, light,
            playerUp, playerDown, playerLeft, playerRight, bullet, panel, door,
            win, start, quit, help, beetleDown, beetleUp;
    private Graphics2D gameWorldGraphics;
    private PlayerController pc;
    private Player p;
    private int[][] map = {
        
        {5, 6, 5, 5, 5, 6, 6, 5, 5, 5, 6, 5, 5, 5, 5, 5, 6, 6, 5, 5, 6, 5, 5, 5, 6, 6, 5, 5, 5, 6, 5, 5, 5, 5, 5, 6, 6, 5, 5},
        {5, 20, 0, 0, 12, 6, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 5, 0, 3, 0, 0, 4, 0, 0, 0, 5, 0, 30, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {6, 0, 0, 1, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 31, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0, 0, 5},
        {5, 0, 2, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 5, 11, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 9, 5, 0, 0, 7, 7, 7, 7, 0, 0, 0, 5, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 5},
        {6, 0, 5, 6, 5, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 5},
        {6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 6, 30, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 31, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {6, 0, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 0, 0, 0, 5, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 3, 0, 0, 4, 0, 0, 0, 5},
        {5, 0, 1, 0, 0, 0, 0, 0, 7, 7, 0, 0, 0, 5, 6, 6, 6, 6, 5, 6, 5, 0, 0, 0, 0, 0, 0, 7, 7, 7, 7, 7, 7, 7, 0, 0, 30, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 7, 7, 7, 7, 0, 6, 5, 5, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 11, 5},
        {5, 0, 0, 0, 0, 0, 30, 7, 7, 7, 7, 6, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 5, 5, 6, 6, 6, 6, 6, 5, 5, 5, 5},
        {5, 0, 0, 0, 31, 0, 0, 0, 7, 7, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 6, 6, 6, 6, 5, 5, 5, 0, 0, 0, 0, 30, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 5, 5, 5, 6, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 31, 0, 0, 0, 5}, 
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 3, 0, 0, 6, 0, 0, 30, 31, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 5, 0, 0, 3, 0, 6, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 0, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 0, 0, 30, 31, 6, 0, 0, 0, 0, 0, 0, 5, 0, 4, 0, 0, 6, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 9, 5},
        {5, 5, 6, 6, 6, 5, 6, 5, 5, 5, 5, 6, 5, 5, 6, 5, 5, 5, 6, 6, 6, 5, 5, 5, 6, 6, 5, 5, 5, 6, 5, 5, 5, 5, 5, 6, 6, 5, 5},
    };
    private int[][] map2 = {
        
        {5, 6, 5, 5, 5, 6, 6, 5, 5, 5, 6, 5, 5, 5, 5, 5, 6, 6, 5, 5, 6, 5, 5, 5, 6, 6, 5, 5, 5, 6, 5, 5, 5, 5, 5, 6, 6, 5, 5},
        {5, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 5, 0, 3, 0, 0, 0, 0, 0, 0, 5, 0, 30, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {6, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 5, 7, 0, 31, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 5, 7, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 25, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 5},
        {6, 0, 5, 6, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 7, 7, 7, 7, 0, 0, 0, 0, 0, 0, 5},
        {6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 6, 30, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 7, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 6, 6, 6, 6, 5, 6, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 6, 5, 5, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 5, 5, 6, 6, 6, 6, 6, 5, 5, 5, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 6, 6, 6, 6, 5, 5, 5, 0, 0, 0, 0, 30, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5, 6, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 31, 0, 0, 0, 5}, 
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 3, 0, 0, 6, 0, 0, 30, 31, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 5, 0, 3, 0, 6, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 31, 6, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 5},
        {5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 9, 5},
        {5, 5, 6, 6, 6, 5, 6, 5, 5, 5, 5, 6, 5, 5, 6, 5, 5, 5, 6, 6, 6, 5, 5, 5, 6, 6, 5, 5, 5, 6, 5, 5, 5, 5, 5, 6, 6, 5, 5},
    };
        
    public GameWorld(){
        
        
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
            update();
            paint();
            try {
                t.sleep(0);
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
    
    public void init() {
        this.grid = new ArrayList<GameObject>();
       
        try {
//            bg = ImageIO.read(new File("src/pyramidpanic/Resources/Background2.bmp"));
//            lrWall = ImageIO.read(new File("src/pyramidpanic/Resources/Block_hor.gif"));
//            udWall = ImageIO.read(new File("src/pyramidpanic/Resources/Block_vert.gif"));
//            scorpionLeft = ImageIO.read(new File("src/pyramidpanic/Resources/Scorpion_left.gif"));
//            scorpionRight = ImageIO.read(new File("src/pyramidpanic/Resources/Scorpion_right.gif"));
//            wall1 = ImageIO.read(new File("src/pyramidpanic/Resources/Wall1.gif"));
//            wall2 = ImageIO.read(new File("src/pyramidpanic/Resources/Wall2.gif"));
//            treasure1 = ImageIO.read(new File("src/pyramidpanic/Resources/Treasure1.gif"));
//            treasure2 = ImageIO.read(new File("src/pyramidpanic/Resources/Treasure2.gif"));
//            sword = ImageIO.read(new File("src/pyramidpanic/Resources/Sword.gif"));
//            scarab = ImageIO.read(new File("src/pyramidpanic/Resources/Scarab.gif"));
//            potion = ImageIO.read(new File("src/pyramidpanic/Resources/Potion.gif"));
//            pistol = ImageIO.read(new File("src/pyramidpanic/Resources/Pistol.gif"));
//            mummyUp = ImageIO.read(new File("src/pyramidpanic/Resources/Mummy_up.gif"));
//            mummyDown = ImageIO.read(new File("src/pyramidpanic/Resources/Mummy_down.gif"));
//            mummyLeft = ImageIO.read(new File("src/pyramidpanic/Resources/Mummy_left.gif"));
//            mummyRight = ImageIO.read(new File("src/pyramidpanic/Resources/Mummy_right.gif"));
//            lives = ImageIO.read(new File("src/pyramidpanic/Resources/Lives.gif"));
//            light = ImageIO.read(new File("src/pyramidpanic/Resources/Light.png"));
//            playerUp = ImageIO.read(new File("src/pyramidpanic/Resources/Explorer_up.gif"));
//            playerDown = ImageIO.read(new File("src/pyramidpanic/Resources/Explorer_down.gif"));
//            playerLeft = ImageIO.read(new File("src/pyramidpanic/Resources/Explorer_left.gif"));
//            playerRight = ImageIO.read(new File("src/pyramidpanic/Resources/Explorer_right.gif"));
//            bullet = ImageIO.read(new File("src/pyramidpanic/Resources/Bullet.gif"));
//            panel = ImageIO.read(new File("src/pyramidpanic/Resources/Panel.gif"));
//            door = ImageIO.read(new File("src/pyramidpanic/Resources/Door.gif"));
//            win = ImageIO.read(new File("src/pyramidpanic/Resources/Congratulation.gif"));
//            start = ImageIO.read(new File("src/pyramidpanic/Resources/Button_start.gif"));
//            quit = ImageIO.read(new File("src/pyramidpanic/Resources/Button_quit.gif"));
//            help = ImageIO.read(new File("src/pyramidpanic/Resources/Button_help.gif"));
//            beetleDown = ImageIO.read(new File("src/pyramidpanic/Resources/Beetle_down.gif"));
//            beetleUp = ImageIO.read(new File("src/pyramidpanic/Resources/Beetle_up.gif"));

              bg = ImageIO.read(new File("Resources/Background2.bmp"));
            lrWall = ImageIO.read(new File("Resources/Block_hor.gif"));
            udWall = ImageIO.read(new File("Resources/Block_vert.gif"));
            scorpionLeft = ImageIO.read(new File("Resources/Scorpion_left.gif"));
            scorpionRight = ImageIO.read(new File("Resources/Scorpion_right.gif"));
            wall1 = ImageIO.read(new File("Resources/Wall1.gif"));
            wall2 = ImageIO.read(new File("Resources/Wall2.gif"));
            treasure1 = ImageIO.read(new File("Resources/Treasure1.gif"));
            treasure2 = ImageIO.read(new File("Resources/Treasure2.gif"));
            sword = ImageIO.read(new File("Resources/Sword.gif"));
            scarab = ImageIO.read(new File("Resources/Scarab.gif"));
            potion = ImageIO.read(new File("Resources/Potion.gif"));
            pistol = ImageIO.read(new File("Resources/Pistol.gif"));
            mummyUp = ImageIO.read(new File("Resources/Mummy_up.gif"));
            mummyDown = ImageIO.read(new File("Resources/Mummy_down.gif"));
            mummyLeft = ImageIO.read(new File("Resources/Mummy_left.gif"));
            mummyRight = ImageIO.read(new File("Resources/Mummy_right.gif"));
            lives = ImageIO.read(new File("Resources/Lives.gif"));
            light = ImageIO.read(new File("Resources/Light.png"));
            playerUp = ImageIO.read(new File("Resources/Explorer_up.gif"));
            playerDown = ImageIO.read(new File("Resources/Explorer_down.gif"));
            playerLeft = ImageIO.read(new File("Resources/Explorer_left.gif"));
            playerRight = ImageIO.read(new File("Resources/Explorer_right.gif"));
            bullet = ImageIO.read(new File("Resources/Bullet.gif"));
            panel = ImageIO.read(new File("Resources/Panel.gif"));
            door = ImageIO.read(new File("Resources/Door.gif"));
            win = ImageIO.read(new File("Resources/Congratulation.gif"));
            start = ImageIO.read(new File("Resources/Button_start.gif"));
            quit = ImageIO.read(new File("Resources/Button_quit.gif"));
            help = ImageIO.read(new File("Resources/Button_help.gif"));
            beetleDown = ImageIO.read(new File("Resources/Beetle_down.gif"));
            beetleUp = ImageIO.read(new File("Resources/Beetle_up.gif"));

            
            
            

        } catch (IOException ex) {
            System.out.println("doesnt work");
        }
        bimg[0] = null;
        bimg[1] = lrWall;
        bimg[2] = udWall;
        bimg[3] = scorpionLeft;
        bimg[4] = scorpionRight;
        bimg[5] = wall1;
        bimg[6] = wall2;
        bimg[7] = treasure1;
        bimg[8] = treasure2;
        bimg[9] = sword;
        bimg[10] = scarab;
        bimg[11] = potion;
        bimg[12] = pistol;
        bimg[13] = mummyUp;
        bimg[14] = mummyDown;
        bimg[15] = mummyLeft;
        bimg[16] = mummyRight;
        bimg[17] = lives;
        bimg[18] = light;
        bimg[19] = playerUp;
        bimg[20] = playerDown;
        bimg[21] = playerLeft;
        bimg[22] = playerRight;
        bimg[23] = bullet;
        bimg[24] = panel;
        bimg[25] = door;
        bimg[26] = win;
        bimg[27] = start;
        bimg[28] = quit;
        bimg[29] = help;
        bimg[30] = beetleDown;
        bimg[31] = beetleUp;
       
        /* 
        //For reading map from a file
        File map = new File("map1.txt");
        try {
            sc = new Scanner(map);
        } catch (FileNotFoundException ex) {
            System.out.println("Map File Not Found");
        }
        */
        
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                int tempI = i * 32 + cameraY;
                int tempJ = j * 32 + cameraX;
                if(map[i][j] > 18 && map[i][j] < 23){
                    
                    grid.add(new Player(tempJ, tempI, map[i][j]));
                    pc = new PlayerController((Player) grid.get(grid.size() - 1));
                    p = (Player) grid.get(grid.size() - 1);
                   
                    grid.remove(grid.size() - 1);
                }
                else if(map[i][j] == 0){
                    grid.add(new GameObject(tempJ, tempI, map[i][j]));
                    grid.get(grid.size() - 1).setSolid(false);
                }
                else if(map[i][j] == 3 || map[i][j] == 4){
                    if(map[i][j] == 3){
                        grid.add(new Scorpion(tempJ, tempI, map[i][j], 0));
                    }
                    else{
                        grid.add(new Scorpion(tempJ, tempI, map[i][j], 1));
                    }
                }
                else if(map[i][j] == 30 || map[i][j] == 31){
                    if(map[i][j] == 30){
                        grid.add(new Beetle(tempJ, tempI, map[i][j], 0));
                    }
                    else{
                        grid.add(new Beetle(tempJ, tempI, map[i][j], 1));
                    }
                }
                else if(map[i][j] == 13 || map[i][j] == 14 || map[i][j] == 15 || map[i][j] == 16){
                    if(map[i][j] == 13){
                        grid.add(new Mummy(tempJ, tempI, map[i][j]));
                    }
                    else if(map[i][j] == 14){
                        grid.add(new Mummy(tempJ, tempI, map[i][j]));
                    }
                    else if(map[i][j] == 15){
                        grid.add(new Mummy(tempJ, tempI, map[i][j]));
                    }
                    else{
                        grid.add(new Mummy(tempJ, tempI, map[i][j]));
                    }
                }
                else{
                    grid.add(new GameObject(tempJ, tempI, map[i][j]));
                }
            }
        }
        this.addKeyListener(pc);
        
    }

    public void update() {
        
        for (int i = 0; i < grid.size(); i++) {
                grid.get(i).update(this);
        }
        
        p.update(this);
    }
    public void paint() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setFont(new Font("TimesRoman", Font.PLAIN, 22)); 
        g.setColor(Color.WHITE);
        drawBackground(g);
        drawMap(g);
        
        p.paint(g);
        for (int i = 0; i < grid.size(); i++) {
                grid.get(i).paint(g, this);
        }
        if(!p.mapLight() && p.getLevel() == 2){
            drawDark(g);
        }
        if(p.drawWinBool == true){
            drawWin(g);
        }
        if(p.drawLoseBool == true){
            drawLose(g);
        }
        drawStats(g);
        g.dispose();
        bs.show();

        //System.out.println(this.toString());
    }
    
    public void drawBackground(Graphics g){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                g.drawImage(bg, i*640-p.getX(), j*448-p.getY(), null);
            }
        }
       
        
    }
    
    public void drawMap(Graphics g){
        int tempX, tempY;
        for(int i = 0; i < grid.size(); i++){
            if(grid.get(i).getObjectType() != 3 && grid.get(i).getObjectType() != 4 
                    && grid.get(i).getObjectType() != 30 && grid.get(i).getObjectType() != 31 && grid.get(i).getObjectType() != 13
                    && grid.get(i).getObjectType() != 14 && grid.get(i).getObjectType() != 15 && grid.get(i).getObjectType() != 16){        
                g.drawImage(bimg[grid.get(i).getObjectType()], grid.get(i).getX() - p.getX(), grid.get(i).getY() - p.getY(), null);
            }
        }
    }
    public void drawBox(Graphics g){
        
    }
    public void drawDark(Graphics g){
         g.drawImage(light, 0, 0, null);
    }
    public void drawStats(Graphics g){
        g.drawString("Score: " + p.getScore()/100 + " Lives: " + p.getLives(), 20, 20);
    }
    public void drawWin(Graphics g){
        grid.clear();
        p.setSpawn(this);
        p.setLevel(1);
        g.drawString("You Win!!!", 260, 200);
        g.drawString("Your Score: "  + p.getScore()/100, 240, 265);
    }
    public void drawLose(Graphics g){
        grid.clear();
        p.setSpawn(this);
        p.setLevel(1);
        g.drawString("You Lose!!!", 260, 200);
        g.drawString("Your Score: "  + p.getScore()/100, 240, 265);
    }
        public void setMap2(){
        int tempX, tempY;
        grid.removeAll(grid);
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                int tempI = i * 32 + cameraY;
                int tempJ = j * 32 + cameraX;
                
                if(map2[i][j] == 0){
                    grid.add(new GameObject(tempJ, tempI, map2[i][j]));
                    grid.get(grid.size() - 1).setSolid(false);
                }
                else if(map2[i][j] == 3 || map2[i][j] == 4){
                    if(map2[i][j] == 3){
                        grid.add(new Scorpion(tempJ, tempI, map2[i][j], 0));
                    }
                    else{
                        grid.add(new Scorpion(tempJ, tempI, map2[i][j], 1));
                    }
                }
                else if(map2[i][j] == 30 || map2[i][j] == 31){
                    if(map2[i][j] == 30){
                        grid.add(new Beetle(tempJ, tempI, map2[i][j], 0));
                    }
                    else{
                        grid.add(new Beetle(tempJ, tempI, map2[i][j], 1));
                    }
                }
                else if(map2[i][j] == 13 || map2[i][j] == 14 || map2[i][j] == 15 || map2[i][j] == 16){
                    if(map2[i][j] == 13){
                        grid.add(new Mummy(tempJ, tempI, map2[i][j]));
                    }
                    else if(map2[i][j] == 14){
                        grid.add(new Mummy(tempJ, tempI, map2[i][j]));
                    }
                    else if(map2[i][j] == 15){
                        grid.add(new Mummy(tempJ, tempI, map2[i][j]));
                    }
                    else{
                        grid.add(new Mummy(tempJ, tempI, map2[i][j]));
                    }
                }
                else{
                    grid.add(new GameObject(tempJ, tempI, map2[i][j]));
                }
            }
        }
    }
    
    
    
    public Player getPlayer(){
        return this.p;
    } 
    
    public BufferedImage getBimg(int index){
        return bimg[index];
    }
}
