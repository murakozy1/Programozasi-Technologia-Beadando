
package snakegame;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Apple {
    
    public int apple_x;
    public int apple_y;
    
    public Apple(){}
    
    /**
     * Lehelyez egy almát véletlenszerű helyre, ha nem tudja lehelyezni az
     * eleinte generált helyre, akkor új koordinátákat generál, amíg nem jár sikerrel
     * @param s a játéktéren lévő Snake objektum
     * @param o a játéktéren lévő akadályok információit tartalmazó objektum
     */
    public void putApple(Snake s, Obstacle o){
        apple_x =  30 * (ThreadLocalRandom.current().nextInt(1, 18 + 1));
        apple_y =  30 * (ThreadLocalRandom.current().nextInt(1, 18 + 1));
        
        while (!checkApple(s, o)){
             apple_x =  30 * (ThreadLocalRandom.current().nextInt(1, 18 + 1));
             apple_y =  30 * (ThreadLocalRandom.current().nextInt(1, 18 + 1));
        }
    }
    
    /**
     * Megnézi, hogy a putApple függvényben kigenerált koordinátákra lehelyezhető-e az alma
     * @param s a játéktéren lévő Snake objektum
     * @param o a játéktéren lévő akadályok információit tartalmazó objektum
     * @return annak megfelelő logikai értékkel tér vissza, hogy lehelyezhető-e az alma
     */
    public boolean checkApple(Snake s, Obstacle o){
        for (int i = 0; i < s.snake_x.size(); i++){
            if ((apple_x == s.snake_x.get(i)) && (apple_y == s.snake_y.get(i))){
                return false;
            }
        }
        
        for (int i = 0; i < o.obstacle_x.size(); i++){
            if ((Objects.equals(apple_x, o.obstacle_x.get(i))) && (Objects.equals(apple_y, o.obstacle_y.get(i)))){
                return false;
            }
        }
        return true;
    }
}
