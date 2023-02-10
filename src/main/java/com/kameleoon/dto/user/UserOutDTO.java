package com.kameleoon.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOutDTO {

    private Long id;

    private String name;

    private String email;
}
