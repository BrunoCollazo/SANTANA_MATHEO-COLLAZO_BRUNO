package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.entity.Turno;
import com.backend.clinicaOdontologica.repository.TurnoRepository;
import com.backend.clinicaOdontologica.service.ITurnoService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private final TurnoRepository turnoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;
    private final ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) {

        LOGGER.info("TurnoEntradaDto: {}", JsonPrinter.toString(turno));
        TurnoSalidaDto turnoSalidaDto = null;
        PacienteSalidaDto pacienteSalidaDto = pacienteService.buscarPacientePorId(turno.getPacienteId());
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(turno.getOdontologoId());

        if(pacienteSalidaDto == null && odontologoSalidaDto == null){
            LOGGER.error("No existen ni el odontologo "+ turno.getOdontologoId()+" ni el paciente " + turno.getPacienteId());

        }else if (pacienteSalidaDto == null){
            LOGGER.error("No existe el paciente " + turno.getPacienteId());

        }else if (odontologoSalidaDto == null){
            LOGGER.error("No existe el odontologo "+ turno.getOdontologoId());

        }else{
            LOGGER.info("TurnoEntradaDto: {}", JsonPrinter.toString(turno));
            Turno entidadTurno = modelMapper.map(turno, Turno.class);

            LOGGER.info("EntidadTurno: {}", JsonPrinter.toString(entidadTurno));

            Paciente paciente = modelMapper.map(pacienteSalidaDto, Paciente.class);
            Odontologo odontologo = modelMapper.map(odontologoSalidaDto, Odontologo.class);

            entidadTurno.setPaciente(paciente);
            entidadTurno.setOdontologo(odontologo);

            Turno turnoRegistrado = turnoRepository.save(entidadTurno);
            LOGGER.info("turnoRegistrado: {}", JsonPrinter.toString(turnoRegistrado));

            turnoSalidaDto = modelMapper.map(turnoRegistrado, TurnoSalidaDto.class);
            LOGGER.info("turnoSalidaDto: {}", JsonPrinter.toString(turnoSalidaDto));

        }
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
        modelMapper.emptyTypeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.map(TurnoEntradaDto::getFechaHora, Turno::setFechaHora));

        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class)
                .addMappings(mapper -> mapper.map(Turno::getPaciente, TurnoSalidaDto::setPacienteSalidaDto))
                .addMappings(mapper -> mapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologoSalidaDto));
        modelMapper.typeMap(OdontologoSalidaDto.class, Odontologo.class);
        modelMapper.typeMap(PacienteSalidaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteSalidaDto::getDomicilioSalidaDto, Paciente::setDomicilio));


    }
}
