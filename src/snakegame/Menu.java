
package snakegame;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class Menu {
    
    public ImageIcon headIcon;
    public JPanel parent;
    private Database db;
    private String name;
    private int score;
    
    /**
     * A Menu osztály konstruktora, amelyben értéket kap a headIcon, ami megjelenik a felugró ablakon,
     * illetve paraméterként átadódik az a JPanel, amin megjelenik a felugró menü ablak.
     * @param parent a JPanel, amin megjelenik a menü ablak
     */
    public Menu(JPanel parent){
        headIcon = new ImageIcon("data/images/headDown.png");
        this.parent = parent;
        
        try{
            this.db = new Database(10);
        }
        
        catch(SQLException e){
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * A név megadásához és elmentéséhez szükséges ablakot dobja fel
     * @return ha a név elmentése sikeres, akkor true értékkel tér vissza a függvény, hogy új játékmenet indulhasson
     * ha visszalép a játékos az előző ablakba (cancel), akkor false értékkel tér vissza
     */
    public boolean drawNameMenu(){
            
            String userInput = JOptionPane.showInputDialog(parent, 
                        "Enter your name:", 
                        "SAVE SCORE",
                        JOptionPane.INFORMATION_MESSAGE);
            if (userInput != null){
                name = userInput;
                
                try{
                    db.putHighScore(name, score);
                }
                catch(SQLException e){
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, e);
                }
                
                return true;
            }
            if (drawMainMenu(score)){return true;}
            return false;
    }
    
    /**
     * A játék végetérésekor felugró főmenü ablakot dobja fel
     * @param score ez az Integer a játékmenetben elért pontszám
     * @return ha új játékot indít a járékos, akkor true értékkel tér vissza, más esetekben false értékkel
     */
    public boolean drawMainMenu(int score){
            this.score = score;
            String[] responses = {"New Game", "Save Score", "Leaderboard", "Exit"};
            int num = JOptionPane.showOptionDialog(parent,
                                        "Your Score: " + score + "\n"
                                        + "Press ESC or 'Exit' to exit the game.",
                                        "GAME OVER",
                                        JOptionPane.YES_NO_CANCEL_OPTION,
                                        JOptionPane.INFORMATION_MESSAGE,
                                        headIcon,
                                        responses,
                                        0);
            
            if (num == 0){return true;}
            if (num == 1){
                if (drawNameMenu()){return true;}                
            }
            if (num == 2){
                if (drawLeaderboardMenu()){return true;}
            }
            if (num == 3 || num == JOptionPane.CLOSED_OPTION){System.exit(0);}
            return false;
    }
    
    /**
     * Ez a függvény jeleníti meg az elmentett legjobb eredményeket, ha ezt választja a játékos a főmenüből
     * @return ha új játékot indít a járékos, akkor true értékkel tér vissza, más esetekben false értékkel
     */
    public boolean drawLeaderboardMenu(){
        String[] responses = {"Back"};
        int num = -2;
        
        num = JOptionPane.showOptionDialog(parent,
                                        db.scoresToStr(),
                                        "LEADERBOARD",
                                        JOptionPane.OK_OPTION,
                                        JOptionPane.INFORMATION_MESSAGE,
                                        headIcon,
                                        responses,
                                        0);
        
        
        if (num >= -1){
            if (drawMainMenu(score)){return true;}
        }
        
        return false;
    }
    
    /**
     * Ha meghívásra kerül ez a függvény, akkor kirajzolja a pause menüt
     * @return true értékkel tér vissza, ha a játékos új játékot szeretne kezdeni
     * false értékkel tér vissza, ha a játékos bezárja az ablakot
     */
    public boolean drawPauseMenu(){
            String[] responses = {"New Game", "Exit"};
            ImageIcon icon = new ImageIcon("data/images/apple.png");
            int num = JOptionPane.showOptionDialog(parent,
                                        "The game is paused. \n "
                                        + "Press ESC to continue. \n"
                                        + "Press 'Exit' to exit game. \n",
                                        "PAUSED",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.INFORMATION_MESSAGE,
                                        icon,
                                        responses,
                                        0);
            if (num == 0){return true;}
            if (num == 1){System.exit(0);}
            if (num == JOptionPane.CLOSED_OPTION){
                return false;
            }
            return false;
    }
    
    
}
