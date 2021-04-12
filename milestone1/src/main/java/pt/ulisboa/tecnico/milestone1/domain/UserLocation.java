package pt.ulisboa.tecnico.milestone1.domain;

import javax.persistence.*;

@Entity
@Table(name = "USER_LOC")
public class UserLocation {

    @EmbeddedId
    private UserLocationId userLocationId;

    @Column(name = "COORDS_X", unique = true, nullable = false)
    private int coordsX;

    @Column(name = "COORDS_Y", unique = true, nullable = false)
    private int coordsY;

    public UserLocation(UserLocationId userLocationId, int coordsX, int coordsY) {
        this.userLocationId = userLocationId;
        this.coordsX = coordsX;
        this.coordsY = coordsY;
    }

    public UserLocation(int userId, int epoch, int coordsX, int coordsY) {
        this.userLocationId = new UserLocationId(userId, epoch);
        this.coordsX = coordsX;
        this.coordsY = coordsY;
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

    public UserLocationId getUserLocationId() {
        return userLocationId;
    }

    public void setUserLocationId(UserLocationId userLocationId) {
        this.userLocationId = userLocationId;
    }
}