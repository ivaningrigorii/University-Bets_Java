package ru.vstu_bet.models.handlers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Data;
import ru.vstu_bet.models.beans.db.Bets;
import ru.vstu_bet.models.beans.db.Game;
import ru.vstu_bet.models.beans.db.Team;

import java.util.ArrayList;
import java.util.List;
@Data
public class BetsHandler {

    private List<Bets> bbets = new ArrayList<>();
    private List<Game> ggames = new ArrayList<>();
    private List<List<Team>> tteams = new ArrayList<>();

    public Bets getOdds(int id_game, int id_user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        Bets bet = new Bets();
        try {
            bet = (Bets) em.createQuery(
                    "select b from Bets b where " +
                            "b.fk_game=:id_game and b.fk_user=:id_user"
            ).setParameter("id_game", id_game).setParameter("id_user", id_user)
                    .setMaxResults(1).getSingleResult();

        } catch (Exception exp) {
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }

        }
        return bet;
    }

    public void update(Bets bet){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(bet);
            em.getTransaction().commit();
        } catch (Exception exp) {
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
    }

    public Bets find(int id_bets) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        Bets bets = new Bets();
        try {
            bets = em.find(Bets.class, id_bets);
        } catch (Exception exp) {
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
        return bets;
    }

    public void create(Bets bet) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
           em.getTransaction().begin();
           em.persist(bet);
           em.getTransaction().commit();
        } catch (Exception exp) {
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }

        }
    }

    public void getMyBets(int id_user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            this.bbets = new ArrayList<>();
            this.ggames = new ArrayList<>();
            this.tteams = new ArrayList<>();

            List<Object[]> betsAndGames = (List<Object[]>)
                    em.createQuery("select b, g from " +
                            "Bets b join Game g on b.fk_game=g.id " +
                            "where b.fk_user = :id_user")
                            .setParameter("id_user", id_user)
                            .getResultList();

            for(Object[] obj : betsAndGames) {
                bbets.add(((Bets) obj[0]));
                ggames.add((Game) obj[1]);
                tteams.add(
                        (List<Team>)
                                em.createQuery(
                                                "select t from TeamsInGame tg join " +
                                                        " Team t on tg.fk_team = t.id" +
                                                        " where tg.fk_game = :id_game"
                                        ).setParameter("id_game", ((Game)obj[1]).getId())
                                        .getResultList()
                );
            }

        } catch (Exception exp) {
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
