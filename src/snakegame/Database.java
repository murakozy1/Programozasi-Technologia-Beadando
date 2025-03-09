
package snakegame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Database {

    int maxScores;
    PreparedStatement insertStatement;
    PreparedStatement deleteStatement;
    Connection connection;

    /**
     * A Database osztály konstruktora, étrehozza a kapcsolatot az adatbázissal
     * @param maxScores ennyi highscoret tárol el maximum az adatbázis
     * @throws SQLException 
     */
    public Database(int maxScores) throws SQLException {
        this.maxScores = maxScores;
        String dbURL = "jdbc:derby://localhost:1527/highscoredb;";
        connection = DriverManager.getConnection(dbURL);
        String insertQuery = "INSERT INTO HIGHSCORES (NAME, SCORE) VALUES (?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);
        String deleteQuery = "DELETE FROM HIGHSCORES WHERE SCORE=?";
        deleteStatement = connection.prepareStatement(deleteQuery);
    }
    
    /**
     * Kiszedi a highscoreokat az adatbázisból egy ArrayListbe
     * @return visszatér ezzel az ArrayListtel
     * @throws SQLException 
     */
    public ArrayList<HighScore> getHighScores() throws SQLException {
        String query = "SELECT * FROM HIGHSCORES";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            highScores.add(new HighScore(name, score));
        }
        sortHighScores(highScores);
        return highScores;
    }
    
    /**
     * Ha még nincs tele az adatbázis, vagy nagyobb az adott highscore, mint a legkisebb adatbázisban lévő, akkor beleteszi
     * @param name a highscore név paramétere
     * @param score a highscore pont paramétere
     * @throws SQLException 
     */
    public void putHighScore(String name, int score) throws SQLException {
        ArrayList<HighScore> highScores = getHighScores();
        if (highScores.size() < maxScores){
            insertScore(name, score);
        } else{
            int leastScore = highScores.get(highScores.size() - 1).getScore();
            if (leastScore < score) {
                deleteScores(leastScore);
                insertScore(name, score);
            }
        }
    }

    /**
     * Rendezi a highscoreokat, csökkenő sorrendben
     * @param highScores a rendezendő highscoreok ArrayListje
     */
    private void sortHighScores(ArrayList<HighScore> highScores) {
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore t, HighScore t1) {
                return t1.getScore() - t.getScore();
            }
        });
    }
    
    /**
     * Beszúr egy highscoret az adatbázisba
     * @param name a highscore név paramétere
     * @param score a highscore pont paramétere
     * @throws SQLException 
     */
    private void insertScore(String name, int score) throws SQLException {
        //Timestamp ts = new Timestamp(System.currentTimeMillis());
        insertStatement.setString(1, name);
        insertStatement.setInt(2, score);
        //insertStatement.setTimestamp(3, ts);
        insertStatement.executeUpdate();
    }

    /**
     * Kitörli az összes highscoret az adatbázisból, aminek a pont paramétere egyezik az 
     * átadott Integerrel
     * @param score az a pont érték, ami alapján törölni kell
     */
    private void deleteScores(int score) throws SQLException {
        deleteStatement.setInt(1, score);
        deleteStatement.executeUpdate();
    }
    
    /**
     * Átalakítja a highscoreokat tartalmazó ArrayListet olyan formába, ahogy azok megjelennek
     * a toplistán
     * @return az átalakított Stringeket tartalmazó tömbbel tér vissza
     */
    public String[] scoresToStr(){
        int len = 0;
        ArrayList<HighScore> hs = new ArrayList<HighScore>();
        try{
            len = getHighScores().size();
            hs = getHighScores();      
        }
        catch(SQLException e){
            
        }
        String[] arr = new String[len];
        
        for (int i = 0; i < len; i++){
            arr[i] = hs.get(i).getName() + ": " + String.valueOf(hs.get(i).getScore());
            
        }
        
        return arr;
    }
}

