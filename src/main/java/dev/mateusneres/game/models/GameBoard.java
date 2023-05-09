package dev.mateusneres.game.models;

import dev.mateusneres.game.enums.MoveDirection;
import dev.mateusneres.game.utils.Util;
import lombok.Data;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class GameBoard {

    private int[][] board;
    private int score;
    private boolean completed;

    public GameBoard(int[][] board, int gameScore) {
        this.board = board;
        this.score = gameScore;

        generateRandomNumber();
        generateRandomNumber();
    }

    public void generateRandomNumber() {
        if (!Util.containsZeroValue(board)) return;

        while (true) {
            int r = ThreadLocalRandom.current().nextInt(board.length);
            int c = ThreadLocalRandom.current().nextInt(board.length);

            if (board[r][c] == 0) {
                board[r][c] = Util.randomValue();
                break;
            }
        }
    }

    public void movementBoard(MoveDirection moveDirection) {
        mergeValues(moveDirection);
        moveValues(moveDirection);

        generateRandomNumber();
    }

    public boolean isGameOver() {
        int[][] savedBoard = Arrays.stream(board).map(int[]::clone).toArray(int[][]::new);

        /* Test merge and move*/
        movementBoard(MoveDirection.UP);
        movementBoard(MoveDirection.DOWN);
        movementBoard(MoveDirection.LEFT);
        movementBoard(MoveDirection.RIGHT);

        if (Arrays.deepEquals(board, savedBoard)) {
            board = savedBoard;
            return true;
        }

        board = savedBoard;
        return false;
    }

    private void mergeValues(MoveDirection direction) {
        switch (direction) {
            case RIGHT:
                for (int r = 0; r < board.length; r++) {
                    for (int c = (board.length - 1); c >= 0; c--) {
                        if (board[r][c] == 0) continue;

                        int value = board[r][c];
                        int nextColumnAvailable = -1;
                        for (int i = (c - 1); i >= 0; i--) {
                            if (board[r][i] != 0) {
                                nextColumnAvailable = i;
                                break;
                            }
                        }

                        if (nextColumnAvailable != -1 && board[r][nextColumnAvailable] == value) {
                            board[r][c] += value;
                            board[r][nextColumnAvailable] = 0;

                            score += board[r][c];
                        }

                    }
                }
                break;
            case LEFT:
                for (int r = 0; r < board.length; r++) {
                    for (int c = 0; c < board.length; c++) {
                        if (board[r][c] == 0) continue;

                        int value = board[r][c];
                        int nextColumnAvailable = -1;

                        for (int i = (c + 1); i < board.length; i++) {
                            if (board[r][i] != 0) {
                                nextColumnAvailable = i;
                                break;
                            }
                        }

                        /* MERGE */
                        if (nextColumnAvailable != -1 && board[r][nextColumnAvailable] == value) {
                            board[r][c] += value;
                            board[r][nextColumnAvailable] = 0;

                            score += board[r][c];
                        }
                    }
                }
                break;
            case UP:
                for (int c = 0; c < board.length; c++) {
                    for (int r = 0; r < board.length; r++) {
                        if (board[r][c] == 0) continue;

                        int value = board[r][c];
                        int nextRowAvailable = -1;

                        for (int i = (r + 1); i < board.length; i++) {
                            if (board[i][c] != 0) {
                                nextRowAvailable = i;
                                break;
                            }
                        }

                        /* MERGE */
                        if (nextRowAvailable != -1 && board[nextRowAvailable][c] == value) {
                            board[r][c] += value;
                            board[nextRowAvailable][c] = 0;

                            score += board[r][c];
                        }
                    }
                }
                break;
            case DOWN:
                for (int c = 0; c < board.length; c++) {
                    for (int r = (board.length - 1); r >= 0; r--) {
                        if (board[r][c] == 0) continue;

                        int value = board[r][c];
                        int nextRowAvailable = -1;

                        for (int i = (r - 1); i >= 0; i--) {
                            if (board[i][c] != 0) {
                                nextRowAvailable = i;
                                break;
                            }
                        }

                        /* MERGE */
                        if (nextRowAvailable != -1 && board[nextRowAvailable][c] == value) {
                            board[r][c] += value;
                            board[nextRowAvailable][c] = 0;

                            score += board[r][c];
                        }
                    }
                }
                break;
        }
    }

    private void moveValues(MoveDirection moveDirection) {
        switch (moveDirection) {
            case RIGHT:
                for (int r = 0; r < board.length; r++) {
                    for (int c = (board.length - 1); c >= 0; c--) {
                        int value = board[r][c];

                        int nextColumnEmpty = -1;
                        for (int i = (board.length - 1); i > c; i--) {
                            if (board[r][i] == 0) {
                                nextColumnEmpty = i;
                                break;
                            }
                        }

                        if (nextColumnEmpty != -1 && board[r][nextColumnEmpty] == 0) {
                            board[r][nextColumnEmpty] = value;
                            board[r][c] = 0;
                        }
                    }
                }
                break;
            case LEFT:
                for (int r = 0; r < board.length; r++) {
                    for (int c = 0; c < board.length; c++) {
                        int value = board[r][c];

                        int nextColumnEmpty = -1;
                        for (int i = 0; i < c; i++) {
                            if (board[r][i] == 0) {
                                nextColumnEmpty = i;
                                break;
                            }
                        }

                        if (nextColumnEmpty != -1 && board[r][nextColumnEmpty] == 0) {
                            board[r][nextColumnEmpty] = value;
                            board[r][c] = 0;

                        }
                    }
                }
                break;
            case UP:
                for (int c = 0; c < board.length; c++) {
                    for (int r = 0; r < board.length; r++) {
                        int value = board[r][c];

                        int nextRowEmpty = -1;
                        for (int i = 0; i < r; i++) {
                            if (board[i][c] == 0) {
                                nextRowEmpty = i;
                                break;
                            }
                        }

                        if (nextRowEmpty != -1 && board[nextRowEmpty][c] == 0) {
                            board[nextRowEmpty][c] = value;
                            board[r][c] = 0;
                        }
                    }
                }
                break;
            case DOWN:
                for (int c = 0; c < board.length; c++) {
                    for (int r = (board.length - 1); r >= 0; r--) {
                        int value = board[r][c];

                        int nextRowEmpty = -1;
                        for (int i = (board.length - 1); i > r; i--) {
                            if (board[i][c] == 0) {
                                nextRowEmpty = i;
                                break;
                            }
                        }

                        if (nextRowEmpty != -1 && board[nextRowEmpty][c] == 0) {
                            board[nextRowEmpty][c] = value;
                            board[r][c] = 0;
                        }
                    }
                }
                break;
        }
    }
}
