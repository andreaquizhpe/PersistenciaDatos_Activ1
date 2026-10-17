/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.act1_cuentaahorros.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name = "cuentaAhorros")
@NoArgsConstructor
public class Cuenta_Ahorros implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "num_id", nullable = false)
    private int id;

    @Column(name = "numCuenta", nullable = false)
    private int numeroCuenta;

    @Column(name = "titular", nullable = false)
    private String titular;

    @Column(name = "saldoCuenta", nullable = false)
    private Double saldo;

    public Cuenta_Ahorros(Integer numeroCuenta, String titular, Double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldo;
    }

    public Cuenta_Ahorros() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

}
