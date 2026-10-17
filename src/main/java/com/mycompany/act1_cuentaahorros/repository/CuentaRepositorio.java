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
public class CuentaRepositorio {

    private final EntityManagerFactory emf
            = Persistence.createEntityManagerFactory(
                    "com.mycompany_Act1_cuentaAhorros_jar_1.0-SNAPSHOTPU"
            );

    public void crear(Cuenta_Ahorros cuenta) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(cuenta);
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

    public Cuenta_Ahorros seleccionar(int id) {

        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Cuenta_Ahorros.class, id);
        } finally {
            em.close();
        }
    }

    public void actualizar(Cuenta_Ahorros cuenta) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.merge(cuenta);

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

    public void eliminar(int id) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Cuenta_Ahorros cuenta
                    = em.find(Cuenta_Ahorros.class, id);

            if (cuenta != null) {
                em.remove(cuenta);
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
