package com.soukaina.book.book;

import com.soukaina.book.common.BaseEntity;
import com.soukaina.book.feedback.Feedback;
import com.soukaina.book.history.BookTransactionHistory;
import com.soukaina.book.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book extends BaseEntity {

    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean shareable;

    @ManyToOne
    @JoinColumn(name = "owner_id")  // optional because by default it is owner_id
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;

    // Marking the getRate() method with @Transient ensures that JPA doesn't attempt to treat it as a persistent property or map it to a column in the database.
    @Transient
    public double getRate() {
        if (feedbacks == null || feedbacks.isEmpty()) {
            return 0.0;
        }
        var rate = this.feedbacks.stream()
                .mapToDouble(Feedback::getScore)
                .average()
                .orElse(0.0); // in case we can't do the calculation

        double roundedRate = Math.round(rate * 10.0) / 10.0;
        return roundedRate;
    }
}
