package pt.ulisboa.tecnico.milestone1.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserLocationId implements Serializable {

    private int userId;
    private int epoch;

    public UserLocationId(int userId, int epoch) {
        this.userId = userId;
        this.epoch = epoch;
    }

    public UserLocationId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLocationId userLocId = (UserLocationId) o;
        return userId == userLocId.userId &&
                epoch == userLocId.epoch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, epoch);
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
