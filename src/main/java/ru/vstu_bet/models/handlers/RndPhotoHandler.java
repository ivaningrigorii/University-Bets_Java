package ru.vstu_bet.models.handlers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ru.vstu_bet.models.beans.db.RandomNamePlayer;
import ru.vstu_bet.models.beans.db.RandomPhotoPlayer;

import java.util.List;

public class RndPhotoHandler {
    public List<RandomPhotoPlayer> getIds(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<RandomPhotoPlayer> list;

        try {
            list = (List<RandomPhotoPlayer>) em.createQuery("select r from RandomPhotoPlayer r").getResultList();
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

    public RandomPhotoPlayer get(int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        RandomPhotoPlayer list;

        try {
            list = em.find(RandomPhotoPlayer.class, id);
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
