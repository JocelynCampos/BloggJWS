package se.edugrade.bloggjws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.edugrade.bloggjws.entities.BloggPost;

@Repository
public interface BlogPostRepository extends JpaRepository<BloggPost, Long> {

}
