package com.mycompany.act1_cuentaahorros.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idCliente;

    @Column(nullable = false, unique = true, length = 10)
    private String cedula;

    @Column(nullable = false)
    private String nombre;

    private String correo;

    @OneToMany(mappedBy = "cliente")
    private List<Cuenta_Ahorros> cuentas = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String cedula, String nombre, String correo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
    }

    public void agregarCuenta(Cuenta_Ahorros cuenta) {
        cuentas.add(cuenta);
        cuenta.setCliente(this);
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Cuenta_Ahorros> getCuentas() {
        return cuentas;
    }
}