package com.fetch.responses

import com.fetch.model.Receipt
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ReceiptDTO(
    val receiptId: Long
)
