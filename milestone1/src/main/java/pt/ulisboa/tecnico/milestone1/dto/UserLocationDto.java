package pt.ulisboa.tecnico.milestone1.dto;

public class UserLocationDto {
    private int epoch;
    private int userId;
    private int coordsX;
    private int coordsY;

    public UserLocationDto(int epoch, int userId, int coordsX, int coordsY) {
        this.epoch = epoch;
        this.userId = userId;
        this.coordsX = coordsX;
        this.coordsY = coordsY;
    }

    public UserLocationDto() {
    }

    public int getEpoch() {
        return epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
