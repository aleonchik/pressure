package ru.leonchik.entiti;

import java.time.LocalDate;
import java.util.Date;

public class Patient {
    private int id;
    private String name;
    private LocalDate birth;

    public Patient() {
    }

    public Patient(String name, LocalDate birth) {
        this.name = name;
        this.birth = birth;
    }

    public Patient(int id, String name, LocalDate birth) {
        this.id = id;
        this.name = name;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() { return birth; }

    public void setBirth(LocalDate birth) { this.birth = birth; }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                '}';
    }
}
