package dev.mateusneres.game.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Score {

    private int gameScore;
    private int timePlayedMin;

}
