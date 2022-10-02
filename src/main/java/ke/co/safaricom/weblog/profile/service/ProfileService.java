package ke.co.safaricom.weblog.profile.service;

import ke.co.safaricom.weblog.profile.entity.Profile;
import ke.co.safaricom.weblog.profile.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Optional<Profile> getProfileById(Long id){
        var profile = profileRepository.findById(id);
        return profile;
    }

}
