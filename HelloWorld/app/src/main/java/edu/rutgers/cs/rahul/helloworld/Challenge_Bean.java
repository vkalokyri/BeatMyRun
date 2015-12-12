package edu.rutgers.cs.rahul.helloworld;

/*
* Created by Valia Kalokyri
* Tested by Valia Kalokyri
* Debugged by Valia Kalokyri
 */
public class Challenge_Bean {

    private String sender_id;
    private String receiver_id;
    private String datetime;
    private String distance;
    private String duration;
    private String status;

    public Challenge_Bean(String sender_id, String receiver_id, String datetime, String distance, String duration, String status) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.datetime = datetime;
        this.distance = distance;
        this.duration = duration;
        this.status = status;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
