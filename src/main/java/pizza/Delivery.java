package pizza;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Delivery_table")
public class Delivery {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String deliveryStatus="Delivered";
    private Long orderId= Long.valueOf(10);
    private Long eventId;
    private String eventStatus;

    @PostPersist
    public void onPostPersist(){
        Delivered delivered = new Delivered();
        BeanUtils.copyProperties(this, delivered);
        delivered.publishAfterCommit();

        GiftAdded giftAdded = new GiftAdded();
        BeanUtils.copyProperties(this, giftAdded);
        giftAdded.publishAfterCommit();

//        try {
//            Thread.sleep((long) (200 + Math.random() * 300));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    @PostUpdate
    public void onPostUpdate(){
        StoppedGift stoppedGift = new StoppedGift();
        BeanUtils.copyProperties(this, stoppedGift);
        stoppedGift.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }




}
