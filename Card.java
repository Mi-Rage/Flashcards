package flashcards;


import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Card {
    Map<String, String> cards = new TreeMap<>();
    Scanner scanner = new Scanner(System.in);

    public void playGame() {
        String action;
        do {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            action = scanner.next();
            scanner.nextLine();
            switch (action) {
                case "add":
                    addCard();
                    break;
                case "remove":
                    // remove a cerd
                case "import":
                    // imoprt file
                case "export":
                    // save file
                case "ask":
                    gamePlay();

            }
        } while (!action.equals("exit"));

    }

    public void addCard() {
        String term;
        String definition;


        System.out.println("The card :");
        do {
            term = scanner.nextLine();
            if (isContainsKey(term)) {
                System.out.printf("The card \"%s\" already exists. Try again:%n", term);
            }
        } while (isContainsKey(term));

        System.out.println("The definition of the card :");
        do {
            definition = scanner.nextLine();
            if (isContainsDef(definition)) {
                System.out.printf("The definition \"%s\" already exists. Try again:%n", definition);
            }
        } while (cards.containsValue(definition));
        cards.put(term, definition);
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
        int quantity;

        do {
            while (true) {
                try {
                    System.out.println("How many times to ask?");
                    quantity = scanner.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("ERROR: Enter only whole numbers! Try again!");
                } finally {
                    scanner.nextLine();
                }
            }
            if (quantity > cards.size()) {
                System.out.printf("ERROR: %s times is more than %s cards! Try again!%n" , quantity , cards.size());
            }
        } while (quantity > cards.size());


        for (var entry : cards.entrySet()) {
            System.out.printf("Print definition of \"%s\":%n", entry.getKey());
            answer = scanner.nextLine();
            if (!entry.getValue().equals(answer) && cards.containsValue(answer)) {
                System.out.printf("Wrong answer. The correct one is \"%s\", you've just written the definition of \"%s\".%n"
                        , entry.getValue(), getKeyFromMap(cards, answer));
            } else if (entry.getValue().equals(answer)) {
                System.out.println("Correct answer.");
            } else {
                System.out.printf("Wrong answer. The correct one is \"%s\".%n", entry.getValue());
            }
        }
    }
}
