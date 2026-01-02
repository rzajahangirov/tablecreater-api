package com.rcompany.tablecreater.dtos.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReadDto {
    private Long id;
    private String username;
    private String email;
    private boolean active;
}
