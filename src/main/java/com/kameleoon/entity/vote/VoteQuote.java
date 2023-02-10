package com.kameleoon.entity.vote;

import com.kameleoon.entity.quote.Quote;
import com.kameleoon.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "VOTE_QUOTE")
public class VoteQuote {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    @Column(name = "updated_score")
    private int scoreAfterVote;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean vote;
}
