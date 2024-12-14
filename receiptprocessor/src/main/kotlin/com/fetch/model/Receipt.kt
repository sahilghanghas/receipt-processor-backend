package com.fetch.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable


@Introspected
@Serdeable
data class Receipt(

    @JsonProperty("retailer")
    val retailer: String,
    val purchaseDate: String,
    val purchaseTime: String,
    val total: String,
    val items: List<Item>
)

@Introspected
@Serdeable
data class Item(
    val shortDescription: String,
    val price: String
)