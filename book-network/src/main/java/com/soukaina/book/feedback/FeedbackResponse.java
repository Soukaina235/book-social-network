package com.soukaina.book.feedback;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private double score;
    private String comment;
    private boolean ownFeedback; // if it is given by the connected user, for example to highlight it
}
