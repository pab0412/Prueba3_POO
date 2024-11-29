/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Bd.Conexion;
import Controller.HelperController;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import models.Cliente;

/**
 *
 * @author Cetecom
 */
public class ClienteController {
    Conexion cx;
    HelperController helper = new HelperController();

    public ClienteController() {
        cx = new Conexion();
        cx.conectar();
    }
    
    public List<Cliente> listarClientes(){
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM CLIENTE;";
        
        try {
            ResultSet rs = cx.EjecutarQuery(query);
            while (rs.next()){
                Cliente cliente = new Cliente(rs.getInt("rut"), 
                        rs.getString("nombre"), 
                        rs.getInt("numero_contacto"), 
                        rs.getString("direccion"), 
                        rs.getString("tipo_cliente"));
                clientes.add(cliente);
            }
        } catch (Exception e) {
            helper.showError("Error " + e.getMessage());
        }
        return clientes;
    }
        
    public void agregarCliente(String nombre, int numero_contacto, String direccion, String tipoCliente){
        String query = "INSERT INTO CLIENTE (nombre, numero_contacto,direccion,tipo_cliente) VALUES (?,?,?,?);";
        try {
            PreparedStatement st = cx.getConnection().prepareStatement(query);
            st.setString(1, nombre);
            st.setInt(2, numero_contacto);
            st.setString(3, direccion);
            st.setString(4, tipoCliente);
            st.executeUpdate();
            helper.showInformation("Cliente agregado");
        } catch (Exception e) {
            helper.showError("Error " + e.getMessage());
        }
    }
    
    public Cliente buscarClientePorRut(int rut){
        Cliente cliente = null;
        String query = "SELECT * FROM CLIENTE WHERE rut = ?;";
        try {
            PreparedStatement st = cx.getConnection().prepareStatement(query);
            st.setInt(1, rut);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()){
                cliente = new Cliente(rs.getInt("rut"), 
                        rs.getString("nombre"), 
                        rs.getInt("numero_contacto"), 
                        rs.getString("direccion"), 
                        rs.getString("tipo_cliente"));
            } else{
                helper.showError("Cliente no encontrado");
            }
        } catch (Exception e) {
            helper.showError("Error  " + e.getMessage());
        }
        return cliente;
    }
    
    public void editarCliente(String nombre, int numeroContacto, String direccion, String tipoCliente, int rutCliente){
        String query = "UPDATE CLIENTE SET nombre = ?, numero_contacto = ?, direccion = ?, tipo_cliente = ? WHERE rut = ?;";
        try {
            Cliente clienteEncontrado = buscarClientePorRut(rutCliente);
            if (clienteEncontrado != null){
                PreparedStatement st = cx.getConnection().prepareStatement(query);
                st.setString(1, nombre);
                st.setInt(2, numeroContacto);
                st.setString(3, direccion);
                st.setString(4, tipoCliente);
                st.setInt(5, rutCliente);
                st.executeUpdate();
                helper.showInformation("Cliente editado correctamente");
            } else{
                helper.showError("Cliente no encontrado");
            }
        } catch (Exception e) {
            helper.showError("Error  " + e.getMessage());
        }
    }
    
    public void eliminarCliente(int rut){
        String query = "DELETE FROM CLIENTE WHERE rut = ?;";
        try {
            Cliente clienteEncontrado = buscarClientePorRut(rut);
            if (clienteEncontrado != null){
                PreparedStatement st = cx.getConnection().prepareStatement(query);
                st.setInt(1, rut);
                st.executeUpdate();
                helper.showWarning("Cliente eliminado correctamente");
            } else {
                helper.showError("Cliente no encontrado");
            }
        } catch (Exception e) {
            helper.showError("Error  " + e.getMessage());
        }
    }
}
