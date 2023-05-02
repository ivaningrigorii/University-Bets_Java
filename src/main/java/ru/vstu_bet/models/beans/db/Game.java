package ru.vstu_bet.models.beans.db;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int game_result = 0;
    private int fk_sport;
    private Date date_game = java.sql.Date.valueOf(LocalDate.now());


    public Game() {}
}
