/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.act1_cuentaahorros.repository;

import com.mycompany.act1_cuentaahorros.entity.Cuenta_Ahorros;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 *
 * @author LENOVO
 */
//CREcion del repositorio para el uso de los metodos
public class CuentaRepositorio {

   
    // Configura la conexión con la base de datos
    private final EntityManagerFactory emf
            = Persistence.createEntityManagerFactory(
                    "com.mycompany_Act1_cuentaAhorros_jar_1.0-SNAPSHOTPU"
            );

    // Crea una nueva cuenta de ahorros
    public void crear(Cuenta_Ahorros cuenta) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(cuenta); // Guarda la cuenta
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Revierte los cambios
            }
            throw e;
        } finally {
            em.close(); // Cierra el EntityManager
        }
    }

    // Busca una cuenta por su ID
    public Cuenta_Ahorros seleccionar(int id) {

        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Cuenta_Ahorros.class, id);
        } finally {
            em.close();
        }
    }

    // Actualiza los datos de una cuenta
    public void actualizar(Cuenta_Ahorros cuenta) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.merge(cuenta); // Actualiza la cuenta

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    // Elimina una cuenta mediante su ID
    public void eliminar(int id) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Busca la cuenta que se desea eliminar
            Cuenta_Ahorros cuenta
                    = em.find(Cuenta_Ahorros.class, id);

            if (cuenta != null) {
                em.remove(cuenta); // Elimina si existe
            }

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    // Obtiene todas las cuentas registradas
    public List<Cuenta_Ahorros> listar() {

        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT c FROM Cuenta_Ahorros c",
                    Cuenta_Ahorros.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

}
