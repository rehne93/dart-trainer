package de.baernreuther.dart.randomnumberfinish.database;


import de.baernreuther.dart.security.database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RandomNumberFinishStateRepository extends JpaRepository<RandomNumberFinishState, Integer> {

     Optional<RandomNumberFinishState> findByAppUser(User user);

     @Modifying
     void deleteByAppUser(User user);


}
