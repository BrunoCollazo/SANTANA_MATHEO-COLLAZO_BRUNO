package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.entity.Turno;
import com.backend.clinicaOdontologica.repository.OdontologoRepository;
import com.backend.clinicaOdontologica.repository.PacienteRepository;
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
    private final PacienteRepository pacienteRepository;
    private final OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepository, PacienteRepository pacienteRepository, OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteRepository = pacienteRepository;
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) {
        LOGGER.info("TurnoEntradaDto: {}", JsonPrinter.toString(turno));

        TurnoSalidaDto turnoSalidaDto = null;

        Odontologo odontologo = odontologoRepository.findById(turno.getOdontologoId())
                .orElse(null);

        Paciente paciente = pacienteRepository.findById(turno.getPacienteId())
                .orElse(null);
        if(paciente == null && odontologo == null){
            LOGGER.error("No existen ni el odontologo "+ turno.getOdontologoId()+" ni el paciente " + turno.getPacienteId());
        }else if (paciente == null){
            LOGGER.error("No existe el paciente " + turno.getPacienteId());
        }else if (odontologo == null){
            LOGGER.error("No existe el odontologo "+ turno.getOdontologoId());
        }else{

            Turno entidadTurno = modelMapper.map(turno, Turno.class);
            LOGGER.info("entidadTurno: {}", JsonPrinter.toString(entidadTurno));

            entidadTurno.setOdontologo(odontologo);
            entidadTurno.setPaciente(paciente);

            Turno turnoRegistrado = turnoRepository.save(entidadTurno);
            LOGGER.info("turnoRegistrado: {}", JsonPrinter.toString(turnoRegistrado));

            turnoSalidaDto = modelMapper.map(turnoRegistrado, TurnoSalidaDto.class);

        }
        LOGGER.info("turnoSalidaDto: {}", JsonPrinter.toString(turnoSalidaDto));
        return turnoSalidaDto;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> turnoSalidaDtos = turnoRepository.findAll()
                .stream()
                .map(turno -> modelMapper.map(turno, TurnoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnoSalidaDtos));

        return turnoSalidaDtos;
    }
    private void configureMapping(){
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class);
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Turno::getPaciente, TurnoSalidaDto::setPacienteSalidaDto))
                .addMappings(mapper -> mapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologoSalidaDto));
    }
}
