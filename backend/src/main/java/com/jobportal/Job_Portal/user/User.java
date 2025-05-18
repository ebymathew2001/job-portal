package com.jobportal.Job_Portal.user;


import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private boolean profileCompleted;

    public User(){

    }

    public User(int id, String email, String password, Role role,boolean profileCompleted){
        this.id=id;
        this.email = email;
        this.password=password;
        this.role=role;
        this.profileCompleted=profileCompleted;
    }

    public int getId(){
        return id;
    }


    public String getPassword(){
        return password;
    }

    public Role getRole(){
        return role;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public void setRole(Role role){
        this.role=role;
    }

    public boolean isProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }
}