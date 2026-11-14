package com.mycompany.act1_cuentaahorros;

import com.mycompany.act1_cuentaahorros.entity.*;
import com.mycompany.act1_cuentaahorros.repository.CuentaRepositorio;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PruebaConsultas {

    private static final String UNIDAD =
            "com.mycompany_Act1_cuentaAhorros_jar_1.0-SNAPSHOTPU";

    public static void main(String[] args) {

        cargarDatos();

        CuentaRepositorio repositorio = new CuentaRepositorio();

        System.out.println("\n=== 1. SUCURSAL Y SALDO MÍNIMO ===");

        List<Cuenta_Ahorros> cuentas =
                repositorio.buscarPorSucursalYSaldo("Centro-Prueba", 300.0);

        for (Cuenta_Ahorros cuenta : cuentas) {
            System.out.println(
                    "Cuenta: " + cuenta.getNumeroCuenta()
                    + " | Sucursal: " + cuenta.getSucursal().getNombre()
                    + " | Saldo: " + cuenta.getSaldo()
            );
        }

        System.out.println("\n=== 2. CUENTAS CON TARJETA ACTIVA ===");

        for (Cuenta_Ahorros cuenta
                : repositorio.buscarConTarjetaActiva()) {

            System.out.println(
                    "Cuenta: " + cuenta.getNumeroCuenta()
                    + " | Tarjeta: " + cuenta.getTarjeta().getNumero()
                    + " | Activa: " + cuenta.getTarjeta().getActiva()
            );
        }

        int idCuenta = obtenerIdCuentaPrueba();

        System.out.println("\n=== 3. MOVIMIENTOS DE NOVIEMBRE ===");

        List<Movimiento> movimientos =
                repositorio.buscarMovimientosPorPeriodo(
                        idCuenta,
                        LocalDateTime.of(2026, 11, 1, 0, 0),
                        LocalDateTime.of(2026, 12, 1, 0, 0)
                );

        for (Movimiento movimiento : movimientos) {
            System.out.println(
                    "Cuenta: " + movimiento.getCuenta().getNumeroCuenta()
                    + " | Fecha: " + movimiento.getFecha()
                    + " | Tipo: " + movimiento.getTipo()
                    + " | Monto: " + movimiento.getMonto()
            );
        }

        System.out.println("\n=== 4. PAGINACIÓN: PÁGINA 1 ===");
        mostrarPagina(repositorio.listarPaginado(1));

        System.out.println("\n=== 4. PAGINACIÓN: PÁGINA 2 ===");
        mostrarPagina(repositorio.listarPaginado(2));
    }

    private static void cargarDatos() {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory(UNIDAD);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Long existentes = em.createQuery(
                    "SELECT COUNT(c) FROM Cliente c "
                    + "WHERE c.cedula = :cedula",
                    Long.class
            )
                    .setParameter("cedula", "0000000001")
                    .getSingleResult();

            if (existentes > 0) {
                em.getTransaction().commit();
                System.out.println(
                        "Los datos de prueba ya están cargados.");
                return;
            }

            Cliente ana = new Cliente(
                    "0000000001", "Ana Prueba", "ana@example.com");

            Cliente bruno = new Cliente(
                    "0000000002", "Bruno Prueba", "bruno@example.com");

            Sucursal centro = new Sucursal(
                    "Centro-Prueba", "Dirección de prueba Centro");

            Sucursal norte = new Sucursal(
                    "Norte-Prueba", "Dirección de prueba Norte");

            em.persist(ana);
            em.persist(bruno);
            em.persist(centro);
            em.persist(norte);

            double[] saldos = {
                100.0, 250.0, 300.0, 450.0, 600.0, 800.0, 1000.0
            };

            for (int i = 0; i < saldos.length; i++) {

                Cliente cliente = i < 4 ? ana : bruno;
                Sucursal sucursal = i < 5 ? centro : norte;

                Cuenta_Ahorros cuenta = new Cuenta_Ahorros(
                        900001 + i,
                        cliente.getNombre(),
                        saldos[i]
                );

                cliente.agregarCuenta(cuenta);
                sucursal.agregarCuenta(cuenta);

                em.persist(cuenta);

                // Las primeras tres cuentas reciben una tarjeta.
                if (i < 3) {
                    TarjetaDebito tarjeta = new TarjetaDebito(
                            "999900000000000" + i,
                            LocalDate.of(2029, 12, 31),
                            cuenta
                    );

                    // La segunda tarjeta queda inactiva.
                    if (i == 1) {
                        tarjeta.bloquear();
                    }

                    cuenta.setTarjeta(tarjeta);
                    em.persist(tarjeta);
                }

                // Movimientos para la primera cuenta.
                if (i == 0) {
                    guardarMovimiento(
                            em, cuenta,
                            LocalDateTime.of(2026, 10, 31, 23, 59),
                            "DEPOSITO", "25.00");

                    guardarMovimiento(
                            em, cuenta,
                            LocalDateTime.of(2026, 11, 1, 0, 0),
                            "DEPOSITO", "100.00");

                    guardarMovimiento(
                            em, cuenta,
                            LocalDateTime.of(2026, 11, 20, 15, 30),
                            "RETIRO", "25.00");

                    guardarMovimiento(
                            em, cuenta,
                            LocalDateTime.of(2026, 12, 1, 0, 0),
                            "DEPOSITO", "10.00");
                }
            }

            em.getTransaction().commit();
            System.out.println("Datos de prueba guardados.");

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;

        } finally {
            em.close();
            emf.close();
        }
    }

    private static void guardarMovimiento(
            EntityManager em,
            Cuenta_Ahorros cuenta,
            LocalDateTime fecha,
            String tipo,
            String monto) {

        Movimiento movimiento = new Movimiento(
                fecha, tipo, new BigDecimal(monto), cuenta);

        cuenta.agregarMovimiento(movimiento);
        em.persist(movimiento);
    }

    private static int obtenerIdCuentaPrueba() {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory(UNIDAD);

        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT c.id FROM Cuenta_Ahorros c "
                    + "WHERE c.numeroCuenta = :numero "
                    + "AND c.cliente.cedula = :cedula",
                    Integer.class
            )
                    .setParameter("numero", 900001)
                    .setParameter("cedula", "0000000001")
                    .getSingleResult();

        } finally {
            em.close();
            emf.close();
        }
    }

    private static void mostrarPagina(List<Cuenta_Ahorros> cuentas) {

        for (Cuenta_Ahorros cuenta : cuentas) {
            String cliente = cuenta.getCliente() == null
                    ? "Sin cliente"
                    : cuenta.getCliente().getNombre();

            System.out.println(
                    "Cuenta: " + cuenta.getNumeroCuenta()
                    + " | Cliente: " + cliente
                    + " | Saldo: " + cuenta.getSaldo()
            );
        }
    }
}