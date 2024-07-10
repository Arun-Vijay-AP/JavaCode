import java.util.* ;

public class RockPaperScissor 
{
    public static void main(String[] args) 
    {
        Scanner ap = new Scanner(System.in) ; 
        while (true) 
        {
            String[] rps = {"Rock" , "Paper" , "Scissor"}     ;
            String  computerMove = rps[new Random().nextInt(rps.length)] ;

            String playerMove ;
            while (true) 
            {
                System.out.println("Options : \n\t1.Rock\n\t2.Paper\n\t3.Scissor\nPlease enter your move :") ;
                playerMove = ap.nextLine() ; 
                if (playerMove.equals("Rock")||playerMove.equals("Paper")||playerMove.equals("Scissor")) 
                    break ;
                System.out.println(playerMove + " is npt a valid move.") ;
            }
            System.out.println("Computer Move : "+computerMove) ;

            if (computerMove.equals(playerMove)) {
                System.out.println("The game was a Tie!!") ;
            }
            else if (playerMove.equals("Rock")) {
                if (computerMove.equals("Paper")) {
                    System.out.println("You Lose!!") ;
                }
                else if (computerMove.equals("Scissor")) {
                    System.out.println("You Win!!") ;
                }
            }
            else if (playerMove.equals("Paper")) {
                if (computerMove.equals("Rock")) {
                    System.out.println("You Win!!") ;
                }
                else if (computerMove.equals("Scissor")) {
                    System.out.println("You Lose!!") ;
                }
            }
            else if (playerMove.equals("Scissor")) {
                if (computerMove.equals("Paper")) {
                    System.out.println("You Win!!") ;
                }
                else if (computerMove.equals("Rock")) {
                    System.out.println("You Lose!!") ;
                }
            }
            System.out.println("Play again ? (y or n)") ;
            String playAgain = ap.nextLine() ;

            if (playAgain.equals("n")) {
                break ;
            }
        }
    }
}
