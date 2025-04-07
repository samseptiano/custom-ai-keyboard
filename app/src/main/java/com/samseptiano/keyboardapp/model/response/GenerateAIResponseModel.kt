package com.samseptiano.keyboardapp.model.response

/**
 * Created by samuel.septiano on 06/04/2025.
 */
data class GenerateAIResponseModel(
    val candidates: List<Candidate>? = null,
    val usageMetadata: UsageMetadata? = null,
    val modelVersion: String? = null
)

data class Candidate(
    val content: Content,
    val finishReason: String,
    val citationMetadata: CitationMetadata?,
    val avgLogprobs: Double?
)

data class Content(
    val parts: List<Part>,
    val role: String
)

data class Part(
    val text: String
)

data class CitationMetadata(
    val citationSources: List<CitationSource>
)

data class CitationSource(
    val startIndex: Int,
    val endIndex: Int,
    val uri: String? = null
)

data class UsageMetadata(
    val promptTokenCount: Int,
    val candidatesTokenCount: Int,
    val totalTokenCount: Int,
    val promptTokensDetails: List<TokenDetail>,
    val candidatesTokensDetails: List<TokenDetail>
)

data class TokenDetail(
    val modality: String,
    val tokenCount: Int
)
