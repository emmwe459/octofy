package com.example.octofy;

/**
 * Created by linneanabo on 2014-12-18.
 */
public class Tag {

    private String word;
    private int weight;

    public Tag (String word, int weight) {
        this.word = word;
        this.weight = weight;
    }

    public void setWord (String word) {
        this.word = word;
    }

    public void setWeight (int weight) {
        this.weight = weight;
    }

    public String getWord() {
        return word;
    }

    public int getWeight() {
        return weight;
    }
}
