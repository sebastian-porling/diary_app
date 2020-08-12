package se.experis.academy.diary_app.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "entries")
public class Entry {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "img", nullable = false)
    private String img;

    @Column(name = "active", nullable = false)
    private boolean active = true ;

    public Entry(long id, String text, Date date, String img) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.img = img;
    }

    public Entry() { }

    public long getId() {
        return id; }

    public String getText() {
        return text; }

    public Date getDate() {
        return date; }

    public String getImg() {
        return img; }

    public void setInactive() {
        active = false;}

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", img='" + img + '\'' +
                ", active=" + active +
                '}';
    }
}
