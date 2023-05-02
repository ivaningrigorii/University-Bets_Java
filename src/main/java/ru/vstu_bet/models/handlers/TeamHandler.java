package ru.vstu_bet.models.handlers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import ru.vstu_bet.models.beans.db.Sport;
import ru.vstu_bet.models.beans.db.Team;
import ru.vstu_bet.models.beans.other.teams.ExpecTeamsBean;
import ru.vstu_bet.models.beans.other.TeamBean;
import ru.vstu_bet.models.beans.other.teams.MiniTeamBean;

import java.util.ArrayList;
import java.util.List;

public class TeamHandler {

    public long getPeopleInTeam(int id_team) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        long obj;

        try {
            obj = (Long) em.createQuery("select count(p) from TeamPlayers p " +
                            "where p.fk_team = :id_team").setParameter("id_team", id_team)
                    .getSingleResult();
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

    public List<TeamBean> getsFull(int id_user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<TeamBean> obj;

        try {
            obj = (List<TeamBean>) em.createQuery(
                    "select new ru.vstu_bet.models.beans.other.TeamBean( " +
                            "tm.id, tm.fk_user, tm.name_team, tm.photo_team, " +
                            "s.name_sport, s.people_count )" +
                            "from Team tm join Sport s" +
                            " on tm.fk_sport = s.id" +
                            " where tm.fk_user = :id_user " +
                            "and tm.expectation=false and " +
                            " (select count(g) from Game g " +
                            " join TeamsInGame tg on tg.fk_game=g.id " +
                            " join Team tthis on tthis.id=tg.fk_team " +
                            " where tthis.id = tm.id and g.game_result=0)=0"
            ).setParameter("id_user", id_user).getResultList();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }

        for (TeamBean team : obj) {
            team.setPeopleInTeam(getPeopleInTeam(team.getId()));
        }

        return obj;

    }

    public void add(Team team) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(team);
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

    public List<Integer> getPlayersId(int id_team) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<Integer> obj;

        try {
            obj = em.createQuery("select p.fk_player from TeamPlayers p " +
                            "where p.fk_team = :id_team").setParameter("id_team", id_team)
                    .getResultList();
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


    public TeamBean find(int id_team, int id_user) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        Team team;
        List<Integer> playersId;
        long contPeopleInTeam;
        Sport sport;

        try {
            team = em.find(Team.class, id_team);
            if (team.getFk_user() != id_user) {
                throw new Exception("это не ваш игрок");
            }

            playersId = getPlayersId(id_team);
            contPeopleInTeam = getPeopleInTeam(id_team);
            sport = new SportHandler().get(team.getFk_sport());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
        return new TeamBean(team, playersId, contPeopleInTeam, sport);
    }

    public TeamBean find(int id_team) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        Team team;
        List<Integer> playersId;
        long contPeopleInTeam;
        Sport sport;

        try {
            team = em.find(Team.class, id_team);

            playersId = getPlayersId(id_team);
            contPeopleInTeam = getPeopleInTeam(id_team);
            sport = new SportHandler().get(team.getFk_sport());
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (emf.isOpen()) {
                emf.close();
            }
        }
        return new TeamBean(team, playersId, contPeopleInTeam, sport);
    }

    public void del(int id_team, int id_user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Team team = em.find(Team.class, id_team);
            if (team.getFk_user() == id_user)
                em.remove(team);
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

    public List<MiniTeamBean> getMyExpecTeams(int fk_user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<MiniTeamBean> list = new ArrayList<>();
        try {
            list = em.createQuery(
                    "select new ru.vstu_bet.models.beans.other.teams.MiniTeamBean(" +
                            "t.id, t.name_team)" +
                            " from Team t where t.fk_user=:fk_user and t.expectation=true "
            ).setParameter("fk_user", fk_user).getResultList();
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

    public void updateEx(int id_team, boolean valEx) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        try {
            Team t = em.find(Team.class, id_team);
            t.setExpectation(valEx);
            em.getTransaction().begin();
            em.merge(t);
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

    public List<MiniTeamBean> getMyNotExpecTeams(int fk_user) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<MiniTeamBean> list = new ArrayList<>();
        try {
            list = em.createQuery(
                    "select new ru.vstu_bet.models.beans.other.teams.MiniTeamBean(" +
                            "t.id, t.name_team)" +
                            " from Team t " +
                            "join Sport s on t.fk_sport=s.id " +
                            "where t.fk_user=:fk_user and t.expectation=false " +
                            "and s.people_count = (select count(pl) from TeamPlayers pl " +
                            "where pl.fk_team = t.id)"
            ).setParameter("fk_user", fk_user).getResultList();
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

    public List<ExpecTeamsBean> getExpecTeams() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        List<ExpecTeamsBean> list = new ArrayList<>();
        try {
            list = (List<ExpecTeamsBean>) em.createQuery(
                    "select new ru.vstu_bet.models.beans.other.teams.ExpecTeamsBean(" +
                            "t.id, t.name_team, s.name_sport, u.id, u.fio) " +
                            "from Team t join User u " +
                            "on t.fk_user=u.id " +
                            "join Sport s on t.fk_sport=s.id " +
                            " where t.expectation = true"
            ).getResultList();
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
