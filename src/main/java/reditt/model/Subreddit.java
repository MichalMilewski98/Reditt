package reditt.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subreddit")
    private Set<Post> posts;


}
