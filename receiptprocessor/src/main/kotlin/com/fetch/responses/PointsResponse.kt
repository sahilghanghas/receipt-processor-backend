package com.fetch.responses

import io.micronaut.serde.annotation.Serdeable
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Response model for the receipt points")
@Serdeable
data class PointsResponse(
    @Schema(description = "The points associated with the receipt")
    val value: Int
)
