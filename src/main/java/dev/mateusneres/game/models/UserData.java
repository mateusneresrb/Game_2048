package dev.mateusneres.game.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserData {

    private String username;
    private GameBoard gameBoard;
    private int bestScore;

}
