package ru.leonchik.entiti;

import java.time.LocalDateTime;

public class Pressure {
    private long id;
    private long patientId;
    private int sys;
    private int dia;
    private int pulse;
    private LocalDateTime dtm;

    public Pressure() {
    }

    public Pressure(long patientId, int sys, int dia, int pulse) {
        this.patientId = patientId;
        this.sys = sys;
        this.dia = dia;
        this.pulse = pulse;
        this.dtm = LocalDateTime.now();
    }

    public Pressure(long id, long patientId, int sys, int dia, int pulse, LocalDateTime dtm) {

        /*
         * String str = "2014-04-08 12:30";
         * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
         * LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
         *
         * LocalDateTime dateTime = LocalDateTime.of(2014, Month.APRIL, 8, 12, 30);
         * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
         * String str = dateTime.format(formatter);
         *
         * LocalDateTime time = LocalDateTime.now();
         * System.out.println("Сейчас = " + time);
         */
        this.id = id;
        this.patientId = patientId;
        this.sys = sys;
        this.dia = dia;
        this.pulse = pulse;
        this.dtm = dtm;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public int getSys() {
        return sys;
    }

    public void setSys(int sys) {
        this.sys = sys;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public LocalDateTime getDtm() {
        return dtm;
    }

    public void setDtm(LocalDateTime dtm) {
        this.dtm = dtm;
    }

    @Override
    public String toString() {
        return "Pressure{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", sys=" + sys +
                ", dia=" + dia +
                ", pulse=" + pulse +
                ", dtm=" + dtm +
                '}';
    }
}
