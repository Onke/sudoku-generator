package com.company;

import java.util.Comparator;

public class BoxComparator implements Comparator<Box> {
    @Override
    public int compare(Box o1, Box o2) {
        return o1.getPossibilities().size() - o2.getPossibilities().size();
    }
}
