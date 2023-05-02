package ru.vstu_bet.models.beans.other;

import ru.vstu_bet.models.beans.db.Player;
import ru.vstu_bet.models.beans.db.RandomNamePlayer;
import ru.vstu_bet.models.beans.db.RandomPhotoPlayer;
import ru.vstu_bet.models.handlers.RndNameHandler;
import ru.vstu_bet.models.handlers.RndPhotoHandler;

public class FullPlayerBean {
    private int id;
    private String photo;
    private String name;
    private int stren_mind;
    private int stren;
    private int endurance;
    private int fk_user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStren_mind() {
        return stren_mind;
    }

    public void setStren_mind(int stren_mind) {
        this.stren_mind = stren_mind;
    }

    public int getStren() {
        return stren;
    }

    public void setStren(int stren) {
        this.stren = stren;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getFk_user() {
        return fk_user;
    }

    public void setFk_user(int fk_user) {
        this.fk_user = fk_user;
    }

    public FullPlayerBean() {
    }

    public FullPlayerBean(int id, String photo, String name, int stren_mind, int stren, int endurance, int fk_user) {
        this.id = id;
        this.photo = photo;
        this.name = name;
        this.stren_mind = stren_mind;
        this.stren = stren;
        this.endurance = endurance;
        this.fk_user = fk_user;
    }

    public FullPlayerBean(Player player, String name, String photo) {
        this.photo = photo;
        this.name = name;
        this.id = player.getId();
        this.fk_user = player.getFk_user();
        this.endurance = player.getEndurance();
        this.stren = player.getStren();
        this.stren_mind = player.getStren_mind();
    }

    public FullPlayerBean(Player player) {
        RandomNamePlayer nameObj = new RndNameHandler().get(player.getFk_name());
        RandomPhotoPlayer photoObj = new RndPhotoHandler().get(player.getFk_photo());

        this.photo = photoObj.getPhoto();
        this.name = nameObj.getName();
        this.id = player.getId();
        this.fk_user = player.getFk_user();
        this.endurance = player.getEndurance();
        this.stren = player.getStren();
        this.stren_mind = player.getStren_mind();
    }

    @Override
    public String toString() {
        return "FullPlayerBean{\n" +
                "id=" + id +
                ",\n photo='" + photo + '\'' +
                ",\n name='" + name + '\'' +
                ",\n stren_mind=" + stren_mind +
                ",\n stren=" + stren +
                ",\n endurance=" + endurance +
                ",\n fk_user=" + fk_user +
                "\n}";
    }
}
