package com.ducminh.blogapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments_closure")
public class CommentClosure {
    @EmbeddedId
    private CommentClosureId commentClosureId;

    @Column(name = "depth")
    private int depth;

    @ManyToOne
    @MapsId("ancestorId")
//    Mục đích: Xác định rằng cột ancestor_id trong bảng comment_closure là khóa ngoại (foreign key) trỏ đến bảng comments.
//    Tác dụng: Khi bạn lấy một CommentClosure, JPA sẽ tự động JOIN với bảng comments dựa vào ancestor_id để lấy đối tượng Comment tương ứng.
    @JoinColumn(name = "ancestor_id")
    private Comment accestor;

    @ManyToOne
    @MapsId("descendantId")
    @JoinColumn(name = "descendant_id")
    private Comment descendant;
}
