package ru.vstu_bet.models.beans.db;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "players")
public class Player {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int fk_photo;
    private int fk_name;
    private int stren_mind;
    private int stren;
    private int endurance;
    private int fk_user;

    public Player() {
    }
}
