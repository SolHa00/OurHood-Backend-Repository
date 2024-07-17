package hello.photo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private List<User> members;

    @OneToMany(mappedBy = "room")
    private List<Moment> moments;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}