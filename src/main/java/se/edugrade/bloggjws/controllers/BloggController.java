package se.edugrade.bloggjws.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class BloggController {

    /************* Ska räcka att man är autentiserad *********************/

    //Hämta alla blogg inlägg
    @GetMapping("/posts")
    @PreAuthorize("isAuthenticated()")
    public String posts() {
        return "posts endpoint - accessible for authenticated users.";
    }


    //Hämtar ett specifikt blogginlägg
    @GetMapping("/post/{id}")
    @PreAuthorize("isAuthenticated()")
    public String post(@PathVariable int id) {
        return "post/id - accessible for authenticated users. Id = " + id;
    }

    /***************** Kräver rollen user *******************/

    //Skapar ett nytt blogginlägg
    @PreAuthorize("hasRole('myClient_USER')")
    @PostMapping("/newpost")
    public String newPost() {
        return "newpost - creates new post for specific user.";
    }

    /*********** Kräver user och rätt ägare av blogginlägget *****************/

    //Uppdaterar ett blogginlägg
    @PreAuthorize("hasRole('myClient_USER')") //      <<<------------------- LÄGG TILL KRAV HÄR
    @PutMapping("/updatepost")
    public String updatePost() {
        return "updatepost";
    }

    /*********** Kräver user, rätt ägare av blogginlägget eller rollen admin ********************/

    //Raderar ett blogginlägg
    @DeleteMapping("/deletepost/{id}")
    @PreAuthorize("hasRole('myCient_ADMIN') or (hasRole('myClient_USER'))") // <<<-----
    public String deletePost() {
        return "deletepost";
    }

    /************ Kräver rollen admin **************/

    //Hämtar information om antalet blogginlägg
    @GetMapping("/count")
    @PreAuthorize("hasRole('myClient_ADMIN')")
    public String count() {
        return "count";
    }

}
