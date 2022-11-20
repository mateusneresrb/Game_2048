package dev.mateusneres.game.models;

import dev.mateusneres.game.controllers.GameController;

import java.util.Scanner;

public class Game2048 extends Game {

    //O que preciso;

    /* INICIA O JOGO DPS EU ADICIONO OUTRAS COISAS, COMEÇAR GAMEPLAY*/
    @Override
    public void criaNovo() {
        System.out.println("Você esta prestes a criar um novo jogo, digite seu nome de usuário:");

        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();


    }

    @Override
    public void executa() {
        System.out.println("START GAME;");
        //Some checks here;

        GameController.start();
    }

    @Override
    public void carrega() {
        System.out.println("BUSCANDO USUÁRIOS CADASTRADOS...");
        System.out.println("JOGO CARREGADO");
    }


}
