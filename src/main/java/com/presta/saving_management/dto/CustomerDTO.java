package com.presta.saving_management.dto;

import lombok.Builder;

@Builder
public record CustomerDTO(
        long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String memberNumber
        ) {
}
