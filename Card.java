package flashcards;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class Card {
    private Map<String, String> cards = new TreeMap<>();
    private ArrayList<String> log = new ArrayList<>();
    StringBuilder stringBuilder = new StringBuilder();
    Scanner scanner = new Scanner(System.in);


    public void playGame() {
        String action;
        do {
            stringBuilder.append("Input the action (add, remove, import, export, ask, exit):");
            printLog(stringBuilder, log);
            action = scanner.next();
            log.add(action);
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
                case "log":
                    saveLog();
                    break;

            }
        } while (!action.equals("exit"));
        System.out.println("Bye bye!");
    }



    public void addCard() {
        String term;
        String definition;

        stringBuilder.append("The card :");
        printLog(stringBuilder, log);
        term = scanner.nextLine();
        log.add(term);
        if (isContainsKey(term)) {
            stringBuilder.append("The card \"").append(term).append("\" already exists.");
            printLog(stringBuilder, log);
            return;
        }
        stringBuilder.append("The definition of the card :");
        printLog(stringBuilder, log);
        definition = scanner.nextLine();
        log.add(definition);
        if (isContainsDef(definition)) {
            stringBuilder.append("The definition \"").append(definition).append("\" already exists.");
            printLog(stringBuilder, log);
            return;
        }
        cards.put(term, definition);
        stringBuilder.append("The pair (\"").append(term).append("\":\"").append(definition).append("\") has been added.");
        printLog(stringBuilder, log);
    }

    public void removeCard() {
        String term;
        stringBuilder.append("The card:");
        printLog(stringBuilder, log);
        term = scanner.nextLine();
        log.add(term);
        if (isContainsKey(term)) {
            cards.remove(term);
            stringBuilder.append("The card has been removed.");
        } else {
            stringBuilder.append("Can't remove \"").append(term).append("\", there is no such card.");
        }
        printLog(stringBuilder, log);
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

        stringBuilder.append("How many times to ask?");
        printLog(stringBuilder, log);
        try {
            quantity = scanner.nextInt();
        } catch (InputMismatchException e) {
            stringBuilder.append("ERROR: Wrong input");
            printLog(stringBuilder, log);
            return;
        }

        scanner.nextLine();
        List<String> keys = new ArrayList<>(cards.keySet());

        for (int i = 0; i < quantity; i++) {
            Random random = new Random();

            randomKey = keys.get(random.nextInt(keys.size()));
            randomValue = cards.get(randomKey);

            stringBuilder.append("Print definition of \"").append(randomKey).append("\":");
            printLog(stringBuilder, log);
            answer = scanner.nextLine();
            log.add(answer);
            if (!randomValue.equals(answer) && cards.containsValue(answer)) {
                stringBuilder.append("Wrong answer. The correct one is \"").append(randomValue).append("\", you've just written the definition of \"").append(getKeyFromMap(cards, answer)).append("\".");
            } else if (randomValue.equals(answer)) {
                stringBuilder.append("Correct answer.");
            } else {
                stringBuilder.append("Wrong answer. The correct one is \"").append(randomValue).append("\".");
            }
            printLog(stringBuilder, log);
        }
    }

    public void exportCard() {
        String fileName;
        stringBuilder.append("File name:");
        printLog(stringBuilder, log);
        fileName = scanner.nextLine();
        log.add(fileName);
        File file = new File(fileName);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (var entry : cards.entrySet()) {
                printWriter.println(entry.getKey());
                printWriter.println(entry.getValue());
            }
        } catch (IOException e) {
            stringBuilder.append("ERROR: An exception occurs ").append(e.getMessage());
            printLog(stringBuilder, log);
        }
        stringBuilder.append(cards.size()).append(" cards have been saved.");
        printLog(stringBuilder, log);
    }

    public void importCard() {
        String fileName;
        stringBuilder.append("File name:");
        printLog(stringBuilder, log);
        fileName = scanner.nextLine();
        log.add(fileName);
        File file = new File(fileName);
        int count = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                cards.put(scanner.nextLine(), scanner.nextLine());
                count++;
            }
        } catch (FileNotFoundException e) {
            stringBuilder.append("File not found: ").append(fileName);
            printLog(stringBuilder, log);
        }
        if (count > 0) {
            stringBuilder.append(count).append(" cards have been loaded.");
            printLog(stringBuilder, log);
        }
    }

    private void saveLog() {
        String fileName;
        System.out.println("File name:");
        fileName = scanner.nextLine();
        File file = new File(fileName);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (String entry : log) {
                printWriter.println(entry);
            }
        } catch (IOException e) {
            System.out.printf("ERROR: An exception occurs %s", e.getMessage());
        }
        System.out.printf("The log has been saved.%n");
    }

    public void printLog(StringBuilder stringBuilder, ArrayList<String> log){
        System.out.println(stringBuilder);
        log.add(stringBuilder.toString());
        stringBuilder.delete(0,stringBuilder.length());
    }
}