package reditt.model;

import javax.persistence.*;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int voteValue;

    @OneToOne
    private User user;

    @OneToOne
    private Post post;
}
