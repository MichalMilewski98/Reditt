package reditt.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Comment> comments;

    private String email;

    private String login;

    private String password;

    private String username;



    public User() { }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
