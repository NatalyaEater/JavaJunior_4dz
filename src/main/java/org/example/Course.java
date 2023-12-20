package org.example;
import javax.persistence.*;

@Entity
@Table(name = "SchoolDB.Courses")

public class Course {
    public Course(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public Course() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "duration")
    private int duration;

    @Override
    public String toString() {
        return "\nCourse{" +
                "id=" + id +
                ", курс='" + title + '\'' +
                ", количество часов=" + duration +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

}
