package com.example.timewax_assignment;

import java.util.Date;

public class Event {
    private int productId;
    private EventCodes eventCode;
    private Date date;

    /**
     * The names of the events that can be stored
     */
    public enum EventCodes {
        FirstAdded, Added, MoreQuantity, LessQuantity, ItemRemoved,
        RemoveAll, CartChecked, CheckOut, Payment, Discount10,
        Discount20, Discount50
    }

    public Event(){
    }

    public Event(int productId, EventCodes eventCode, Date date){
        this.productId = productId;
        this.eventCode = eventCode;
        this.date = date;
    }

    //methods added for possible future implementations

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public EventCodes getEventCode() {
        return eventCode;
    }

    public void setEventCode(EventCodes eventCode) {
        this.eventCode = eventCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
