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

        // Crea la conexión con la unidad de persistencia configurada en Hibernate
        try (EntityManagerFactory emf = (EntityManagerFactory) Persistence.createEntityManagerFactory("com.mycompany_Act1_cuentaAhorros_jar_1.0-SNAPSHOTPU")) {

            // Crea el administrador de entidades para trabajar con la base de datos
            EntityManager manager = (EntityManager) emf.createEntityManager();

            // Inicia una transacción
            manager.getTransaction().begin();

            // Crea un objeto con los datos de una cuenta de ahorros
            Cuenta_Ahorros cuenta = new Cuenta_Ahorros(123475, "Juan Ramirez", 12.65);

            // Inserta la cuenta en la base de datos
            manager.persist(cuenta);

            // Confirma la transacción y guarda los cambios
            manager.getTransaction().commit();

            // Muestra un mensaje de confirmación
            System.out.println("Cuenta guardada correctamente");
        }

        // Ejecuta la interfaz gráfica en el hilo de eventos de Swing
        java.awt.EventQueue.invokeLater(() -> {

            // Crea la ventana principal
            View_Cuenta vista = new View_Cuenta();

            // Crea el controlador y lo relaciona con la vista
            CuentaController controlador
                    = new CuentaController(vista);

            // Ubica la ventana en el centro de la pantalla
            vista.setLocationRelativeTo(null);

            // Hace visible la interfaz gráfica
            vista.setVisible(true);

            // Agrega un listener para los eventos de la ventana
            vista.addWindowListener(new java.awt.event.WindowAdapter() {

            });
        });
    }
}
