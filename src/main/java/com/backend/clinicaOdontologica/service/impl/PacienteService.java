package com.backend.clinicaOdontologica.service.impl;
import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.repository.PacienteRepository;
import com.backend.clinicaOdontologica.service.IPacienteService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService implements IPacienteService {


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

        LOGGER.info("PacienteEntradaDto: {}", JsonPrinter.toString(paciente));
        Paciente entidadPaciente = modelMapper.map(paciente, Paciente.class);
        LOGGER.info("EntidadPaciente: {}", JsonPrinter.toString(entidadPaciente));
        Paciente pacienteRegistrado = pacienteRepository.save(entidadPaciente);
        LOGGER.info("PacienteRegistrado: {}", JsonPrinter.toString(pacienteRegistrado));
        PacienteSalidaDto pacienteSalidaDto = modelMapper.map(pacienteRegistrado, PacienteSalidaDto.class);
        LOGGER.info("PacienteSalidaDto: {}", JsonPrinter.toString(pacienteSalidaDto));
        return pacienteSalidaDto;
    }

    @Override//NO HAY QUE LANZAR LA EXCEPCION ACA
    public PacienteSalidaDto buscarPacientePorId(Long id) {
        Paciente pacienteBuscado = pacienteRepository.findById(id).orElse(null);
        LOGGER.info("PacienteBuscado: {}", JsonPrinter.toString(pacienteBuscado));
        PacienteSalidaDto pacienteEncontrado = null;
                if(pacienteBuscado != null){
                    pacienteEncontrado = modelMapper.map(pacienteBuscado, PacienteSalidaDto.class);
                    LOGGER.info("PacienteEncontrado: {}", JsonPrinter.toString(pacienteEncontrado));
                }else LOGGER.error("No se ha encontrado el paciente con id {}", id);

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


    public void eliminarPaciente(Long id) {
        if(buscarPacientePorId(id) != null){
            //llamada a la capa repositorio para eliminar
            pacienteRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el paciente con id {}", id);
        } else {
            //excepcion resource not found
        }

    }

    @Override
    public PacienteSalidaDto actualizarPaciente(PacienteEntradaDto pacienteEntradaDto, Long id) {
        return null;
    }


    private void configureMapping(){
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDto::getDomicilioEntradaDto, Paciente::setDomicilio));
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilioSalidaDto));
    }
}



