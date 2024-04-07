package exercise.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
public class GuestCreateDTO {

    @NotBlank
    private String name;
    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "^[+][+0-9]*")
    @Size(min = 11, max = 13)
    private String phoneNumber;

    @NotNull
    @Pattern(regexp = "[0-9]*")
    @Size(min = 4, max = 4)
    private String clubCard;

    @FutureOrPresent
    private LocalDate cardValidUntil;
// END
}