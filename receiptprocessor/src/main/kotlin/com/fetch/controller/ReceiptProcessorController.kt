package com.fetch.controller

import com.fetch.responses.ErrorResponse
import com.fetch.responses.PointsResponse
import com.fetch.model.Receipt
import com.fetch.model.Item
import com.fetch.responses.ReceiptDTO
import com.fetch.service.ReceiptProcessorService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse


@Controller("/receipts")
@Suppress("unused")
class ReceiptProcessorController(private val receiptProcessorService: ReceiptProcessorService) {

    @Operation(
        summary = "Submits a receipt for processing.",
        description = "Submits a receipt for processing.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Returns the ID assigned to the receipt.",
                content = [Content(schema = Schema(implementation = ReceiptDTO::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @Post("/process")
    fun submitReceipt(@Body receipt: Receipt): HttpResponse<Any> {
        return try {
            if (receipt.retailer.isBlank() || receipt.purchaseDate.isBlank() || receipt.purchaseTime.isBlank()) {
                throw IllegalArgumentException("Invalid receipt data")
            }

            val receiptId = receiptProcessorService.createReceipt(receipt)

            HttpResponse.ok(mapOf("id" to receiptId))

        } catch (ex: IllegalArgumentException) {
            HttpResponse.badRequest(ErrorResponse("Invalid receipt data entered: ${ex.message}"))
        }
    }

    @Operation(
        summary = "Returns the points awarded for the receipt.",
        description = "Returns the points awarded for the receipt.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "The number of points awarded.",
                content = [Content(schema = Schema(implementation = PointsResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "No receipt found for that ID.",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @Get("/{id}/points")
    fun getPoints(@PathVariable id: String): HttpResponse<Any> {

        val points = receiptProcessorService.getReceiptPoints(id)
        return if (points != null) {
            HttpResponse.ok(mapOf("points" to points))
        } else {
            HttpResponse.notFound("No receipt found for that ID.")
        }
    }
}