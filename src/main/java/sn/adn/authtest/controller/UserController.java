package sn.adn.authtest.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sn.adn.authtest.dao.IUser;
import sn.adn.authtest.domaine.User;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
@CrossOrigin("*")
public class UserController {

    // Calling the jpa repository methods
    private IUser userRepository;

    // method called for registration of user
    @PostMapping("/register")
    public Object register(@RequestBody User user){
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        user.setPassword(b.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
        return user;
    }

    // method called to get all users
    @GetMapping("/users")
    public List<User> listAll(){
        return userRepository.findAll();
    }

    // method call to get a user
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id){
        Optional<User> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()){
            return null;
        }
        return optionalUser.get();
    }

    // method called to update a user
    @PutMapping("/user/{id}")
    public User update(@RequestBody User user, @PathVariable int id){
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()){
            return null;
        }
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        userRepository.save(user);
        return user;
    }

    // method called to delete a user
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

}
