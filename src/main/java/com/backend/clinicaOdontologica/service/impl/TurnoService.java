package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.entity.Turno;
import com.backend.clinicaOdontologica.repository.TurnoRepository;
import com.backend.clinicaOdontologica.service.ITurnoService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private final TurnoRepository turnoRepository;
    private final ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) {
//        Turno entidadTurno = modelMapper.map(turno, Turno.class);
//
//        Turno turnoRegistrado = turnoRepository.save(entidadTurno);
//
//        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoRegistrado, TurnoSalidaDto.class);
//
//        return turnoSalidaDto;
        return null;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        return null;
    }
    private void configureMapping(){
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class);
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Turno::getPaciente, TurnoSalidaDto::setPacienteSalidaDto))
                .addMappings(mapper -> mapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologoSalidaDto));
    }
}
