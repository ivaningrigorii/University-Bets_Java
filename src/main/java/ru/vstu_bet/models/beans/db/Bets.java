package ru.vstu_bet.models.beans.db;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "bets")
public class Bets {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double money;
    private int expected_result;
    private int fk_game;
    private int fk_user;

    public Bets() {}
}
