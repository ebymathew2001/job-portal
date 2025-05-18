package com.jobportal.Job_Portal.user;

public class UserDTO {
    private int id;
    private String email;
    private Role role;
    private boolean profileCompleted;

    public UserDTO(User user){
        this.id=user.getId();
        this.email=user.getEmail();
        this.role=user.getRole();
        this.profileCompleted=user.isProfileCompleted();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }
}
