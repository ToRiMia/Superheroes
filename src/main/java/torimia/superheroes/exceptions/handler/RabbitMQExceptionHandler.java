package torimia.superheroes.exceptions.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQExceptionHandler implements RabbitListenerErrorHandler {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.dead-letter.name}")
    private final String deadLetterQueueName;

    private static final String HEADER_ATTEMPTS = "attempt_count";

    @Override
    public Object handleError(Message message, org.springframework.messaging.Message<?> shortMessage, ListenerExecutionFailedException e) {
        int attempt = getAttempt(message);

        if (attempt > 3) {
            log.info("Message: \"{}\", forwarded to \"dead_messages\" queue.", shortMessage.getPayload());
            sendToDeadLetterQueue(shortMessage);
        } else {
            log.error("Error in reading RabbitMQ message: " + e.getCause().getMessage());
            sendToRetryProcess(message, shortMessage, attempt);
        }
        return null;
    }

    private void sendToRetryProcess(Message message, org.springframework.messaging.Message<?> shortMessage, int attempt) {
        rabbitTemplate.convertAndSend(message.getMessageProperties().getReceivedRoutingKey(), shortMessage.getPayload(), m -> {
            m.getMessageProperties().getHeaders().put(HEADER_ATTEMPTS, attempt + 1);
            return m;
        });
    }

    private void sendToDeadLetterQueue(org.springframework.messaging.Message<?> shortMessage) {
        rabbitTemplate.convertAndSend(deadLetterQueueName, shortMessage.getPayload());
    }

    private int getAttempt(Message message) {
        int attempt;
        if (message.getMessageProperties().getHeaders().get(HEADER_ATTEMPTS) == null) {
            attempt = 1;
        } else {
            attempt = Integer.parseInt(message.getMessageProperties().getHeaders().get(HEADER_ATTEMPTS).toString());
        }
        return attempt;
    }
}
