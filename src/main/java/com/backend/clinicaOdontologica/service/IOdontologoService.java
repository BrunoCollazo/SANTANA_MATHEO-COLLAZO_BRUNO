package com.backend.clinicaOdontologica.service;

import com.backend.entity.Odontologo;

import java.util.List;

public interface IOdontologoService {
    Odontologo guardarOdontologo(Odontologo odontologo);
    List<Odontologo> listarOdontologos();

}
