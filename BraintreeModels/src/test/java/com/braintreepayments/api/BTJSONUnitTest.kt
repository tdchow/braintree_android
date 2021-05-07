package com.braintreepayments.api

import org.junit.Assert.assertEquals
import org.junit.Test

class BTJSONUnitTest {

    @Test
    fun getString_returnsFirstLevelProperty() {
        // language=JSON
        val json = """{ "property":  "value" }"""
        val sut = BTJSON(json)
        assertEquals("value", sut.getString("$.property"))
    }

    @Test
    fun getString_returnsSecondLevelProperty() {
        // language=JSON
        val json = """{ "property0": { "property1":  "value" }}"""
        val sut = BTJSON(json)
        assertEquals("value", sut.getString("$.property0.property1"))
    }
}