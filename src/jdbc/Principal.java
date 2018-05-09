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
            ResultSet resultado = null;
            try (Statement st = conexion.createStatement()) {
                String tabla = JOptionPane.showInputDialog("Que tabla quieres ver");
                System.out.println("TODAS LAS COLUMNAS     PULSA 1");
                System.out.println("VARIAS COLUMNAS        PULSA 2");
                int columnas = Integer.parseInt(JOptionPane.showInputDialog("Cuantas columnas quieres ver"));
                switch (columnas) {
                    case 1:
                        resultado = st.executeQuery("SELECT * FROM " + tabla);
                        ResultSetMetaData md = resultado.getMetaData();
                        for (int i = 1; i <= md.getColumnCount(); i++) {
                            System.out.print(md.getColumnName(i) + "\t");
                        }
                        while (resultado.next()) {
                            System.out.println(" ");
                            for (int i = 1; i <= md.getColumnCount(); i++) {
                                System.out.print(resultado.getString(i) + "\t");
                            }
                        }
                    case 2:

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
            String Tabla = JOptionPane.showInputDialog("Dime la tabla que quieres ver");
            String columna = JOptionPane.showInputDialog("Dime una columna de la tabla " + Tabla);
            String valor = JOptionPane.showInputDialog("Dime el valor de la columna " + columna + " que quieres cambiar");
            String cambio = JOptionPane.showInputDialog("Ahora dime por cual valor quieres sustituir el valor " + valor);
            int row = st.executeUpdate("UPDATE " +Tabla+ " " + "SET " +columna+ " " + " = '"+cambio+"' WHERE " +columna+ " " + " = '"+valor+"' ");
            conexion.commit();
            System.out.println("El numero de filas afectadas ha sido " + row);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, "No se ha actualizado correctamente");
        }
    }

    public static void InsertardatosBasedeDatos(Connection conexion) {
        JOptionPane.showMessageDialog(null, "Has accedido a la opción Insertar Datos");
        ResultSet resultado;
        try {
            Statement st = conexion.createStatement();
            String Tabla = JOptionPane.showInputDialog("Dime la tabla que quieres ver");
            String Valor1 = JOptionPane.showInputDialog("Escribe el primer valor para la tabla "+Tabla);
            String Valor2 = JOptionPane.showInputDialog("Escribe el segundo valor para la tabla "+Tabla);
            int update = st.executeUpdate("INSERT INTO "+Tabla+ " " + "VALUES ('"+Valor1+"' , '"+Valor2+"')");
            conexion.commit();
            System.out.println("El numero de filas afectadas ha sido " +update);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}