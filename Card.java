package flashcards;

import java.util.Scanner;

public class Card {
    private static int quantity;
    private GameCards[] gameCard;
    Scanner scanner = new Scanner(System.in);

    public void initCard(){

        System.out.print("Input the number of cards:\n>");
        quantity = scanner.nextInt();
        gameCard = new GameCards[quantity];
        scanner.nextLine();
        for (int i = 0; i < quantity; i++) {
            System.out.print("The card #" + i + ">");
            String term = scanner.nextLine();
            System.out.print("The definition of the card #" + i + ">");
            String definition = scanner.nextLine();
            gameCard[i] = new GameCards(term, definition);
            System.out.println(gameCard[i].getTerm() + " " + gameCard[i].getDefinition());
        }
    }

    public static int getQuantity(){
        return quantity;
    }

    public void gamePlay() {
        String answer;
        for (int i = 0; i < Card.getQuantity(); i++) {
            System.out.print("Print the definition of \"" + gameCard[i].getTerm() + "\"\n>");
            answer = scanner.nextLine();
            if (answer.equals(gameCard[i].getDefinition())) {
                System.out.println("Correct answer");
            } else {
                System.out.println("Wrong answer. The correct one is \"" + gameCard[i].getDefinition() + "\"");
            }
        }
    }
}
