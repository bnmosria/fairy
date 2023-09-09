package problem.builder;

import problem.ProblemDetail;
import problem.member.Detail;
import problem.member.Status;
import problem.member.Title;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

public class ProblemDetailBuilderTest {

    @Status(HttpStatus.NOT_FOUND)
    @Detail("Any exception thrown")
    @Title("The title")
    public static class AnyExceptionMock extends RuntimeException {}

    @Test
    public void itCreatesAProblemDetailWithGivenException() {
        RuntimeException runtimeException = new RuntimeException("Given exception");

        ProblemDetail problemDetail = new ProblemDetailBuilder(runtimeException).build();

        Assert.isInstanceOf(ProblemDetail.class, problemDetail);
    }

    @Test
    public void itShouldSetTheCorrectResponseStatus() {
        RuntimeException exception = new AnyExceptionMock();

        ProblemDetail problemDetail = new ProblemDetailBuilder(exception).build();
        Integer status = problemDetail.getStatus();

        Assert.isTrue(
                404 == status,
                String.format("Status %s is not same as %s", 404, status)
        );
    }

    @Test
    public void itShouldSetTheCorrectDetailMessage() {
        String message = "The user was not found";

        RuntimeException runtimeException = new RuntimeException(message);

        ProblemDetail problemDetail = new ProblemDetailBuilder(runtimeException).build();

        Assert.isTrue(
                message.equals(problemDetail.getDetail()),
                String.format("Message %s is not same as %s", message, problemDetail.getDetail())
        );
    }

}
