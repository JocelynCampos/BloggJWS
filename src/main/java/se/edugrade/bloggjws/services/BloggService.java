package se.edugrade.bloggjws.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
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
    public BloggPost createNewPost(BloggPost post, Authentication auth) {
        post.setId(null);
        post.setAuthorSub(auth.getName());
        return bloggPostRepository.save(post);
    }

    @Transactional
    public BloggPost updateExistingPost(Long id, BloggPost incoming, Authentication auth) {
        BloggPost existingPost = bloggPostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find post with id: " + id));

        boolean isOwner = existingPost.getAuthorSub().equals(auth.getName());
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_myClient_ADMIN"));
        if (!isOwner && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to update this post");
        }

        existingPost.setTitle(incoming.getTitle());
        existingPost.setContent(incoming.getContent());
        return bloggPostRepository.save(existingPost);
    }

    @Transactional
    public void deletePost(Long id, Authentication auth) {
        BloggPost existingPostToDelete = bloggPostRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find post: " + id));
        boolean isOwner = existingPostToDelete.getAuthorSub().equals(auth.getName());
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_myClient_ADMIN"));
        if(!isOwner && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have authority to delete this post. Therefore post with id: " + id + " could not be deleted.");
        }
        bloggPostRepository.delete(existingPostToDelete);
        System.out.println("Deleted post: " + existingPostToDelete + " by:" + auth.getName() + ". With role: " + auth.getAuthorities());
    }

    @Transactional(readOnly = true)
    public long countAllPosts() {
        return bloggPostRepository.count();
    }
}
