package ru.vstu_bet.models.beans.db;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sport")
@Data
public class Sport {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name_sport;
    private int people_count;
    private String photo;

    public Sport() {}
}
