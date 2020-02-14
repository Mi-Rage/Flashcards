package flashcards;

public class Main {
    public static void main(String[] args) {
        String fileInput = null;
        String fileExport = null;

// Let's check the arguments for import and export files

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-import":
                    if (i + 1 == args.length || args[i + 1].startsWith("-")) {
                        System.out.println("Error : Нет значения для ключа -input");
                    } else {
                        fileInput = args[i + 1];
                    }
                    break;
                case "-export":
                    if (i + 1 == args.length || args[i + 1].startsWith("-")) {
                        System.out.println("Error : Нет значения для ключа -export");
                    } else {
                        fileExport = args[i + 1];
                    }
                    break;
            }
        }

        Card card = new Card();
        card.playGame(fileInput, fileExport);

    }
}
