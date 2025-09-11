package se.edugrade.bloggjws.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class BloggController {

    /************* Ska räcka att man är autentiserad *********************/

    //Hämta alla blogg inlägg
    @GetMapping("/posts")
    public String posts() {

    }

    //Hämtar ett specifikt blogginlägg
    @GetMapping("/post/{id}")
    public String post(@PathVariable int id) {

    }

    /***************** Kräver rollen user *******************/

    //Skapar ett nytt blogginlägg
    @PostMapping("/newpost")
    public String newPost() {

    }

    /*********** Kräver user och rätt ägare av blogginlägget *****************/

    //Uppdaterar ett blogginlägg
    @PutMapping("/updatepost")
    public String updatePost() {

    }

    /*********** Kräver user, rätt ägare av blogginlägget eller rollen admin ********************/

    //Raderar ett blogginlägg
    @DeleteMapping("/deletepost/{id}")
    public String deletePost() {

    }

    /************ Kräver rollen admin **************/

    //Hämtar information om antalet blogginlägg
    @GetMapping("/count")
    public String count() {

    }

}
