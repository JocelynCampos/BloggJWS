package se.edugrade.bloggjws.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.edugrade.bloggjws.entities.BloggPost;
import se.edugrade.bloggjws.repositories.BloggPostRepository;

import java.util.List;

@Service
public class BloggService {

    private final BloggPostRepository bloggPostRepository;


    public BloggService(BloggPostRepository bloggPostRepository) {
        this.bloggPostRepository = bloggPostRepository;
    }


    @Transactional(readOnly = true)
    public List<BloggPost> getAllPosts() {
        return bloggPostRepository.findAll();
    }

    @Transactional(readOnly = true)
    public BloggPost getSpecificPost(Long id) {
        return bloggPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find post with id: " + id));
    }

    @Transactional
    public BloggPost createNewPost(BloggPost post) {
        post.setId(null);
        return bloggPostRepository.save(post);
    }

    @Transactional
    public BloggPost updateExistingPost(BloggPost post) {
        if(post.getId() == null || !bloggPostRepository.existsById(post.getId())) {
            throw new EntityNotFoundException("Could not find post with id: " + post.getId());

        }
        return bloggPostRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        if(!bloggPostRepository.existsById(id)) {
            throw new EntityNotFoundException("Could not remove post with id: " + id);
        }
        bloggPostRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countAllPosts() {
        return bloggPostRepository.count();
    }
}
