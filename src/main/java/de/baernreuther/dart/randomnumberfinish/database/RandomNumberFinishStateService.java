package de.baernreuther.dart.randomnumberfinish.database;

import de.baernreuther.dart.randomnumberfinish.database.entity.RandomNumberFinishState;
import de.baernreuther.dart.security.database.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RandomNumberFinishStateService {

    private final RandomNumberFinishStateRepository randomNumberFinishStateRepository;
    private final UserDetailsService userDetailsService;

    public RandomNumberFinishState getOrCreate(String userName) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        Optional<RandomNumberFinishState> state = this.randomNumberFinishStateRepository.findByAppUser((User) userDetails);

        if (state.isEmpty()) {
            RandomNumberFinishState newState = RandomNumberFinishState.builder()
                    .currentNumber(2)
                    .appUser((User) userDetails)
                    .build();
            return this.randomNumberFinishStateRepository.save(newState);
        }

        // Zu faul das ordentlich zu bauen
        state = this.randomNumberFinishStateRepository.findByAppUser((User) userDetails);
        return state.get();
    }

    public RandomNumberFinishState save(RandomNumberFinishState randomNumberFinishState) {
        return this.randomNumberFinishStateRepository.saveAndFlush(randomNumberFinishState);
    }

    @Transactional
    public void remove(String userName) {
        this.randomNumberFinishStateRepository.deleteByAppUser((User) this.userDetailsService.loadUserByUsername(userName));
        this.randomNumberFinishStateRepository.flush();
    }
}
