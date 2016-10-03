package pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Evgenia on 02.10.2016.
 */
public class FileReading {
    private ArrayList<State> statesArr = new ArrayList<>();
    private String regEx = "[a-zA-Z]\\d+,.?=[a-zA-Z]\\d+";

    Pattern patternName = Pattern.compile("[a-zA-Z]");
    Pattern patternNum = Pattern.compile("\\d+");
    Pattern patternChar = Pattern.compile(",.=");

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

        Matcher matcherName = patternName.matcher(str);
        Matcher matcherNum = patternNum.matcher(str);
        Matcher matcherChar = patternChar.matcher(str);

        State currentState = new State();

        if(matcherName.find()){

            if(matcherNum.find()){

                int index = Integer.valueOf(matcherNum.group(0));
                if(index == 0){
                    statesArr.add(index, currentState);
                    currentState.setName(matcherName.group(0));
                }else {
                    if (statesArr.get(index) == null) {
                        statesArr.add(index, currentState);
                        currentState.setName(matcherName.group(0));
                    } else {
                        currentState = statesArr.get(index);
                    }
                }

                if(matcherChar.find()){
                    String find = matcherChar.group(0);
                    find = find.substring(1, find.length()-2);

                    ArrayList<Object> arr = new ArrayList<>();
                    arr.add(0, find);

                    if(matcherNum.find()){
                        int countGr = matcherNum.groupCount();
                        index = Integer.valueOf(matcherNum.group(countGr-1));
                        arr.add(index);
                    }
                    currentState.addArr(arr);
                }
            }
        }



    }

    private void addState(State state, int index){
        if(statesArr.get(index) == null){
            statesArr.add(index, state);
        }
    }

    public void printResult(){
        for(State st : statesArr){
            st.printResult();
        }
    }

}
