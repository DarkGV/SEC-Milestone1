package pt.ulisboa.tecnico.milestone1.domain;

import javax.persistence.*;

@Entity
@Table(name = "USER_LOC")
public class UserLocation {

    @Id
    @GeneratedValue(generator = "UserLocationSequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "UserLocationSequence", sequenceName = "USER_LOC_SEQ")
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "EPOCH", nullable = false)
    private Long epoch;

    @Column(name = "COORDS_X", nullable = false)
    private Long coordsX;

    @Column(name = "COORDS_Y", nullable = false)
    private Long coordsY;

    public UserLocation(Long id, Long userId, Long epoch, Long coordsX, Long coordsY) {
        this.id = id;
        this.userId = userId;
        this.epoch = epoch;
        this.coordsX = coordsX;
        this.coordsY = coordsY;
    }

    public UserLocation(Long userId, Long epoch, Long coordsX, Long coordsY) {
        this.userId = userId;
        this.epoch = epoch;
        this.coordsX = coordsX;
        this.coordsY = coordsY;
    }

    public UserLocation() {
    }

    public Long getCoordsX() {
        return coordsX;
    }

    public void setCoordsX(Long coordsX) {
        this.coordsX = coordsX;
    }

    public Long getCoordsY() {
        return coordsY;
    }

    public void setCoordsY(Long coordsY) {
        this.coordsY = coordsY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEpoch() {
        return epoch;
    }

    public void setEpoch(Long epoch) {
        this.epoch = epoch;
    }
}