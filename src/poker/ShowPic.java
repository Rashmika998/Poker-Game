/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Asus
 */
public class ShowPic {
    //display clip art
    public void showArt(String name) throws IOException{
        Scanner sc = new Scanner(getClass().getResourceAsStream(name+".txt"));


        while( sc.hasNextLine()){
            System.out.println(sc.nextLine());
        }
    }
    
     public void tapEnter()
    { 
        System.out.println("Press Enter key to continue...");
        try{
            System.in.read();
        }  
        catch(Exception e)
        {}  
    }

}


