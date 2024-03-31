package exercise.daytime;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
@Component
public class Night implements Daytime {
    private String name = "night";
    private String message;

    public String getName() {
        return name;
    }

    // BEGIN
    @PostConstruct
    public void init() {
        this.message = "Bean is initialized!";
        System.out.println(message);
    }
    // END
}
