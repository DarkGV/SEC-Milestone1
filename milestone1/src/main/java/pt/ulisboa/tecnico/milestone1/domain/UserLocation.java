package pt.ulisboa.tecnico.milestone1.domain;

import javax.persistence.*;

@Entity
@Table(name = "USER_LOC")
public class UserLocation {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private int id;

    @Column(name = "USER_ID", nullable = false)
    private int userId;

    @Column(name = "EPOCH", nullable = false)
    private int epoch;

    @Column(name = "COORDS_X", nullable = false)
    private int coordsX;

    @Column(name = "COORDS_Y", nullable = false)
    private int coordsY;

    public UserLocation(Integer id, int userId, int epoch, int coordsX, int coordsY) {
        this.id = id;
        this.userId = userId;
        this.epoch = epoch;
        this.coordsX = coordsX;
        this.coordsY = coordsY;
    }

    public UserLocation(int userId, int epoch, int coordsX, int coordsY) {
        new UserLocation(null, userId, epoch, coordsX, coordsY);
    }

    public UserLocation() {
    }

    public int getCoordsX() {
        return coordsX;
    }

    public void setCoordsX(int coordsX) {
        this.coordsX = coordsX;
    }

    public int getCoordsY() {
        return coordsY;
    }

    public void setCoordsY(int coordsY) {
        this.coordsY = coordsY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEpoch() {
        return epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }
}