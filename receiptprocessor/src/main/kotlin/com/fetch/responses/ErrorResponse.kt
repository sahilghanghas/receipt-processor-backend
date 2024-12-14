package com.fetch.responses

import io.micronaut.serde.annotation.Serdeable
import io.swagger.v3.oas.annotations.media.Schema

// ErrorResponse data class to hold error details
@Schema(description = "Error response model")
@Serdeable
data class ErrorResponse(
    @Schema(description = "Error message")
    val message: String
)
