package app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Bill {

    private int id;
    private String series;
    private BigDecimal totalAmountToPay;
    private LocalDate printDate;
    private Reservation reservation;

    public Bill(int id, String series, BigDecimal totalAmountToPay, LocalDate printDate, Reservation reservation) {
        this.id = id;
        this.series = series;
        this.totalAmountToPay = totalAmountToPay;
        this.printDate = printDate;
        this.reservation = reservation;
    }

    public Bill(String series, BigDecimal totalAmountToPay, LocalDate printDate, Reservation reservation) {
        this.id = -1;
        this.series = series;
        this.totalAmountToPay = totalAmountToPay;
        this.printDate = printDate;
        this.reservation = reservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public BigDecimal getTotalAmountToPay() {
        return totalAmountToPay;
    }

    public void setTotalAmountToPay(BigDecimal totalAmountToPay) {
        this.totalAmountToPay = totalAmountToPay;
    }

    public LocalDate getPrintDate() {
        return printDate;
    }

    public void setPrintDate(LocalDate printDate) {
        this.printDate = printDate;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
