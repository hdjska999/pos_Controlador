package controlador;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import conexion.Conexion;
import vista.VistaFacturacion;

public class VentaTXT {

    private String nombreCliente;
    private String cedulaCliente;
    private String telefonoCliente;
    private String direccionCliente;
    private String fechaActual = "";
    private String nombreArchivoTXTVenta = "";

    // Método para obtener los datos del cliente
    public void DatosClientes(int idCliente) {
        Connection cn = Conexion.conectar();
        String sql = "SELECT * FROM tb_cliente WHERE idCliente = '" + idCliente + "'";
        Statement st;
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                nombreCliente = rs.getString("nombre") + " " + rs.getString("apellido");
                cedulaCliente = rs.getString("cedula");
                telefonoCliente = rs.getString("telefono");
                direccionCliente = rs.getString("direccion");
            }
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para generar la factura en archivo de texto (.txt)
    public void generarFacturaTXT() {
        try {
            // Cargar la fecha actual
            Date date = new Date();
            fechaActual = new SimpleDateFormat("yyyy/MM/dd").format(date);
            // Cambiar el formato de la fecha de / a _
            String fechaNueva = fechaActual.replace("/", "_");
            nombreArchivoTXTVenta = "Venta_" + nombreCliente + "_" + fechaNueva + ".txt";

            // Crear el archivo de texto
            File directory = new File("src/txt");
            if (!directory.exists()) {
                directory.mkdirs(); // Crear directorio si no existe
            }

            File file = new File("src/txt/" + nombreArchivoTXTVenta);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            // Agregar la información de la factura al archivo de texto
            writer.write("Factura: 001\n");
            writer.write("Fecha: " + fechaActual + "\n\n");

            // Datos de la empresa
            String ruc = "0987654321001";
            String nombre = "©SoFIGang";
            String telefono = "0987654321";
            String direccion = "Gotham City";
            String razon = "Software que Impulsa tu Negocio";

            writer.write("RUC: " + ruc + "\n");
            writer.write("Nombre: " + nombre + "\n");
            writer.write("Telefono: " + telefono + "\n");
            writer.write("Direccion: " + direccion + "\n");
            writer.write("Razon Social: " + razon + "\n");
            writer.write("\n");

            // Datos del cliente
            writer.write("Datos del cliente:\n");
            writer.write("Cedula/RUC: " + cedulaCliente + "\n");
            writer.write("Nombre: " + nombreCliente + "\n");
            writer.write("Telefono: " + telefonoCliente + "\n");
            writer.write("Direccion: " + direccionCliente + "\n");
            writer.write("\n");

            // Productos
            writer.write("Productos:\n");
            writer.write("Cantidad | Descripcion | Precio Unitario | Precio Total\n");

            double totalFactura = 0.0;  // Variable para acumular el total

            for (int i = 0; i < VistaFacturacion.table.getRowCount(); i++) {
                String producto = VistaFacturacion.table.getValueAt(i, 1).toString();
                String cantidadStr = VistaFacturacion.table.getValueAt(i, 2).toString();
                String precioStr = VistaFacturacion.table.getValueAt(i, 3).toString();
                String totalStr = VistaFacturacion.table.getValueAt(i, 7).toString();

                // Convertir cantidad, precio y total a valores numéricos para hacer la suma
                double cantidad = Double.parseDouble(cantidadStr);
                double precio = Double.parseDouble(precioStr);
                double total = Double.parseDouble(totalStr);

                // Escribir la línea del producto
                writer.write(cantidad + " | " + producto + " | " + precio + " | " + total + "\n");

                // Sumar el total de este producto al total de la factura
                totalFactura += total;
            }

            // Total a pagar
            writer.write("\nTotal a pagar: $" + String.format("%.2f", totalFactura) + "\n");


            // Firma
            writer.write("\nCancelacion y firma:\n");
            writer.write("\n");
            writer.write("_______________________\n");

            // Mensaje de agradecimiento
            writer.write("\n¡Gracias por su compra!\n");

            // Cerrar el escritor
            writer.close();

            // Verificar si el archivo se creó y abrirlo
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("No se pudo crear el archivo de texto.");
            }

        } catch (IOException e) {
            System.out.println("Error al generar el archivo: " + e);
        }
    }
}
