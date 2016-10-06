package pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Evgenia on 02.10.2016.
 */
public class MachineGraph {
    private Map<Integer, State> usualStArr = new HashMap<>();
    private Map<Integer, State> finalStArr = new HashMap<>();

    private String regEx = "[a-zA-Z]\\d+,.?=[a-zA-Z]\\d+";

    Pattern patternFirstName = Pattern.compile("[a-zA-Z]");
    Pattern patternSecondName = Pattern.compile("=[a-zA-Z]");
    Pattern patternNum = Pattern.compile("\\d+");
    Pattern patternChar = Pattern.compile(",.=");

    Matcher matcherFirstName;
    Matcher matcherSecondName;
    Matcher matcherNum;
    private Matcher matcherChar;

    public void readFile(String fileName){
        BufferedReader bufferedReader;
        String curLine;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
            while ((curLine = bufferedReader.readLine()) != null) {
                writeToArrays(curLine);
            }
        }catch (IOException ioe){
            ioe.toString();
        }


    }

    public void writeToArrays(String str){
        if(!str.matches(regEx))
            return;

        matcherFirstName = patternFirstName.matcher(str);
        matcherSecondName = patternSecondName.matcher(str);
        matcherNum = patternNum.matcher(str);
        matcherChar = patternChar.matcher(str);




        State currentState = new State();

        if(matcherFirstName.find()){

            currentState.setName(matcherFirstName.group());

            if(matcherNum.find(matcherFirstName.end())){

                int index = Integer.valueOf(matcherNum.group());
                currentState.setNumber(index);


                if(currentState.isFinal()){
                    if(finalStArr.containsKey(currentState.getNumber())){
                        currentState = finalStArr.get(currentState.getNumber());
                    }else{
                        finalStArr.put(currentState.getNumber(), currentState);
                    }
                }else{
                    if(usualStArr.containsKey(currentState.getNumber())){
                        currentState = usualStArr.get(currentState.getNumber());
                    }else{
                        usualStArr.put(currentState.getNumber(), currentState);
                    }
                }

                if(matcherChar.find()){
                    String find = matcherChar.group();
                    find = find.substring(1, find.length()-1);

                    Object arr[] = new Object[2];
                    arr[0] = find;



                    State nextState = new State();
                    if(matcherSecondName.find()) {
                        String secName = matcherSecondName.group();
                        secName = secName.substring(secName.length()-1);
                        nextState.setName(secName);
                        if(matcherNum.find(matcherSecondName.end())) {
                            index = Integer.valueOf(matcherNum.group());
                            nextState.setNumber(index);

                            arr[1] = nextState;
                            currentState.addArr(arr);

                            if (nextState.isFinal()) {
                                if (!finalStArr.containsKey(nextState.getNumber())) {
                                    finalStArr.put(nextState.getNumber(), nextState);
                                }
                            } else {
                                if (!usualStArr.containsKey(nextState.getNumber())) {
                                    usualStArr.put(nextState.getNumber(), nextState);
                                }
                            }
                        }
                    }
                }
            }
        }



    }

    public void printResult(){
        System.out.println("usual states");
        for(Map.Entry<Integer, State> st : usualStArr.entrySet()){
            st.getValue().printResult();
        }
        System.out.println("final states");
        for(Map.Entry<Integer, State> st : finalStArr.entrySet()){
            st.getValue().printResult();
        }
    }


    public Map<Integer, State> getUsualStArr() {
        return usualStArr;
    }

    public Map<Integer, State> getFinalStArr() {
        return finalStArr;
    }


    public void readWord(String word){
        char chars[] = word.toCharArray();
        int lines = chars.length + 3;
        int cols = usualStArr.size() + finalStArr.size() + 1;
        Object array[][] = new Object[lines][cols];

        initArray(array, chars);

        State curState = (State) array[0][1];

        for(int i = 2; i < array.length-1; i++){
            ArrayList<State> states = readSym((char)array[i][0], curState);
            if(states.isEmpty()){
                System.out.println("couldn't read word " + word);
                return;
            }else{
                int line = 0;
                for(int statesCount = 0; statesCount < states.size(); statesCount++){
                    for(int colsCount = 1; colsCount < cols; colsCount++){
                        if(array[line][colsCount].equals(states.get(statesCount))){
                            array[i][colsCount] = 1;
                            curState = (State) array[line][colsCount];
                            break;
                        }
                    }
                }

            }
        }
        if(curState.isFinal()) {
            System.out.println("had read word " + word);
        }else{
            System.out.println("had read word " + word + " but not final state");
        }
        printArr(array, cols);

    }

    protected ArrayList<State> readSym(char ch, State currState){
        ArrayList<State> states = new ArrayList<>();
        ArrayList<ArrayList> passages = currState.getPassages();
        for(int i = 0; i < passages.size(); i++){
            ArrayList arrayList = passages.get(i);
            if(Character.toString(ch).equals(arrayList.get(0))){
                states.addAll(arrayList.subList(1,arrayList.size()));
                return states;
            }
        }
        return states;
    }

    protected void initArray(Object[][] arr, char[] chars){
        int countCols = 1;
        int i = 0;
        for (Map.Entry<Integer, State> entry : usualStArr.entrySet()) {
            arr[i][countCols++] = entry.getValue();
        }
        for (Map.Entry<Integer, State> entry : finalStArr.entrySet()) {
            arr[i][countCols++] = entry.getValue();
        }

        int k = 0;
        int countLines = 2;
        for(int j = 0; j < chars.length; j++){
            arr[countLines++][k] = chars[j];
        }
        // the beginning state q0
        arr[1][0] = -1;
        arr[1][1] = 1;
        arr[0][0] = 0;
        arr[arr.length-1][0] = -2;
    }

    protected void printArr(Object[][] arr, int cols){
        System.out.println("array for word ");
        for(int i = 0; i < arr.length; i++){
            for(int k = 0; k < cols; k++){
                if(arr[i][k] == null) arr[i][k] = 00;
                System.out.print(arr[i][k] + "  ");
            }
            System.out.println();
        }
    }

}
