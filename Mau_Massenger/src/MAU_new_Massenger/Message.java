package MAU_new_Massenger;

import javax.swing.*;
import java.io.Serializable;
import java.util.Calendar;

public class Message implements Serializable {
   
    private String txt;
    private ImageIcon imageIcon;
    private Calendar time;          
    private User sender;
    private User reciver;
    //The new code
    private String Agon;


    public Message(User reciver, User sender, String txt){
    this.reciver=reciver;
    this.sender=sender;
    this.txt=txt;
    //this.imageIcon=imageIcon;
    //this.time=time;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReciver() {
        return reciver;
    }

    public void setReciver(User reciver) {
        this.reciver = reciver;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
    public String toString()
    {
    	return sender.getName();
    }
}
