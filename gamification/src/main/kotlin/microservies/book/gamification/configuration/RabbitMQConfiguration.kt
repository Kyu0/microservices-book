package microservies.book.gamification.configuration

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.*
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory

@Configuration
class RabbitMQConfiguration: RabbitListenerConfigurer {

    @Bean
    fun multiplicationExchange(@Value("\${multiplication.exchange}") exchangeName: String): TopicExchange {
        return TopicExchange(exchangeName)
    }

    @Bean
    fun gamificationMultiplicationQueue(@Value("\${multiplication.queue}") queueName: String): Queue {
        return Queue(queueName, true)
    }

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange, @Value("\${multiplication.anything.routing-key}") routingKey: String): Binding {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey)
    }

    @Bean
    fun consumerJackson2MEssageConverter(): MappingJackson2MessageConverter {
        return MappingJackson2MessageConverter()
    }

    @Bean
    fun messageHandlerMethodFactory(): DefaultMessageHandlerMethodFactory {
        val factory = DefaultMessageHandlerMethodFactory()

        factory.setMessageConverter(consumerJackson2MEssageConverter())

        return factory
    }

    override fun configureRabbitListeners(registrar: RabbitListenerEndpointRegistrar?) {
        registrar?.messageHandlerMethodFactory = messageHandlerMethodFactory()
    }
}