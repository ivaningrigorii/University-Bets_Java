package ru.vstu_bet.models.beans.other.games;

import lombok.Data;
import ru.vstu_bet.models.beans.other.teams.MiniTeamBean;
@Data
public class TeamGameBean {
    private int id_game;
    private String name_sport;
    private int id_team;
    private String name_team;
    private boolean play_start;
    private int user_id;

    private int game_result = 0;


    public TeamGameBean() {
    }

    public TeamGameBean(int id_game, String name_sport, int id_team,
                        String name_team, boolean play_start, int user_id, int game_result) {
        this.id_game = id_game;
        this.name_sport = name_sport;
        this.id_team = id_team;
        this.name_team = name_team;
        this.play_start = play_start;
        this.user_id = user_id;
        this.game_result = game_result;
    }
}
