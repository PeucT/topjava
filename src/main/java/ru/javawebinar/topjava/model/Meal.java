package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.GET_ALL, query = "SELECT m FROM Meal m WHERE m.user.id = ?1 ORDER BY m.id desc , m.dateTime"),
        @NamedQuery(name = Meal.GET_SINGLE, query = "SELECT m FROM Meal m WHERE m.id = ?1 AND m.user.id = ?2"),
        @NamedQuery(name = Meal.GET_BETWEEN, query = "SELECT m FROM Meal m WHERE m.user.id = ?1 AND m.dateTime >= ?2 AND m.dateTime <= ?3 ORDER BY m.id desc , m.dateTime"),
})

// LEFT JOIN FETCH m.user

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(name = "meals_unique_user_datetime_idx", columnNames = "user_id")})
public class Meal extends AbstractBaseEntity {
    public static final String GET_ALL = "Meal.getAll";
    public static final String GET_SINGLE = "Meal.getSingle";
    public static final String GET_BETWEEN = "Meal.getBetween";

    @Column(name = "date_time", columnDefinition = "LocalDateTime")
    @NotNull
    @DateTimeFormat
    private LocalDateTime dateTime;

    @Column(name = "description")
    @NotBlank
    @Size(max = 255)
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(min = 1, max = 10000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
