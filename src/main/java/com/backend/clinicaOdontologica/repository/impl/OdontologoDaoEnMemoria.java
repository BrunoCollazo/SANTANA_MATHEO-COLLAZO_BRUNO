package com.backend.clinicaOdontologica.repository.impl;

import com.backend.entity.Odontologo;
import com.backend.repository.IDao;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoEnMemoria implements IDao<Odontologo> {
    private final Logger LOGGER = Logger.getLogger(OdontologoDaoEnMemoria.class);

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
