package pt.ulisboa.tecnico.milestone1.dto;

public class UserReportRequest {
    private Long userId;
    private Long epoch;
    private String pos;
    private boolean isByzantine;

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

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public boolean getIsByzantine() {
        return isByzantine;
    }

    public boolean isByzantine() {
        return isByzantine;
    }

    public void setIsByzantine(boolean byzantine) {
        isByzantine = byzantine;
    }

    public void setByzantine(boolean byzantine) {
        isByzantine = byzantine;
    }
}
