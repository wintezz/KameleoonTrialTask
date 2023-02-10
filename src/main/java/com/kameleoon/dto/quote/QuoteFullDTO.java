package com.kameleoon.dto.quote;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuoteFullDTO {

    private Long id;

    private String userName;

    private int number;

    private String text;
}
