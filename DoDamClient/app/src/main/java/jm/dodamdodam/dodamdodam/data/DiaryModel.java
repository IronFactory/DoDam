package jm.dodamdodam.dodamdodam.data;

import java.io.Serializable;

/**
 * Created by IronFactory on 15. 11. 29..
 */
public class DiaryModel implements Serializable {
    private String content;
    private int feel;
    private long date;
    private String word;


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFeel() {
        return feel;
    }

    public void setFeel(int feel) {
        this.feel = feel;
    }
}
