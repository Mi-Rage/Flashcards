package flashcards;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class Card {
    private Map<String, String> cards = new TreeMap<>();
    private Map<String, Integer> errors = new TreeMap<>();
    private ArrayList<String> log = new ArrayList<>();
    StringBuilder stringBuilder = new StringBuilder();
    Scanner scanner = new Scanner(System.in);


    public void playGame() {
        String action;
        do {
            printLog("Input the action (add, remove, import, export, ask, log, hardest card, reset stats, exit):");
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
                case "hardest":
                    hardestCard();
                    break;
                case "reset":
                    resetStats();
                    break;

            }
        } while (!action.equals("exit"));
        System.out.println("Bye bye!");
    }


    public void addCard() {
        String term;
        String definition;

        printLog("The card :");
        term = scanner.nextLine();
        log.add(term);
        if (isContainsKey(term)) {
            printLog("The card \"" + term + "\" already exists.");
            return;
        }
        printLog("The definition of the card :");
        definition = scanner.nextLine();
        log.add(definition);
        if (isContainsDef(definition)) {
            printLog("The definition \"" + definition + "\" already exists.");
            return;
        }
        cards.put(term, definition);
        errors.put(term, 0);
        printLog("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
    }

    public void removeCard() {
        String term;
        printLog("The card:");
        term = scanner.nextLine();
        log.add(term);
        if (isContainsKey(term)) {
            cards.remove(term);
            errors.remove(term);
            printLog("The card has been removed.");
        } else {
            printLog("Can't remove \"" + term + "\", there is no such card.");
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
        int errorCounter;

        printLog("How many times to ask?");
        try {
            quantity = scanner.nextInt();
        } catch (InputMismatchException e) {
            printLog("ERROR: Wrong input");
            return;
        }

        scanner.nextLine();
        List<String> keys = new ArrayList<>(cards.keySet());

        for (int i = 0; i < quantity; i++) {
            Random random = new Random();

            randomKey = keys.get(random.nextInt(keys.size()));
            randomValue = cards.get(randomKey);
            errorCounter = errors.get(randomKey);

            printLog("Print definition of \"" + randomKey + "\":");
            answer = scanner.nextLine();
            log.add(answer);
            if (!randomValue.equals(answer) && cards.containsValue(answer)) {
                printLog("Wrong answer. The correct one is \"" + randomValue + "\", you've just written the definition of \"" + getKeyFromMap(cards, answer) + "\".");
                errorCounter += 1;
            } else if (randomValue.equals(answer)) {
                printLog("Correct answer.");
            } else {
                printLog("Wrong answer. The correct one is \"" + randomValue + "\".");
                errorCounter += 1;
            }
            errors.put(randomKey, errorCounter);
        }
    }

    public void exportCard() {
        String fileName;
        printLog("File name:");
        fileName = scanner.nextLine();
        log.add(fileName);
        File file = new File(fileName);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (Map.Entry<String, String> entry : cards.entrySet()) {
                printWriter.println(entry.getKey());
                printWriter.println(entry.getValue());
                printWriter.println(errors.get(entry.getKey()));
            }
        } catch (IOException e) {
            printLog("ERROR: An exception occurs " + e.getMessage());
        }
        printLog(cards.size() + " cards have been saved.");
    }

    public void importCard() {
        String fileName;
        String term;
        String definition;
        int error;

        printLog("File name:");
        fileName = scanner.nextLine();
        log.add(fileName);
        File file = new File(fileName);
        int count = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                term = scanner.nextLine();
                definition = scanner.nextLine();
                error = Integer.parseInt(scanner.nextLine());
                cards.put(term, definition);
                errors.put(term, error);
                count++;
            }
        } catch (FileNotFoundException e) {
            printLog("File not found: " + fileName);
        }
        if (count > 0) {
            printLog(count + " cards have been loaded.");
        }
    }

    private void hardestCard() {
        int maxError = 0;
        int maxErrorCount = 0;
        stringBuilder.append("The hardest card is ");
        for (var entry : errors.entrySet()) {
            if (entry.getValue() > 0) {
                maxError = (maxError < entry.getValue()) ? entry.getValue() : maxError;
            }
        }
        for (var entry : errors.entrySet()) {
            if (entry.getValue() > 0) {
                if (entry.getValue() == maxError) {
                    stringBuilder.append("\"").append(entry.getKey()).append("\"").append(",");
                    maxErrorCount += 1;
                }
            }
        }
        if (maxErrorCount > 1) {
            stringBuilder.insert(16,"s").delete(18, 20).insert(18, "are");
        } else {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1).append(".");
        }
        if (maxError > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1).append(". You have ").append(maxError).append(" errors answering them.");
        } else {
            stringBuilder.delete(0, stringBuilder.length());
            stringBuilder.append("There are no cards with errors.");
        }
        printLog(stringBuilder.toString());
        stringBuilder.delete(0,stringBuilder.length());
    }

    private void saveLog() {
        String fileName;
        printLog("File name:");
        fileName = scanner.nextLine();
        log.add(fileName);
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

    public void printLog(String str) {
        System.out.println(str);
        log.add(str);
    }

    public void resetStats(){
        errors.replaceAll((k, v) -> 0);
        printLog("Card statistics has been reset.");
    }
}