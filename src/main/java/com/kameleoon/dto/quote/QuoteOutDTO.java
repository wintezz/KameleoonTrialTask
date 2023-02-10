package com.kameleoon.dto.quote;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteOutDTO {

    private Long id;

    private String text;
}
