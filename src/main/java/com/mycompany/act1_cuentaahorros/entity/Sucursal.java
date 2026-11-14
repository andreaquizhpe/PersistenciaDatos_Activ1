package com.mycompany.act1_cuentaahorros.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sucursal")
public class Sucursal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idSucursal;

    @Column(nullable = false)
    private String nombre;

    private String direccion;

    @OneToMany(mappedBy = "sucursal")
    private List<Cuenta_Ahorros> cuentas = new ArrayList<>();

    public Sucursal() {
    }

    public Sucursal(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public void agregarCuenta(Cuenta_Ahorros cuenta) {
        cuentas.add(cuenta);
        cuenta.setSucursal(this);
    }

    public Long getIdSucursal() {
        return idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Cuenta_Ahorros> getCuentas() {
        return cuentas;
    }
}