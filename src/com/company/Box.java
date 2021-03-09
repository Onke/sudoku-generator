package com.company;

import java.util.LinkedList;
import java.util.List;

public class Box {
    private final String id;
    private int fact;
    private List<Integer> possibilities;

    public Box(String id) {
        fact = 0;
        this.id = id;
        initialisePossibilities();
    }

    private void initialisePossibilities() {
        possibilities = new LinkedList<>();
        for (int i = 1; i < 10; i++)
            possibilities.add(i);
    }

    public int getFact() {
        return fact;
    }

    public void setFact(int fact) {
        this.fact = fact;
        possibilities.clear();
    }

    public String getId() {
        return id;
    }

    public List<Integer> getPossibilities() {
        return possibilities;
    }

    public void setPossibilities(List<Integer> possibilities) {
        this.possibilities = possibilities;
    }


}
