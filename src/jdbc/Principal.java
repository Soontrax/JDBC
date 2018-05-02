/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author infor05
 */
public class Principal {

    public static void main(String[] args) {
        try {
            boolean salir = false;
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/beer", "root", "12345");
            conexion.setAutoCommit(false);
            while (!salir) {
                JOptionPane.showMessageDialog(null, "Bienvenido al Gestor de base de datos del IES Francesc Borja Moll");
                System.out.println("1. Consultar la Base de Datos");
                System.out.println("2. Actualizar la Base de Datos(UPDATE)");
                System.out.println("3. Insertar datos a la Base de Datos(INSERT)");
                System.out.println("4. Salir");
                int opcion = Integer.parseInt(JOptionPane.showInputDialog("Que opción quieres"));
                switch (opcion) {
                    case 1:
                        Principal.ConsultarBasedeDatos(conexion);
                        break;
                    case 2:
                        Principal.ActualizarBasedeDatos(conexion);
                        break;
                    case 3:
                        Principal.InsertardatosBasedeDatos(conexion);
                        break;
                    case 4:
                        salir = true;
                        JOptionPane.showMessageDialog(null, "Que pases un buen dia");
                }
            }
        } catch (SQLException ex) {
            System.out.println("La conexión no se ha establecido correctamente");
        }
    }

    public static void ConsultarBasedeDatos(Connection conexion) {
        try {
            JOptionPane.showMessageDialog(null, "Has accedido a la opción Consultar Base de Datos");
            ResultSet resultado;
            try (Statement st = conexion.createStatement()) {
                resultado = st.executeQuery("SELECT * FROM bar");
                String nombre;
                String direccion;
                while (resultado.next()) {
                    nombre = resultado.getString("name");
                    direccion = resultado.getString("address");
                    System.out.println(nombre);
                    System.out.println(direccion);
                }
            }
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, "La consulta no se ha hayado correctamente");
        }

    }

    public static void ActualizarBasedeDatos(Connection conexion) {
        JOptionPane.showMessageDialog(null, "Has accedido a la opción Actualizar Base de Datos");
        ResultSet resultado;
        try {
            Statement st = conexion.createStatement();
            int row = st.executeUpdate("UPDATE bar SET name = 'Pepe' WHERE name = 'Satisfaction'");
            System.out.println("El numero de filas afectadas ha sido" + row);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, "No se ha actualizado correctamente");
        }
    }

    public static void InsertardatosBasedeDatos(Connection conexion) {
        JOptionPane.showMessageDialog(null, "Has accedido a la opción Insertar Datos");
        ResultSet resultado;
        try {
            Statement st = conexion.createStatement();
            int update = st.executeUpdate("INSERT INTO bar " + "VALUES('Antonio' , 'Algaida')");
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}