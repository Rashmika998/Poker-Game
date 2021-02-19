package poker;

import java.util.Random;


public class CardDeck {
    private int noOfCardsInDeck;
    private CardProperties[] cardPack;
    
    public CardDeck()
    {
	cardPack=new CardProperties [52]; //no of cards in the pack
	createDeck();
    }
    
    //method to create a deck
    public void createDeck(){
        CardProperties.suits suits[] = CardProperties.suits.values();
        noOfCardsInDeck=0;
        
        //create cards
        for(int i = 0;i < suits.length; i++){
            CardProperties.suits cardSuit=suits[i];
            for(int j =0; j < 13;j++){
                cardPack[noOfCardsInDeck++] = new CardProperties(cardSuit, CardProperties.cardValues.giveCardValue(j));
            }
        }
    }
    
    //method to shuffle cards
    public void shuffleCards()
    {
        int num=cardPack.length;
        Random ran=new Random();
        for(int i=0;i<cardPack.length;i++)
        {
            int randVal=i+ran.nextInt(num-i); //generate a random index from the remaining cards to shuffle(swap)
            CardProperties randCard=cardPack[randVal];
            cardPack[randVal]=cardPack[i];
            cardPack[i]=randCard;
        }
    }
    
    //method to get a certain card
    public CardProperties[] getCards(int n)
    {
        if(n<0)
            throw new IllegalArgumentException("Error! Entered value is invalid");
        
        if(n>noOfCardsInDeck)
            throw new IllegalArgumentException("Sorry! Not enough cards left in the deck");
        
        CardProperties[] giveCards=new CardProperties[n];
        for(int y=0;y<n;y++)
        {
            noOfCardsInDeck--;
            giveCards[y]=cardPack[noOfCardsInDeck];
        }
        return giveCards;
    } 
    
    public void addCard(CardProperties card){
        cardPack[noOfCardsInDeck] = card;
        noOfCardsInDeck++;
        
    }
    
    //method to display cards
    public void displayCards(){
        for(int i=0;i<cardPack.length;i++){
            cardPack[i].displaySuit();
            cardPack[i].displayValue();
        }
    }
}
