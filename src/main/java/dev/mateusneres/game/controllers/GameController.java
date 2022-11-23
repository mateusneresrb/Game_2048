package dev.mateusneres.game.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.mateusneres.game.enums.MoveDirection;
import dev.mateusneres.game.models.GameBoard;
import dev.mateusneres.game.models.UserData;
import dev.mateusneres.game.utils.*;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    static boolean run = true;
    private static List<UserData> usersDataList = new ArrayList<>();
    private static UserData userData;

    private static void loadAllData() {
        if (!FileUtil.existsDataFile() || FileUtil.isEmpty()) return;

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Type typeOfUsersSaved = new TypeToken<List<UserData>>() {
        }.getType();

        try {
            String usersJson = FileUtil.readFileAsString();
            usersDataList = gson.fromJson(usersJson, typeOfUsersSaved);
        } catch (Exception e) {
            Logger.error("An error occurred while trying to load data from the save file;");
        }
    }

    public static void start() {
        updateBoardView(userData);
        hookKeyboards();
    }

    public static void createOrLoadGame(String username) {
        GameBoard gameBoard = new GameBoard(new int[4][4], 0);
        UserData user = new UserData(username, gameBoard, 0);

        userData = usersDataList.stream().filter(userD -> userD.getUsername().equalsIgnoreCase(username)).findFirst().orElse(user);
        userData.resetGame();
    }

    public static boolean containsUserData(String name) {
        if (usersDataList == null) loadAllData();

        return usersDataList.stream().anyMatch(userData -> userData.getUsername().equalsIgnoreCase(name));
    }

    private static void updateUserBestScore() {
        if (userData.getBestScore() > userData.getGameBoard().getScore()) return;
        userData.setBestScore(userData.getGameBoard().getScore());
    }

    private static void updateBoardView(UserData userData) {
        updateUserBestScore();
        
        TableBuilder tableBuilder = new TableBuilder();

        tableBuilder.setBorders(TableBuilder.Borders.FRAME).frame(true);
        tableBuilder.setPadding(3);
        tableBuilder.setAutoColor(true);
        tableBuilder.setValues(userData.getGameBoard().getBoard());

        try {
            Util.clearScreen();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Logger.info("  " + ConsoleColors.WHITE_BACKGROUND_BRIGHT + ConsoleColors.RED_BOLD_BRIGHT + "\t  2048 GAME  \t" + ConsoleColors.RESET + "\n");
        Logger.info("User: " + userData.getUsername() + " | Score: " + userData.getGameBoard().getScore() + " | Best: " + userData.getBestScore());
        Logger.info(tableBuilder.build());
        Logger.info("TIP: Use arrow keys to move the tiles.");
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
                    //SAVE GAME HERE;
                    return;
                }

                /* SETA (CIMA) */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_UP) {
                    userData.getGameBoard().movementBoard(MoveDirection.UP);
                    updateBoardView(userData);
                    return;
                }

                /* SETA (ESQUERDA) */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_LEFT) {
                    userData.getGameBoard().movementBoard(MoveDirection.LEFT);
                    updateBoardView(userData);
                    return;
                }

                /* SETA (DIREITA) */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_RIGHT) {
                    userData.getGameBoard().movementBoard(MoveDirection.RIGHT);
                    updateBoardView(userData);
                    return;
                }

                /* SETA (BAIXO) */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_DOWN) {
                    userData.getGameBoard().movementBoard(MoveDirection.DOWN);
                    updateBoardView(userData);
                }

            }

        });
    }

}

