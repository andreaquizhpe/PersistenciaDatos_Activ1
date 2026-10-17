/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.act1_cuentaahorros;

import com.mycompany.act1_cuentaahorros.controller.CuentaController;
import com.mycompany.act1_cuentaahorros.entity.Cuenta_Ahorros;
import com.mycompany.act1_cuentaahorros.entity.view.View_Cuenta;
import jakarta.persistence.*;

/**
 *
 * @author LENOVO
 */
public class Act1_cuentaAhorros {

    public static void main(String[] args) {
        try (EntityManagerFactory emf = (EntityManagerFactory) Persistence.createEntityManagerFactory("com.mycompany_Act1_cuentaAhorros_jar_1.0-SNAPSHOTPU")) {
            EntityManager manager = (EntityManager) emf.createEntityManager();
            manager.getTransaction().begin();

            Cuenta_Ahorros cuenta = new Cuenta_Ahorros(123475, "Juan Ramirez", 12.65);
            manager.persist(cuenta);
            manager.getTransaction().commit();

            System.out.println("Cuenta guardada correctamente");
        }
        java.awt.EventQueue.invokeLater(() -> {

            View_Cuenta vista = new View_Cuenta();

            CuentaController controlador
                    = new CuentaController(vista);

            vista.setLocationRelativeTo(null);
            vista.setVisible(true);

            vista.addWindowListener(new java.awt.event.WindowAdapter() {
              
            });
        });
    }
}
