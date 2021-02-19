package poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


public class PlayGame {
    
    boolean direction; //direction of the game
    private CardDeck playingDeck; //playing deck
    private ArrayList<ArrayList<CardProperties>> hands; //all of the players hands
    ArrayList<String>playerNames = new ArrayList();
    private int dealer;
    private Stack<CardProperties> stockpile = new Stack();
    private int[] finalScores = new int [3]; //array to store the scores
    
    
    public PlayGame(){
        dealer = 0;
        
        //add player names to the array list
        playerNames.add("User");
        playerNames.add("Player 1");
        playerNames.add("Player 2");
        
        //create a deck
        playingDeck= new CardDeck(); 
        playingDeck.createDeck();
         
        
    }
    
    //function to start the game
    public void startGame(){
        boolean fold;
        int ans;
        int[] scores = new int [3]; //array to store the scores
        
        //assign scores as 0 initially
        for(int i=0;i<3;i++){
            finalScores[i] = 0;
        }
        
        
        for(int round = 0; round < 10; round++){
            
            //assign scores as 0 initially
            for(int i=0;i<3;i++){
                scores[i] = 0;
            }
            
            if(round != 9)
                System.out.println("------------------------ROUND 0"+(round+1)+"------------------------");
            
            else
                System.out.println("------------------------ROUND "+(round+1)+"------------------------");
            
            playingDeck.shuffleCards();
        
            hands= new ArrayList<ArrayList<CardProperties>>();
            for(int i=0;i<playerNames.size();i++)
            {
                //create the hand by distributing 2 cards for each player
                ArrayList<CardProperties> hand= new ArrayList<CardProperties>(Arrays.asList(playingDeck.getCards(2)));
               hands.add(hand); //adding the 2 cards to hands variable(all of every player hand)
            }
            
            fold = false;
            CardProperties[] flop = playingDeck.getCards(3); //flop round, 3 cards taken from the deck 
            CardProperties[] turn = playingDeck.getCards(1); //turn round, card taken from the deck
            CardProperties[] river = playingDeck.getCards(1); //river round, card taken from the deck
            Scanner sc = new Scanner(System.in);
        
        
            System.out.println("User's Cards");
            getPlayerCard(playerNames.get(0)); //display user cards
            System.out.println("\n**************FIRST ROUND - FLOP**************");
        
        
            //display the taken 3 cards
            for(int i=0;i<3;i++){
                flop[i].displaySuit();
                flop[i].displayValue();
            }     
        
            while(true){
                System.out.print("\nAre you Proceed with the cards after the FLOP? (Yes = 1, No = 0): ");
                ans = sc.nextInt();
                if(ans == 1 || ans == 0){
                    break;
                }
            
                else{
                    System.out.println("Number you entered was not valid! Try again!");
                }
            }
        
            //if user is not giving up cards
            if(ans == 1){
                System.out.println("\n**************SECOND ROUND - TURN**************");
            
                //display the taken cards
                for(int i=0;i<1;i++){
                    turn[i].displaySuit();
                    turn[i].displayValue();
                }
            
                while(true){
                    System.out.print("\nAre you Proceed with the cards after the TURN? (Yes = 1, No = 0): ");
                    ans = sc.nextInt();
                    if(ans == 1 || ans == 0){
                        break;
                    }
            
                    else{
                        System.out.println("Number you entered was not valid! Try again!");
                    }
                }
            
                //if user still proceed with the games
                if(ans == 1){
                    System.out.println("\n**************THIRD ROUND - RIVER**************");
                
                    //display the taken cards
                    for(int i=0;i<1;i++){
                        river[i].displaySuit();
                        river[i].displayValue();
                    } 
                
                    while(true){
                        System.out.print("\nAre you Proceed with the cards after the RIVER? (Yes = 1, No = 0): ");
                        ans = sc.nextInt();
                        if(ans == 1 || ans == 0){
                            break;
                        }
            
                        else{
                            System.out.println("Number you entered was not valid! Try again!");
                        }
                    }
                
                    //if user give up the RIVER round make fold true
                    if (ans == 0){
                        scores[0] = 0;
                        fold = true;
                    }
                }
            
                //if user give up the round make fold true and proceed TURN round to other two players
                else if(ans == 0){
                    scores[0] = 0;
                    fold = true;
                    System.out.println("\n**************THIRD ROUND - RIVER**************");
                
                    for(int i=0;i<1;i++){
                        river[i].displaySuit();
                        river[i].displayValue();
                    }
                }
            
            }
        
            //if user give up cards in FLOP round
            else if(ans == 0){
                scores[0] = 0;
                fold = true;
                System.out.println("\n**************SECOND ROUND - TURN**************");
            
                for(int i=0;i<1;i++){
                    turn[i].displaySuit();
                    turn[i].displayValue();
                }
            
                System.out.println("\n**************THIRD ROUND - RIVER**************");
                
                for(int i=0;i<1;i++){
                    river[i].displaySuit();
                    river[i].displayValue();
                } 
            }
        
            //display the other two players cards
            for(int i=0;i<playerNames.size();i++){
                if(playerNames.get(i) != "User"){
                    System.out.println("\n"+playerNames.get(i)+"'s Cards");
                    getPlayerCard(playerNames.get(i));
                }
            
            }
        
            //create an array as a combination of all 5 dealer cards
            CardProperties[] dealerCards = new CardProperties[5];
            int i=0;
            for(i=0;i<3;i++){
                dealerCards[i]=flop[i];
            }
        
            dealerCards[i++]=turn[0];
            dealerCards[i]=river[0];
        
            //showdown 
            showdown(playerNames,dealerCards,scores,fold);
        
            //get player rankings
            displayRankings(scores, playerNames);
            
            for(int a=0;a<3;a++){
                finalScores[a] += scores[a];
            }
            
            /*add the distributed and dealer cards tp the deck again for the next round*/
            for(int a=0;a<3;a++){
                for(int b=0;b<2;b++){
                    playingDeck.addCard(getCard(playerNames.get(a), b));
                }
            }
            
            for(int a=0;a<5;a++){
                playingDeck.addCard(dealerCards[a]);
            }
            
            if(round != 9)
                System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~END OF ROUND 0"+(round+1)+"~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
            
            else
                System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~END OF ROUND "+(round+1)+"~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
            
        }
        
        int max = -1;
        int winnerIndex=0;
        
        System.out.println("\n\t\t\tFINAL SCORES\n");
        
        //display the scores at the end of all rounds
        for(int i =0; i < 3;i++){
            System.out.print("\n\n"+playerNames.get(i)+" : "+finalScores[i]);
            if(max < finalScores[i]){
                max = finalScores[i];
                winnerIndex = i;
            }  
        }
        
        /*find the winner(s)*/
        if(max == finalScores[0] && max == finalScores[1] && max == finalScores[2])
            System.out.println("\n\n\tWINNERS : "+playerNames.get(0)+" , "+playerNames.get(1)+" , "+playerNames.get(2));
        
        else if(max == finalScores[0] && max == finalScores[1])
            System.out.println("\n\n\tWINNERS : "+playerNames.get(0)+" , "+playerNames.get(1));
        
        else if(max == finalScores[0] && max == finalScores[1])
            System.out.println("\n\n\tWINNERS : "+playerNames.get(0)+" , "+playerNames.get(1));
        
        else if(max == finalScores[1] && max == finalScores[2])
            System.out.println("\n\n\tWINNERS : "+playerNames.get(1)+" , "+playerNames.get(2));
        
        else if(max == finalScores[0] && max == finalScores[2])
            System.out.println("\n\n\tWINNERS : "+playerNames.get(0)+" , "+playerNames.get(2));
        
        else
            System.out.println("\n\n\tWINNER : "+playerNames.get(winnerIndex));
        
        
        
    }
    
    public void displayRankings(int[] scores, ArrayList<String> playNames){
        for(int i =0 ;i < 3;i++){
            System.out.print(playerNames.get(i)+" : ");
            switch(scores[i]){
                case 0:{System.out.println("0");break;}
                case 1:{System.out.println("HIGH CARD");break;}
                case 2:{System.out.println("ONE PAIR");break;}
                case 4:{System.out.println("TWO PAIR");break;}
                case 6:{System.out.println("THREE OF KIND");break;}
                case 8:{System.out.println("STRAIGHT");break;}
                case 10:{System.out.println("FLUSH");break;}
                case 12:{System.out.println("FULL HOUSE");break;}
                case 15:{System.out.println("FOUR OF KIND");break;}
                case 20:{System.out.println("STRAIGHT FLUSH");break;}
                case 30:{System.out.println("ROYAL FLUSH");break;}
            }
        }
    }
    
    //function to get user hand
    public ArrayList<CardProperties> playerHand(String playid){    
        int n=playerNames.indexOf(playid);
        return hands.get(n);
    }
    
    //function to get user cards
    public CardProperties getCard(String playid,int index){
        ArrayList<CardProperties> hand = playerHand(playid);
        return hand.get(index);
    }
    
    //function diplay user cards
    public void getPlayerCard (String playid){
        ArrayList<CardProperties> hand = playerHand(playid);
        for(int i=0;i<hand.size();i++){
            hand.get(i).displaySuit();
            hand.get(i).displayValue();
        }
        System.out.println();
    }
    
    //function to display the showdown
    public void showdown(ArrayList<String> playNames,CardProperties[] dealerCards,int[] scores,boolean fold){
        
        for(int i=0;i<playNames.size();i++){
            boolean royal = false;
            
            //if the user gave up the round, fold is true
            if(fold && playNames.get(i) == "User")
                continue;
            
            int card=0,royalFlush=0,index=0;
            boolean a=false,k=false,q=false,ja=false,t=false; //taken check whether a card value repeats
            CardProperties[] combination = new CardProperties[7]; //array of cards combining dealer cards and player cards
            CardProperties[] temp = new CardProperties[5];
            for(int j=0;j<5;j++){
                combination[j] = dealerCards[j];
            }
            
            for(int j=5;j<7;j++){
                combination[j] = getCard(playNames.get(i), card++);
            }
            
            /*check cards for royal flush*/
            for(int x=0;x<7;x++){
                
                //check the card values according to royal flush rankings
                /*if the card value is ACE and it is not repeated*/
                if(combination[x].getCardValue() == CardProperties.cardValues.ACE && !a){
                    royalFlush++;
                    temp[index++] = combination[x];
                    a = true;
                }
                    
                else if(combination[x].getCardValue() == CardProperties.cardValues.KING && !k){
                    royalFlush++;
                    temp[index++] = combination[x];
                    k = true;
                }
                
                else if(combination[x].getCardValue() == CardProperties.cardValues.QUEEN && !q){
                    royalFlush++;
                    temp[index++] = combination[x];
                    q = true;
                }
                
                else if(combination[x].getCardValue() == CardProperties.cardValues.JACK && !ja){
                    royalFlush++;
                    temp[index++] = combination[x];
                    ja = true;
                }
                
                else if(combination[x].getCardValue() == CardProperties.cardValues.TEN && !t){
                    royalFlush++;
                    temp[index++] = combination[x];
                    t = true;
                }
            }
            
            
            int hearts=0,clubs=0,spades=0,diamonds=0;
            List<CardProperties.cardValues> cardVal = new ArrayList<>(); /*lists to store cards to check flush ranking*/
            
            List<CardProperties.cardValues> HeartsCardVal = new ArrayList<>(); /*lists to store same suit of HEARTS cards*/
            List<CardProperties.cardValues> ClubsCardVal = new ArrayList<>(); /*lists to store same suit of CLUBS cards*/
            List<CardProperties.cardValues> SpadesCardVal = new ArrayList<>(); /*lists to store same suit of SPADES cards*/
            List<CardProperties.cardValues> DiamondsCardVal = new ArrayList<>(); /*lists to store same suit of DIAMONDS cards*/
            
            //add the corresponding same suit cards to the correct list and add all the card values to the cradVal list 
            for(int x=0;x<7;x++){
                cardVal.add(combination[x].getCardValue());
                
                if(combination[x].getCardSuit() == CardProperties.suits.HEARTS){
                    hearts++;
                    HeartsCardVal.add(combination[x].getCardValue());
                }
                else if(combination[x].getCardSuit() == CardProperties.suits.CLUBS){
                    clubs++;
                    ClubsCardVal.add(combination[x].getCardValue());
                }
                else if(combination[x].getCardSuit() == CardProperties.suits.SPADES){
                    spades++;
                    SpadesCardVal.add(combination[x].getCardValue());
                }
                else if(combination[x].getCardSuit() == CardProperties.suits.DIAMONDS){
                    diamonds++;
                    DiamondsCardVal.add(combination[x].getCardValue());
                }
            }
            
            //sort the cardVal list to ascending order to check the flush ranking
            Collections.sort(cardVal,new Comparator<CardProperties.cardValues>(){
                @Override
                public int compare(CardProperties.cardValues o1, CardProperties.cardValues o2){
                    return o1.compareTo(o2);
                }
            });  
            
            //check whether there are consecutive values contains
            boolean[] check = {false,false,false,false,false,false};
            for(int u=0;u<cardVal.size()-1;u++){
                if(cardVal.get(u+1).ordinal() - cardVal.get(u).ordinal() == 1){
                    check[u] = true;
                }
            }
            
            //get the number of card values in whole 7 cards
            int two=0,three=0,four=0,five=0,six=0,seven=0,eight=0,nine=0,ten=0,jack=0,queen=0,king=0,ace=0;
            for(int x=0;x<7;x++){
                switch(combination[x].getCardValue()){
                    case TWO:{two++;break;}
                    case THREE:{three++;break;}
                    case FOUR:{four++;break;}
                    case FIVE:{five++;break;}
                    case SIX:{six++;break;}
                    case SEVEN:{seven++;break;}
                    case EIGHT:{eight++;break;}
                    case NINE:{nine++;break;}
                    case TEN:{ten++;break;}
                    case JACK:{jack++;break;}
                    case QUEEN:{queen++;break;}
                    case KING:{king++;break;}
                    case ACE:{ace++;break;}
                }
            }
            
            //royal flush
            if(royalFlush == 5 && (temp[0].getCardSuit() == temp[1].getCardSuit() && temp[1].getCardSuit() == temp[2].getCardSuit() && 
                temp[2].getCardSuit() == temp[3].getCardSuit() && temp[3].getCardSuit() == temp[4].getCardSuit())){
                scores[i] = 30;
                royal = true;
            }
            
            
            /*straight flush for each suit*/
            //if there are more than 5 suits of HEARTS in the 7 cards
            else if(hearts >= 5){
                //sort the HEARTS card values to ascending order
                Collections.sort(HeartsCardVal,new Comparator<CardProperties.cardValues>(){
                    @Override
                    public int compare(CardProperties.cardValues o1, CardProperties.cardValues o2){
                        return o1.compareTo(o2);
                    }
                });  
                
                boolean[] checkHearts = new boolean [HeartsCardVal.size()]; //array to check whether there are consecutive values
                for(int u=0;u<HeartsCardVal.size()-1;u++){
                    
                    //if the gap between two values are one
                    if(HeartsCardVal.get(u+1).ordinal() - HeartsCardVal.get(u).ordinal() == 1){
                        checkHearts[u] = true;
                    }
                }
                
                //if there are 5 HEARTS cards and if the ascending order of those cards contains 5 consecutive values, then boolean array must have 4 consecutive true values
                if(hearts == 5 && (checkHearts[0] && checkHearts[1] && checkHearts[2] && checkHearts[3])){
                    scores[i] =20;
                }
                
                //if there are 6 HEARTS cards and if the ascending order of those cards contains 5 consecutive values, then boolean array must have 4 consecutive true values
                else if(hearts == 6 && ((checkHearts[0] && checkHearts[1] && checkHearts[2] && checkHearts[3]) || (checkHearts[1] && checkHearts[2] && checkHearts[3] && checkHearts[4]))){
                    scores[i] = 20;
                }
                
                //if there are 7 HEARTS cards and if the ascending order of those cards contains 5 consecutive values, then boolean array must have 4 consecutive true values
                else if(hearts == 7 && ((checkHearts[0] && checkHearts[1] && checkHearts[2] && checkHearts[3]) || (checkHearts[1] && checkHearts[2] && checkHearts[3] && checkHearts[4]) || (checkHearts[2] && checkHearts[3] && checkHearts[4] && checkHearts[5]))){
                    scores[i] = 20;
                }
                
                //if there aren't any consecutive values then it is flush ranking
                /*flush*/
                else
                    scores[i] = 10;
            }
            
            //if there are more than 5 suits of CLUBS in the 7 cards
            else if(clubs >= 5){
                //sort the CLUBS card values to ascending order
                Collections.sort(ClubsCardVal,new Comparator<CardProperties.cardValues>(){
                    @Override
                    public int compare(CardProperties.cardValues o1, CardProperties.cardValues o2){
                        return o1.compareTo(o2);
                    }
                });  
                
                boolean[] checkClubs = new boolean [ClubsCardVal.size()]; //array to check whether there are consecutive values
                for(int u=0;u<ClubsCardVal.size()-1;u++){
                    if(ClubsCardVal.get(u+1).ordinal() - ClubsCardVal.get(u).ordinal() == 1){
                        checkClubs[u] = true;
                    }
                }
                
                //if there are 5 CLUBS cards and if the ascending order of those cards contains 5 consecutive values, then boolean array must have 4 consecutive true values
                if(clubs == 5 && (checkClubs[0] && checkClubs[1] && checkClubs[2] && checkClubs[3])){
                    scores[i] = 20;
                }
                
                //if there are 6 CLUBS cards and if the ascending order of those cards contains 5 consecutive values, then boolean array must have 4 consecutive true values
                else if(clubs == 6 && ((checkClubs[0] && checkClubs[1] && checkClubs[2] && checkClubs[3]) || (checkClubs[1] && checkClubs[2] && checkClubs[3] && checkClubs[4]))){
                    scores[i] = 20;
                }
                
                //if there are 7 CLUBS cards and if the ascending order of those cards contains 5 consecutive values, then boolean array must have 4 consecutive true values
                else if(clubs == 7 && ((checkClubs[0] && checkClubs[1] && checkClubs[2] && checkClubs[3]) || (checkClubs[1] && checkClubs[2] && checkClubs[3] && checkClubs[4]) || (checkClubs[2] && checkClubs[3] && checkClubs[4] && checkClubs[5]))){
                    scores[i] = 20;
                }
                
                //if there aren't any consecutive values then it is flush ranking
                /*flush*/
                else
                    scores[i] = 10;
            }
            
            //if there are more than 5 suits of SPADES in the 7 cards
            else if(spades >= 5){
                //sort the SPADES card values to ascending order
                Collections.sort(SpadesCardVal,new Comparator<CardProperties.cardValues>(){
                    @Override
                    public int compare(CardProperties.cardValues o1, CardProperties.cardValues o2){
                        return o1.compareTo(o2);
                    }
                });  
                
                boolean[] checkSpades = new boolean [SpadesCardVal.size()]; //array to check whether there are consecutive values
                for(int u=0;u<SpadesCardVal.size()-1;u++){
                    if(SpadesCardVal.get(u+1).ordinal() - SpadesCardVal.get(u).ordinal() == 1){
                        checkSpades[u] = true;
                    }
                }
                
                //if there are 5 SPADES cards and if the ascending order of those cards contains 5 consecutive values, then boolean array must have 4 consecutive true values
                if(spades == 5 && (checkSpades[0] && checkSpades[1] && checkSpades[2] && checkSpades[3])){
                    scores[i] = 20;
                }
                
                //if there are 6 SPADES cards and if the ascending order of those cards contains 5 consecutive values, then boolean array must have 4 consecutive true values
                else if(spades == 6 && ((checkSpades[0] && checkSpades[1] && checkSpades[2] && checkSpades[3]) || (checkSpades[1] && checkSpades[2] && checkSpades[3] && checkSpades[4]))){
                    scores[i] = 20;
                }
                
                //if there are 7 SPADES cards and if the ascending order of those cards contains 5 consecutive values, then boolean array must have 4 consecutive true values
                else if(spades == 7 && ((checkSpades[0] && checkSpades[1] && checkSpades[2] && checkSpades[3]) || (checkSpades[1] && checkSpades[2] && checkSpades[3] && checkSpades[4]) || (checkSpades[2] && checkSpades[3] && checkSpades[4] && checkSpades[5]))){
                    scores[i] = 20;
                }
                
                //if there aren't any consecutive values then it is flush ranking
                /*flush*/
                else
                    scores[i] = 10;
            }
            
            //if there are more than 5 suits of DIAMONDS in the 7 cards
            else if(diamonds >= 5){
                //sort the DIAMONDS card values to ascending order
                Collections.sort(DiamondsCardVal,new Comparator<CardProperties.cardValues>(){
                    @Override
                    public int compare(CardProperties.cardValues o1, CardProperties.cardValues o2){
                        return o1.compareTo(o2);
                    }
                });  
                
                boolean[] checkDiamonds = new boolean [DiamondsCardVal.size()]; //array to check whether there are consecutive values
                for(int u=0;u<DiamondsCardVal.size()-1;u++){
                    if(DiamondsCardVal.get(u+1).ordinal() - DiamondsCardVal.get(u).ordinal() == 1){
                        checkDiamonds[u] = true;
                    }
                }
                
                //if there are 5 DIAMONDS cards and if the ascending order of those cards contains 5 consecutive values, then boolean array must have 4 consecutive true values
                if(diamonds == 5 && (checkDiamonds[0] && checkDiamonds[1] && checkDiamonds[2] && checkDiamonds[3])){
                    scores[i] = 20;
                }
                
                //if there are 6 DIAMONDS cards and if the ascending order of those cards contains 5 consecutive values, then boolean array must have 4 consecutive true values
                else if(diamonds == 6 && ((checkDiamonds[0] && checkDiamonds[1] && checkDiamonds[2] && checkDiamonds[3]) || (checkDiamonds[1] && checkDiamonds[2] && checkDiamonds[3] && checkDiamonds[4]))){
                    scores[i] = 20;
                }
                
                //if there are 7 DIAMONDS cards and if the ascending order of those cards contains 5 consecutive values, then boolean array must have 4 consecutive true values
                else if(diamonds == 7 && ((checkDiamonds[0] && checkDiamonds[1] && checkDiamonds[2] && checkDiamonds[3]) || (checkDiamonds[1] && checkDiamonds[2] && checkDiamonds[3] && checkDiamonds[4]) || (checkDiamonds[2] && checkDiamonds[3] && checkDiamonds[4] && checkDiamonds[5]))){
                    scores[i] = 20;
                }
                
                //if there aren't any consecutive values then it is flush ranking
                /*flush*/
                else
                    scores[i] = 10;
            }
            
            //four of kind
            else if(ace == 4 || two == 4 || three == 4 || four == 4 || five == 4 || six ==4 || seven == 4 || eight == 4 || nine == 4 || ten == 4 || jack == 4 || queen == 4 || king == 4){
                scores[i] = 15;
            }
            
            //full house
            else if((ace == 3 || two == 3 || three == 3 || four == 3 || five == 3 || six == 3 || seven == 3 || eight == 3 || nine == 3 || ten == 3 || 
               jack == 3 || queen == 3 || king == 3) && ((ace == 2 || two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2 || nine == 2 || ten == 2 || 
               jack == 2 || queen == 2 || king == 2))){
                scores[i] = 12;
            }
           
            /*straight*/
            /*from line 403, if there are consecutive values then boolean array must have 4 consecutive true values*/
            else if((check[0] && check[1] && check[2] && check[3]) || (check[1] && check[2] && check[3] && check[4]) || (check[2] && check[3] && check[4] && check[5])){
                scores[i] = 8;
            }
            
            //three of kind
            else if(ace == 3 || two == 3 || three == 3 || four == 3 || five == 3 || six == 3 || seven == 3 || eight == 3 || nine == 3 || ten == 3 || 
               jack == 3 || queen == 3 || king == 3){
                scores[i] = 6;
            }
            
            /*two pair*/
            /*********************checks for each pair*********************/
            else if(ace == 2 && ( two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2 || nine == 2 || ten == 2 || 
               jack == 2 || queen == 2 || king == 2)){
                scores[i] = 4;
            }
            
            else if(two == 2 && (ace == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2 || nine == 2 || ten == 2 || 
               jack == 2 || queen == 2 || king == 2)){
                scores[i] = 4;
            }
            
            else if(three == 2 && (ace == 2 || two == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2 || nine == 2 || ten == 2 || 
               jack == 2 || queen == 2 || king == 2)){
                scores[i] = 4;
            }
            
            else if(four == 2 && (ace == 2 || two == 2 || three == 2 || five == 2 || six == 2 || seven == 2 || eight == 2 || nine == 2 || ten == 2 || 
               jack == 2 || queen == 2 || king == 2)){
                scores[i] = 4;
            }
            
            else if(five == 2 && (ace == 2 || two == 2 || three == 2 || four == 2 || six == 2 || seven == 2 || eight == 2 || nine == 2 || ten == 2 || 
               jack == 2 || queen == 2 || king == 2)){
                scores[i] = 4;
            }
            
            else if(six == 2 && (ace == 2 || two == 2 || three == 2 || four == 2 || five == 2 || seven == 2 || eight == 2 || nine == 2 || ten == 2 || 
               jack == 2 || queen == 2 || king == 2)){
                scores[i] = 4;
            }
            
            else if(seven == 2 && (ace == 2 || two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || eight == 2 || nine == 2 || ten == 2 || 
               jack == 2 || queen == 2 || king == 2)){
                scores[i] = 4;
            }
            
            else if(eight == 2 && (ace == 2 || two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || nine == 2 || ten == 2 || 
               jack == 2 || queen == 2 || king == 2)){
                scores[i] = 4;
            }
            
            else if(nine == 2 && (ace == 2 || two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2 || ten == 2 || 
               jack == 2 || queen == 2 || king == 2)){
                scores[i] = 4;
            }
            
            else if(ten == 2 && (ace == 2 || two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2 || nine == 2 || 
               jack == 2 || queen == 2 || king == 2)){
                scores[i] = 4;
            }
            
            else if(jack == 2 && (ace == 2 || two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2 || nine == 2 || ten == 2 || 
                queen == 2 || king == 2)){
                scores[i] = 4;
            }
            
            else if(queen == 2 && (ace == 2 || two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2 || nine == 2 || ten == 2 || 
               jack == 2 || king == 2)){
                scores[i] = 4;
            }
            
            else if(king == 2 && (ace == 2 || two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2 || nine == 2 || ten == 2 || 
               jack == 2 || queen == 2)){
                scores[i] = 4;
            }
            
            //one pair
            else if(ace == 2 || two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2 || nine == 2 || ten == 2 || 
               jack == 2 || queen == 2 || king == 2){
                scores[i] = 2;
            }
            
            //ace-high
            else{
                scores[i] = 1;
            }
            
        }
    }
}

