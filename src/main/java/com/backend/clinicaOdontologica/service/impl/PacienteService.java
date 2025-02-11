package com.backend.clinicaOdontologica.service.impl;
import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.repository.PacienteRepository;
import com.backend.clinicaOdontologica.service.IPacienteService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class    PacienteService implements IPacienteService {

    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private final PacienteRepository pacienteRepository;
    private final ModelMapper modelMapper;

    public PacienteService(PacienteRepository pacienteRepository, ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }


    @Override
    public PacienteSalidaDto registrarPaciente(PacienteEntradaDto paciente) {

        LOGGER.info("PacienteEntradaDto {}", JsonPrinter.toString(paciente));
        Paciente entidadPaciente = modelMapper.map(paciente, Paciente.class);
        Paciente pacienteRegistrado = pacienteRepository.save(entidadPaciente);
        PacienteSalidaDto pacienteSalidaDto = modelMapper.map(pacienteRegistrado, PacienteSalidaDto.class);
        LOGGER.info("pacienteSalidaDto {}", JsonPrinter.toString(pacienteSalidaDto));
        return pacienteSalidaDto;
    }

    @Override
    public PacienteSalidaDto buscarPacientePorId(Long id) {
        LOGGER.info("Pacientelogo a buscar: {}", id);
        Paciente pacienteBuscado = pacienteRepository.findById(id).orElse(null);
        LOGGER.info("PacienteBuscado: {}", JsonPrinter.toString(pacienteBuscado));
        PacienteSalidaDto pacienteEncontrado = null;
                if(pacienteBuscado != null){
                    pacienteEncontrado = modelMapper.map(pacienteBuscado, PacienteSalidaDto.class);
                }
        LOGGER.info("PacienteEncontrado: {}", JsonPrinter.toString(pacienteEncontrado));
        return pacienteEncontrado;
    }

    @Override
    public List<PacienteSalidaDto> listarPacientes() {
        List<PacienteSalidaDto> pacienteSalidaDtos = pacienteRepository.findAll()
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los pacientes: {}", JsonPrinter.toString(pacienteSalidaDtos));

        return pacienteSalidaDtos;
    }


    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        LOGGER.info("Paciente id a eliminar {}", id);
        if(buscarPacientePorId(id) != null){
            pacienteRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el paciente con id {}", id);
        } else {
            LOGGER.error("No existe el paciente con id = {}", id);
            throw new ResourceNotFoundException("No existe el paciente con id " + id);
        }

    }

    @Override
    public PacienteSalidaDto actualizarPaciente(PacienteEntradaDto pacienteEntradaDto, Long id) throws ResourceNotFoundException {
        LOGGER.info("Paciente recibido {} {}", id, JsonPrinter.toString(pacienteEntradaDto));
        PacienteSalidaDto pacienteSalidaDto = null;

        if(buscarPacientePorId(id) !=null) {
            Paciente entidadPaciente = modelMapper.map(pacienteEntradaDto, Paciente.class);
            entidadPaciente.setId(id);
            entidadPaciente.getDomicilio().setId(buscarPacientePorId(id).getDomicilioSalidaDto().getId());
            Paciente pacienteActualizado = pacienteRepository.save(entidadPaciente);

            pacienteSalidaDto = modelMapper.map(pacienteActualizado, PacienteSalidaDto.class);
        }else{
            LOGGER.error("No existe paciente con id {}",id);
            throw new ResourceNotFoundException("No existe el paciente con id " + id);
        }
        return pacienteSalidaDto;
    }


    private void configureMapping(){
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDto::getDomicilioEntradaDto, Paciente::setDomicilio));
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilioSalidaDto));
    }
}



