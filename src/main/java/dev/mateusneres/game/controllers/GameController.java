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

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    private static List<UserData> usersDataList = new ArrayList<>();
    private static UserData userData;

    private static void loadAllGameData() {
        if (!FileUtil.existsDataFile() || FileUtil.isEmpty()) return;

        Gson gson = new Gson();

        Type typeOfUsersSaved = new TypeToken<List<UserData>>() {
        }.getType();

        try {
            String usersJson = FileUtil.readFileAsString();
            usersDataList = gson.fromJson(usersJson, typeOfUsersSaved);
        } catch (Exception e) {
            Logger.error("Não foi possível carregar o arquivo!");
            Logger.error("Possíveis causas: Arquivo corrompido ou inexistente!");

            System.exit(1);
        }
    }

    private static void saveGameData() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (usersDataList.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(userData.getUsername()))) {
            usersDataList.remove(usersDataList.stream().filter(user -> user.getUsername().equalsIgnoreCase(userData.getUsername())).findFirst().orElse(null));
        }

        usersDataList.add(userData);

        try {
            try (FileWriter writer = new FileWriter(FileUtil.getDataFile())) {
                gson.toJson(usersDataList, writer);
                writer.flush();
            }

        } catch (IOException e) {
            Logger.error("Não foi possível salvar os dados no arquivo!");
            Logger.error("Possíveis causas: Arquivo não pode ser acessado/Não pode ser criado");

            System.exit(1);
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
    }

    public static void resetUserData() {
        userData.resetGame();
    }

    public static boolean containsUserData(String name) {
        if (usersDataList.isEmpty()) loadAllGameData();

        return usersDataList.stream().anyMatch(userData -> userData.getUsername().equalsIgnoreCase(name));
    }

    private static void updateUserBestScore() {
        if (userData.getBestScore() > userData.getGameBoard().getScore()) return;
        userData.setBestScore(userData.getGameBoard().getScore());
    }

    private static boolean isUserCompleted() {
        boolean gameCompleted = false;
        GameBoard gameBoard = userData.getGameBoard();

        for (int r = 0; r < gameBoard.getBoard().length; r++) {
            for (int c = 0; c < gameBoard.getBoard().length; c++) {
                if (gameBoard.getBoard()[r][c] == 2048) {
                    gameCompleted = true;
                    break;
                }
            }
        }

        return gameCompleted;
    }

    private static void updateBoardView(UserData userData) {
        updateUserBestScore();
        saveGameData();

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

        if (isUserCompleted()) {
            userData.getGameBoard().setCompleted(true);
            Logger.info("Você alcançou a peça 2048 e atingiu o objetivo do jogo!");
            Logger.info("O jogo ainda permanecerá aberto para atingir uma maior pontuação!");
        }
    }

    private static void finishGameOver() {
        if (userData.getGameBoard().isGameOver()) {
            Logger.info("Você perdeu! GAME OVER!!!");
            Logger.info("Sua pontuação desta partida foi de: " + userData.getGameBoard().getScore() + " pontos");

            userData.resetGame();
            saveGameData();

            System.exit(1);
        }
    }

    private static void hookKeyboards() {
        // Might throw a UnsatisfiedLinkError if the native library fails to load or a RuntimeException if hooking fails
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true); // Use false here to switch to hook instead of raw input

        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            @Override
            public void keyPressed(GlobalKeyEvent event) {

                /* ESC */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) {
                    saveGameData();
                    keyboardHook.shutdownHook();
                    Logger.info("Você finalizou o jogo, volte sempre!");
                    return;
                }

                /* ARROW (UP) */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_UP) {
                    userData.getGameBoard().movementBoard(MoveDirection.UP);
                    updateBoardView(userData);
                    finishGameOver();
                    return;
                }

                /* ARROW (LEFT) */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_LEFT) {
                    userData.getGameBoard().movementBoard(MoveDirection.LEFT);
                    updateBoardView(userData);
                    finishGameOver();
                    return;
                }

                /* ARROW (RIGHT) */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_RIGHT) {
                    userData.getGameBoard().movementBoard(MoveDirection.RIGHT);
                    updateBoardView(userData);
                    finishGameOver();
                    return;
                }

                /* ARROW (DOWN) */
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_DOWN) {
                    userData.getGameBoard().movementBoard(MoveDirection.DOWN);
                    updateBoardView(userData);
                    finishGameOver();
                }

            }
        });
    }
}
