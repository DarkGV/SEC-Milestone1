package pt.ulisboa.tecnico.milestone1.config;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private GridConfig config;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createRegularUsers();
        createByzantineUsers();
    }

    private void createRegularUsers(){
        for (int i = 1; i <= config.getUsers(); i++){
            String name = "u" + i;

            if (userRepository.existsByName(name)) {
                break;
            }

            User user = userRepository.save(new User(name));
            userLocationRepository.save(
                    new UserLocation(
                            user.getId(),
                            0L,
                            generateRandomLong(config.getMin(), config.getMax()),
                            generateRandomLong(config.getMin(), config.getMax())));
        }
    }

    private void createByzantineUsers(){
        for (int i = 1; i <= config.getByzantineUsers(); i++) {
            String name = "b" + i;

            if (userRepository.existsByName(name)) {
                break;
            }

            User user = userRepository.save(new User(name));

            for (int j = 1; j <= generateRandomInt(2, config.getMaxByzantineLocations()); j++) {
                userLocationRepository.save(
                        new UserLocation(
                                user.getId(),
                                0L,
                                generateRandomLong(config.getMin(), config.getMax()),
                                generateRandomLong(config.getMin(), config.getMax())));
            }
        }
    }

    private Long generateRandomLong(int min, int max){
        return (long) generateRandomInt(min, max);
    }

    private Integer generateRandomInt(int min, int max){
        return new Random().ints(min, max + 1).findFirst().getAsInt();
    }
}
