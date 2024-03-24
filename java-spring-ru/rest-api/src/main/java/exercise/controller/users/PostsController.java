package exercise.controller.users;

import exercise.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostsController {
    private List<Post> posts = Data.getPosts();

    @GetMapping("/users/{id}/posts")
    public List<Post> show(@PathVariable String id) {
        var post = posts.stream()
                .filter(p ->p.getUserId()==(Integer.parseInt(id)));
        //       .findAny();
          return post.toList();
      //  return post;
    }

    @PostMapping("/users/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    //public Post create(@RequestBody Post post, @PathVariable String id) { /* код */
        public Post  create(@PathVariable String id, @RequestBody Post post) {
        post.setUserId(Integer.parseInt(id));
        post.setSlug(post.getSlug());
        post.setTitle(post.getTitle());
        post.setBody(post.getBody());
        posts.add(post);
        return post;
    }



}

// BEGIN

// END
