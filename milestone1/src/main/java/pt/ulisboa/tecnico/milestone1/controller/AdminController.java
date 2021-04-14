package pt.ulisboa.tecnico.milestone1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ulisboa.tecnico.milestone1.domain.User;
import pt.ulisboa.tecnico.milestone1.domain.UserLocation;
import pt.ulisboa.tecnico.milestone1.repository.UserLocationRepository;
import pt.ulisboa.tecnico.milestone1.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserLocationRepository userLocationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/createuserloc/{userId}/{epoch}/{x}/{y}")
    public UserLocation createUserLocation(@PathVariable("userId") long userId,
                                           @PathVariable("epoch") long epoch,
                                           @PathVariable("x") long coordsX,
                                           @PathVariable("y") long coordsY){
        return userLocationRepository.save(new UserLocation(userId, epoch, coordsX, coordsY));
    }

    @GetMapping("/get/{userId}/{epoch}")
    public UserLocation getUserLocationAtEpoch(@PathVariable("userId") long userId,
                                               @PathVariable("epoch") long epoch){
        return userLocationRepository.findUserLocationByUserIdAndEpoch(userId, epoch);
    }

    @GetMapping("/getList/{x}/{y}/{epoch}")
    public List<UserLocation> listUsersAtLocation(@PathVariable("x") long coordsX,
                                                  @PathVariable("y") long coordsY,
                                                  @PathVariable("epoch") long epoch){
        return userLocationRepository.findAllByCoordsXAndCoordsYAndEpoch(coordsX, coordsY, epoch);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userRepository.findUserById(id);
    }

    @GetMapping("/createuser/{name}")
    public User createUser(@PathVariable("name") String name){
        return userRepository.save(new User(name));
    }
}
