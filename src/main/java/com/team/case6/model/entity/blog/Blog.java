package com.team.case6.model.entity.blog;


import com.team.case6.model.entity.classify.Category;
import com.team.case6.model.entity.user.UserInfo;
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


}
