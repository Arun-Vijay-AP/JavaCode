import java.util.* ;

class NumGuess {
    public static void main(String[] args) {
        Scanner ap = new Scanner(System.in) ;
        String play = "yes" ;

        while (play.equals("yes")) 
        {
            Random random = new Random() ;
            int random_number = random.nextInt(1,10) ;
            int guess = 0 ;
            int tries = 0 ;

            while (tries<3) 
            {
                System.out.println("Enter a Number between 1 to 10 : ") ;
                guess = ap.nextInt() ;
                tries++ ;
                if (guess == random_number) {
                    System.out.println("Awesome! Your Guessed Correctly!!!!") ;
                    System.out.println("The No. of Tries taken : "+tries) ;
                    System.out.println("Would you like play it again : ") ;
                    break ;
                }
                else {
                    System.out.println("Just away get closer :(") ;
                }
            }
            if (tries == 3 && guess!=random_number) {
                System.out.println("You are almost there better luck next time.") ;
                System.out.println("The Number : "+random_number) ;
                System.out.println("Would you like play it again : ") ;
            }
            play = ap.next().toLowerCase() ;
            System.out.println("--------------------------------------") ;
        }
    }
}
