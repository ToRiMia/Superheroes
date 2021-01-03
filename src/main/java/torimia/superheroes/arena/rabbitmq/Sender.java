//package torimia.superheroes.arena.rabbitmq;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import torimia.superheroes.arena.model.dto.Battle;
//
//
//public class Sender {
//
//    @Autowired
//    private RabbitTemplate template;
//
//    @Autowired
//    private Queue queue;
//
//    public void send(Battle request) {
//        String message = "Hello World!";
//        this.template.convertAndSend(queue.getName(), request.toString());
//        System.out.println(" [x] Sent '" + message + "'");
//    }
//}
