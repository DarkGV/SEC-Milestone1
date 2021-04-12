package pt.ulisboa.tecnico.milestone1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ulisboa.tecnico.milestone1.domain.UserLocation;
import pt.ulisboa.tecnico.milestone1.repository.UserLocationRepository;

import java.util.List;

@RestController
@RequestMapping("/userlocation")
public class UserLocationController {
    @Autowired
    private UserLocationRepository userLocationRepository;

    @GetMapping("/create/{userId}/{epoch}/{x}/{y}")
    public UserLocation createUserLocation(@PathVariable("userId") int userId,
                                           @PathVariable("epoch") int epoch,
                                           @PathVariable("x") int coordsX,
                                           @PathVariable("y") int coordsY){
        return userLocationRepository.save(new UserLocation(userId, epoch, coordsX, coordsY));
    }

    @GetMapping("/get/{userId}/{epoch}")
    public UserLocation getUserLocationAtEpoch(@PathVariable("userId") int userId,
                                               @PathVariable("epoch") int epoch){
        return userLocationRepository.findUserLocationByUserIdAndEpoch(userId, epoch);
    }

    @GetMapping("/getList/{x}/{y}/{epoch}")
    public List<UserLocation> listUsersAtLocation(@PathVariable("x") int coordsX,
                                                  @PathVariable("y") int coordsY,
                                                  @PathVariable("epoch") int epoch){
        return userLocationRepository.findAllByCoordsXAndCoordsYAndEpoch(coordsX, coordsY, epoch);
    }
}
