package com.company;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SudokuGeneratorTest {
    //private SudokuGenerator generator = new SudokuGenerator();
    private SudokuGrid sudokuGrid = new SudokuGrid();
    private List<Box> grid = sudokuGrid.getGrid();

    @Test
    void waveFunctionCollapse() {

        grid = new SudokuGenerator().getGrid();
        for (Box box : grid) {
            if (sudokuGrid.isValid(box, box.getFact()))
                System.out.println("Box - " + box.getId().substring(0, 2) + " is valid");
            else {
                System.out.println("Box - " + box.getId().substring(0, 2) + " is invalid");
                break;
            }
        }
    }

    @Test
    void getMinPossibilities() {
        BoxComparator comparator = new BoxComparator();
        Box box1 = new Box("A1");
        Box box2 = new Box("A2");
        Box box3 = new Box("B1");
        Box box4 = new Box("B2");

        LinkedList<Integer> list1 = new LinkedList<>();
        LinkedList<Integer> list2 = new LinkedList<>();
        LinkedList<Integer> list3 = new LinkedList<>();
        LinkedList<Integer> list4 = new LinkedList<>();

        list1.add(1);

        list2.add(1);
        list2.add(2);
        list2.add(3);

        list3.add(1);
        list3.add(2);
        list3.add(3);
        list3.add(4);
        list3.add(5);
        list3.add(6);

        list4.add(1);
        list4.add(2);
        list4.add(3);
        list4.add(4);


        box1.setPossibilities(list1);
        box2.setPossibilities(list2);
        box3.setPossibilities(list3);
        box4.setPossibilities(list4);

        List<Box> grid = new LinkedList<>();
        grid.add(box1);
        grid.add(box2);
        grid.add(box3);
        grid.add(box4);

        grid.sort(comparator);
        SudokuGenerator sudokuGenerator = new SudokuGenerator();
        assertEquals(sudokuGenerator.getMinPossibilities(grid), box1);

    }
}