package dev.mateusneres.game.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class User {

    private String username;
    private GameBoard gameBoard;
    private int bestScore;

}
