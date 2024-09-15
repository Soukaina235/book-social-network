package com.soukaina.book.feedback;

import jakarta.validation.constraints.*;

public record FeedbackRequest (
        // we are not having an id here because we decided that
        // when the feedback is given, it should not be updatable
        @Positive(message = "200")
        @Min(value = 0, message = "201")
        @Max(value = 5, message = "202")
        Double score,
        // same error code for all three, because same error message
        @NotNull(message = "203")
        @NotEmpty(message = "203")
        @NotBlank(message = "203")
        String comment,
        @NotNull(message = "204")
        Integer bookId
) {
}
