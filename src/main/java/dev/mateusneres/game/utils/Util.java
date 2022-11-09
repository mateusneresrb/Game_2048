package dev.mateusneres.game.utils;

import java.io.IOException;
import java.util.InputMismatchException;

import static java.lang.Math.random;

/**
 * Esta classe deve conter somente métodos estáticos que são úteis no geral,
 * como um método para transpor uma matriz, buscar um elemento em um vetor ou
 * limpar a tela do console.
 *
 * @author Professor Gabriel de Carvalho
 */

public class Util {

    public static void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    /**
     * Este método retorna uma matriz de sudoku 9x9 resolvida.
     */
    public static int[][] getSudoku() {
        int matriz[][] = new int[9][9];
        solve(matriz);
        return matriz;
    }

    private static boolean solve(int[][] matriz) {
        int[] findPos = find_empty_space(matriz);
        int row, col;
        if (findPos[0] == -1) return true;
        else {
            row = findPos[0];
            col = findPos[1];
        }
        int[] possibilities = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        for (int i : shuffle(possibilities)) {
            if (valid(matriz, i, findPos)) {
                matriz[row][col] = i;
                if (solve(matriz)) return true;
                matriz[row][col] = 0;
            }
        }
        return false;
    }

    private static boolean valid(int[][] matriz, int value, int[] pos) {
        for (int j = 0; j < matriz[0].length; j++)
            if (matriz[pos[0]][j] == value && pos[1] != j) return false;

        for (int i = 0; i < matriz.length; i++)
            if (matriz[i][pos[1]] == value && pos[0] != i) return false;

        int box_x = pos[1] / 3;
        int box_y = pos[0] / 3;

        for (int i = box_y * 3; i < box_y * 3 + 3; i++) {
            for (int j = box_x * 3; j < box_x * 3 + 3; j++) {
                if (matriz[i][j] == value && pos[0] != i && pos[1] != j) return false;
            }
        }
        return true;
    }

    private static int[] find_empty_space(int[][] matriz) {
        int[] result = {-1, -1};
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (matriz[i][j] == 0) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * Este método retorna um inteiro 'i', tal que 'rangeB' >= 'i' >= 'rangeA'.
     *
     * @param rangeA primeiro inteiro possível de ser gerado
     * @param rangeB segundo inteiro possível de ser gerado
     * @return um número aleatório entre 'rangeA' e 'rangeB'
     */
    public static int randInt(int rangeA, int rangeB) {
        if (rangeA < 0) throw new InputMismatchException("rangeA must bu non negative (" + rangeA + ")");
        if (rangeA > rangeB)
            throw new InputMismatchException("rangeB cannot be smaller than rangeA(" + rangeB + " < " + rangeA + ")");
        if (rangeA == rangeB) return rangeA;

        return (int) (random() * 100 * (rangeB - rangeA)) % (1 + rangeB - rangeA) + rangeA;

    }

    /**
     * Este método recebe um vetor de inteiros 'vet' e retorna um novo vetor,
     * composto por todos os elementos de 'vet' porém em posições aletórias
     * (embaralhado).
     *
     * @param vet vetor cujos elementos serão embaralhados.
     * @return vetor de inteiros com os elementos de 'vet' embaralhados.
     */
    public static int[] shuffle(int vet[]) {
        boolean posicoesInvalidas[] = new boolean[vet.length];
        int result[] = new int[vet.length];
        int rangeB = vet.length - 1;
        int posAtual;
        for (int i = 0; i < vet.length; i++) {
            posAtual = -1;

            while (posAtual == -1) {
                int temp = randInt(0, rangeB);
                if (!posicoesInvalidas[temp]) {
                    posicoesInvalidas[temp] = true;
                    posAtual = temp;
                }
            }
            result[posAtual] = vet[i];
        }
        return result;
    }
}
