package pt.ulisboa.tecnico.milestone1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pt.ulisboa.tecnico.milestone1.domain.User;
import pt.ulisboa.tecnico.milestone1.domain.UserLocation;
import pt.ulisboa.tecnico.milestone1.repository.UserLocationRepository;
import pt.ulisboa.tecnico.milestone1.repository.UserRepository;

import java.util.Random;

@Component
public class DatabaseInit implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLocationRepository userLocationRepository;

    @Value("${user.count:25}")
    private int userCount;

    @Value("${grid.value.min:0}")
    private int gridMin;

    @Value("${grid.value.max:50}")
    private int gridMax;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 1; i <= userCount; i++){
            User user = userRepository.save(new User("n" + i));
            userLocationRepository.save(
                    new UserLocation(
                            user.getId(),
                            0L,
                            generateRandomLong(gridMin, gridMax),
                            generateRandomLong(gridMin, gridMax)));
        }
    }

    private Long generateRandomLong(int min, int max){
        return Long.valueOf(new Random().ints(min, max + 1).findFirst().getAsInt());
    }
}
