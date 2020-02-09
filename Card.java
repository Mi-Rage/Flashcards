package flashcards;


import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Card {
    Map<String, String> card = new TreeMap<>();
    Scanner scanner = new Scanner(System.in);

    public void initCard() {
        String term;
        String definition;

        int quantity;
        while(true){
            try {
                System.out.print("Input the number of cards:\n>");
                quantity = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("ERROR: Enter only whole numbers! Try again!");
            }
            finally {
                scanner.nextLine();
            }
        }

        for (int i = 0; i < quantity; i++) {
            System.out.print("The card #" + i + "\n>");
            do {
                term = scanner.nextLine();
                if (isContainsKey(term)) {
                    System.out.println("The card \"" + term + "\" already exists. Try again:");
                }
            } while (isContainsKey(term));

            do {
                System.out.print("The definition of the card #" + i + "\n>");
                definition = scanner.nextLine();
                if (isContainsDef(definition)) {
                    System.out.print("The definition \"" + definition + "\" already exists.");
                }
            } while (card.containsValue(definition));
            card.put(term, definition);
        }
    }

    // Does the map have a "key" value ?
    public boolean isContainsKey(String term){
        return card.containsKey(term);
    }

    // Does the map have a "definition" value ?
    public boolean isContainsDef(String definition){
        return card.containsValue(definition);
    }


    public void gamePlay(){
        String answer;
        for (var entry : card.entrySet()) {
            System.out.print("Print the definition of \"" + entry.getKey() + "\"\n>");
            answer = scanner.nextLine();
            if(answer.equals(entry.getValue())) {
                System.out.println("Correct answer.");
            } else {
                System.out.print("Wrong answer. The correct one is \"" + entry.getValue() + "\"");
                if (card.containsValue(answer)) {
                    for (var find : card.entrySet()) {
                        if (find.getValue().equals(answer)){
                            System.out.println(", you've just written the definition of \"" + find.getKey() + "\".");
                            break;
                        }
                    }
                }  else {
                    System.out.println(".");
                }
            }
        }

    }



}
