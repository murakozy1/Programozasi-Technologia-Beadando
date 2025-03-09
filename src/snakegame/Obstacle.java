
package snakegame;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Obstacle {
    
    private int obstacleSum = 5;
    public ArrayList<Integer> obstacle_x = new ArrayList<Integer>();
    public ArrayList<Integer> obstacle_y = new ArrayList<Integer>();
    
    public Obstacle(){}
    
    
    /**
     * Megvizsgálja, hogy egy adott helyen nincsen-e már akadály
     * @param x generált x koordináta
     * @param y generált y koordináta
     * @return annak megfelelő logikai értékkel tér vissza, hogy lehelyezhető-e az akadály
     */
    public boolean checkObstacle(int x, int y){
        for (int i = 0; i < obstacle_x.size(); i++){
            if ((Objects.equals(x, obstacle_x.get(i))) && (Objects.equals(y, obstacle_y.get(i)))){
                return false;
            }
        }
        
        return true;
    }
    
    
    /**
     * Lehelyezi az akadályokat inicializáláskor
     * figyel arra, hogy ne helyezze le abban a sorban és oszlopban, amelyekben a kígyó elindulhat,
     * így elkerülve az azonnali ütközést
     */
    public void putObstacles(){
        int tmp_x;
        int tmp_y;
        for (int i = 0; i < obstacleSum; i++){
            tmp_x = 30 * (ThreadLocalRandom.current().nextInt(0, 19 + 1));
            tmp_y = 30 * (ThreadLocalRandom.current().nextInt(0, 19 + 1));
            if (tmp_x == 270){
                tmp_x += 60;
            }
            if (tmp_y == 270){
                tmp_y += 30;
            }
            while (!checkObstacle(tmp_x, tmp_y)){
                tmp_x = 30 * (ThreadLocalRandom.current().nextInt(0, 19 + 1));
                tmp_y = 30 * (ThreadLocalRandom.current().nextInt(0, 19 + 1));
                
                if (tmp_x == 270){
                    tmp_x += 60;
                }
                if (tmp_y == 270){
                    tmp_y += 30;
                }
            }
            obstacle_x.add(tmp_x);
            obstacle_y.add(tmp_y);
        }
    }
    
    
}
