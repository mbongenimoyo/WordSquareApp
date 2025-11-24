package org.moyo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

class WordSquareSolverTest {


    static DictionaryLoader loader = new DictionaryLoader();;

    @BeforeAll
    public static void beforeTests(){
        loader.loadFromTextFile("src/test/resources/TestWord.txt");
    }

    @Test
    public void givenEmptySequence_whenSolveWordSquare_thenThrowException() {

        WordSquareSolver resolver = new WordSquareSolver(loader);

        Assertions.assertThrows(IllegalArgumentException.class, () -> resolver.solveWordSquare(3, ""));
    }

    @Test
    public void givenNullSequence_whenSolveWordSquare_thenThrowException() {

        WordSquareSolver resolver = new WordSquareSolver(loader);

        Assertions.assertThrows(IllegalArgumentException.class, () -> resolver.solveWordSquare(3, null));
    }


    @Test
    public void giveGridSizeOfZero_whenSolveWordSquare_thenReturnEmptyList() {

        WordSquareSolver resolver = new WordSquareSolver(loader);

        Assertions.assertEquals(List.of(), resolver.solveWordSquare(0, "sdsasdfas"));
    }

    @Test
    public void givenBaseCase1_whenSolveWordSquare_thenReturnListOfFourWords() {
        WordSquareSolver resolver = new WordSquareSolver(loader);

        Assertions.assertEquals(List.of("moan","once","acme","need"), resolver.solveWordSquare(4, "aaccdeeeemmnnnoo"));
    }

    @Test
    public void givenBaseCase2_thenReturnListOfFIveWords() {
        WordSquareSolver resolver = new WordSquareSolver(loader);

        Assertions.assertEquals(List.of("feast","earth","armor","stone","threw"), resolver.solveWordSquare(5, "aaaeeeefhhmoonssrrrrttttw"));
    }


    @Test
    public void givenBaseCase3_whenSolveWordSquare_thenReturnListOfSevenWords() {
        WordSquareSolver resolver = new WordSquareSolver(loader);

        Assertions.assertEquals(List.of("bravado","renamed","analogy","valuers","amoebas","degrade","odyssey"), resolver.solveWordSquare(7, "aaaaaaaaabbeeeeeeedddddggmmlloooonnssssrrrruvvyyy"));
    }

}