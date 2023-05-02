package ru.vstu_bet.models.handlers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ru.vstu_bet.models.beans.db.TeamPlayers;

public class TeamPlayerHandler {

    public void add(int team_id, int player_id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            if (get(team_id, player_id) == null) {
                TeamPlayers teamPlayer = new TeamPlayers(team_id, player_id);
                em.getTransaction().begin();
                em.persist(teamPlayer);
                em.getTransaction().commit();
            }
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
    }

    public TeamPlayers get(int team_id, int player_id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        TeamPlayers teamPlayers = new TeamPlayers();
        try {
            teamPlayers = (TeamPlayers) em.createQuery("select tp from TeamPlayers tp " +
                            "where tp.fk_player = :pi and tp.fk_team = :ti")
                    .setParameter("ti", team_id)
                    .setParameter("pi", player_id)
                    .getSingleResult();
        } catch (Exception exp) {
            teamPlayers = null;
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }

        return teamPlayers;
    }

    public void del(int team_id, int player_id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TeamPlayers  tp = (TeamPlayers) em.createQuery("select tp from TeamPlayers tp " +
                            "where tp.fk_player = :pi and tp.fk_team = :ti")
                    .setParameter("ti", team_id)
                    .setParameter("pi", player_id)
                    .getSingleResult();
            em.remove(tp);
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
