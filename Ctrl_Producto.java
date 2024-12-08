package controlador;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import conexion.Conexion;
import modelo.Producto;

public class Ctrl_Producto {
	//metodo para registrar nuevo producto
		public boolean guardar(Producto objeto) {
			boolean respuesta=false;
			Connection cn=conexion.Conexion.conectar();
			try {
				PreparedStatement consulta=cn.prepareStatement("insert into tb_producto values (?,?,?,?,?,?,?,?)");
				consulta.setInt(1, 0);
				consulta.setString(2, objeto.getNombre());
				consulta.setInt(3, objeto.getCantidad());
				consulta.setDouble(4, objeto.getPrecio());
				consulta.setString(5, objeto.getDescripcion());
				consulta.setInt(6, objeto.getPorcentajeIva());
				consulta.setInt(7, objeto.getIdCategoria());
				consulta.setInt(8, objeto.getEstado());
				
				if(consulta.executeUpdate()>0) {
					respuesta=true;
				}
				cn.close();
			}catch(SQLException e) {
				System.out.println("Error al guardar producto: "+ e);
			}
			return respuesta;
		}
		
		//metodo para consultar existencia de producto
			public boolean existeProducto(String producto) {
				boolean respuesta=false;
				String sql="select nombre from tb_producto where descripcion= '" + producto +"'";
				Statement st;
				try {
					Connection cn=Conexion.conectar();
					st=cn.createStatement();
					ResultSet rs=st.executeQuery(sql);
					while(rs.next()) {
						respuesta=true;
					}
					
				}catch(SQLException e) {
					System.out.println("Error al eliminar producto: "+ e);
				}
				return respuesta;
			}
			
			//metodo para actualizar producto
			public boolean actualizar(Producto objeto, int idProducto) {
					boolean respuesta=false;
					Connection cn=conexion.Conexion.conectar();
					try {
						PreparedStatement consulta=cn.prepareStatement("update tb_producto set nombre=?, cantidad=?, precio=?, descripcion=?, porcentajeIva=?, idCategoria=?, estado=? where idProducto ='"+ idProducto + "'");
						consulta.setString(1, objeto.getNombre());
						consulta.setInt(2, objeto.getCantidad());
						consulta.setDouble(3, objeto.getPrecio());
						consulta.setString(4, objeto.getDescripcion());
						consulta.setInt(5, objeto.getPorcentajeIva());
						consulta.setInt(6, objeto.getIdCategoria());
						consulta.setInt(7, objeto.getEstado());

						
						if(consulta.executeUpdate()>0) {
							respuesta=true;
						}
						cn.close();
					}catch(SQLException e) {
						System.out.println("Error al actualizar categoria: "+ e);
					}
					return respuesta;
				}
			
			//metodo para eliminar producto
				public boolean eliminar(int idProducto) {
						boolean respuesta=false;
						Connection cn=conexion.Conexion.conectar();
						try {
							PreparedStatement consulta=cn.prepareStatement("delete from tb_producto where idProducto ='"+ idProducto + "'");
							consulta.executeUpdate();
							
							if(consulta.executeUpdate()>0) {
								respuesta=true;
							}
							cn.close();
						}catch(SQLException e) {
							System.out.println("Error al eliminar producto: "+ e);
						}
						return respuesta;
					}
				
				//metodo para actualizar stock 
				public boolean actualizarStock(Producto object, int idProducto) {
					boolean respuesta=false;
					Connection cn=conexion.Conexion.conectar();
					try {
						PreparedStatement consulta=cn.prepareStatement("update tb_producto set cantidad = ? where idProducto ='"+ idProducto + "'");
						consulta.setInt(1, object.getCantidad());
						
						if(consulta.executeUpdate()>0) {
							respuesta=true;
						}
						cn.close();
					}catch(SQLException e) {
						System.out.println("Error al actualizar stock producto: "+ e);
					}
					return respuesta;
				}
}
