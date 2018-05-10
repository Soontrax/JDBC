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
            while (!salir) {
                JOptionPane.showMessageDialog(null, "Bienvenido al Gestor de bas"
                        + "e de datos del IES Francesc Borja Moll");
                System.out.println("1. Consultar la Base de Datos");
                System.out.println("2. Actualizar la Base de Datos(UPDATE)");
                System.out.println("3. Insertar datos(INSERT)");
                System.out.println("4. Salir");
                int opcion = Integer.parseInt(JOptionPane.showInputDialog("Que o"
                        + "pción quieres"));
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

    public static void ConsultarBasedeDatos(Connection conexion) throws SQLException {
        JOptionPane.showMessageDialog(null, "Has accedido a la opción Consul"
                + "tar Base de Datos");
        ResultSet resultado = null;
        Statement st = conexion.createStatement();
        String tabla = JOptionPane.showInputDialog("Que tabla quieres v"
                + "er");
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
        st.close();
        resultado.close();
    }

    public static void ActualizarBasedeDatos(Connection conexion) throws SQLException {
        JOptionPane.showMessageDialog(null, "Has accedido a la opción Actualizar"
                + " Base de Datos");
        ResultSet resultado = null;

        Statement st = conexion.createStatement();
        String Tabla = JOptionPane.showInputDialog("Dime la tabla que quieres ver");
        String columna = JOptionPane.showInputDialog("Dime una columna de l"
                + "a tabla " + Tabla);
        String valor = JOptionPane.showInputDialog("Dime el valor de la colu"
                + "mna " + columna + " que quieres cambiar");
        String cambio = JOptionPane.showInputDialog("Ahora dime por cual va"
                + "lor quieres sustituir el valor " + valor);
        int row = st.executeUpdate("UPDATE " + Tabla + " " + "SET " + columna
                + " " + " = '" + cambio + "' WHERE " + columna + " " + " = '" + valor + "' ");
        System.out.println("El numero de filas afectadas ha sido " + row);
        resultado.close();
        st.close();
    }

    public static void InsertardatosBasedeDatos(Connection conexion) throws SQLException {
        JOptionPane.showMessageDialog(null, "Has accedido a la opción Insertar "
                + "Datos");
        ResultSet resultado = null;
        conexion.setAutoCommit(false);
        try {
            Statement st = conexion.createStatement();
            String Tabla = JOptionPane.showInputDialog("Dime la tabla que quier"
                    + "es ver");
            String Valor1 = JOptionPane.showInputDialog("Escribe el primer valor"
                    + " para la tabla " + Tabla);
            String Valor2 = JOptionPane.showInputDialog("Escribe el segundo val"
                    + "or para la tabla " + Tabla);
            /**
             *
             */
            String Tabla1 = JOptionPane.showInputDialog("Dime la segunda tabla q"
                    + "ue quieres ver");
            String Valor3 = JOptionPane.showInputDialog("Escribe el primer val"
                    + "or para la segunda tabla " + Tabla1);
            String Valor4 = JOptionPane.showInputDialog("Escribe el segundo val"
                    + "or para la segunda tabla " + Tabla1);
            int update = st.executeUpdate("INSERT INTO " + Tabla + " " + "VALUES ('" + Valor1 + "' , '" + Valor2 + "')");
            int update1 = st.executeUpdate("INSERT INTO " + Tabla1 + " " + "VALUES ('" + Valor3 + "' , '" + Valor4 + "')");
            conexion.commit();
            System.out.println("El numero de filas afectadas ha sido " + update);
            st.close();
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            conexion.rollback();
            System.out.println("Los cambios no se han podido establecer correctamente");
        }
    }
}