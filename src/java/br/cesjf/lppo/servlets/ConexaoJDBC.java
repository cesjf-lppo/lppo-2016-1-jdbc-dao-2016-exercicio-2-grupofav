/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lppo.servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author igor
 */
public class ConexaoJDBC {
    private static Connection instancia = null;
    
    private ConexaoJDBC(){};
    
    public static Connection getInstance() throws SQLException{
        if(instancia == null){
            instancia = DriverManager.getConnection("jdbc:derby://localhost:1527/lppo-2016-1", "usuario", "senha");
        }
        return instancia;
    }
    
}
