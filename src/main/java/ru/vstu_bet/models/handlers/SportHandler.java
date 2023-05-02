package ru.vstu_bet.models.handlers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ru.vstu_bet.models.beans.db.RandomPhotoPlayer;
import ru.vstu_bet.models.beans.db.Sport;

import java.util.ArrayList;
import java.util.List;

public class SportHandler {
    public List<Sport> getAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<Sport> list;

        try {
            list = em.createQuery("select s from Sport s")
                    .getResultList();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }

        return list;
    }

    public Sport get(int id_sport){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        Sport obj;

        try {
            obj = em.find(Sport.class, id_sport);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }

        return obj;
    }
}
