package com.company;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SudokuGridTest {
    Box box = new Box("D2II");
    private SudokuGrid grid = new SudokuGrid();

    @Test
    void getBox() {


        Box box1 = grid.getBox("D2II");
        if (box1 != null)
            assertEquals(box.getId(), box1.getId());
        else System.out.println("Box not found");
    }

    @Test
    void clear() {
        SudokuGrid grid = new SudokuGrid();
        grid.clearBoxValues(box);
        assertEquals(box.getPossibilities().size(), 0);
    }

    @Test
    void setBoxValues() {
        grid.setBoxValues(box, 5);
        assertEquals(box.getFact(), 5);
        List<Integer> list = new LinkedList<>();
        list.add(6);
        list.add(9);
        grid.setBoxValues(box, list);
        assertEquals(box.getPossibilities(), list);
    }

    @Test
    void isValid() {
        boolean isNotValid = grid.isValid(box, 0);
        assertFalse(isNotValid);
    }
}