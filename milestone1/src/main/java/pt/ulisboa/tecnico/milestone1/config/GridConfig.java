package pt.ulisboa.tecnico.milestone1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "grid")
public class GridConfig {
    private Integer min;
    private Integer max;
    private Integer users;
    private Integer byzantineUsers;
    private Integer maxByzantineLocations;

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public Integer getUsers() {
        return users;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public void setUsers(Integer users) {
        this.users = users;
    }

    public Integer getByzantineUsers() {
        return byzantineUsers;
    }

    public void setByzantineUsers(Integer byzantineUsers) {
        this.byzantineUsers = byzantineUsers;
    }

    public Integer getMaxByzantineLocations() {
        return maxByzantineLocations;
    }

    public void setMaxByzantineLocations(Integer maxByzantineLocations) {
        this.maxByzantineLocations = maxByzantineLocations;
    }
}
