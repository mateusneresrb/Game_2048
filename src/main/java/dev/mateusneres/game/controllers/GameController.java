package dev.mateusneres.game.controllers;

import dev.mateusneres.game.enums.MoveDirection;
import dev.mateusneres.game.models.GameBoard;
import dev.mateusneres.game.models.Score;
import dev.mateusneres.game.models.UserData;
import dev.mateusneres.game.utils.ConsoleColors;
import dev.mateusneres.game.utils.FileUtil;
import dev.mateusneres.game.utils.TableBuilder;
import dev.mateusneres.game.utils.Util;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GameController {

    static boolean run = true;
    List<UserData> userDataSaved = new ArrayList<>();
    private static UserData userData;

    public void loadAllData() {
        if (!FileUtil.existsDataFile() || FileUtil.isEmpty()) return;

        File dataFile = FileUtil.getDataFile();

        //FINISH LOAD FILE;
    }


    public static void start() {
        GameBoard gameBoard = new GameBoard(new int[4][4], new Score(0, 0));
        userData = new UserData("MateusN", gameBoard, 0);

        updateBoardView(userData);
        hookKeyboards();
    }

    public static void newGame() {


    }

    public static void loadGame() {


    }


    private static void updateBoardView(UserData userData) {
        TableBuilder tableBuilder = new TableBuilder();

        tableBuilder.setBorders(TableBuilder.Borders.FRAME).frame(true);
        tableBuilder.setPadding(3);
        tableBuilder.setAutoColor(true);
        tableBuilder.setValues(userData.getGameBoard().getBoard());

        try {
            Util.clearScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("  " + ConsoleColors.WHITE_BACKGROUND_BRIGHT + ConsoleColors.RED_BOLD_BRIGHT + "\t  2048 GAME  \t" + ConsoleColors.RESET + "\n");
        System.out.println("User: " + userData.getUsername() + " | Score: " + userData.getGameBoard().getScore().getGameScore() + " | Best: " + userData.getBestScore());
        System.out.println(tableBuilder.build());
        System.out.println("TIP: Use arrow keys to move the tiles.");
    }

    private static void hookKeyboards() {
        // Might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // Use false here to switch to hook instead of raw input

        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            @Override
            public void keyPressed(GlobalKeyEvent event) {

                /* ESC */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) {
                    keyboardHook.shutdownHook();
                    System.out.println("PROGRAMA FINALIZADO!");
                    return;
                }

                /* SETA (CIMA) */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_UP) {
                    userData.getGameBoard().movementBoard(MoveDirection.UP);
                    userData.getGameBoard().generateRandomNumber();
                    updateBoardView(userData);
                    return;
                }

                /* SETA (ESQUERDA) */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_LEFT) {
                    userData.getGameBoard().movementBoard(MoveDirection.LEFT);
                    userData.getGameBoard().generateRandomNumber();
                    updateBoardView(userData);
                    return;
                }

                /* SETA (DIREITA) */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_RIGHT) {
                    userData.getGameBoard().movementBoard(MoveDirection.RIGHT);
                    userData.getGameBoard().generateRandomNumber();
                    updateBoardView(userData);
                    return;
                }

                /* SETA (BAIXO) */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_DOWN) {
                    userData.getGameBoard().movementBoard(MoveDirection.DOWN);
                    userData.getGameBoard().generateRandomNumber();
                    updateBoardView(userData);
                    return;
                }

            }

        });
    }

}

