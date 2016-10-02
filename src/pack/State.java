package pack;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Evgenia on 02.10.2016.
 */
public class State {
    public String name;
    public boolean final_s = false;
    public ArrayList<ArrayList> passages = new ArrayList<>();

    public void setName(String name){
        this.name = name;
        final_s = name.equals("f") ? true : false;
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
