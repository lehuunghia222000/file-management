package com.init.user_management.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LogoutRequest {
    private String token;
}
