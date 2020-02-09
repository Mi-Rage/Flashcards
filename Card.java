package flashcards;


import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Card {
    Map<String, String> cards = new TreeMap<>();
    Scanner scanner = new Scanner(System.in);

    public void initCard() {
        String term;
        String definition;

        int quantity;
        while (true) {
            try {
                System.out.println("Input the number of cards:");
                quantity = scanner.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("ERROR: Enter only whole numbers! Try again!");
            } finally {
                scanner.nextLine();
            }
        }

        for (int i = 0; i < quantity; i++) {
            System.out.println("The card #" + i);
            do {
                term = scanner.nextLine();
                if (isContainsKey(term)) {
                    System.out.println("The card \"" + term + "\" already exists. Try again:");
                }
            } while (isContainsKey(term));

            System.out.println("The definition of the card #" + i + ":");
            do {
                definition = scanner.nextLine();
                if (isContainsDef(definition)) {
                    System.out.println("The definition \"" + definition + "\" already exists. Try again:");
                }
            } while (cards.containsValue(definition));
            cards.put(term, definition);
        }
    }

    // Does the map have a "key" value ?
    public boolean isContainsKey(String term) {
        return cards.containsKey(term);
    }

    // Does the map have a "definition" value ?
    public boolean isContainsDef(String definition) {
        return cards.containsValue(definition);
    }

    // Get key from map by value
    public <K, V> K getKeyFromMap(Map<K, V> card, V description) {
        for (Map.Entry<K, V> entry : card.entrySet()) {
            if (entry.getValue().equals(description)) {
                return entry.getKey();
            }
        }
        return null;
    }


    public void gamePlay() {
        String answer;

        for (var entry : cards.entrySet()) {
            System.out.println("Print definition of \"" + entry.getKey() + "\":");
            answer = scanner.nextLine();
            if (!entry.getValue().equals(answer) && cards.containsValue(answer)) {
                System.out.println("Wrong answer. The correct one is \"" + entry.getValue()
                        + "\", you've just written the definition of \""
                        + getKeyFromMap(cards, answer) + "\".");
            } else if (entry.getValue().equals(answer)) {
                System.out.println("Correct answer.");
            } else {
                System.out.println("Wrong answer. The correct one is \"" + entry.getValue() + "\"");
            }
        }
    }
}
