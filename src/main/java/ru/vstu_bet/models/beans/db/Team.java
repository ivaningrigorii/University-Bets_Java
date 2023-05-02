package ru.vstu_bet.models.beans.db;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "team")
@Data
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int fk_user;
    private int fk_sport;
    private String name_team;
    private String photo_team;
    private boolean expectation = false;

    public Team() {}
}
