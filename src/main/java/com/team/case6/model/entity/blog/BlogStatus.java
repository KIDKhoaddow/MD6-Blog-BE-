package com.team.case6.model.entity.blog;


import com.team.case6.model.entity.extra.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String updateAt;
    @Enumerated(EnumType.ORDINAL)
    private Status status=Status.PENDING;
    private boolean verify=true;
    private boolean confirm=false;
}
