package hello.photo.domain.room.entity;

import hello.photo.domain.moment.entity.Moment;
import hello.photo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private String roomDescription;
    private LocalDateTime createdAt;

    @ManyToOne
    private User host;

    @ManyToMany
    private List<User> members = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Moment> moments;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}