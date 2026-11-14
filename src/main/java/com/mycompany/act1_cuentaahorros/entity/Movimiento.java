package com.mycompany.act1_cuentaahorros.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
public class Movimiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idMovimiento;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal monto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta_Ahorros cuenta;

    public Movimiento() {
    }

    public Movimiento(LocalDateTime fecha, String tipo,
                      BigDecimal monto, Cuenta_Ahorros cuenta) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.monto = monto;
        this.cuenta = cuenta;
    }

    public Long getIdMovimiento() {
        return idMovimiento;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Cuenta_Ahorros getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta_Ahorros cuenta) {
        this.cuenta = cuenta;
    }
}