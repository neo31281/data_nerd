import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


public class Hangman
{
	//Initialize instance variables
	private int numLetters, numIncorrectGuesses, numLettersGuessedCorrectly;
	private char[] selectedWordArray;
	private boolean[] guessedLetter;
	private ArrayList<Character> lettersGuessed = new ArrayList<Character>();
	private static int wins, losses, gamesCounter;

	public static void main(String[] args)
	{
		//Initialize local variables
		Scanner sc = new Scanner(System.in);
		String continueGame = "";
		String word;
		//do while loop to continue game play
		do{
			Hangman game = new Hangman();
			String[] wordArray = game.getWordsFromFile("words.txt");
			if(wordArray.length > 0){
				word = game.getRandomWord(wordArray);
				game.playGame(word);
				System.out.println("Play Again? (y/n)");
				continueGame = sc.nextLine();
			}
		}while(continueGame.equalsIgnoreCase("y"));
		
	}
	//Game play method
	public void playGame(String word)
	{
		//initialize local variables
		Scanner input = new Scanner(System.in);
		char usersGuess;
		selectedWordArray = word.toCharArray();
		numLetters = word.length();
		guessedLetter = new boolean[numLetters];
		Arrays.fill(guessedLetter, false);

		//for loop to count the 7 guesses
		for(int i = 7; i >= 1; i--){
			//if statment stop loop if word has been guessed
			if(!isWordComplete(guessedLetter)){
				boolean moveOn = false;
				boolean inputGood = true;
				//do-while to loop back for wrong entries and so on
				do{
					//for loop prints out word on screen as guesses go on
					for(int j = 0; j < guessedLetter.length; j++){
						if(guessedLetter[j] == false){
							System.out.print("_ ");
						}else{
							System.out.print(selectedWordArray[j] + " ");
						}
					}
					System.out.printf("\nYou have %d guesses remaining\n", i);
					if(i < 7){
						System.out.println("You have guessed" + lettersGuessed);
					}
					System.out.print("Guess a letter: ");
					String userInput = input.nextLine();

					//exception handeling for more than one char or not a char
					try{
						if(userInput.length() > 1){
							throw new TooManyException();
						}
						if(!userInput.matches("[a-z]+")){
							throw new NotCharException();
						}
						inputGood = true;

					}
					catch(TooManyException e){
						System.out.println("Guesses must be one character only.\n");
						inputGood = false;
						moveOn = true;
					}
					catch(NotCharException e){
						System.out.println("Improper guess. Guesses must be characters only.\n");
						inputGood = false;
						moveOn = true;
					}
					//if statement to continue if use input is good
					if(inputGood){
						usersGuess = userInput.charAt(0);
						usersGuess = Character.toLowerCase(usersGuess);
						//checks if letter has already been guessed.
						if(hasLetterBeenUsed(usersGuess)){
							System.out.println("You've already guessed that letter!");
							moveOn = true;
						}else{
							for(int k = 0; k < selectedWordArray.length; k++){
								if(usersGuess == selectedWordArray[k]){
									guessedLetter[k] = true;
									numLettersGuessedCorrectly++;
								}else{
									numIncorrectGuesses++;
								}
							}
							lettersGuessed.add(usersGuess);
							moveOn = false;
						}
					}
				}while(moveOn);
			}
		}
		//Outputs win or lose and total
		if(isWordComplete(guessedLetter)){
			System.out.println("Congratulations! You guessed it!");
			wins++;
		}else{
			System.out.println("Sorry, you did not guess it. The word was " + word);
			losses++;
		}

		gamesCounter++;
		System.out.printf("Stats: %d Wins and %d Losses\n", getWins(), getLosses());
		System.out.printf("You have won %.0f%% of the time.\n", getWinPercentage());
	}
	//This method gets the number of words from the file
    public int getNumberOfWordsInFile(String fileName)
    {
        int wordCount = 0;
        File file = new File("words.txt");
        try{
        	Scanner sc = new Scanner(new FileInputStream(file));
        	int count = 0;
	        while (sc.hasNext()) 
	        {
	            sc.next();
	            wordCount++;
	        }
        }
        catch(FileNotFoundException e){
        }
        
        
        return wordCount;
    }
    //This method gets the words from the file
    public String[] getWordsFromFile(String fileName)
    {
        int wordCount = getNumberOfWordsInFile(fileName);
        String[] wordArray = new String[wordCount];
        File file = new File(fileName);
        try{
        	Scanner sc = new Scanner(new FileInputStream(file));
        	int i = 0;
	        while (sc.hasNext()) 
	        {
	            wordArray[i] = sc.next();
	            i++;
	        }
	        
	        sc.close();
        }
        catch(FileNotFoundException e){
        	System.out.println("File Not Found");
        }
     
        return wordArray;
    }
    //This method gets a random word
    public String getRandomWord(String[] wordArray)
    {
   
        Random rd = new Random();
        int count = getNumberOfWordsInFile("words.txt");
        int randNo = rd.nextInt(count - 1);
        String randomWord = wordArray[randNo];

        return randomWord;
    }
    //This method checks if the user has guessed the word
    public boolean isWordComplete(boolean[] array) 
    {
        boolean isComplete = true;
        
        for (int i = 0; i < array.length; i++) 
        {
            if (array[i] == false) 
            {
                isComplete = false;
                break;
            }
        }
        return isComplete;
    }
    //This method checks if the letter has been guess already
    public boolean hasLetterBeenUsed(char c)
    {
    	boolean letterUsed = false;
    	for (int i = 0; i < lettersGuessed.size(); i++)
    	{
    		if(lettersGuessed.indexOf(c) >= 0)
    		{
    			letterUsed = true;
    		}
    	}
    	return letterUsed;
    }

    //These methods calculate
	public static int getWins(){
		return wins;
	}
	public static int getLosses(){
		return losses;
	}
	public static double getWinPercentage(){
		double percentage = ((double)wins/gamesCounter) * 100;
		return percentage;
	}
}
