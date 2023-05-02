package ru.vstu_bet.models.beans.other.games;

import lombok.Data;
import ru.vstu_bet.models.beans.other.teams.MiniTeamBean;
import ru.vstu_bet.models.handlers.Odds;

import java.util.ArrayList;
import java.util.List;
@Data
public class GameBean {
    private int id_game;
    private String name_sport;
    private List<Boolean> play_starts = new ArrayList<>();
    private List<MiniTeamBean> teams = new ArrayList<>();
    private List<Integer> user_ids = new ArrayList<>();
    private int result_game = 0;

    private double[] odds = new double[]{};

    public GameBean(TeamGameBean t1, TeamGameBean t2) {
        this.id_game  = t1.getId_game();
        this.name_sport = t1.getName_sport();
        play_starts.add(t1.isPlay_start());
        play_starts.add(t2.isPlay_start());
        teams.add(new MiniTeamBean(t1.getId_team(), t1.getName_team()));
        teams.add(new MiniTeamBean(t2.getId_team(), t2.getName_team()));
        user_ids.add(t1.getUser_id());
        user_ids.add(t2.getUser_id());
        this.result_game = t1.getGame_result();
        this.odds = new Odds().getOdds(this.id_game);
    }

    public GameBean() {}
}
