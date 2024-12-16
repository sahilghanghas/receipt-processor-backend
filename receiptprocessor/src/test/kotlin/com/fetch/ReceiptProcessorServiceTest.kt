package com.fetch

import com.fetch.model.Item
import com.fetch.model.Receipt
import com.fetch.service.ReceiptProcessorService
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@MicronautTest
class ReceiptProcessorServiceTest {

    @Inject
    lateinit var receiptProcessorService: ReceiptProcessorService

    @Test
    fun `createReceiptId should return a receipt Id`() {
        val receiptId = receiptProcessorService.createReceiptId(getReceipt())

        assertTrue(receiptId.isNotEmpty())
    }

    @Test
    fun `calculatePointsOnReceipt should return 28 value for the sample receipt`(){

        val receiptId = receiptProcessorService.createReceiptId(getReceipt())

        val points = receiptProcessorService.getReceiptPoints(receiptId)

        assertEquals(28, points)
    }

    private fun getReceipt(): Receipt {

        val item1 = Item(
            shortDescription = "Mountain Dew 12PK",
            price = "6.49"
        )

        val item2 = Item(
            shortDescription = "Emils Cheese Pizza",
            price = "12.25"
        )

        val item3 = Item(
            shortDescription = "Knorr Creamy Chicken",
            price = "1.26"
        )

        val item4 = Item(
            shortDescription = "Doritos Nacho Cheese",
            price = "3.35"
        )

        val item5 = Item(
            shortDescription = "Klarbrunn 12-PK 12 FL OZ",
            price = "12.00"
        )

        val item = listOf(item1, item2, item3, item4, item5)

        return Receipt(
            retailer = "Target",
            purchaseDate = "2022-01-01",
            purchaseTime = "13:01",
            items = item,
            total = "35.35"
        )
    }
}