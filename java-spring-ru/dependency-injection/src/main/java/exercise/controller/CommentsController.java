package exercise.controller;

import exercise.model.Post;
import exercise.repository.PostRepository;
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

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.exception.ResourceAlreadyExistsException;
@RestController
@RequestMapping("/comments")

// BEGIN
public class CommentsController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "")
    public List<Comment> index() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Comment show(@PathVariable long id) {
        var comment =  commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        return comment;
    }


    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment add (@RequestBody Comment commentData) {
        var comment_name = commentRepository.findById(commentData.getId()).isPresent() ? commentData: new Comment() ;
        //  var product1 = new Product(0,productData.getTitle(),productData.getPrice());
        //   var product2 = productRepository.findById (productData.getId()).get();

        if (comment_name==(commentData))
            throw new ResourceAlreadyExistsException("Product with id " + commentData.getId() + " already exist");
        else {
            comment_name.setPostId(commentData.getPostId());
            comment_name.setId(commentData.getId());
            comment_name.setBody(commentData.getBody());
            return commentRepository.save(comment_name);
        }
    }

    @PutMapping(path = "/{id}")
    public Comment update(@PathVariable long id, @RequestBody Comment commentData) {
        var comment =  commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

       // comment.setId(commentData.getId());
        comment.setId(id);
        comment.setPostId(commentData.getPostId());
        comment.setBody(commentData.getBody());

        commentRepository.save(comment);

        return comment;
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
          commentRepository.deleteById(id);
        postRepository.deleteById(id);
    }
}


// END
