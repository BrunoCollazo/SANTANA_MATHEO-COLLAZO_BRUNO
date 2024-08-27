package com.backend.clinicaOdontologica.repository.impl;

import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.repository.IDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoEnMemoria implements IDao<Odontologo> {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoDaoEnMemoria.class);

    private List<Odontologo> odontologos = new ArrayList<>();
    private static long id = 1;

    @Override
    public Odontologo registrar(Odontologo odontologo) {
        Odontologo odontologoRegistrado = new Odontologo(odontologo.getMatricula(), odontologo.getNombre(), odontologo.getApellido());
        odontologoRegistrado.setId(id++);

        odontologos.add(odontologoRegistrado);
        LOGGER.info("Odontolgo registrado: " + odontologoRegistrado);
        return odontologoRegistrado;
    }

    @Override
    public Odontologo buscarPorId(Long id) {
        return null;
    }

    @Override
    public List<Odontologo> listarTodos() {
        LOGGER.info("Odontolgos listados: " + odontologos);
        return odontologos;
    }

}
