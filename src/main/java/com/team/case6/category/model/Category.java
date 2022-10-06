package com.team.case6.category.model;

import com.team.case6.tag.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String picture;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tag_category")
    private Set<Tag> tag = new HashSet<>();

}
