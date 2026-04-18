package com.ecommerce.Santix.DTOs.Error;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@JsonPropertyOrder({"code","message","details","timestamp"})
@Getter
@Builder
public class ErrorDTO {

    private int code;
    private String message;
    private String details;
    private LocalDateTime timestamp;
}
