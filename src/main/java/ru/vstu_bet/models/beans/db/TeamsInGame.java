package ru.vstu_bet.models.beans.db;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "teams_in_game")
public class TeamsInGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int fk_team;
    private int fk_game;
    private boolean ready_play = false;

    public TeamsInGame(int fk_team, int fk_game) {
        this.fk_team = fk_team;
        this.fk_game = fk_game;
    }

    public TeamsInGame() {}
}
