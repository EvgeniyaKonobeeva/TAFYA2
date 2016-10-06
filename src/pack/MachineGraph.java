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
        int lines = chars.length + 2;
        int cols = usualStArr.size() + finalStArr.size();
        Object array[][] = new Object[lines][cols];
        for(int i = 0; i < lines; i++){
            for(int j = 0; j < usualStArr.size(); j++){
                array[i][j] = usualStArr.get(j);
            }
            for(i)
        }

        for(int i = 0; i< lines - 2; i++){
            if(readSym(chars[i], i) != null){
                continue;
            }else{
                System.out.println("COULDN'T READ WORD " + word);
                break;
            }
        }




    }

    protected State readSym(char ch, State currState, Object ar[][]){
        State state = usualStArr.get(currState.getNumber());
        ArrayList<ArrayList> passages = state.getPassages();

        for(int i = 0; i < passages.size(); i++){
            ArrayList pas = passages.get(i);
            if(Character.toString(ch).equals(pas.get(0))){

            }
        }
        return state;
    }

}
