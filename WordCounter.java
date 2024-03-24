import java.io.*;
import java.util.regex.*;
import java.util.*;

public class WordCounter{

    public static int processText(StringBuffer sb, String s) throws InvalidStopwordException, TooSmallText {
        //create regular expressions
        Pattern regex = Pattern.compile("[0-9a-zA-Z']+");
        Matcher regexMatcher = regex.matcher(sb);

        //marks if stop word is found
        boolean stopWordFound = false;
        int numWords = 0;
        int numFinal = 0;

        while (regexMatcher.find()) {
            //if stopword is found, marks amount of words until stopwords
            if(s != null && regexMatcher.group().equals(s)) {
                stopWordFound = true;
                System.out.println("I just found the word: " + regexMatcher.group());
                numWords++;
                numFinal = numWords;
                continue;
            }
            //continue with rest of words
            System.out.println("I just found the word: " + regexMatcher.group());
            numWords++;
        }
        //checks exceptions
        if(numWords < 5) 
            throw new TooSmallText("Only found " + numWords + " words.");
        if(s != null && stopWordFound == false)
            throw new InvalidStopwordException("Couldn't find stopword: " + s);
        
        //if stopword exists, return number of words until then
        //else, return full amount of words in string
        if(stopWordFound == true)
            return numFinal;
        else
            return numWords;
    }
    

    public static StringBuffer processFile(String s) throws EmptyFileException, IOException{

        String line = new String();
        //tries to create file out of path given in String
        try {
            //line reads from the file and places into string
            BufferedReader reader = new BufferedReader(new FileReader(s));
            line = reader.readLine();
        }  catch(FileNotFoundException e) {
            //Asks to try again if file does not exist, retries with new file
            Scanner myObj = new Scanner(System.in);
            System.out.println("file could not be opened. Please try again: ");
            String file = myObj.nextLine();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            line = reader.readLine();
        }
        
        //throws EmptyFileException if file is empty
        if(line == null) {
            throw new EmptyFileException(s + " was empty");
        }

        //creates StringBuffer out of String to return
        StringBuffer sb = new StringBuffer(line);

        return sb;
    }

    public static void main(String[] args) throws Exception {

        String fileOrText = args[0];
        //Create Scanner to prompt user
        Scanner myObj = new Scanner(System.in);
        System.out.println("Please enter 1 to process a file, or 2 to process text: ");
        String choice = myObj.nextLine();
        //If answer not 1 or 2, prompts user to reenter
        while(!(choice.equals("1") || choice.equals("2"))) {
            System.out.println("Oops! Incorrect number inputted. Please try again: ");
            choice = myObj.nextLine();
        }
        //Sets stopword as null or as stopword inputted
        String stopWord = new String();
        if(args.length == 1) {
            stopWord = null;
        } else {
            stopWord = args[1];
        }
        StringBuffer sb = new StringBuffer(fileOrText);
        int wordLength = 0;
        //If choice is 1, process file inputted
        //catches if file is empty
        try {
            if(choice.equals("1")) {
                sb = processFile(fileOrText);
            }
        } catch(EmptyFileException e) {
            System.out.println(e);
            return;
        }
        //if choice is 2 or if file processed, process text
        try {
            wordLength = processText(sb, stopWord);
            System.out.println(wordLength);
        } catch(InvalidStopwordException e) {
            //if stopword is invalid, prompt user to re enter
            System.out.println(e);
            System.out.println("Re enter stopword to try again: ");
            stopWord = myObj.nextLine();
            wordLength = processText(sb, stopWord);
            System.out.println(wordLength);
        } catch(TooSmallText e) {
            System.out.println(e);    
        } 
        

    }
}