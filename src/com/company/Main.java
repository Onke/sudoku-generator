package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("This Grid generator only generates valid grid");
        System.out.print("How many grid to create: ");
        Scanner input = new Scanner(System.in);
        int numberOfGrids = input.nextInt();
        System.out.println();
        for (int i = 0; i < numberOfGrids; i++) {
            System.out.println("                  Grid " + (i + 1));
            SudokuGenerator generator = new SudokuGenerator();
            generator.waveFunctionCollapse();
            generator.printSudoku();
            System.out.println();

        }

    }
}
