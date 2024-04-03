package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("")
    public List<PostDTO> index() {
        var posts = postRepository.findAll();
        var result = posts.stream()
                .map(this::toDTO)
                .toList();
        return result;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
      public PostDTO show(@PathVariable Long id) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id "+id + " not found"));
       var commentDTO = new ArrayList<CommentDTO>();
  //var commentDTO = commentRepository.findAllByPostId(id);

   var commentsDTO = new ArrayList<CommentDTO>();
        List<Comment> comments = commentRepository.findByPostId(id);
        var res = comments.stream()
                .map(this::tocomDTO)
                .collect(Collectors.toList());
              var dto = new PostDTO();

        dto.setId(post.getId());
        dto.setBody(post.getBody());
        dto.setTitle(post.getTitle());

        dto.setComments(res);
       return dto;
    }

    private PostDTO toDTO(Post post) {
        var dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
       return dto;
    }

    private CommentDTO tocomDTO (Comment comment) {
        var comdto = new CommentDTO();
        comdto.setId(comment.getId());
        //comdto.setPostId(comment.getPostId());
        comdto.setBody(comment.getBody());
        return comdto;
    }
}
// END
