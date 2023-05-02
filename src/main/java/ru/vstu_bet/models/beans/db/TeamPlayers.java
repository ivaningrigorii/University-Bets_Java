package ru.vstu_bet.models.beans.db;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "teamplayers")
@Data
public class TeamPlayers {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int fk_team;
    private int fk_player;

    public TeamPlayers() {}

    public TeamPlayers(int fk_team, int fk_player) {
        this.fk_team = fk_team;
        this.fk_player = fk_player;
    }
}
