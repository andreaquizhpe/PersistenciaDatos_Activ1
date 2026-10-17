/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.act1_cuentaahorros.controller;

import com.mycompany.act1_cuentaahorros.entity.Cuenta_Ahorros;
import com.mycompany.act1_cuentaahorros.entity.view.View_Cuenta;
import com.mycompany.act1_cuentaahorros.repository.CuentaRepositorio;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class CuentaController {

    private final View_Cuenta view;
    private final CuentaRepositorio repo;
    private int idSeleccionado = -1;

    public CuentaController(View_Cuenta view) {

        this.view = view;
        this.repo = new CuentaRepositorio();

        view.getBtnCrear().addActionListener(e -> crear());
        view.getBtnConsultar().addActionListener(e -> limpiar());
        view.getBtnLimpiar().addActionListener(e -> actualizar());
        view.getBtnEliminar().addActionListener(e -> eliminar());

        view.getTblCuentas().getSelectionModel()
                .addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        seleccionarFila();
                    }
                });
    }
 private void crear() {
        try {
            int numero = Integer.parseInt(
                    view.getTxtNumeroCuenta().getText().trim());

            String titular = view.getTxtTitular().getText().trim();

            double saldo = Double.parseDouble(
                    view.getTxtSaldo().getText().trim());

            if (titular.isEmpty() || saldo < 0) {
                JOptionPane.showMessageDialog(view,
                        "Verifique los datos ingresados.");
                return;
            }

            Cuenta_Ahorros cuenta =
                    new Cuenta_Ahorros(numero, titular, saldo);

            repo.crear(cuenta);

            JOptionPane.showMessageDialog(view,
                    "Cuenta creada correctamente.");

            consultar();
            limpiar();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view,
                    "Número de cuenta o saldo inválido.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    "Error al crear: " + e.getMessage());
        }
    }

    private void consultar() {
        try {
            List<Cuenta_Ahorros> cuentas = repo.listar();

            DefaultTableModel modelo = new DefaultTableModel(
                    new Object[]{"ID", "Número", "Titular", "Saldo"}, 0) {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            for (Cuenta_Ahorros cuenta : cuentas) {
                modelo.addRow(new Object[]{
                    cuenta.getId(),
                    cuenta.getNumeroCuenta(),
                    cuenta.getTitular(),
                    cuenta.getSaldo()
                });
            }

            view.getTblCuentas().setModel(modelo);
            idSeleccionado = -1;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    "Error al consultar: " + e.getMessage());
        }
    }

    private void seleccionarFila() {
        int fila = view.getTblCuentas().getSelectedRow();

        if (fila == -1) {
            return;
        }

        int filaModelo = view.getTblCuentas()
                .convertRowIndexToModel(fila);

        DefaultTableModel modelo =
                (DefaultTableModel) view.getTblCuentas().getModel();

        idSeleccionado = Integer.parseInt(
                modelo.getValueAt(filaModelo, 0).toString());

        view.getTxtNumeroCuenta().setText(
                modelo.getValueAt(filaModelo, 1).toString());

        view.getTxtTitular().setText(
                modelo.getValueAt(filaModelo, 2).toString());

        view.getTxtSaldo().setText(
                modelo.getValueAt(filaModelo, 3).toString());
    }

    private void actualizar() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(view,
                    "Seleccione una cuenta de la tabla.");
            return;
        }

        try {
            Cuenta_Ahorros cuenta =
                    repo.seleccionar(idSeleccionado);

            if (cuenta == null) {
                JOptionPane.showMessageDialog(view,
                        "La cuenta ya no existe.");
                consultar();
                return;
            }

            int numero = Integer.parseInt(
                    view.getTxtNumeroCuenta().getText().trim());

            String titular = view.getTxtTitular().getText().trim();

            double saldo = Double.parseDouble(
                    view.getTxtSaldo().getText().trim());

            if (titular.isEmpty() || saldo < 0) {
                JOptionPane.showMessageDialog(view,
                        "Verifique los datos ingresados.");
                return;
            }

            cuenta.setNumeroCuenta(numero);
            cuenta.setTitular(titular);
            cuenta.setSaldo(saldo);

            repo.actualizar(cuenta);

            JOptionPane.showMessageDialog(view,
                    "Cuenta actualizada correctamente.");

            consultar();
            limpiar();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view,
                    "Número de cuenta o saldo inválido.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    "Error al actualizar: " + e.getMessage());
        }
    }

    private void eliminar() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(view,
                    "Seleccione una cuenta de la tabla.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                view,
                "¿Desea eliminar esta cuenta?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            repo.eliminar(idSeleccionado);

            JOptionPane.showMessageDialog(view,
                    "Cuenta eliminada correctamente.");

            consultar();
            limpiar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    "Error al eliminar: " + e.getMessage());
        }
    }

    private void limpiar() {
        idSeleccionado = -1;
        view.getTblCuentas().clearSelection();
        view.getTxtNumeroCuenta().setText("");
        view.getTxtTitular().setText("");
        view.getTxtSaldo().setText("");
    }

//    public void cerrar() {
//        repo.cerrar();
//    }
}
