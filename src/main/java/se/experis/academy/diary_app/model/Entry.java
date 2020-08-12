package se.experis.academy.diary_app.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents an entry as a data object and entity for JPA
 */
@Entity(name = "entries")
public class Entry {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "img", nullable = false)
    private String img;

    @Column(name = "active", nullable = false)
    private boolean active = true ;

    /**
     * Constructor
     * @param text The text for the entry
     * @param date The date for the entry, strings are automatically parsed
     * @param img The image url
     */
    public Entry(String text, Date date, String img) {
        this.text = text;
        this.date = date;
        this.img = img;
    }

    public Entry() { }

    public long getId() { return id; }

    public String getText() { return text; }

    /**
     * Parses the date into a formated string
     * @return date as yyyy-mm-dd
     */
    public String getDate() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public String getImg() { return img; }

    public void setInactive() { active = false;}

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
