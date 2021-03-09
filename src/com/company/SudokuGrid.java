package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SudokuGrid {

    private List<Box> grid;

    public SudokuGrid() {
        grid = new LinkedList();
        initialiseGrid();
    }

    public List<Box> getGrid() {
        return grid;
    }

    /**
     * Initialises the grid
     * <p>
     * * ID of a  box made of a letter, number, Roman numeral e.g A9II
     * letter - row
     * number - column
     * Roman numeral - block
     */
    private void initialiseGrid() {
        String[] columnIds = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
        String[] blockIds = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        int k = 0;
        for (int i = 1; i < 10; i++) {
            if ((i != 4) && (i != 7) && (k >= 2))
                k -= 2;
            else if (i == 4)
                k = 3;
            else if (i == 7)
                k = 6;
            for (int j = 0; j < 9; j++) {
                if (j == 3 || j == 6)
                    k++;
                grid.add(new Box(columnIds[j] + i + blockIds[k]));
            }
        }
    }

    /**
     * Search a box by ID and return it or else return null
     *
     * @param id the ID of a box
     * @return a Box or null if non is found
     */
    public Box getBox(String id) {
        Stream<Box> filteredStream = grid.stream()
                .filter(box -> box.getId().startsWith(String.valueOf(id.charAt(0)))) // filter by column
                .filter(box -> box.getId().contains(String.valueOf(id.charAt(1)))); // filter by row

        ArrayList<Box> filteredBox = (ArrayList<Box>) filteredStream.collect(Collectors.toList());
        if (filteredBox.size() != 0)
            return filteredBox.remove(0);
        return null;
    }

    /**
     * Clears all possibilities and sets fact to 0;
     */
    public void clearBoxValues(Box box) {
        box.setFact(0); // setFact() also clears the possibilities
    }

    /**
     * Sets the fact of the given box
     *
     * @param box     Box that we want to change the values of
     * @param newFact the new fact
     */
    public void setBoxValues(Box box, int newFact) {
        boolean isValidFact = isValid(box, newFact);
        if (isValidFact)
            box.setFact(newFact);
        else {
            System.out.println("The number " + newFact + " is invalid in box - " + box.getId().substring(0, 2) + "  :(");
        }
    }

    /**
     * Sets the possibilities of the given box
     *
     * @param box              Box that we want to change the values of
     * @param newPossibilities the new possibilities
     */
    public void setBoxValues(Box box, List<Integer> newPossibilities) {
        box.setPossibilities(newPossibilities);
    }

    /**
     * Checks if the fact of the given box is valid
     *
     * @param box     Box that we want to change the values of
     * @param newFact the new fact
     * @return true if fact is valid else false
     */
    public boolean isValid(Box box, int newFact) {
        if (newFact <= 0 || newFact > 9)
            return false;
        String boxID = box.getId();

        //Validating for Column
        Stream<Box> stream = grid.stream()
                .filter(box1 -> box1.getId().startsWith(String.valueOf(boxID.charAt(0)))); // filter by column
        ArrayList<Box> filteredList = (ArrayList<Box>) stream.collect(Collectors.toList());
        for (Box curBox : filteredList) {
            if (curBox.getFact() == newFact)
                return false;

        }

        //Validating for Row
        stream = grid.stream()
                .filter(box1 -> box1.getId().contains(String.valueOf(boxID.charAt(1)))); // filter by row
        filteredList = (ArrayList<Box>) stream.collect(Collectors.toList());
        for (Box curBox : filteredList) {
            if (curBox.getFact() == newFact)
                return false;
        }

        //Validating for Block
        stream = grid.stream()
                .filter(box1 -> box1.getId().endsWith(boxID.substring(2)))   //filter by block - but some blocks end the same like VII and II
                .filter(box1 -> box1.getId().length() == boxID.length()); //filter by length of roman numeral
        filteredList = (ArrayList<Box>) stream.collect(Collectors.toList());
        for (Box curBox : filteredList) {
            if (curBox.getFact() == newFact)
                return false;
        }

        return true;
    }

}
