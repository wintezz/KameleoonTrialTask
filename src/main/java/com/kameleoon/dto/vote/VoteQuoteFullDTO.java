package com.kameleoon.dto.vote;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteQuoteFullDTO {

    private long userId;

    private int vote;

    private LocalDateTime createdAt;
}
