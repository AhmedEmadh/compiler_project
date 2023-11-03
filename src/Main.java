import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

enum Type{SEMICOLON,IF,THEN,END,REPEAT,UNTIL,IDENTIFIER,ASSIGN,READ,WRITE,LESSTHAN,EQUAL,PLUS,MINUS,MULT,DIV,OPENBRACKET,CLOSEBRACKET,NUMBER};
class Token {
    String value;
    Type type;
    Token(){
        this.value = "";
        this.type = Type.SEMICOLON;
    }
    Token(String value,Type type){
        this.value = value;
        this.type = type;
    }
}
public class Main {
    /* there are the reserved words in the language */
    static String[] reserved_words = {"if","then","else","end","repeat","until","read","write"};
    /* there are the special symbols in the tiny language */
    static String[] special_symbols = {"+","-","*","/","=","<","(",")",";",/*":="*/};
    /* checks if the string is a reserved word or not */
    static Type ConvertStrToTokenType(String str) {
        if (str.equals(";")) {
            return Type.SEMICOLON;
        } else if (str.equals("if")) {
            return Type.IF;
        } else if (str.equals("then")) {
            return Type.THEN;
        } else if (str.equals("end")) {
            return Type.END;
        } else if (str.equals("repeat")) {
            return Type.REPEAT;
        } else if (str.equals("until")) {
            return Type.UNTIL;
        } else if (str.equals(":=")) {
            return Type.ASSIGN;
        } else if (str.equals("read")) {
            return Type.READ;
        } else if (str.equals("write")) {
            return Type.WRITE;
        } else if (str.equals("<")) {
            return Type.LESSTHAN;
        } else if (str.equals("=")) {
            return Type.EQUAL;
        } else if (str.equals("+")) {
            return Type.PLUS;
        } else if (str.equals("-")) {
            return Type.MINUS;
        } else if (str.equals("*")) {
            return Type.MULT;
        }else if (str.equals("/")) {
            return Type.DIV;
        }else if (str.equals("(")) {
            return Type.OPENBRACKET;
        }else if (str.equals(")")) {
            return Type.CLOSEBRACKET;
        }else if (is_number(str)) {
            return Type.NUMBER;
        }
        else if (is_identifier(str)){
            return Type.IDENTIFIER;
        }
        return Type.SEMICOLON;
    }
    static ArrayList<Token> ConvertToTokens(ArrayList<String> str_tokens){
        ArrayList<Token> tokens = new ArrayList<Token>();
        for(int i=0;i<str_tokens.size();i++){
            tokens.add(new Token(str_tokens.get(i),ConvertStrToTokenType(str_tokens.get(i))));
        }
        return tokens;
    }
    static boolean is_reserved_word(String s) {
        for (int i = 0; i < reserved_words.length; i++) {
            if (s.equals(reserved_words[i])) {
                return true;
            }
        }
        return false;
    }
    /* checks if the string is a number or not */
    static boolean is_number(String s){
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            // The string is not a valid number
            return false;
        }
    }
    /* checks if the string is a special symbol or not */
    static boolean is_special_Symbol(String s) {
        for (int i = 0; i < special_symbols.length; i++) {
            if (s.equals(special_symbols[i])) {
                return true;
            }
        }
        return false;
    }
    /* checks if the char is a special symbol or not */
    static boolean is_special_Symbol(char c) {
        for (int i = 0; i < special_symbols.length; i++) {
            if (c == special_symbols[i].charAt(0)) {
                return true;
            }
        }
        return false;
    }
    /* checks if the string is a valid identifier in the language */
    static boolean is_identifier(String s){
        if(!Character.isLetter(s.charAt(0))){
            return false;
        }
        for(int i=0;i<s.length();i++){
            /* if the character is not a digit or a letter */
            if((!Character.isLetter(s.charAt(i)))&&(!Character.isDigit(s.charAt(i)))){
                return false;
            }
        }
        return true;
    }
    /* checks if a char is a letter */
    static String TokenTypeToString(Type type){
        String result = "";
        switch (type){
            case SEMICOLON:
                result = "SEMICOLON";
                break;
            case IF:
                result = "IF";
                break;
            case THEN:
                result = "THEN";
                break;
            case END:
                result = "END";
            break;
            case REPEAT:
                result = "REPEAT";
            break;
            case UNTIL:
                result = "UNTIL";
            break;
            case IDENTIFIER:
                result = "IDENTIFIER";
                break;
            case ASSIGN:
                result = "ASSIGN";
            break;
            case READ:
                result = "READ";
            break;
            case WRITE:
                result = "WRITE";
            break;
            case LESSTHAN:
                result = "LESSTHAN";
            break;
            case EQUAL:
                result = "EQUAL";
            break;
            case PLUS:
                result = "PLUS";
            break;
            case MINUS:
                result = "MINUS";
            break;
            case MULT:
                result = "MULT";
            break;
            case DIV:
                result = "DIV";
            break;
            case OPENBRACKET:
                result = "OPENBRACKET";
            break;
            case CLOSEBRACKET:
                result = "CLOSEBRACKET";
            break;
            case NUMBER:
                result = "NUMBER";
            break;
        }
        return result;
    }
    static boolean is_letter(char c){
        return Character.isLetter(c);
    }
    /* checks if a character is a digit */
    static boolean is_digit(char c){
        return Character.isDigit(c);
    }
    /* checks if a character is a white space (space or tap or newline(Enter)) */
    static boolean is_white_space(char c){
        return ((c == ' ')||(c == '\t')||(c == '\n'));
    }
    /* converts file into a string and retrun it bypassing address of the file */
    static String getStringFromFile(String filePath){
        String fileName = filePath; // Replace with the path to your file
        String line;
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContent = content.toString();
        return fileContent;
    }
    /* prints the dynamic array's elements (array list)
     * note: the dynamic array is called ArrayList<Type>
     *  */
    static void printStringArrayList(ArrayList<String> strings){
        System.out.print("{");
        for (int i=0;i<strings.size();i++){
            System.out.print(strings.get(i));
            if(i != strings.size()-1){
                System.out.print(",");
            }
        }
        System.out.println("}");
    }
    static void printTokenArrayList(ArrayList<Token> tokens){
        System.out.println("Tokens are:");
        for (int i=0;i<tokens.size();i++){
            System.out.println(tokens.get(i).value + ","+ TokenTypeToString(tokens.get(i).type));
        }
    }
    /* it concatenates all characters in a char queue into one string and return it */
    static String queueToString(Queue<Character> queue){
        String result = new String();
        while(!queue.isEmpty()){
            result = result + queue.poll();
        }
        return result;
    }
    static boolean is_number(char c){
        return Character.isDigit(c);
    }
    /* it is a variable used to store the character pointed in the current iteration */
    static char charAt;
    /* CreateTokens creates a string array tokens of the text and it is the most important function in the scanner */
    public static ArrayList<String> CreateTokens(String text){
        /* define dynamic array of strings that will be returned at the end of the function after being completed */
        ArrayList<String> tokens = new ArrayList<>();
        /* create a queue that will store characters in the queue,
         * before going to Done state to store them in the tokens dynamic array to be returned
         * */
        Queue<Character> aQueue = new LinkedList<>();
        /*define states of the FSM that will be used
         * start: will be the start point of every token detection
         * innum: the program will go there if it detects a number
         * inid: the program will go there if it detects a letter to check if it is identifier or not
         * inassign: the program will go there if it detects ':' in assignment operator
         * done: the program will go there when token detected completely to check if it is a valid token or not
         * insymbol: the program will go there when a special symbol is detected
         * error: the program will go ther when the program in Done state checking the validation of the token
         * and found that it is not a valid token
         *  */
        enum State{start,innum,inid,inassign,incomment,done,insymbol,error};
        /* initializing state variable with start */
        State state = State.start;
        for(int i=0;i<text.length();i++){
            switch (state) {
                case start:
                    charAt = text.charAt(i);
                    if (is_white_space(charAt)) {
                        state = State.start;
                    } else if (is_digit(charAt)) {
                        aQueue.add(charAt);
                        state = State.innum;
                    } else if (is_letter(charAt)) {
                        aQueue.add(charAt);
                        state = State.inid;
                    } else if (is_special_Symbol(charAt)) {
                        state = State.insymbol;
                    } else if (charAt == ':') {
                        aQueue.add(charAt);
                        state = State.inassign;
                    } else if (charAt == '{') {
                        state = State.incomment;
                    } else {
                        state = State.done;
                    }
                    break;
                case innum:
                    charAt = text.charAt(i);
                    if (is_digit(charAt)) {
                        aQueue.add(charAt);
                        state = State.innum;
                    } else {
                        state = State.done;
                    }
                    break;
                case inid:
                    charAt = text.charAt(i);
                    if (is_letter(charAt)||is_number(charAt)) {
                        aQueue.add(charAt);
                        state = State.inid;
                    } else {
                        state = State.done;
                    }
                    break;
                case inassign:
                    charAt = text.charAt(i);
                    if (charAt == '=') {
                        aQueue.add(charAt);
                        state = State.done;
                    } else {
                        state = State.done;
                    }
                    break;
                case insymbol:
                    i--;
                    charAt = text.charAt(i);
                    aQueue.add(charAt);
                    state = State.done;
                    break;
                case incomment:
                    charAt = text.charAt(i);
                    if (charAt == '}') {
                        state = State.start;
                    }
                    break;
                case done:
                    i--;
                    charAt = text.charAt(i);
                    String token = queueToString(aQueue);
                    state = State.start;
                    if (is_reserved_word(token)) {
                        tokens.add(token);
                    } else if (is_special_Symbol(token)) {
                        tokens.add(token);
                    } else if (is_number(token)) {
                        tokens.add(token);
                    } else if (is_identifier(token)) {
                        tokens.add(token);
                    } else if (charAt == '='){
                        tokens.add(token);
                    }else{
                        state = State.error;
                    }
                    if(charAt == '{'){
                        state = State.incomment;
                    }
                    if (charAt == ';'){
                        tokens.add(";");
                    }
                    break;
                case error:
                    System.out.println("Error in the text");
                    break;
                default:
                    charAt = text.charAt(i);
                    break;
            }
        }
        return tokens;
    }
    /* this function converts reference path into absulute path to use it in getStringFromFile function */
    public static String convertToAbsolutePath(String referencePath) {
        // Get the absolute path of the current working directory
        Path currentPath = Paths.get("").toAbsolutePath();

        // Create a Path object for the reference path
        Path referencePathObject = Paths.get(referencePath);

        // Resolve the absolute path using the reference path
        Path absolutePath = currentPath.resolve(referencePathObject);

        return absolutePath.toString();
    }
    /* it is the main function where the program starts */
    public static void main(String[] args) {
        /*convert the reference path of the file that contains language to absolute path that contains that file*/
        String path = convertToAbsolutePath("src/file.txt");
        /* get text from file as a string and put it in text object*/
        String text = getStringFromFile(path);
        /* get tokens from string as dynamic array of strings*/
        ArrayList<String> str_tokens = CreateTokens(text);
        /*get tokens as Token type that contains the value and the Type*/
        ArrayList<Token> tokens = ConvertToTokens(str_tokens);
        /* print the dynamic array of token types as array */
        printTokenArrayList(tokens);
        /* wait for user to press enter to see the output and finish the program */
        new Scanner(System.in).nextLine();
    }
}