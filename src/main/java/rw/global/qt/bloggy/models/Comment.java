package rw.global.qt.bloggy.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.global.qt.bloggy.audits.Initializer;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends Initializer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String content;
    @ManyToOne
    private Blog blog;
    @ManyToOne
    private User user;
    public Comment(String content, User user,Blog blog){
        this.content = content;
        this.user = user;
        this.blog = blog;

    }

}
