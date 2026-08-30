package com.vidilin.hotel.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for hotel contact information")
public record ContactsDto(
        @Schema(description = "Phone number")
        String phone,
        @Schema(description = "Email address")
        String email
) {}
