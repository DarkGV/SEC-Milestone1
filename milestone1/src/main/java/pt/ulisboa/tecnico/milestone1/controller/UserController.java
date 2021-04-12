package pt.ulisboa.tecnico.milestone1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.ulisboa.tecnico.milestone1.domain.User;
import pt.ulisboa.tecnico.milestone1.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userRepository.findUserById(id);
    }

    @GetMapping("/create/{name}")
    public User createUser(@PathVariable("name") String name){
        return userRepository.save(new User(name));
    }
}
