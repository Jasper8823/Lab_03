package pl.edu.pja.tpo02.flashcardsapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class FlashcardsController {
    private final FileService fileService;
    Scanner scanner = new Scanner(System.in);
    Printer printer;
    @Autowired
    public FlashcardsController(FileService fs,Printer p) {
        printer=p;
        fileService=fs;
    }

    public void displayMenu() {
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. Add a new word to dictionary");
            System.out.println("2. Get a random word for testing");
            System.out.println("3. See all words from dictionary");
            System.out.println("4. Search for a word");
            System.out.println("5. Delete words");
            System.out.println("6. Modify words");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addNewWord();
                case 2 -> test();
                case 3 -> display(true);
                case 4 -> wordSearch();
                case 5 -> wordDelete();
                case 6 -> wordModify();
                case 7 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 7);
    }
    private void wordModify(){
        display(false);
        System.out.println("Choose translation to modify");
        int i=scanner.nextInt();
        System.out.println("Choose language");
        System.out.println("1. English");
        System.out.println("2. German");
        System.out.println("3. Polish");
        int number = scanner.nextInt();
        System.out.println("Insert new word");
        String line = scanner.nextLine();
        line = scanner.nextLine();
        if(number>=1&&number<=3){
            fileService.modifyWord(i,number,line);
        }else{
            System.out.println("Error");
        }
    }
    private void wordDelete(){
        display(false);
        System.out.println("Choose translation to delete");
        int i=scanner.nextInt();
        fileService.deleteEntry(i-1);
    }

    private void wordsSort(){
        List<Entry> entries = fileService.getAllWods();
        System.out.println("Choose language");
        System.out.println("1. English");
        System.out.println("2. German");
        System.out.println("3. Polish");
        int number, number1;
        number = scanner.nextInt();
        System.out.println("Choose type of sort");
        System.out.println("1. Descending");
        System.out.println("2. Ascending");
        number1 = scanner.nextInt();
        if((number>=1&&number<=3)&&(number1==1||number1==2)){
            String[] words = new String[entries.size()];
            Map<String,Entry> map = new HashMap<>();
            for (int i=0;i<entries.size();i++){
                switch (number){
                    case 1:
                        map.put(entries.get(i).getEng().toLowerCase(),entries.get(i));
                        words[i]=entries.get(i).getEng().toLowerCase();
                        break;
                    case 2:
                        map.put(entries.get(i).getGer().toLowerCase(),entries.get(i));
                        words[i]=entries.get(i).getGer().toLowerCase();
                        break;
                    case 3:
                        map.put(entries.get(i).getPol().toLowerCase(),entries.get(i));
                        words[i]=entries.get(i).getPol().toLowerCase();
                        break;
                }
            }
            if(number1==2){
                Arrays.sort(words);
            }else {
                Arrays.sort(words,Collections.reverseOrder());
            }
            System.out.println("    English             German              Polish");
            for (int i=0;i<entries.size();i++) {
                System.out.print(map.get(words[i]).getId());
                if (map.get(words[i]).getId() < 10) {
                    System.out.print("   ");
                } else if (map.get(words[i]).getId() < 100) {
                    System.out.print("  ");
                } else if (map.get(words[i]).getId() < 1000) {
                    System.out.print(" ");
                }
                System.out.print(printer.getWord(map.get(words[i]).getEng()));
                for (int j = 0; j < 20 - map.get(words[i]).getEng().length(); j++) {
                    System.out.print(" ");
                }
                System.out.print(printer.getWord(map.get(words[i]).getGer()));
                for (int j = 0;j < 20 - map.get(words[i]).getGer().length(); j++) {
                    System.out.print(" ");
                }
                System.out.println(printer.getWord(map.get(words[i]).getPol()));
            }
        }else{
            System.out.println("Error");
        }
    }
    private void display(boolean t) {
        List<Entry> entries = fileService.getAllWods();
        if (entries.isEmpty()) {
            System.out.println("There is no words yet");
            return;
        }
        String line;
        if(t) {
            System.out.println("Do you want to sort? (YES/NO)");
            line = scanner.nextLine();
        }else{
            line="NO";
        }
        if(line.equals("YES")){
            wordsSort();
        }else {
            System.out.println("All words:");
            System.out.println("    English             German              Polish");
            for (Entry entry : entries) {
                System.out.print(entry.getId());
                if (entry.getId() < 10) {
                    System.out.print("   ");
                } else if (entry.getId() < 100) {
                    System.out.print("  ");
                } else if (entry.getId() < 1000) {
                    System.out.print(" ");
                }
                System.out.print(printer.getWord(entry.getEng()));
                for (int i = 0; i < 20 - entry.getEng().length(); i++) {
                    System.out.print(" ");
                }
                System.out.print(printer.getWord(entry.getGer()));
                for (int i = 0; i < 20 - entry.getGer().length(); i++) {
                    System.out.print(" ");
                }
                System.out.println(printer.getWord(entry.getPol()));
            }
        }
    }
    private void wordSearch(){
        List<Entry> entries = fileService.getAllWods();
        System.out.println("Insert the word you want to find");
        String line = scanner.nextLine();
        for (Entry entry : entries){
            if(wordCheck(entry.getEng(),line)||wordCheck(entry.getGer(),line)||wordCheck(entry.getPol(),line)){
                System.out.print(printer.getWord(entry.getEng()));
                for (int i = 0; i < 20 - entry.getEng().length(); i++) {
                    System.out.print(" ");
                }
                System.out.print(printer.getWord(entry.getGer()));
                for (int i = 0; i < 20 - entry.getGer().length(); i++) {
                    System.out.print(" ");
                }
                System.out.println(printer.getWord(entry.getPol()));
            }
        }
    }

    private boolean wordCheck(String word, String wordp){

        for (int i=0;i<word.length()-wordp.length();i++){
            String subs = word.substring(i,i+wordp.length());
            if(subs.toLowerCase().equals(wordp.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    private void addNewWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the English word:");
        String englishWord = scanner.nextLine();
        System.out.println("Enter the German translation:");
        String germanWord = scanner.nextLine();
        System.out.println("Enter the Polish translation:");
        String polishWord = scanner.nextLine();
        fileService.addToDb(englishWord, germanWord, polishWord);
        System.out.println("New word added successfully!");
    }
    private void test() {
        {
            List<Entry> entries = fileService.getAllWods();
            Random rand = new Random();
            if(entries.isEmpty()){
                System.out.println("Dictionary is empty yet");
            }else {
                int val = rand.nextInt(entries.size()), l = rand.nextInt(3);
                String germanWord,polishWord,englishWord;
                switch (l) {
                    case 0 -> {
                        System.out.println("English:" + printer.getWord(entries.get(val).getEng()));
                        System.out.println("Enter the German translation:");
                        germanWord = scanner.nextLine();
                        System.out.println("Enter the Polish translation:");
                        polishWord = scanner.nextLine();
                        if (germanWord.equalsIgnoreCase(entries.get(val).getGer()) && polishWord.equalsIgnoreCase(entries.get(val).getPol())) {
                            System.out.println("You are correct");
                        } else if (germanWord.equalsIgnoreCase(entries.get(val).getGer())) {
                            System.out.println("Polish translation is incorrect, it should be:" + printer.getWord(entries.get(val).getPol()));
                        } else if (polishWord.equalsIgnoreCase(entries.get(val).getPol())) {
                            System.out.println("German translation is incorrect, it should be:" + printer.getWord(entries.get(val).getGer()));
                        } else {
                            System.out.println("Both translation are incorrect, " +
                                    "german translation is:" + printer.getWord(entries.get(val).getGer()) +
                                    ", polish translation is:" + printer.getWord(entries.get(val).getPol()));
                        }
                    }
                    case 1 -> {
                        System.out.println("German:" + printer.getWord(entries.get(val).getGer()));
                        System.out.println("Enter the English translation:");
                        englishWord = scanner.nextLine();
                        System.out.println("Enter the Polish translation:");
                        polishWord = scanner.nextLine();
                        if (englishWord.equalsIgnoreCase(entries.get(val).getEng()) && polishWord.equalsIgnoreCase(entries.get(val).getPol())) {
                            System.out.println("You are correct");
                        } else if (englishWord.equalsIgnoreCase(entries.get(val).getEng())) {
                            System.out.println("Polish translation is incorrect, it should be:" + printer.getWord(entries.get(val).getPol()));
                        } else if (polishWord.equalsIgnoreCase(entries.get(val).getPol())) {
                            System.out.println("English translation is incorrect, it should be:" + printer.getWord(entries.get(val).getEng()));
                        } else {
                            System.out.println("Both translation are incorrect, " +
                                    "english translation is:" + printer.getWord(entries.get(val).getEng()) +
                                    ", polish translation is:" + printer.getWord(entries.get(val).getPol()));
                        }
                    }
                    case 2 -> {
                        System.out.println("Polish:" + printer.getWord(entries.get(val).getPol()));
                        System.out.println("Enter the English translation:");
                        englishWord = scanner.nextLine();
                        System.out.println("Enter the German translation:");
                        germanWord = scanner.nextLine();
                        if (germanWord.equalsIgnoreCase(entries.get(val).getGer()) && englishWord.equalsIgnoreCase(entries.get(val).getEng())) {
                            System.out.println("You are correct");
                        } else if (germanWord.equalsIgnoreCase(entries.get(val).getGer())) {
                            System.out.println("English translation is incorrect, it should be:" + printer.getWord(entries.get(val).getEng()));
                        } else if (englishWord.equalsIgnoreCase(entries.get(val).getEng())) {
                            System.out.println("German translation is incorrect, it should be:" + printer.getWord(entries.get(val).getGer()));
                        } else {
                            System.out.println("Both translation are incorrect, " +
                                    "english translation is:" + printer.getWord(entries.get(val).getEng()) +
                                    ", german translation is:" + printer.getWord(entries.get(val).getGer()));
                        }
                    }
                }
            }
        }
    }
}