package com.team.case6.model.entity.extra;


import com.team.case6.model.entity.blog.Blog;
import com.team.case6.model.entity.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Likies")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserInfo userInfo;
    @ManyToOne
    private Blog blog;
}
