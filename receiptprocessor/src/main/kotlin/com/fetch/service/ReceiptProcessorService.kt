package com.fetch.service

import com.fetch.model.Item
import com.fetch.model.Receipt
import jakarta.inject.Singleton
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.ceil
import io.micronaut.core.annotation.Introspected

@Singleton
class ReceiptProcessorService {

    private val receiptPoints = mutableMapOf<String, Int>()

    fun createReceipt(receipt: Receipt): String {

        val receiptId = generateUniqueId()

        receiptPoints[receiptId] = calculatePointsOnReceipt(receipt)

        return receiptId
    }

    private fun generateUniqueId(): String {
        return java.util.UUID.randomUUID().toString()
    }

    private fun calculatePointsOnReceipt(receipt: Receipt): Int {

        val retailerPoints = calculateRetailerPoints(receipt.retailer)
        val totalInvoicePoints = calculateTotalInvoicePoints(receipt.total)
        val purchaseDatePoints = calculatePurchaseDatePoints(receipt.purchaseDate)
        val purchaseTime = calculatePurchaseTime(receipt.purchaseTime)
        val itemsPoints = calculateItemsPoints(receipt.items)

        return retailerPoints + totalInvoicePoints + purchaseDatePoints + purchaseTime + itemsPoints
    }

    private fun calculateRetailerPoints(retailer: String): Int {
        return retailer.count { it.isLetterOrDigit() }
    }

    private fun calculateTotalInvoicePoints(total: String): Int {

        var points = 0
        val number = total.toDouble()

        points = when {
            number % 1 == 0.0 && number % 0.25 == 0.0 -> 50 + 25
            number % 1 == 0.0 -> 50  // Check if the number is a round number
            number % 0.25 == 0.0 -> 25  // Check if the number is divisible by 0.25
            else -> 0  // Default case, not divisible by 0.25
        }

        return points
    }

    private fun calculatePurchaseDatePoints(dateStr: String): Int {
        // Define the expected date format
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        try {
            // Parse the date string to a LocalDate
            val date = LocalDate.parse(dateStr, formatter)

            // Extract the day of the month
            val dayOfMonth = date.dayOfMonth

            // Check if the day is odd and return 6 if true, else 0
            return if (dayOfMonth % 2 != 0) 6 else 0
        } catch (e: Exception) {
            // Handle invalid date format
            println("Invalid date format")
            return 0
        }
    }

    private fun calculatePurchaseTime(timeStr: String): Int {

        val formatter = DateTimeFormatter.ofPattern("HH:mm")

        try {
            // Parse the time string to a LocalTime
            val time = LocalTime.parse(timeStr, formatter)

            // Extract the hour
            val hour = time.hour

            // Check if the hour is between 2:00 PM & 4:00 PM and return 10 if true, else 0
            return if (hour in 14..16) 10 else 0
        } catch (e: Exception) {
            // Handle invalid time format
            println("Invalid time format")
            return 0
        }
    }

    private fun calculateItemsPoints(items: List<Item>): Int {

        var points = 0

        points = ( items.size / 2 ) * 5

        for (item in items) {
            points += calculateItemPoints(item)
        }

        return points
    }

    private fun calculateItemPoints(item: Item): Int {

        var points = 0

        if ( item.shortDescription.length % 3 == 0 ) {
            points = ceil(item.price.toDouble() * 0.2).toInt()
        }

        return points
    }

    fun getReceiptPoints(id : String): Int? {
        return receiptPoints[id]
    }
}