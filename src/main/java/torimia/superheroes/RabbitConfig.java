package torimia.superheroes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RabbitConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public Queue hello() {
        return new Queue("battle");
    }

//    @Bean
//    public RabbitTemplate rabbitTemplate(){
//        return new RabbitTemplate();
//    } //краще створити самому

//    @Bean
//    public Sender sender() {
//        return new Sender();
//    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final RabbitTemplate template = new RabbitTemplate(this.connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

//    @Bean
//    public CommandLineRunner tutorial() {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//
//            }
//        };
//    }
}