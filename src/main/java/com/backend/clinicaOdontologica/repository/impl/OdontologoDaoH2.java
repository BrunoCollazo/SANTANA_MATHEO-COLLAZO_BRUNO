package com.backend.clinicaOdontologica.repository.impl;

import com.backend.clinicaOdontologica.dbconnection.H2Connection;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.repository.IDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements IDao<Odontologo> {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoDaoH2.class);

    @Override
    public Odontologo registrar(Odontologo odontologo){
        Odontologo odontologoRegistrado = null;
        Connection connection = null;

        LOGGER.info("Odontolgo recibido: " + odontologo);

        try{

            connection = H2Connection.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement =connection.prepareStatement(
                    "INSERT INTO ODONTOLOGOS (MATRICULA, NOMBRE, APELLIDO) VALUES (?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, odontologo.getMatricula());
            preparedStatement.setString(2, odontologo.getNombre());
            preparedStatement.setString(3, odontologo.getApellido());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            odontologoRegistrado = new Odontologo(odontologo.getMatricula(), odontologo.getNombre(), odontologo.getApellido());

            while (resultSet.next()){
                odontologoRegistrado.setId(resultSet.getLong(1));
            }
            connection.commit();

            LOGGER.info("Odontolgo registrado: " +odontologoRegistrado);

        }catch (Exception exception){
            LOGGER.error("Error intentando registrar el odontologo: " + exception.getMessage());
            exception.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                    LOGGER.info("rollback ejecutado");
                } catch (SQLException sqlException) {
                    LOGGER.error("Error intentando ejecutar el rollback: " + sqlException.getMessage());
                    exception.printStackTrace();
                }
            }
        }finally {
            try{
                connection.close();
            }catch (Exception exception){
                LOGGER.error("Error intentando cerrar la conexion: " + exception.getMessage());
                exception.printStackTrace();
            }
        }

        return odontologoRegistrado;
    }

    @Override
    public List<Odontologo> listarTodos(){
        List<Odontologo> odontologos = new ArrayList<>();
        Connection connection = null;

        try{

            connection = H2Connection.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID, MATRICULA, NOMBRE, APELLIDO FROM ODONTOLOGOS");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Odontologo odontologo = new Odontologo(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                odontologos.add(odontologo);
            }
            LOGGER.info("Odontolgos obtenidos: " +odontologos.size());
        }catch (Exception exception){
            LOGGER.error("Error intentando listar odontologos: " + exception.getMessage());
            exception.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception exception){
                LOGGER.error("Error intentando cerrar la conexion: " + exception.getMessage());
                exception.printStackTrace();
            }
        }

        return odontologos;
    }
}
