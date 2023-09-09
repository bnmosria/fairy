package problem;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

import java.net.URI;

@Data
@Getter
@Setter
@Builder
public class ProblemDetail {
    public static final MediaType JSON_MEDIA_TYPE = MediaType.APPLICATION_PROBLEM_JSON;

    private URI type;
    private String title;
    private String detail;
    private Integer status;
    private URI instance;
}