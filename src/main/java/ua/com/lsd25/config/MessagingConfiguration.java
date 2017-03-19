package ua.com.lsd25.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

/**
 * @author vzagnitko
 */
@Configuration
public class MessagingConfiguration {

    private static final String ORDER_QUEUE = "order-queue";

    @Bean
    public JmsTemplate jmsTemplate(@Autowired ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultDestinationName(ORDER_QUEUE);
        return template;
    }

}