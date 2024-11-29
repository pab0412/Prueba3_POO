/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestiondeclientes;

import Bd.Conexion;
import views.MenuPrincipalClientes;

/**
 *
 * @author Cetecom
 */
public class GestionDeClientes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Conexion cx = new Conexion();
        cx.conectar(); // Verificar conexion
        MenuPrincipalClientes mpc = new MenuPrincipalClientes();
        mpc.setVisible(true);
    }
    
}
