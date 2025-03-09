
package snakegame;

import java.util.ArrayList;
import java.util.Objects;

public class Snake{
    
    public int SNAKE_MOVEMENT = 30;
    public int SNAKE_WIDTH = 30;
    public int SNAKE_HEIGHT = 30;
    public int snakeLength = 2;
    
    public ArrayList<Integer> snake_x = new ArrayList<Integer>();
    public ArrayList<Integer> snake_y = new ArrayList<Integer>();
    
    public Snake(){}
    
    /**
     * Hozzáad egy elemet a kígyóhoz
     */
    public void addPart(){      
        snake_x.add(1, snake_x.get(0));
        snake_y.add(1, snake_y.get(0));
    }
    
    /**
     * Leellenőrzi, hogy a kígyó elfogyasztott-e egy almát, ha igen, akkor meghívja az
     * Apple.putApple és az addPart függvényt
     * @param a Alma objektum
     * @param o akadályokat tartalmazó objektum
     */
    public void appleCheck(Apple a, Obstacle o){
        if (a.apple_x == snake_x.get(0) && a.apple_y == snake_y.get(0)){
            snakeLength++;
            addPart();
            a.putApple(this, o);
        }
    }
    
    /**
     * Elvégzi a kígyó mozgatását a dir-nek megfelelően, a fej elemet mozgatja,
     * a többi elemet pedig a fej elem elmozdításához igazodva tolja el
     * @param dir irányt meghatározó String
     */
    public void move(String dir){
            int tmp_x = snake_x.get(0);
            int tmp_y = snake_y.get(0);
            
            for (int i = snakeLength-1; i > 0; i--){
                snake_x.set(i, snake_x.get(i-1));
                snake_y.set(i, snake_y.get(i-1));
            }

            switch(dir){
                case "left":
                    snake_x.set(0, tmp_x - SNAKE_MOVEMENT);
                    break;
                case "right":                    
                    snake_x.set(0, tmp_x + SNAKE_MOVEMENT);
                    break;
                case "up":
                    snake_y.set(0, tmp_y - SNAKE_MOVEMENT);
                    break;
                case "down":
                    snake_y.set(0, tmp_y + SNAKE_MOVEMENT);
                    break;
                default:
                    break;
            }
    }
    
    /**
     * Megvizsgálja hogy a kígyó nem ütközött-e akadállyal, fallal, vagy saját magával
     * @param o az akadályokat tartalmazó objektum
     * @return annak megfelelő logikai értékkel tér vissza, hogy a kígyó ütközött-e
     */
    public boolean collisionCheck(Obstacle o){
        if ((snake_x.get(0) < 0) || (snake_x.get(0) > 570) || (snake_y.get(0) < 0) || (snake_y.get(0) > 570)){
            return true;
        }
        
        for (int i = 1; i < snake_x.size(); i++){
            if ((Objects.equals(snake_x.get(0), snake_x.get(i))) && (Objects.equals(snake_y.get(0), snake_y.get(i)))){
                return true;
            }
        }
        
        for (int i = 0; i < o.obstacle_x.size(); i++){
            if ((Objects.equals(snake_x.get(0), o.obstacle_x.get(i))) && (Objects.equals(snake_y.get(0), o.obstacle_y.get(i)))){
                return true;
            }
        }

        return false;
    }
    
    /**
     * Inicializálja a kezdeti, 2 elemből álló kígyót
     */
    public void snakeInit(){
        for (int i = 0; i < snakeLength; i++){
            snake_x.add(270 + (i * 30));
            snake_y.add(270);
        }
    }
    
}

