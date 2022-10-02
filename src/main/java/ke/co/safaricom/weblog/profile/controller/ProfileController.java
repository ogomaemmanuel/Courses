package ke.co.safaricom.weblog.profile.controller;

import ke.co.safaricom.weblog.profile.entity.Profile;
import ke.co.safaricom.weblog.profile.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id){
        var profile = profileService.getProfileById(id);
        return ResponseEntity.of(profile);
    }

}
