package com.perseo.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")

public class User implements UserDetails {

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        @Column(name = "user_id")
        private int userId;

        @Column(name = "username")
        private String username;

        @Column(name = "email")
        private String email;

        @Column(name = "password")
        private String password;

        @Enumerated(EnumType.STRING)
        private UserRole role;

        public int getUserId() {
                return userId;
        }

        public void setUserId(int userId) {
                this.userId = userId;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public UserRole getRole() {
                return role;
        }

        public void setRole(UserRole role) {
                this.role = role;
        }

        public List<WorkExperience> getWorkExperiences() {
                return workExperiences;
        }

        public void setWorkExperiences(List<WorkExperience> workExperiences) {
                this.workExperiences = workExperiences;
        }

        public List<Course> getPurchasedCourses() {
                return purchasedCourses;
        }

        public void setPurchasedCourses(List<Course> purchasedCourses) {
                this.purchasedCourses = purchasedCourses;
        }

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private List<WorkExperience> workExperiences = new ArrayList<>();

        @ManyToMany
        @JoinTable(
                name = "user_courses",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "course_id"))

        private List<Course> purchasedCourses = new ArrayList<>();
}
        /*@Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority(role.name()));
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
}*/
