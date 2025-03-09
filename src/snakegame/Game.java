
package snakegame;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Font;

public class Game extends JPanel{
    
    private long timeStart = System.currentTimeMillis();
    private int secondsPassed;
    private Snake snake;
    private Apple apple;
    private Obstacle obstacles;
    private Menu menu;
    private Images i;
    private final int FPS = 10;
    private String dir;
    private Timer newFrameTimer;
    private String timeStr = "00:00";
    
    public enum STATE{
        GAME, PAUSED, MENU, OVER;
    }
    public STATE State = STATE.GAME;
    
    /**
     * Game osztály konstruktora, inicializálódnak az irányváltoztatáshoz és a megállításhoz szükséges actionok 
     */
    public Game(){
                
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            /**
             * A függvény a bal nyíl gomb lenyomásának eseményét figyeli, 
             * ha lehetséges (a kígyó nem jobbra megy), akkor megváltoztatja a kígyó mozgásának
             * irányát balra
             */
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (dir != "right"){
                    i.snakeHeadImage = i.left;
                    dir = "left";
                }
            }
        });
        
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            /**
             * A függvény a jobb nyíl gomb lenyomásának eseményét figyeli, 
             * ha lehetséges (a kígyó nem balra megy), akkor megváltoztatja a kígyó mozgásának
             * irányát jobbra
             */
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (dir != "left"){
                    i.snakeHeadImage = i.right;
                    dir = "right";
                }
            }
        });
        
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            /**
             * A függvény a fel nyíl gomb lenyomásának eseményét figyeli, 
             * ha lehetséges (a kígyó nem lefele megy), akkor megváltoztatja a kígyó mozgásának
             * irányát felfele
             */
            @Override
            public void actionPerformed(ActionEvent ae) { 
                if (dir != "down"){
                    i.snakeHeadImage = i.up;
                    dir = "up";
                }
           }
        });
        
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            /**
             * A függvény a le nyíl gomb lenyomásának eseményét figyeli, 
             * ha lehetséges (a kígyó nem felfele megy), akkor megváltoztatja a kígyó mozgásának
             * irányát lefele
             */
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (dir != "up"){
                    i.snakeHeadImage = i.down;
                    dir = "down";
                }
            }
        });
        
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            /**
             * A függvény az ESC gomb leenyomásának eseményét figyeli, megnyílik/bezárul a pause menu a gomb lenyomásakor
             */
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (State == STATE.GAME){
                    State = STATE.PAUSED;
                }
                else if (State == STATE.PAUSED){
                    State = STATE.GAME;    
                }
            }
        });
        
        init();
        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();
    } 
    
    /**
     * A függvény elvégzi egy új játékmenet inicializálását, példányosítja az objektumokat,
     * bizonyos paramétereket visszaállít kiindulási állapotba, illetve inicializálja a kígyó kezdeti állapotát,
     * valamint lehelyezi az akadályokat és egy almát (ezeket véletlenszerűen)
     */
    public void init() {
        snake = new Snake();
        apple = new Apple();
        obstacles = new Obstacle();
        menu = new Menu(this);
        i = new Images();
        State = STATE.GAME;
        snake.snakeLength = 2;
        secondsPassed = 0;
        timeStr = "00:00";
        
        randomDirection();
        obstacles.putObstacles();
        apple.putApple(snake, obstacles);
        snake.snakeInit();
    }
    
    /**
     * Egy véletéenszerűen generált szám alapján eldönti, hogy kezdetben merre induljon el a kígyó a 4 irány közül
     */
    public void randomDirection(){
        int tmp = ThreadLocalRandom.current().nextInt(1, 4 + 1);
        switch (tmp){
            case 1:
                dir = "left";
                i.snakeHeadImage = i.left;
                break;
            case 2:
                dir = "right";
                i.snakeHeadImage = i.right;
                break;
            case 3:
                dir = "up";
                i.snakeHeadImage = i.up;
            case 4:
                dir = "down";
                i.snakeHeadImage = i.down;
            default:
                break;   
        }
    }
    
    /**
     * Megfelelő formátummá alakítja az eltelt másodperceket, a kirajzoláshoz (MM:SS)
     * @param secs az eltelt másodpercek a játékmenetben
     * @return visszatér a másodpercekkel MM:SS formátumban
     */
    public String secondsToStr(int secs){
        String str = "";
        
        if (secs % 60 < 10){
            str += "0" + String.valueOf(secs / 60) + ":0" +String.valueOf(secs - (secs / 60) * 60);
        }
        else{
            str += "0" + String.valueOf(secs / 60) + ":" +String.valueOf(secs - (secs / 60) * 60);
        }
        
        return str;
    }
    
    /**
     * Elvégzi a kirajzolási feladatokat a játékmenet közben
     * @param grphcs 
     */
    @Override
    protected void paintComponent(Graphics grphcs) {
        if (State != STATE.OVER){
            super.paintComponent(grphcs);
            grphcs.drawImage(i.background, 0, 0, 600, 600, null);
            
            for (int ii = 0; ii < obstacles.obstacle_x.size(); ii++){
                grphcs.drawImage(i.obstacleImage, obstacles.obstacle_x.get(ii), obstacles.obstacle_y.get(ii), 30, 30, null);
            }
            
            grphcs.drawImage(i.appleImage, apple.apple_x, apple.apple_y, 30, 30, null);     
            for (int ii = 1; ii < snake.snakeLength; ii++){
                grphcs.drawImage(i.snakeBodyImage, snake.snake_x.get(ii), snake.snake_y.get(ii), null);
            }
            grphcs.drawImage(i.snakeHeadImage, snake.snake_x.get(0), snake.snake_y.get(0), null);
            
            Font font = new Font("Arial", Font.BOLD, 15);
            grphcs.setFont(font);
            grphcs.drawString(timeStr, 615 / 2, 20);
            grphcs.drawString("Score: " + (String.valueOf(snake.snakeLength - 2)), 10, 20);
        }
        
        Toolkit.getDefaultToolkit().sync();   
    }
         
    /**
     * Implementálja az ActionListenert, figyeli az eseményeket és ennek megfelelően hív
     * megfelelő függvényeket (move, appleCheck, collisionCheck),
     * ha áll a játék (paused), vagy vége van (over), akkor meghívja a menüket megjelenítő függvéneket
     * elvégzi a rajzolást minden képfrissítés után, a repaint függvény hívásával
     * meghívja az init függvényt, ha új játékmenetet kell inicializálni
     */
    class NewFrameListener implements ActionListener {
            
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (State == STATE.GAME || State == STATE.PAUSED){
                if (State == STATE.GAME){
                    
                    if (System.currentTimeMillis() - 970 >= timeStart){
                        timeStart = System.currentTimeMillis();
                        secondsPassed++;
                        timeStr = secondsToStr(secondsPassed);
                    }
                    
                    snake.appleCheck(apple, obstacles);
                    snake.move(dir);
                    if (snake.collisionCheck(obstacles)){
                        State = STATE.OVER;
                    }
                    
                    if (State == STATE.OVER){
                        //newFrameTimer.stop();
                        if (menu.drawMainMenu(snake.snakeLength-2)){
                            init();
                        }
                    }
                }
                else{
                    if (menu.drawPauseMenu()){init();}
                    else{State = STATE.GAME;}
                }
                repaint();
            }
            
            
        }
    }  
}
