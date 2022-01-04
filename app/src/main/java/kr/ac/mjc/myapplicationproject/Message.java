package kr.ac.mjc.myapplicationproject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message { //채팅메세지 들어가게 해주는 클래스
    private String id;
    private String message;
    private String writerId;
    private Date data;

    public Message(){
        data = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getFormattedTime(){
        SimpleDateFormat sdf =new SimpleDateFormat("HH:mm");
        return sdf.format(this.data);
    }
}
