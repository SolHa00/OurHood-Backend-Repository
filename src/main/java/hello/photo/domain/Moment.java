package hello.photo.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Moment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image; //임시로 타입을 String으로..

    @ManyToOne
    private Room room;

    @OneToMany(mappedBy = "moment")
    private List<Comment> comments;
}