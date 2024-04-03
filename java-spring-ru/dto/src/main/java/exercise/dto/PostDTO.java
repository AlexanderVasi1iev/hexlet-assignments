package exercise.dto;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Comment;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comments;

// BEGIN
@Setter
@Getter
public class PostDTO   {
    private Long id;
    private String Title;
    private String Body;
    private List<CommentDTO> comments;

   // public void setComments ((CommentDTO) comments) {    }

  //  public void setComments(ArrayList<CommentDTO> comments) {   }

    //public void setComments(ArrayList<CommenDTO> comments) {
   // }
    // public void setComments(List <CommentDTO> comments) {

    // private List<Comments> commentsList ;

 
}
// END
