package pack;

/**
 * Created by evgen_001 on 06.10.2016.
 */
public class Word {
    private String word;
    private Object array[][];

    public Word(String word){
        this.word = word;
    }


    public void setWord(String word) {
        this.word = word;
    }

    public void setArray(Object[][] array) {
        this.array = array;
    }

    public String getWord() {
        return word;
    }

    public Object[][] getArray() {
        return array;
    }
}
