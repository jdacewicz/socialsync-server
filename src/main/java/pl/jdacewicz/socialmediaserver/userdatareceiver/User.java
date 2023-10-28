package pl.jdacewicz.socialmediaserver.userdatareceiver;

import lombok.Builder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
record User(String userId,

            @Indexed(unique = true)
            String email,

            String password,

            String firstname,

            String lastname,

            String profilePictureName) implements UserDetails {

    final static String DEFAULT_USER_PROFILE_PICTURE_NAME = "default_user.png";
    final static String MAIN_DIRECTORY = "data/users";

    static class UserBuilder {
        private String profilePictureName = DEFAULT_USER_PROFILE_PICTURE_NAME;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    String getFolderDirectory() {
        if (profilePictureName.equals(DEFAULT_USER_PROFILE_PICTURE_NAME)) {
            return MAIN_DIRECTORY;
        }
        return String.format("%s/%s", MAIN_DIRECTORY, userId);
    }
}
