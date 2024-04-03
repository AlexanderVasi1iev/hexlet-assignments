package exercise.repository;

import java.util.ArrayList;
import java.util.List;

import exercise.dto.CommentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import exercise.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Transactional
    long deleteByPostId(long postId);

  //  ArrayList<CommentDTO> findByPostId (Long id);
  List <Comment>  findByPostId (Long id);
    // ArrayList<CommentDTO> findByPostId(Long PostId);
    

    //  ArrayList<CommentDTO> findByPostId(long postId);

    //List<CommentDTO> findAllByPostId(Long postId);
}
