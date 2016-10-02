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
        Pattern patternName = Pattern.compile("[a-zA-Z]");
        Pattern patternNum = Pattern.compile("\\d+");
        Pattern patternChar = Pattern.compile(",.?=");

        Matcher matcherName = patternName.matcher(str);
        Matcher matcherNum = patternNum.matcher(str);
        Matcher matcherChar = patternChar.matcher(str);

        State currentState = new State();

        if(matcherName.find()){

            currentState.setName(matcherName.group(0));

            if(matcherNum.find()){

                int index = Integer.valueOf(matcherNum.group(0));
                addState(currentState, index);

                if(matcherChar.find()){
                    String find = matcherChar.group();
                    find = find.substring(1, find.length()-1);

                    ArrayList<Object> arr = new ArrayList<>();
                    arr.add(0, find);

                    if(matcherName.find()){
                        int countGr = matcherName.groupCount();
                        State nextState = new State();
                        nextState.setName(matcherName.group(countGr - 1));

                        if(matcherNum.find()){
                            countGr = matcherNum.groupCount();
                            index = Integer.valueOf(matcherNum.group(countGr-1));
                            addState(nextState, index);
                        }
                    }
                }
            }
        }



    }

    private void addState(State state, int index){
        if(!statesArr.contains(state)){
            statesArr.add(index, state);
        }
    }

}
