package pt.ulisboa.tecnico.milestone1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.ulisboa.tecnico.milestone1.domain.UserLocation;
import java.util.List;

public interface UserLocationRepository extends JpaRepository<UserLocation, Integer> {
    UserLocation findUserLocationByUserIdAndEpoch(int userId, int epoch);
    List<UserLocation> findAllByCoordsXAndCoordsYAndEpoch(int coordsX, int coordsY, int epoch);
}
