package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.exception.ResourceAlreadyExistsException;
@RestController
@RequestMapping("/posts")

// BEGIN
public class PostsController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Post> index() {
        return postRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Post show(@PathVariable long id) {
        var post =  postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        return post;
    }


    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Post add (@RequestBody Post postData) {
        var post_name = postRepository.findByTitle(postData.getTitle()).isPresent() ? postData: new Post() ;
        //  var product1 = new Product(0,productData.getTitle(),productData.getPrice());
        //   var product2 = productRepository.findById (productData.getId()).get();

        if (post_name==(postData))
            throw new ResourceAlreadyExistsException("Product with id " + postData.getTitle() + " already exist");
        else {
            //  product_name.setId(productData.getId());
            post_name.setTitle(postData.getTitle());
            post_name.setBody(postData.getBody());
            return postRepository.save(post_name);
        }
    }

    @PutMapping(path = "/{id}")
    public Post update(@PathVariable long id, @RequestBody Post postData) {
        var post =  postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        post.setTitle(postData.getTitle());
        post.setBody(postData.getBody());

        postRepository.save(post);

        return post;
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        postRepository.deleteById(id);
        commentRepository.deleteByPostId(id);
    }
}

// END
