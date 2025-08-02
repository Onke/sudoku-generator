package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SudokuPuzzleGenerator {

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD,
        KOREAN
    }

    private final List<Box> solution;
    private final List<Box> puzzle;

    public SudokuPuzzleGenerator() {
        SudokuGenerator generator = new SudokuGenerator();
        generator.waveFunctionCollapse();
        solution = cloneGrid(generator.getGrid());
        puzzle = cloneGrid(generator.getGrid());
    }

    private List<Box> cloneGrid(List<Box> grid) {
        List<Box> clone = new ArrayList<>();
        for (Box b : grid) {
            Box newBox = new Box(b.getId());
            newBox.setFact(b.getFact());
            clone.add(newBox);
        }
        return clone;
    }

    public void createPuzzle(Difficulty difficulty) {
        int cellsToRemove;
        switch (difficulty) {
            case EASY:
                cellsToRemove = 30;
                break;
            case MEDIUM:
                cellsToRemove = 40;
                break;
            case HARD:
                cellsToRemove = 50;
                break;
            case KOREAN:
                cellsToRemove = 55;
                break;
            default:
                cellsToRemove = 30;
        }

        Random rand = new Random();
        int removed = 0;
        while (removed < cellsToRemove) {
            int idx = rand.nextInt(puzzle.size());
            Box box = puzzle.get(idx);
            if (box.getFact() != 0) {
                box.setFact(0);
                removed++;
            }
        }
    }

    public List<Box> getPuzzle() {
        return puzzle;
    }

    public List<Box> getSolution() {
        return solution;
    }

    public void printPuzzle() {
        printGrid(puzzle);
    }

    public void printSolution() {
        printGrid(solution);
    }

    private void printGrid(List<Box> grid) {
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
                int val = grid.get(pos).getFact();
                System.out.print(val == 0 ? "   " : " " + val + " ");
                pos++;
                if (j == 8) {
                    System.out.println(" |");
                }
            }
        }
        System.out.println("   +-----------+----------+----------+");
    }
}
