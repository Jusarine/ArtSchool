package com.artschool.model.entity;

import com.artschool.model.enumeration.Audience;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Course {

    private static final double DAYS_IN_WEEK = 7;

    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "course_discipline",
            joinColumns = {@JoinColumn(name = "course_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "discipline_id", nullable = false)}
    )
    private List<Discipline> disciplines = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Audience audience;

    private Integer spaces;

    private Integer fee;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "date_id")
    private Date date;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "course_day",
            joinColumns = {@JoinColumn(name = "course_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "day_id", nullable = false)}
    )
    private List<Day> days = new ArrayList<>();

    private Long weeksAmount;

    private Long daysAmount;

    @Lob
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "course_student",
            joinColumns = {@JoinColumn(name = "course_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "student_id", nullable = false)}
    )
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Photo> photos = new HashSet<>();

    public Course() {
    }

    public Course(String name, List<Discipline> disciplines, Audience audience, Integer spaces, Integer fee,
                  Date date, List<Day> days, String description, Instructor instructor) {
        this.name = name;
        this.disciplines = disciplines;
        this.audience = audience;
        this.spaces = spaces;
        this.fee = fee;
        this.date = date;
        this.days = days;
        setDaysAndWeeksAmount();
        this.description = description;
        this.instructor = instructor;
    }

    public Course(long id, String name, List<Discipline> disciplines, Audience audience, Integer spaces,
                  Integer fee, Date date, List<Day> days, String description, Instructor instructor) {
        this.id = id;
        this.name = name;
        this.disciplines = disciplines;
        this.audience = audience;
        this.spaces = spaces;
        this.fee = fee;
        this.date = date;
        this.days = days;
        setDaysAndWeeksAmount();
        this.description = description;
        this.instructor = instructor;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Integer getSpaces() {
        return spaces;
    }

    public void setSpaces(Integer spaces) {
        this.spaces = spaces;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Date getDate() {
        return date;
    }

    private List<DayOfWeek> getDaysOfWeek() {
        List<DayOfWeek> daysOfWeek = new ArrayList<>();
        for (Day day : days) {
            daysOfWeek.add(day.getName());
        }
        return daysOfWeek;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public void addDay(Day day) {
        days.add(day);
    }

    public void removeDay(Day day) {
        days.remove(day);
    }

    public Long getWeeksAmount() {
        return weeksAmount;
    }

    public void setWeeksAmount(Long weeksAmount) {
        this.weeksAmount = weeksAmount;
    }

    public Long getDaysAmount() {
        return daysAmount;
    }

    public void setDaysAmount(Long daysAmount) {
        this.daysAmount = daysAmount;
    }

    private void setDaysAndWeeksAmount() {
        List<LocalDate> allDaysBetweenDates = date.getStartDate().datesUntil(date.getEndDate())
                .collect(Collectors.toList());
        this.daysAmount = allDaysBetweenDates.stream().filter(day -> getDaysOfWeek().contains(day.getDayOfWeek()))
                .count();
        this.weeksAmount = Math.round(allDaysBetweenDates.size() / DAYS_IN_WEEK);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public void removePayment(Payment payment) {
        payments.remove(payment);
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", audience=" + audience +
                ", fee=" + fee +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course that = (Course) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .append(audience, that.audience)
                .append(spaces, that.spaces)
                .append(fee, that.fee)
                .append(date, that.date)
                .append(description, that.description)
                .append(instructor, that.instructor)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(audience)
                .append(spaces)
                .append(fee)
                .append(date)
                .append(description)
                .append(instructor)
                .toHashCode();
    }
}
