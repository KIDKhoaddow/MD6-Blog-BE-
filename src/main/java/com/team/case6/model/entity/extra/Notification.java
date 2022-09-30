package com.team.case6.model.entity.extra;


import com.team.case6.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    public Notification(String content, User user1, User user2) {
        this.content = content;
        this.user1 = user1;
        this.user2 = user2;
    }
}
