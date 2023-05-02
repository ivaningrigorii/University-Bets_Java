package ru.vstu_bet.models.handlers;

import jakarta.persistence.*;
import ru.vstu_bet.models.beans.db.Player;
import ru.vstu_bet.models.beans.db.User;
import ru.vstu_bet.models.beans.other.FullPlayerBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayerHandler {

    public void add(Player player) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(player);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
    }

    public List<Player> gets(int id_user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        List<Player> players = new ArrayList();
        try {
            players = (List<Player>) em.createQuery("select p from Player p where p.fk_user=:fk_user")
                    .setParameter("fk_user", id_user).getResultList();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
        return players;
    }

    public List<FullPlayerBean> getsFull(int id_user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<FullPlayerBean> list = new ArrayList<>();
        try {
            list = (List<FullPlayerBean>) em.createQuery("select " +
                            "new ru.vstu_bet.models.beans.other.FullPlayerBean(p, n.name, ph.photo)" +
                            " from Player p join RandomPhotoPlayer ph on p.fk_photo=ph.id " +
                            "join RandomNamePlayer n on p.fk_name=n.id where p.fk_user = :id_user")
                    .setParameter("id_user", id_user).getResultList();
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

    public FullPlayerBean getFull(int id_player) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        FullPlayerBean fullPlayerBean = new FullPlayerBean();
        try {
            fullPlayerBean = (FullPlayerBean) em.createQuery("select " +
                            "new ru.vstu_bet.models.beans.other.FullPlayerBean(p, n.name, ph.photo)" +
                            " from Player p join RandomPhotoPlayer ph on p.fk_photo=ph.id " +
                            "join RandomNamePlayer n on p.fk_name=n.id where p.id = :id_player")
                    .setParameter("id_player", id_player).getSingleResult();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
        return fullPlayerBean;
    }

    public List<FullPlayerBean> getPlayersForIds(List<Integer> playIds) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<FullPlayerBean> players = new LinkedList<FullPlayerBean>();
        try {
            for (int id : playIds) {
                players.add(getFull(id));
            }
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
        return players;
    }

    public Player getPlayer(int id_player) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        Player player = new Player();
        try {
            player = (Player) em.find(Player.class, id_player);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
        return player;
    }

    public void delete(int id_player, User user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(em.find(Player.class, id_player));
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
    }
}
