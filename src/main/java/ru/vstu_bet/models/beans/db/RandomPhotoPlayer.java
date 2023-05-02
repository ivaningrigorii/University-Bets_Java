package ru.vstu_bet.models.beans.db;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "random_ph_pl")
public class RandomPhotoPlayer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String photo;
}
