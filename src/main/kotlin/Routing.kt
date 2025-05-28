package com.camilo

import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel
import dev.langchain4j.store.embedding.EmbeddingSearchRequest
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    val embeddingModel = AllMiniLmL6V2EmbeddingModel()
    val embeddingStore = InMemoryEmbeddingStore<TextSegment>()

    sequenceOf("I like football", "Te weather is good today")
        .map(TextSegment::from)
        .onEach { println("creating segment for ${it.text()}") }
        .forEach { embeddingStore.add(embeddingModel.embed(it).content(), it) }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/question") {
            val searchMovieQuery = call.receive<Map<String, String>>()
            val queryEmbedding = embeddingModel.embed(searchMovieQuery.getValue("text")).content()

            val embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(1)
                .build()

            val matches = embeddingStore.search(embeddingSearchRequest).matches().first()


            call.respond(
                mapOf(
                    "score" to matches.score(),
                    "text" to matches.embedded().text()
                )
            )
        }
    }
}
