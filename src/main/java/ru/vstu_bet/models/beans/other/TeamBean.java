package ru.vstu_bet.models.beans.other;

import lombok.Data;
import ru.vstu_bet.models.beans.db.Sport;
import ru.vstu_bet.models.beans.db.Team;

import java.util.List;

@Data
public class TeamBean {
    private int id;
    private int fk_user;
    private String name_team;
    private String photo_team;
    private String nameSport;
    private long peopleInTeam;
    private int peopleInSport;
    private List<Integer> players;

    public TeamBean(int id, int fk_user, String name_team, String photo_team,
                    String nameSport, int peopleInSport, long peopleInTeam) {
        this.id = id;
        this.fk_user = fk_user;
        this.name_team = name_team;
        this.photo_team = photo_team;
        this.nameSport = nameSport;
        this.peopleInSport = peopleInSport;
        this.peopleInTeam = peopleInTeam;
    }
    public TeamBean(int id, int fk_user, String name_team, String photo_team, String nameSport, int peopleInSport) {
        this.id = id;
        this.fk_user = fk_user;
        this.name_team = name_team;
        this.photo_team = photo_team;
        this.nameSport = nameSport;
        this.peopleInSport = peopleInSport;
    }

    public TeamBean(Team team, List<Integer> playersId, long peopleInTeam, Sport sport) {
        this.id = team.getId();
        this.name_team = team.getName_team();
        this.fk_user = team.getFk_user();
        this.peopleInTeam = peopleInTeam;
        this.players = playersId;
        this.photo_team = team.getPhoto_team();
        this.nameSport = sport.getName_sport();
        this.peopleInSport = sport.getPeople_count();
    }

    public TeamBean() {}
}
