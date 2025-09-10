package se.edugrade.bloggjws.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "blogg_posts")

public class BloggPost {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "author_sub", nullable = false, length = 100)
    private String authorSub;

    public BloggPost() {}

    public BloggPost(String title, String content, String authorSub) {
        this.title = title;
        this.content = content;
        this.authorSub = authorSub;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorSub() {
        return authorSub;
    }

    public void setAuthorSub(String authorSub) {
        this.authorSub = authorSub;
    }
}
