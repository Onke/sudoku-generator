package com.company;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SudokuGenerator {
    private List<Box> grid;
    private SudokuGrid sudokuGrid;
    private boolean wasSuccessful;

    public SudokuGenerator() {
        wasSuccessful = true;
        sudokuGrid = new SudokuGrid();
        grid = sudokuGrid.getGrid();
    }

    public List<Box> getGrid() {
        return grid;
    }

    public void waveFunctionCollapse() {

        while (!isComplete()) {
            Box minBox = getMinPossibilities(grid);
            if (minBox == null) {
                wasSuccessful = false;
                break;
            }
            int randomPossibility = getRandomPossibility(minBox);
            if (randomPossibility == 0) {
                wasSuccessful = false;
                break;
            }
            boolean isValidFact = sudokuGrid.isValid(minBox, randomPossibility);
            if (isValidFact) {
                sudokuGrid.setBoxValues(minBox, randomPossibility);
                removeFact(minBox, randomPossibility);
            } else
                wasSuccessful = false;
        }

        if (!wasSuccessful) {
            wasSuccessful = true;
            sudokuGrid = new SudokuGrid();
            grid = sudokuGrid.getGrid();
            waveFunctionCollapse();
        }

    }


    /**
     * finds the box with least number of possibilities remaining
     *
     * @return box with the least number of possibilities remaining
     */
    public Box getMinPossibilities(List<Box> grid) {
        BoxComparator comparator = new BoxComparator();
        int min = Integer.MAX_VALUE;
        for (Box curBox : grid) {
            if (curBox.getPossibilities().size() < min && curBox.getPossibilities().size() > 0)
                min = curBox.getPossibilities().size();
        }
        int finalMin = min;
        Stream<Box> stream = grid.stream()
                .filter(box -> box.getPossibilities().size() == finalMin); // filter by column
        ArrayList<Box> filteredList = (ArrayList<Box>) stream.collect(Collectors.toList());
        Collections.shuffle(filteredList);
        if (filteredList.size() > 0)
            return filteredList.get(0);
        return null;
    }

    /**
     * Randomly pick one possibility from the box
     *
     * @param box sudoku box
     * @return random possibility
     */
    public int getRandomPossibility(Box box) {
        int randInt = new Random().nextInt(box.getPossibilities().size());
        if (box.getPossibilities().size() > 0)
            return box.getPossibilities().get(randInt);
        return 0;
    }

    /**
     * Remove fact from all boxes in the same row, column, and block
     *
     * @param box  sudoku box
     * @param fact new fact of box
     */
    public void removeFact(Box box, int fact) {
        String boxID = box.getId();

        //Remove from Column
        Stream<Box> stream = grid.stream()
                .filter(box1 -> box1.getId().startsWith(String.valueOf(boxID.charAt(0)))); // filter by column
        ArrayList<Box> filteredList = (ArrayList<Box>) stream.collect(Collectors.toList());
        for (Box curBox : filteredList) {
            curBox.getPossibilities().remove((Integer) fact);
        }

        //Remove from Row
        stream = grid.stream()
                .filter(box1 -> box1.getId().contains(String.valueOf(boxID.charAt(1)))); // filter by row
        filteredList = (ArrayList<Box>) stream.collect(Collectors.toList());
        for (Box curBox : filteredList) {
            curBox.getPossibilities().remove((Integer) fact);
        }

        //Remove from Block
        stream = grid.stream()
                .filter(box1 -> box1.getId().endsWith(boxID.substring(2)))   //filter by block - but some blocks end the same like VII and II
                .filter(box1 -> box1.getId().length() == boxID.length()); //filter by length of roman numeral
        filteredList = (ArrayList<Box>) stream.collect(Collectors.toList());
        for (Box curBox : filteredList) {
            curBox.getPossibilities().remove((Integer) fact);
        }

    }

    /**
     * Check if the all boxes contain facts
     *
     * @return true if sudoku grid has been completed
     */
    public boolean isComplete() {
        for (Box box : grid) {
            if (box.getFact() == 0)
                return false;
        }
        return true;
    }

    /**
     * Displays the generated tree
     */
    public void printSudoku() {

        if (!wasSuccessful)
            return;
        System.out.println("      A  B  C    D  E  F    G  H  I");
        System.out.println("   +-----------+----------+----------+");
        int pos = 0;
        int k = 0;

        for (int i = 1; i < 10; i++) {

            if ((i != 4) && (i != 7) && (k >= 2))
                k -= 2;
            else if (i == 4) {
                k = 3;
                System.out.println("   +-----------+----------+----------+");
            } else if (i == 7) {
                k = 6;
                System.out.println("   +-----------+----------+----------+");
            }
            System.out.print(i + "  | ");
            for (int j = 0; j < 9; j++) {
                if (j == 3 || j == 6) {
                    k++;
                    System.out.print(" |");
                }


                System.out.print(" " + grid.get(pos).getFact() + " ");
                pos++;
                if (j == 8) {
                    System.out.println(" |");
                }

            }
        }
        System.out.println("   +-----------+----------+----------+");
    }


}
