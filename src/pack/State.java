package pack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Created by Evgenia on 02.10.2016.
 */
public class State {
    private String name;
    private boolean final_s = false;
    private int number;
    private ArrayList<ArrayList> passages = new ArrayList<>();


    public void setName(String name){
        this.name = name;
        final_s = name.equals("f") ? true : false;
    }


    public boolean isFinal(){
        return final_s;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ArrayList> getPassages() {
        return passages;
    }

    public void addArr(Object[] arr){
        boolean found = false;
        for(int i = 0; i < passages.size(); i++){
            ArrayList arr1 = passages.get(i);
            if(arr1.get(0) == arr[0]){
                arr1.add(arr[1]);
                found = true;
                break;
            }
        }
        if(!found){
            ArrayList<Object> list = new ArrayList<>();
            list.add(arr[0]);
            list.add(arr[1]);
            passages.add(list);
        }

    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void printResult(){
        System.out.println( "name : " + this.name + this.number + " is final : " + this.final_s);

        for(int i = 0; i < this.passages.size(); i++){
            ArrayList arr = this.passages.get(i);
            System.out.print( " sym " + arr.get(0) + " --> ");
            for(int j = 1; j < arr.size(); j++){
                System.out.print(((State)arr.get(j)).getName() + ((State)arr.get(j)).getNumber() + " ");
            }
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object obj) {
        State st = (State) obj;
        if(st.name.equals(this.name) && st.final_s == this.final_s){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
