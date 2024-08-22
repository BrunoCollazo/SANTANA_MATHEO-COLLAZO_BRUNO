package com.backend.clinicaOdontologica.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Connection {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/clinica_odontologica", "sa", "sa");
    }

    public static void crearTablaOdontologos(){
        Connection connection = null;
        try{
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/clinica_odontologica;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}