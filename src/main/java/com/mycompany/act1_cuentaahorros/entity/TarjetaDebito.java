package com.mycompany.act1_cuentaahorros.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tarjeta_debito")
public class TarjetaDebito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idTarjeta;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    @Column(nullable = false)
    private Boolean activa = true;

    @OneToOne(optional = false)
    @JoinColumn(name = "cuenta_id", nullable = false, unique = true)
    private Cuenta_Ahorros cuenta;

    public TarjetaDebito() {
    }

    public TarjetaDebito(String numero, LocalDate fechaVencimiento,
                         Cuenta_Ahorros cuenta) {
        this.numero = numero;
        this.fechaVencimiento = fechaVencimiento;
        this.cuenta = cuenta;
    }

    public void activar() {
        this.activa = true;
    }

    public void bloquear() {
        this.activa = false;
    }

    public Long getIdTarjeta() {
        return idTarjeta;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Boolean getActiva() {
        return activa;
    }

    public Cuenta_Ahorros getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta_Ahorros cuenta) {
        this.cuenta = cuenta;
    }
}