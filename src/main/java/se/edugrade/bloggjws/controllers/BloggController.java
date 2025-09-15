package se.edugrade.bloggjws.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.edugrade.bloggjws.entities.BloggPost;
import se.edugrade.bloggjws.services.BloggService;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class BloggController {

    private final BloggService bloggService;

    public BloggController(BloggService bloggService) {
        this.bloggService = bloggService;
    }

    /************* Ska räcka att man är autentiserad *********************/

    //Hämta alla blogg inlägg
    @GetMapping("/posts")
    public List<BloggPost> allPosts() {
        return bloggService.getAllPosts();
    }


    //Hämtar ett specifikt blogginlägg
    @GetMapping("/post/{id}")
    public BloggPost showsSpecificPost(@PathVariable Long id) {
        return bloggService.getSpecificPost(id);
    }

    /***************** Kräver rollen user *******************/

    //Skapar ett nytt blogginlägg
    @PostMapping("/newpost")
    public BloggPost createNewPost(@RequestBody BloggPost dto, Authentication auth) {
        String sub = auth.getName();
        return bloggService.createNewPost(dto, auth);
    }

    /*********** Kräver user och rätt ägare av blogginlägget *****************/

    //Uppdaterar ett blogginlägg
    @PutMapping("/updatepost/{id}")
    public BloggPost updatePost(@PathVariable Long id,
                                @RequestBody BloggPost dto,
                                Authentication auth) {
        return bloggService.updateExistingPost(id, dto, auth);
    }

    /*********** Kräver user, rätt ägare av blogginlägget eller rollen admin********************/

    /********Extra endpoint********/
    //Raderar ett specifikt inlägg
    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity<Void> deletePostByPath(@PathVariable Long id, Authentication auth) {
        bloggService.deletePost(id, auth);
        return ResponseEntity.noContent().build();
    }
    /****************/

    //Raderat ett blogginlägg
    @DeleteMapping("/deletepost")
    public ResponseEntity deletePost(@RequestParam Long id, Authentication auth) {
         bloggService.deletePost(id, auth);
         return ResponseEntity.noContent().build();
    }

    /************ Kräver rollen admin **************/

    //Hämtar information om antalet blogginlägg
    @GetMapping("/count")
    public long countAmountOfPosts() {
        return bloggService.countAllPosts();
    }

}
