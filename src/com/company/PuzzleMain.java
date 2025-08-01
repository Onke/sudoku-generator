package com.company;

public class PuzzleMain {
    public static void main(String[] args) {
        for (SudokuPuzzleGenerator.Difficulty diff : SudokuPuzzleGenerator.Difficulty.values()) {
            SudokuPuzzleGenerator puzzleGenerator = new SudokuPuzzleGenerator();
            puzzleGenerator.createPuzzle(diff);
            System.out.println("\n" + diff + " Puzzle:");
            puzzleGenerator.printPuzzle();
        }
    }
}
