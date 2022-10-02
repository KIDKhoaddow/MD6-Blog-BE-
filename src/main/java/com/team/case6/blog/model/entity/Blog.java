package com.team.case6.blog.model.entity;

import com.team.case6.category.model.Category;
import com.team.case6.core.model.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "blogs")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Category category;
    private String title;
    @Lob
    private String describes;
    @Lob
    private String content;
    private String picture;
    private String createAt;
    @OneToOne
    private BlogStatus blogStatus;
    @ManyToOne
    private UserInfo userInfo;
    private Long countLike;
}
