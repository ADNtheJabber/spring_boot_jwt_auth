package sn.adn.authtest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.adn.authtest.domaine.User;

public interface IUser extends JpaRepository<User, Integer> {
    //add more specifics requests here if needed

    User findByLogin(String login);

    @Query("SELECT u FROM User u where u.login=:login")
    User getUserByEmail(@Param("login") String login);
}
