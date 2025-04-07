package com.samseptiano.keyboardapp.model.request

/**
 * Created by samuel.septiano on 06/04/2025.
 */
data class GenerateAIRequestModel(
    var contents: List<Parts>? = null
)

data class Parts(
    var parts: List<Text>? = null
)

data class Text(
    var text: String? = null
)