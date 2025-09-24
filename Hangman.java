import java.util.Scanner;
public class Hangman {
    public static String[] words = {"ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
    "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
    "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
    "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
    "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon", 
    "python", "rabbit", "ram", "rat", "raven","rhino", "salmon", "seal",
    "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
    "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
    "wombat", "zebra"};

    public static String[] gallows = {
    " +---+\n" +
    " |   |\n" +
    "     |\n" +
    "     |\n" +     //if the user didn't miss any guesses
    "     |\n" +
    "     |\n" +
    "=========\n",
    
    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "     |\n" +      // if the user missed one guess
    "     |\n" +
    "     |\n" +
    "=========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    " |   |\n" +     // if the user missed two guesses
    "     |\n" +
    "     |\n" +
    "=========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|   |\n" +    // if the user missed three guesses
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +    // if the user missed four guesses
    " O   |\n" +
    "/|\\  |\n" + //if you were wondering, the only way to print '\' is with a trailing escape character, which also happens to be '\'
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" +      // if the user missed five guesses
    "/    |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +       // if the user missed six guesses
    "/|\\  |\n" + 
    "/ \\  |\n" +
    "     |\n" +
    " =========\n"};

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
    
        String chosenWord = getRandomWord();    // het te raden woord
        String guessedLetters = "";             // string voor de geraden letters
        int misses = 0;                         // aantal missers

        System.out.println("Welcome to Hangman!");
        System.out.println("Try to guess the word before you run out of guesses.");
        System.out.println("You have 6 incorrect guesses before you lose.");
        System.out.print("Enter any characters to guess: ");
    

        
        while (misses < 6) {
            String guess = scan.nextLine();
            char guessedChar = guess.charAt(0);  // neem alleen de eerste letter

            if(guess.length() > 1){                            // Als meer dan 1 letter input dan teruggeven: 
                System.out.print("Please type one letter at a time: ");
                continue;                                    // skip deze beurt, niet verder gaan
            }

            if(guessedLetters.indexOf(guessedChar)>= 0){                            // controleer in guessedLetters of de ingevoerde letter al eerder hierin voorkomt, indien wel geeft .indexOf() de positie weer van deze letter dus geeft altijd iets van 0 of groter als positie weer. 
                System.out.println("You already guessed that letter. Try a new one!");
                continue;                              // skip deze beurt, niet verder gaan
            }
        
            guessedLetters += guessedChar;             // voeg de letter toe aan guessedLetters

            String wordProgress = printWord(chosenWord, guessedLetters); // toon het woord met geraden letters
            boolean correct = wordProgress.contains(String.valueOf(guessedChar)); // controleer of de laatste gok correct was
            if (!correct){                                              // als de laatste gok verkeerd was
                 misses++;                                              // verhoog het aantal missers
            }
                printGallows(misses);                                 // toon de gallows na elke fout
                System.out.println(wordProgress+"\n");          // Toon de woord
                System.out.println("Misses: "+ misses+"\n");                             //Toon de aantal misses
                System.out.println("Guessed letters: "+ guessedLetters+"\n");            //Toon de geraden letters
                System.out.print("Userinput: ");
            if (chosenWord.equals(wordProgress.replace(" ", ""))) {       // Checken of de speler gewonnen heeft
                System.out.println("The word was: "+chosenWord+"\n");
                System.out.println(" ");
                System.out.println("Congratulations! You guessed the word!");
                break;
            }else if (misses == 6) {
                System.out.println("Game over! The word was: " + chosenWord);
            }
        }    
        scan.close();
        
    }
    public static String getRandomWord() { // random nummer genereren om woord te kiezen uit de lijst
        int randomIndex = (int) (Math.random() * words.length);
        return words[randomIndex];
    }

    public static String printWord(String chosenWord, String guessedLetters){ //methode om het gekozn woord te tonen met geraden letters en aangeeft of gok correct is
       char[] charChosenWord = chosenWord.toCharArray();  // zet het gekozen woord om naar een array van characters
       StringBuilder progress = new StringBuilder();      // StringBuilder om de voortgang op te bouwen
       boolean found = false;                             //boolean om bij te houden of de laatste gegokte letter correct was

       for(char c : charChosenWord){                      // Loop door elk karakter van het gekozen woord
        if (guessedLetters.indexOf(c) >= 0 ){             // Als de letter al is geraden (komt voor in guessedLetters)
            progress.append(c).append("");            // Voeg de letter en een spatie toe aan de StringBuilder       
           // System.out.print(c +" ");                     // Toon de letter (met spatie erachter)
            if(guessedLetters.charAt(guessedLetters.length()-1) == c){      // Als de laatst ingevoerde letter gelijk is aan deze letter
                found = true;                             // Zet found op true (de laatste gok was correct)
            } 
        } else {
            progress.append("_ ");               // Als de letter nog niet is geraden, voeg underscore toe aan StringBuilder progress
           // System.out.print("_ ");               // Als de letter nog niet is geraden, toon een underscore
            
       }
    }
    return progress.toString();                                         // Geeft de voortgang van het woord terug
    }
    public static void printGallows(int misses){
        switch (misses) {
            case 0:
            System.out.println(gallows[0]);
                break;
            case 1:
            System.out.println(gallows[1]);
                break;
            case 2:
            System.out.println(gallows[2]);
                break;
            case 3:
            System.out.println(gallows[3]);
                break;
            case 4:
            System.out.println(gallows[4]);
                break;
            case 5:
            System.out.println(gallows[5]);
                break;
            case 6:
            System.out.println(gallows[6]);
                break;
            default:
                System.out.println("The man has been hanged... Game over!");
        }
    }
        
    
}





