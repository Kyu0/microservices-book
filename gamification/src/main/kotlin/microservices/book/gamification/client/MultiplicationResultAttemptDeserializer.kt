package microservices.book.gamification.client

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*

import microservices.book.gamification.client.dto.MultiplicationResultAttempt

/**
 * Multiplication 마이크로서비스로부터 오는 답안을
 * Gamification에서 사용하는 형태로 역직렬화
 */
class MultiplicationResultAttemptDeserializer: JsonDeserializer<MultiplicationResultAttempt>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): MultiplicationResultAttempt {
        val objectCodec = p!!.codec
        val node: JsonNode = objectCodec.readTree(p)

        return MultiplicationResultAttempt(
            node.get("user").get("alias").asText(),
            node.get("multiplication").get("factorA").asInt(),
            node.get("multiplication").get("factorB").asInt(),
            node.get("resultAttempt").asInt(),
            node.get("correct").asBoolean()
        )
    }
}