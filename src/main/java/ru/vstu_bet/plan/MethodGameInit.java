package ru.vstu_bet.plan;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContext;
import ru.vstu_bet.models.beans.db.Game;
import ru.vstu_bet.models.beans.db.Player;
import ru.vstu_bet.models.beans.other.TeamBean;
import ru.vstu_bet.models.beans.other.games.GameBean;
import ru.vstu_bet.models.handlers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MethodGameInit implements Runnable {
    private ServletContext context;

    public MethodGameInit(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            List<Object[]> games=(List<Object[]>)
                    em.createQuery("select tm.fk_game, count(tm.fk_game) from Game g join TeamsInGame tm " +
                            "on tm.fk_game=g.id " +
                            "where g.game_result = 0 and tm.ready_play=true " +
                            "group by tm.fk_game").getResultList();
            List<Integer> id_games = new ArrayList<>();
            for (Object[] game : games) {
                if (Integer.parseInt(game[1].toString())==2) {
                    id_games.add(Integer.parseInt(game[0].toString()));
                }
            }

            for(int id : id_games) {
                GameBean gameBean = new GameHandler().findGameBean(id);
                int[] strongTeams = new int[2];

                for (int i = 0; i < strongTeams.length; i++) {
                    TeamBean teamGameBean = new TeamHandler()
                            .find(gameBean.getTeams().get(i).getId_team());
                    List<Integer> id_players = teamGameBean.getPlayers();
                    for (int j = 0; j < (int) teamGameBean.getPeopleInTeam(); j++) {
                        Player player = new PlayerHandler().getPlayer(id_players.get(j));
                        int lvl_player = player.getStren()+ player.getEndurance()
                                + player.getStren_mind();
                        strongTeams[i]+=lvl_player;
                    }
                }
                int max_strong = strongTeams[0]+strongTeams[1];
                double ks[] = new double[3];

                ks[0] = (double)  strongTeams[0]/max_strong;
                ks[1] = (double)  strongTeams[1]/max_strong;

                if (Math.abs(ks[0]-ks[1])<0.3) {
                    ks[2] = 0.3;
                } else {
                    ks[2] = 0.1;
                }
                ks[0] = ks[0]*(1-ks[2]);
                ks[1] = ks[1]*(1-ks[2]);

                Random rnd = new Random();

                int res = rnd.nextInt(1000);
                int result_game = 0;
                if (res < ks[0]*1000) {
                    result_game = 1;
                }
                if ((res > ks[0]*1000)&&(res < ks[0]*1000+ks[1]*1000)) {
                    result_game = 2;
                }
                if (res > ks[0]*1000+ks[1]*1000){
                    result_game = 3;
                }

                double sum = (Double) em.createQuery(
                        "select sum(b.money) from Bets b " +
                                " where b.fk_game = :id_game " +
                                "group by b.fk_game"
                ).setParameter("id_game", id).getSingleResult();

                double[] k = new Odds().getOddsWithNothing(id);

                em.getTransaction().begin();
                Game game = em.find(Game.class, id);
                game.setGame_result(result_game);
                game.setDate_game(java.sql.Date.valueOf(LocalDate.now()));
                em.merge(game);

                List<Object[]> list = (List<Object[]>) em.createQuery(
                        "select b.fk_user, b.money, b.expected_result from Bets b " +
                                "where b.fk_game=:id_game"
                ).setParameter("id_game", id).getResultList();

                for(Object[] obj : list) {
                    if (Integer.parseInt(obj[2].toString())==result_game) {
                        double score = k[Integer.parseInt(obj[2].toString())-1] *
                                Double.parseDouble(obj[1].toString());
                        em.createQuery("update User u " +
                                "set u.score = u.score + :score " +
                                "where u.id = :fk_user")
                                .setParameter("score", score)
                                .setParameter("fk_user", Integer.parseInt(obj[0].toString()))
                                .executeUpdate();
                    }
                }
                em.getTransaction().commit();
            }

        } catch (Exception exp) {
            System.out.println(exp.getMessage());
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
