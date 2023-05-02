package ru.vstu_bet.models.handlers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ru.vstu_bet.models.beans.db.Game;
import ru.vstu_bet.models.beans.db.Player;
import ru.vstu_bet.models.beans.db.Team;
import ru.vstu_bet.models.beans.db.TeamsInGame;
import ru.vstu_bet.models.beans.other.games.GameBean;
import ru.vstu_bet.models.beans.other.games.TeamGameBean;

import java.util.ArrayList;
import java.util.List;

public class GameHandler {
    public void addTeam(int id_team, int fk_user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            Team team1 = em.find(Team.class, id_team);
            if ((long) em.createQuery(
               "select count(t) from Team t where t.expectation=true " +
                       "and t.fk_sport=:fk_sport and not t.fk_user=:fk_user"
            ).setParameter("fk_sport", team1.getFk_sport())
                    .setParameter("fk_user", fk_user).getSingleResult() == 0) {
                team1.setExpectation(true);
                em.getTransaction().begin();
                em.merge(team1);
                em.getTransaction().commit();
            } else {
                Team team2 = (Team) em.createQuery("select t from " +
                        "Team t where t.expectation=true " +
                        "and t.fk_sport=:fk_sport and not t.fk_user=:fk_user")
                        .setParameter("fk_sport", team1.getFk_sport())
                        .setParameter("fk_user", fk_user)
                        .setMaxResults(1).getSingleResult();
                Game game = new Game();
                game.setFk_sport(team1.getFk_sport());

                em.getTransaction().begin();
                team2.setExpectation(false);
                em.persist(game);
                em.persist(new TeamsInGame(team1.getId(), game.getId()));
                em.persist(new TeamsInGame(team2.getId(), game.getId()));
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

    public List<GameBean> myActiveGames(int id_user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<GameBean> gameBeanList = new ArrayList<>();
        try {
            List<TeamGameBean> list = em.createQuery(
                    "select new ru.vstu_bet.models.beans.other.games.TeamGameBean(" +
                            "g.id, s.name_sport, tm.id, tm.name_team, t.ready_play, u.id, g.game_result) " +
                            "from TeamsInGame t join " +
                            "Game g on g.id = t.fk_game join" +
                            " Team tm on t.fk_team = tm.id " +
                            "join Sport s on g.fk_sport = s.id " +
                            "join User u on u.id = tm.fk_user " +
                            "where g.id in " +
                            "(select distinct g.id from TeamsInGame t join " +
                            "Game g on g.id = t.fk_game join" +
                            " Team tm on t.fk_team = tm.id " +
                            "join Sport s on g.fk_sport = s.id " +
                            "join User u on u.id = tm.fk_user " +
                            "where u.id=:fk_user)" +
                            " order by g.id desc"
            ).setParameter("fk_user", id_user).getResultList();
            System.out.println(list);
            if (!list.isEmpty()) {
                for (int i = 1; i < list.size(); i+=2) {
                    if ((!list.get(i).isPlay_start())||
                    (!list.get(i - 1).isPlay_start())) {
                        gameBeanList.add(new GameBean(list.get(i-1), list.get(i)));
                    }
                }
            }
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
        return gameBeanList;
    }

    public void del(int id_game) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.find(Game.class, id_game));
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

    public void toReadyToStart(int id_game, int id_team, boolean val) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TeamsInGame teamsInGame = (TeamsInGame) em.createQuery(
                    "select tg from TeamsInGame tg " +
                            "where tg.fk_team = :fk_team and tg.fk_game = :fk_game"
            ).setParameter("fk_team", id_team).setParameter("fk_game", id_game)
                            .setMaxResults(1).getSingleResult();
            teamsInGame.setReady_play(val);
            em.merge(teamsInGame);
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

    public GameBean findGameBean(int id_game) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        GameBean gameBean = new GameBean();
        try {

            List<TeamGameBean> list = (List<TeamGameBean>) em.createQuery(
                    "select new ru.vstu_bet.models.beans.other.games.TeamGameBean(" +
                            "g.id, s.name_sport, tm.id, tm.name_team, t.ready_play, u.id, g.game_result)" +
                            " from TeamsInGame t join " +
                            " Game g on g.id = t.fk_game join" +
                            " Team tm on t.fk_team = tm.id " +
                            "join Sport s on g.fk_sport = s.id " +
                            "join User u on u.id = tm.fk_user " +
                            "where g.id = :id_game" +
                            " order by g.id desc"
            ).setParameter("id_game", id_game).getResultList();
            gameBean = new GameBean(list.get(0), list.get(1));
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            if (emf.isOpen()) emf.close();
        }

        return gameBean;
    }

    public List<GameBean> betsActive(String findWord) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<GameBean> gameBeanList = new ArrayList<>();
        try {
            if (findWord==null) findWord = "";
            findWord+="%";

            List<TeamGameBean> list = (List<TeamGameBean>) em.createQuery(
                    "select new ru.vstu_bet.models.beans.other.games.TeamGameBean(" +
                            "g.id, s.name_sport, tm.id, tm.name_team, t.ready_play, u.id, g.game_result)" +
                            " from TeamsInGame t join " +
                            " Game g on g.id = t.fk_game join" +
                            " Team tm on t.fk_team = tm.id " +
                            "join Sport s on g.fk_sport = s.id " +
                            "join User u on u.id = tm.fk_user " +
                            "where  g.game_result=0 and  g.id in " +
                            "(select distinct g.id " +
                            "from TeamsInGame t join " +
                            "Game g on g.id = t.fk_game join" +
                            " Team tm on t.fk_team = tm.id " +
                            "join Sport s on g.fk_sport = s.id " +
                            "join User u on u.id = tm.fk_user " +
                            " where tm.name_team like :findWord and g.game_result = 0" +
                            ")" +
                            " order by g.id desc"
            ).setParameter("findWord", findWord).getResultList();
            System.out.println(list);
            if (!list.isEmpty()) {
                for (int i = 1; i < list.size(); i+=2) {
                    if (!((list.get(i-1).isPlay_start())&&(list.get(i).isPlay_start()))) {
                        gameBeanList.add(new GameBean(list.get(i-1), list.get(i)));
                    }
                }
            }
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            if (emf.isOpen()) emf.close();
        }

        return gameBeanList;
    }

}
