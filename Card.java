package flashcards;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class Card {
    private Map<String, String> cards = new TreeMap<>();
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
                    removeCard();
                    break;
                case "import":
                    importCard();
                    break;
                case "export":
                    exportCard();
                    break;
                case "ask":
                    gamePlay();
                    break;

            }
        } while (!action.equals("exit"));
        System.out.println("Bye bye!");
    }

    public void addCard() {
        String term;
        String definition;

        System.out.println("The card :");
        term = scanner.nextLine();
        if (isContainsKey(term)) {
            System.out.printf("The card \"%s\" already exists.%n", term);
            return;
        }
        System.out.println("The definition of the card :");
        definition = scanner.nextLine();
        if (isContainsDef(definition)) {
            System.out.printf("The definition \"%s\" already exists.%n", definition);
            return;
        }
        cards.put(term, definition);
        System.out.printf("The pair (\"%s\":\"%s\") has been added.%n", term, definition);
    }

    public void removeCard() {
        String term;
        System.out.println("The card:");
        term = scanner.nextLine();
        if (isContainsKey(term)) {
            cards.remove(term);
            System.out.println("The card has been removed.");
        } else {
            System.out.printf("Can't remove \"%s\", there is no such card.%n", term);
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
        String randomKey;
        String randomValue;
        int quantity;

        System.out.println("How many times to ask?");
        try {
            quantity = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("ERROR: Wrong input");
            return;
        }

        scanner.nextLine();
        List<String> keys = new ArrayList<>(cards.keySet());

        for (int i = 0; i < quantity; i++) {
            Random random = new Random();

            randomKey = keys.get(random.nextInt(keys.size()));
            randomValue = cards.get(randomKey);

            System.out.printf("Print definition of \"%s\":%n", randomKey);
            answer = scanner.nextLine();
            if (!randomValue.equals(answer) && cards.containsValue(answer)) {
                System.out.printf("Wrong answer. The correct one is \"%s\", you've just written the definition of \"%s\".%n"
                        , randomValue, getKeyFromMap(cards, answer));
            } else if (randomValue.equals(answer)) {
                System.out.println("Correct answer.");
            } else {
                System.out.printf("Wrong answer. The correct one is \"%s\".%n", randomValue);
            }
        }
    }

    public void exportCard() {
        String fileName;
        System.out.println("File name:");
        fileName = scanner.nextLine();
        File file = new File(fileName);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (var entry : cards.entrySet()) {
                printWriter.println(entry.getKey());
                printWriter.println(entry.getValue());
            }
        } catch (IOException e) {
            System.out.printf("ERROR: An exception occurs %s", e.getMessage());
        }
        System.out.printf("%d cards have been saved.%n", cards.size());
    }

    public void importCard() {
        String fileName;
        System.out.println("File name:");
        fileName = scanner.nextLine();
        File file = new File(fileName);
        int count = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                cards.put(scanner.nextLine(), scanner.nextLine());
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);

        }
        if (count > 0) {
            System.out.printf("%d cards have been loaded.%n", count);
        }
    }
}