package ru.vstu_bet.models.beans.other.teams;

import lombok.Data;

@Data
public class MiniTeamBean {
    private int id_team;
    private String name_team;

    public MiniTeamBean() {
    }

    public MiniTeamBean(int id_team, String name_team) {
        this.id_team = id_team;
        this.name_team = name_team;
    }
}
