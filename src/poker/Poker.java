package poker;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Poker {
    
    
    public static void main(String[] args) {
        /*show clip art*/
        ShowPic pic = new ShowPic();
        try {
            pic.showArt("art");
        } catch (IOException ex) {
            Logger.getLogger(Poker.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("\n\n\n");
        pic.tapEnter();
        System.out.println("\n\n\n");
        try {
            pic.showArt("Help");
        } catch (IOException ex) {
            Logger.getLogger(Poker.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("\n\n\n");
        pic.tapEnter();
        PlayGame game = new PlayGame();
        game.startGame();
        
    }
    
}
