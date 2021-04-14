package pt.ulisboa.tecnico.milestone1.dto;

import pt.ulisboa.tecnico.milestone1.domain.UserLocation;

import java.util.List;
import java.util.stream.Collectors;

public class UserReport {
    private Long userId;
    private Long epoch;
    private Long coordsX;
    private Long coordsY;

    public UserReport(Long userId, Long epoch, Long coordsX, Long coordsY) {
        this.userId = userId;
        this.epoch = epoch;
        this.coordsX = coordsX;
        this.coordsY = coordsY;
    }

    public UserReport(UserLocation userLoc) {
        this.userId = userLoc.getUserId();
        this.epoch = userLoc.getEpoch();
        this.coordsX = userLoc.getCoordsX();
        this.coordsY = userLoc.getCoordsY();
    }

    public UserReport() {
    }

    public List<UserReport> convertToUserReportList(List<UserLocation> userLocations){
        return userLocations.stream().map(userLocation -> new UserReport(userLocation)).collect(Collectors.toList());
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
}
