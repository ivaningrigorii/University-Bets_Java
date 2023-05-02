package ru.vstu_bet.models.beans.other.teams;

import lombok.Data;

@Data
public class ExpecTeamsBean {
    private int id_team;
    private String name;
    private String name_sport;
    private int user_id;
    private String user_name;

    public ExpecTeamsBean(int id_team, String name, String name_sport, int user_id, String user_name) {
        this.id_team = id_team;
        this.name = name;
        this.name_sport = name_sport;
        this.user_id = user_id;
        this.user_name = user_name;
    }

    public ExpecTeamsBean() {}
}
