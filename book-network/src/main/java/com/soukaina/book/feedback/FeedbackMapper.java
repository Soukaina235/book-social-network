package com.soukaina.book.feedback;

import com.soukaina.book.book.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackRequest request) {
        return Feedback.builder()
                .score(request.score())
                .comment(request.comment())
                .book(
                        Book.builder()
                                .id(request.bookId()) // why only the id ?
                                // he had a warning because he has to set the fields with primitive types
                                // but i didn't yet i did as he did
                                .archived(false)
                                .shareable(false)
                                .build()
                )
                .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer id) {
        return FeedbackResponse.builder()
                .score(feedback.getScore())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreatedBy(), id))
                .build();
    }
}
