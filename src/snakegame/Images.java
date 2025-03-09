
package snakegame;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Images {

    public Image left;
    public Image right;
    public Image up;
    public Image down;
    public Image background;
    public Image snakeBodyImage;
    public Image appleImage;
    public Image obstacleImage;
    public Image snakeHeadImage;

    /**
     * Images osztály konstruktora, betölti  képeket a data/images könyvtárból
     */
    public Images(){
        background = new ImageIcon("data/images/background.png").getImage();
        
        left = new ImageIcon("data/images/headLeft.png").getImage();
        right = new ImageIcon("data/images/headRight.png").getImage();
        up = new ImageIcon("data/images/headUp.png").getImage();
        down = new ImageIcon("data/images/headDown.png").getImage();
        
        appleImage = new ImageIcon("data/images/apple.png").getImage();
        snakeBodyImage = new ImageIcon("data/images/snakeBody.png").getImage();
        obstacleImage = new ImageIcon("data/images/obstacle.png").getImage();
    };
}
