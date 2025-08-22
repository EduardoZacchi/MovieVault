package com.movievault.controller.response;

import lombok.Builder;

@Builder
public record JwtTokenResponse(Long id,
                               String email) {
}
