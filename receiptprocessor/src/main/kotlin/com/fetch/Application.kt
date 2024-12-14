package com.fetch

import com.fetch.model.Item
import com.fetch.model.Receipt
import io.micronaut.runtime.Micronaut.run
import io.micronaut.serde.annotation.SerdeImport
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
	info = Info(
		title = "Receipt Processor Service",
		version = "1.0.0",
		description = "A simple receipt processor"
	)
)
@SerdeImport(Receipt::class)
@SerdeImport(Item::class)
object Application {
	@JvmStatic
	fun main(args: Array<String>) {
		run(Application::class.java, *args)
	}
}

