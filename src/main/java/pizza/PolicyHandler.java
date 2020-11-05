package pizza;

import pizza.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    //LDH
    @Autowired
    DeliveryRepository deliveryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaid_RequestDelivery(@Payload Paid paid){

        if(paid.isMe()){

            //LDH
            Delivery delivery = new Delivery();
            delivery.setOrderId(paid.getOrderId());
            delivery.setDeliveryStatus("Delivered");
            delivery.setEventId(Long.valueOf(10));
            delivery.setEventStatus("Waiting");

            deliveryRepository.save(delivery);

            System.out.println("##### listener RequestDelivery : " + paid.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverStoppedEvent_StopGift(@Payload StoppedEvent stoppedEvent){

        if(stoppedEvent.isMe()){

            Delivery delivery = new Delivery();
            delivery.setDeliveryStatus("Delivered");
            delivery.setEventId(stoppedEvent.getId());
            delivery.setEventStatus("StoppedEvent");

            deliveryRepository.save(delivery);
            System.out.println("##### listener StopGift : " + stoppedEvent.toJson());
        }
    }

}
