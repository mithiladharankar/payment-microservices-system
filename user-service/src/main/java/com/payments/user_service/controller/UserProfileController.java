package com.payments.user_service.controller;

import com.payments.user_service.domain.UserProfile;
import com.payments.user_service.repository.UserProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {
    private final UserProfileRepository userProfileRepository;

    public UserProfileController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @GetMapping("/me")
    public UserProfile getMyProfile(Authentication authentication) {
        String username = authentication.getName();

        return userProfileRepository.findByUsername(username)
                .orElseGet(() -> {
                    UserProfile profile = new UserProfile();
                    profile.setUsername(username);
                    profile.setFullName("New User");
                    profile.setAddress("");
                    return profile;
                });
    }

    @PostMapping("/me")
    public UserProfile updateMyProfile(
            Authentication authentication,
            @RequestBody UserProfile profileRequest) {

        String username = authentication.getName();

        Optional<UserProfile> existingOpt = userProfileRepository.findByUsername(username);
        UserProfile profile = existingOpt.orElseGet(UserProfile::new);

        profile.setUsername(username);
        profile.setFullName(profileRequest.getFullName());
        profile.setAddress(profileRequest.getAddress());

        return userProfileRepository.save(profile);
    }
}
