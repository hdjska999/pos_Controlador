package controlador;

import com.itextpdf.io.image.ImageData;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import conexion.Conexion;
import java.io.FileNotFoundException;
import java.sql.*;
import javax.swing.JOptionPane;

public class Reportes {
	
	    public void ReporteClientes() {
	    	String ruta = System.getProperty("user.home") + "/Desktop/Reporte_Clientes.pdf";
	        try {
	            // Crear PdfWriter y PdfDocument
	            PdfWriter writer = new PdfWriter(ruta);
	            PdfDocument pdf = new PdfDocument(writer);
	            Document documento = new Document(pdf);

	            // Agregar imagen al encabezado
	            String imagePath = "src/img/_ Small.png";
	            ImageData imageData = ImageDataFactory.create(imagePath);
	            Image header = new Image(imageData);
	            header.setWidth(650);
	            header.setAutoScale(true);
	            documento.add(header);

	            // Formato al texto
	            Paragraph parrafo = new Paragraph("Reporte creado por \nX © SoFIGang\n\n")
	            		.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
	                    .setFontSize(18)
	                    .setBold()
	                    .setFontColor(new DeviceGray(0.3f));
	            parrafo.add("Reporte de Clientes \n\n");
	            documento.add(parrafo);

	            // Crear tabla con 5 columnas
	            float[] columnWidths = {1, 3, 3, 3, 4};
	            Table tabla = new Table(columnWidths);
	            tabla.addCell("Codigo");
	            tabla.addCell("Nombres");
	            tabla.addCell("Cedula");
	            tabla.addCell("Telefono");
	            tabla.addCell("Direccion");

	            // Consultar datos y llenar la tabla
	            try {
	                Connection cn = Conexion.conectar();
	                PreparedStatement pst = cn.prepareStatement(
	                        "SELECT idCliente, CONCAT(nombre, ' ', apellido) AS nombres, cedula, telefono, direccion FROM tb_cliente");
	                ResultSet rs = pst.executeQuery();
	                while (rs.next()) {
	                    tabla.addCell(rs.getString(1));
	                    tabla.addCell(rs.getString(2));
	                    tabla.addCell(rs.getString(3));
	                    tabla.addCell(rs.getString(4));
	                    tabla.addCell(rs.getString(5));
	                }
	            } catch (SQLException e) {
	                System.out.println("Error al obtener los datos: " + e);
	            }

	            // Agregar tabla al documento
	            documento.add(tabla);

	            // Cerrar documento
	            documento.close();

	            JOptionPane.showMessageDialog(null, "Reporte creado exitosamente en " + ruta);
	        } catch (FileNotFoundException e) {
	            System.out.println("Error al crear archivo: " + e);
	        } catch (Exception e) {
	            System.out.println("Error general: " + e);
	        }
	    }
	    
	    public void ReporteProductos() {
	        String ruta = System.getProperty("user.home") + "/Desktop/Reporte_Productos.pdf";
	        try {
	            // Crear PdfWriter y PdfDocument
	            PdfWriter writer = new PdfWriter(ruta);
	            PdfDocument pdf = new PdfDocument(writer);
	            Document documento = new Document(pdf);

	            // Agregar imagen al encabezado
	            String imagePath = "src/img/Notion Banner ☆彡 Small.png";
	            ImageData imageData = ImageDataFactory.create(imagePath);
	            Image header = new Image(imageData);
	            header.setWidth(650);
	            header.setAutoScale(true);
	            header.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER); // Centrado
	            documento.add(header);

	            // Formato al texto
	            Paragraph parrafo = new Paragraph("Reporte creado por \nX © SoFIGang\n\n")
	                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
	                    .setFontSize(18)
	                    .setBold()
	                    .setFontColor(DeviceGray.GRAY);
	            parrafo.add("Reporte de Productos \n\n");
	            documento.add(parrafo);

	            // Crear tabla con las columnas especificadas
	            float[] columnWidths = {3, 5, 4, 5, 7, 5, 6};
	            Table tabla = new Table(columnWidths);
	            tabla.addCell("Codigo");
	            tabla.addCell("Nombre");
	            tabla.addCell("Cant.");
	            tabla.addCell("Precio");
	            tabla.addCell("Descripcion");
	            tabla.addCell("Por. Iva");
	            tabla.addCell("Categoria");

	            // Consultar datos y llenar la tabla
	            try {
	                Connection cn = Conexion.conectar();
	                PreparedStatement pst = cn.prepareStatement(
	                        "SELECT p.idProducto, p.nombre, p.cantidad, p.precio, p.descripcion, "
	                                + "p.porcentajeIva, c.descripcion AS categoria, p.estado "
	                                + "FROM tb_producto AS p, tb_categoria AS c "
	                                + "WHERE p.idCategoria = c.idCategoria;");
	                ResultSet rs = pst.executeQuery();
	                while (rs.next()) {
	                    tabla.addCell(rs.getString(1)); // Codigo
	                    tabla.addCell(rs.getString(2)); // Nombre
	                    tabla.addCell(rs.getString(3)); // Cantidad
	                    tabla.addCell(rs.getString(4)); // Precio
	                    tabla.addCell(rs.getString(5)); // Descripción
	                    tabla.addCell(rs.getString(6)); // Porcentaje de IVA
	                    tabla.addCell(rs.getString(7)); // Categoría
	                }
	            } catch (SQLException e) {
	                System.out.println("Error al obtener los datos: " + e);
	            }

	            // Agregar la tabla al documento
	            documento.add(tabla);

	            // Cerrar documento
	            documento.close();

	            JOptionPane.showMessageDialog(null, "Reporte creado exitosamente en " + ruta);
	        } catch (FileNotFoundException e) {
	            System.out.println("Error al crear el archivo: " + e);
	        } catch (Exception e) {
	            System.out.println("Error general: " + e);
	        }
	    }
	    
	    public void ReporteCategorias() {
	        String ruta = System.getProperty("user.home") + "/Desktop/Reporte_Categorias.pdf";
	        try {
	            // Crear PdfWriter y PdfDocument
	            PdfWriter writer = new PdfWriter(ruta);
	            PdfDocument pdf = new PdfDocument(writer);
	            Document documento = new Document(pdf);

	            // Agregar imagen al encabezado
	            String imagePath = "src/img/_ Small 3.png";
	            ImageData imageData = ImageDataFactory.create(imagePath);
	            Image header = new Image(imageData);
	            header.setWidth(650);
	            header.setAutoScale(true);
	            header.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER); // Centrado
	            documento.add(header);

	            // Formato al texto
	            Paragraph parrafo = new Paragraph("Reporte creado por \nX © SoFIGang\n\n")
	                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
	                    .setFontSize(18)
	                    .setBold()
	                    .setFontColor(DeviceGray.GRAY);
	            parrafo.add("Reporte de Categorías \n\n");
	            documento.add(parrafo);

	            // Crear tabla con 3 columnas
	            Table tabla = new Table(3); // 3 columnas
	            tabla.addCell("Codigo");
	            tabla.addCell("Descripcion");
	            tabla.addCell("Estado");

	            // Consultar datos y llenar la tabla
	            try {
	                Connection cn = Conexion.conectar();
	                PreparedStatement pst = cn.prepareStatement("SELECT * FROM tb_categoria");
	                ResultSet rs = pst.executeQuery();
	                while (rs.next()) {
	                    tabla.addCell(rs.getString(1)); // Código
	                    tabla.addCell(rs.getString(2)); // Descripción
	                    tabla.addCell(rs.getString(3)); // Estado
	                }
	            } catch (SQLException e) {
	                System.out.println("Error al obtener los datos: " + e);
	            }

	            // Agregar la tabla al documento
	            documento.add(tabla);

	            // Cerrar documento
	            documento.close();

	            JOptionPane.showMessageDialog(null, "Reporte creado exitosamente en " + ruta);
	        } catch (FileNotFoundException e) {
	            System.out.println("Error al crear el archivo: " + e);
	        } catch (Exception e) {
	            System.out.println("Error general: " + e);
	        }
	    }
	    
	    public void ReporteVentas() {
	        String ruta = System.getProperty("user.home") + "/Desktop/Reporte_Ventas.pdf";
	        try {
	            // Crear PdfWriter y PdfDocument
	            PdfWriter writer = new PdfWriter(ruta);
	            PdfDocument pdf = new PdfDocument(writer);
	            Document documento = new Document(pdf);

	            // Agregar imagen al encabezado
	            String imagePath = "src/img/_ Small 2.png";
	            ImageData imageData = ImageDataFactory.create(imagePath);
	            Image header = new Image(imageData);
	            header.setWidth(650);
	            header.setAutoScale(true);
	            header.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER); // Centrado
	            documento.add(header);

	            // Formato al texto
	            Paragraph parrafo = new Paragraph("Reporte creado por \\nX © SoFIGang\\n\\n")
	                    .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
	                    .setFontSize(18)
	                    .setBold()
	                    .setFontColor(DeviceGray.GRAY);
	            parrafo.add("Reporte de Ventas \n\n");
	            documento.add(parrafo);

	            // Crear tabla con las columnas especificadas
	            float[] columnWidths = {3, 9, 4, 5, 3};
	            Table tabla = new Table(columnWidths);
	            tabla.addCell("Codigo");
	            tabla.addCell("Cliente");
	            tabla.addCell("Tot. Pagar");
	            tabla.addCell("Fecha Venta");
	            tabla.addCell("Estado");

	            // Consultar datos y llenar la tabla
	            try {
	                Connection cn = Conexion.conectar();
	                PreparedStatement pst = cn.prepareStatement(
	                        "SELECT cv.idCabeceraVenta AS id, CONCAT(c.nombre, ' ', c.apellido) AS cliente, "
	                                + "cv.valorPagar AS total, cv.fechaVenta AS fecha, cv.estado "
	                                + "FROM tb_cabecera_venta AS cv, tb_cliente AS c "
	                                + "WHERE cv.idCliente = c.idCliente;");
	                ResultSet rs = pst.executeQuery();
	                while (rs.next()) {
	                    tabla.addCell(rs.getString(1)); // Codigo
	                    tabla.addCell(rs.getString(2)); // Cliente
	                    tabla.addCell(rs.getString(3)); // Total Pagar
	                    tabla.addCell(rs.getString(4)); // Fecha Venta
	                    tabla.addCell(rs.getString(5)); // Estado
	                }
	            } catch (SQLException e) {
	                System.out.println("Error al obtener los datos: " + e);
	            }

	            // Agregar la tabla al documento
	            documento.add(tabla);

	            // Cerrar documento
	            documento.close();

	            JOptionPane.showMessageDialog(null, "Reporte creado exitosamente en " + ruta);
	        } catch (FileNotFoundException e) {
	            System.out.println("Error al crear el archivo: " + e);
	        } catch (Exception e) {
	            System.out.println("Error general: " + e);
	        }
	    }
}
