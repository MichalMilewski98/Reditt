package reditt.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    private Date dateCreated;

    private int score;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "subreddit_id")
    private Subreddit subreddit;

    @OneToMany
    @JoinColumn(name = "post")
    private Set<Comment> comments;

    
}
