package poker;


public class CardProperties {
    
    enum suits{
        HEARTS,CLUBS,SPADES,DIAMONDS;
        static final suits[] SUITS= suits.values(); //get all the elements in the cardValues enumeration and made it final to have it constant
        public static suits giveCardSuit(int index)
	{
            return suits.SUITS[index]; //returns the corresponding value in the card
	}
        public void print(){
            
        }
    }
    
    enum cardValues{
        TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,JACK,KING,QUEEN,ACE;
        static final cardValues[] CARDVALUES= cardValues.values(); //get all the elements in the cardValues enumeration and made it final to have it constant
        public static cardValues giveCardValue(int index)
	{
            return cardValues.CARDVALUES[index]; //returns the corresponding value in the card
	}
        
    }
    
    private suits suit;
    private cardValues value;
    
    public CardProperties(){} //default constructor
    
    //parameterized constructor
    public CardProperties(suits suit, cardValues value){
        this.suit = suit;
        this.value = value;
    }
    
    //function to return the card suit
    public suits getCardSuit(){
        return suit;
    }
    
    //function to return card value
    public cardValues getCardValue(){
        return value;
    }
    
    //method to print suits
    public void displaySuit(){
        switch(suit){
            case HEARTS:{
                System.out.print("♥ ");
                break;
            }
            case CLUBS:{
                System.out.print("♣ ");
                break;
            }
            case SPADES:{
                System.out.print("♠ ");
                break;
            }
            case DIAMONDS:{
                System.out.print("♦ ");
                break;
            }
        }
    }
    
    //method to print card values
    public void displayValue(){
        switch(value){
            case TWO:{
                System.out.println("2");
                break;
            }
            case THREE:{
                System.out.println("3");
                break;
            }
            case FOUR:{
                System.out.println("4");
                break;
            }
            case FIVE:{
                System.out.println("5");
                break;
            }
            case SIX:{
                System.out.println("6");
                break;
            }
            case SEVEN:{
                System.out.println("7");
                break;
            }
            case EIGHT:{
                System.out.println("8");
                break;
            }
            case NINE:{
                System.out.println("9");
                break;
            }
            case TEN:{
                System.out.println("10");
                break;
            }
            case JACK:{
                System.out.println("J");
                break;
            }
            case QUEEN:{
                System.out.println("Q");
                break;
            }
            case KING:{
                System.out.println("K");
                break;
            }
            case ACE:{
                System.out.println("A");
                break;
            }
        }
    }
    
   
    
}
