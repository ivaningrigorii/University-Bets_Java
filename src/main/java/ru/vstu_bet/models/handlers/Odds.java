package ru.vstu_bet.models.handlers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Odds {

    public double[] getOdds(int id_game) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        double s = 0;
        double[] sT = new double[2];

        try {
            s = (Double)
                    em.createQuery("select sum(b.money) " +
                                    " from Bets b where b.fk_game=:id_game " +
                                    "group by b.fk_game ")
                            .setParameter("id_game", id_game)
                            .getSingleResult();
            for (int i = 0; i < 2; i++) {
                sT[i] = (Double)
                        em.createQuery("select sum(b.money) from Bets b " +
                                        "where b.fk_game=:id_game and b.expected_result=:exp_res" +
                                        " group by b.fk_game")
                                .setParameter("exp_res", i+1)
                                .setParameter("id_game", id_game)
                                .getSingleResult();
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
        return new double[]{(s / sT[0]), (s / sT[1])};
    }

    public double[] getOddsWithNothing(int id_game) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        double s = 0;
        double[] sT = new double[3];

        try {
            s = (Double)
                    em.createQuery("select sum(b.money) " +
                                    " from Bets b where b.fk_game=:id_game " +
                                    "group by b.fk_game ")
                            .setParameter("id_game", id_game)
                            .getSingleResult();
            for (int i = 0; i < sT.length; i++) {
                sT[i] = (Double)
                        em.createQuery("select sum(b.money) from Bets b " +
                                        "where b.fk_game=:id_game and b.expected_result=:exp_res" +
                                        " group by b.fk_game")
                                .setParameter("exp_res", i+1)
                                .setParameter("id_game", id_game)
                                .getSingleResult();
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
        return new double[]{(s / sT[0]), (s / sT[1]), (s/sT[2])};
    }
}
