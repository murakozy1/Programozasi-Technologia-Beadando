
package snakegame;

import java.awt.Dimension;
import javax.swing.JFrame;

public class SnakeGUI {
    private JFrame frame;
    private Game gameArea;
    
    /**
     * A SnakeGUI konstruktora, példányosításra kerül a Game osztály, ami hozzá lesz adva egy JFrame-hez
     */
    public SnakeGUI(){
        frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        gameArea = new Game();
        
        frame.getContentPane().add(gameArea);
        
        frame.setPreferredSize(new Dimension(615, 630));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        
    }
}
