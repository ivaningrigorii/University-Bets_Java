package ru.vstu_bet.models.handlers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ru.vstu_bet.models.beans.db.Player;
import ru.vstu_bet.models.beans.db.RandomNamePlayer;

import java.util.ArrayList;
import java.util.List;

public class RndNameHandler {
    public List<RandomNamePlayer> getIds(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<RandomNamePlayer> list;

        try {
            list = (List<RandomNamePlayer>) em.createQuery("select r from RandomNamePlayer r").getResultList();
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

    public RandomNamePlayer get(int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        RandomNamePlayer list;

        try {
            list = em.find(RandomNamePlayer.class, id);
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
}
