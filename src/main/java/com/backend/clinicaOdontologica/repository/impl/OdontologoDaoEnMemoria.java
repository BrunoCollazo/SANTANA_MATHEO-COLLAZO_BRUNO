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
    public Odontologo guardar(Odontologo odontologo) {
        Odontologo odontologoGuardado = new Odontologo(odontologo.getMatricula(), odontologo.getNombre(), odontologo.getApellido());
        odontologoGuardado.setId(id++);

        odontologos.add(odontologoGuardado);
        LOGGER.info("Odontolgo guardado: " + odontologoGuardado);
        return odontologoGuardado;
    }

    @Override
    public List<Odontologo> listarTodos() {
        LOGGER.info("Odontolgos listados: " + odontologos);
        return odontologos;
    }





}
