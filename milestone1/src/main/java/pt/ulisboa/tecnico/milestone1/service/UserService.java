package pt.ulisboa.tecnico.milestone1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ulisboa.tecnico.milestone1.domain.UserLocation;
import pt.ulisboa.tecnico.milestone1.dto.UserReport;
import pt.ulisboa.tecnico.milestone1.repository.UserLocationRepository;
import pt.ulisboa.tecnico.milestone1.repository.UserRepository;

import java.util.List;

@Component
public class UserService {
    private static final String DATABASE_ERROR_MESSAGE = "Database command invalid";

    @Autowired
    private UserLocationRepository userLocationRepository;

    @Autowired
    private UserRepository userRepository;

    public UserReport submitUserLocation(Long userId, Long epoch, String pos) throws Exception {
        String[] coords = retrieveCoordsFromPos(pos);
        UserLocation userLocation = new UserLocation(userId, epoch, Long.valueOf(coords[0]), Long.valueOf(coords[1]));
        validateUserDataForCreation(userLocation);

        try {
            return new UserReport(userLocationRepository.save(userLocation));
        } catch (Exception e) {
            throw new Exception(DATABASE_ERROR_MESSAGE);
        }
    }

    public UserReport obtainLocationReport(Long userId, Long epoch, Long callerUserId, boolean isHa) throws Exception {
        if (!isHa && userId != callerUserId) {
            throw new Exception("Not allowed");
        }

        try {
            return new UserReport(userLocationRepository.findUserLocationByUserIdAndEpoch(userId, epoch));
        } catch (Exception e) {
            throw new Exception(DATABASE_ERROR_MESSAGE);
        }
    }

    public List<UserReport> obtainUsersAtLocation(Long epoch, String pos, boolean isHa) throws Exception {
        if (!isHa) {
            throw new Exception("Not allowed");
        }

        String[] coords = retrieveCoordsFromPos(pos);
        try {
            return new UserReport().convertToUserReportList(
                    userLocationRepository.findAllByCoordsXAndCoordsYAndEpoch(
                            Long.valueOf(coords[0]),
                            Long.valueOf(coords[1]),
                            epoch));
        } catch (Exception e) {
            throw new Exception(DATABASE_ERROR_MESSAGE);
        }
    }

    private String[] retrieveCoordsFromPos(String pos) throws Exception {
        String[] coords = pos.split("-");

        if (coords.length != 2) {
            throw new Exception("Invalid pos");
        }
        return coords;
    }

    private void validateUserDataForCreation(UserLocation userLocation) throws Exception{
        if (userLocation.getUserId() == null) {
            throw new Exception();
            //throw new UserInvalidException(USR_0001);
        }
        if (userLocation.getCoordsX() == null || userLocation.getCoordsX() < 0 || userLocation.getCoordsX() > 50) {
            throw new Exception();
            //throw new UserInvalidException(USR_0002);
        }
        if (userLocation.getCoordsY() == null || userLocation.getCoordsY() < 0 || userLocation.getCoordsY() > 50) {
            throw new Exception();
            //throw new UserInvalidException(USR_0003);
        }
        if (userLocation.getEpoch() == null || userLocation.getEpoch() < 0) {
            throw new Exception();
            //throw new UserInvalidException(USR_0004);
        }
    }
}
