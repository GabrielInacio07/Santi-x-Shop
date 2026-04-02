package com.ecommerce.Santix.DTOs.Error;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;


@Getter
@Builder
public class ErrorDTO {

    private String code;
    private String message;
    private LocalDateTime timestamp;
}
