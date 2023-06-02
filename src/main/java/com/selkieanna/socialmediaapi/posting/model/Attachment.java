package com.selkieanna.socialmediaapi.posting.model;

import jakarta.persistence.*;
import org.hibernate.type.descriptor.jdbc.BinaryJdbcType;

@Entity
@Table(name = "attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    private BinaryJdbcType data;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
