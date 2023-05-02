package ru.vstu_bet.models.beans.db;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "random_nm_pl")
public class RandomNamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
