package dev.mateusneres.game.models;

import dev.mateusneres.game.controllers.GameController;
import dev.mateusneres.game.utils.FileUtil;
import dev.mateusneres.game.utils.Logger;

import java.util.Scanner;

public class Game2048 extends Game {

    @Override
    public void criaNovo() {
        Logger.info("Você esta prestes a criar um novo jogo, digite seu nome de usuário:");

        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();

        if (GameController.containsUserData(username)) {
            Logger.info("* Encontramos um progresso para este usuário!");
            Logger.info("* Digite 1 para carregar-lo ou 2 para redefinir-lo!");

            try {
                int choose = scanner.nextInt();

                if (choose == 1) {
                    Logger.info("Recuperamos o progresso do usuário: " + username + ".");
                    GameController.createOrLoadGame(username);
                    return;
                }

                if (choose == 2) {
                    Logger.info("Um novo jogo será iniciado para o usuário: " + username + ".");
                    GameController.createOrLoadGame(username);
                    return;
                }

                Logger.info("Você precisa escolher entre o digito 1 ou 2.");

            } catch (NumberFormatException e) {
                Logger.error("Você precisa informar um digito nesta opção!");
                Logger.error("Digitos disponíveis: 1 ou 2");
            }
            return;
        }

        GameController.createOrLoadGame(username);
    }

    @Override
    public void executa() {
        GameController.start();
    }

    @Override
    public void carrega() {
        Logger.info("Digite o nome de usuário no qual o seu progresso foi salvo:");

        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();

        if (GameController.containsUserData(username)) {
            Logger.info("* Não encontramos nenhum progresso para este usuário, um novo jogo será iniciado!");
        }

        GameController.createOrLoadGame(username);
    }

}
