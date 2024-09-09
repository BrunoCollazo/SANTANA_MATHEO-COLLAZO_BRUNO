package com.backend.clinicaOdontologica.service.impl;
import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.repository.OdontologoRepository;
import com.backend.clinicaOdontologica.service.IOdontologoService;
import com.backend.clinicaOdontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {

    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    public OdontologoService(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo) {

        LOGGER.info("OdontologoEntradaDto {}", JsonPrinter.toString(odontologo));
        Odontologo entidadOdonologo = modelMapper.map(odontologo, Odontologo.class);

        Odontologo odontologoRegistrado = odontologoRepository.save(entidadOdonologo);

        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoRegistrado, OdontologoSalidaDto.class);

        LOGGER.info("odontologoSalidaDto {}", JsonPrinter.toString(odontologoSalidaDto));
        return odontologoSalidaDto;
    }

    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(Long id){
        LOGGER.info("Odontologo a buscar: {}", id);
        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        LOGGER.info("OdontologoBuscado: {}", JsonPrinter.toString(odontologoBuscado));
        OdontologoSalidaDto odontologoEncontrado = null;
            if(odontologoBuscado != null){
                odontologoEncontrado = modelMapper.map(odontologoBuscado, OdontologoSalidaDto.class);
            }else {
                LOGGER.error("No se ha encontrado el odontologo con id {}", id);
            }

        LOGGER.info("OdontologoEncontrado: {}", JsonPrinter.toString(odontologoEncontrado));
        return odontologoEncontrado;
    }

    @Override
    public List<OdontologoSalidaDto> listarOdontologos() {
        List<OdontologoSalidaDto> odontologoSalidaDtos = odontologoRepository.findAll()
                .stream()
                .map(odontologo -> modelMapper.map(odontologo, OdontologoSalidaDto.class))
                .toList();
        LOGGER.info("Listado de todos los odontologos: {}", JsonPrinter.toString(odontologoSalidaDtos));

        return odontologoSalidaDtos;
    }

    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        LOGGER.info("Odontologo id a eliminar {}", id);
        if(buscarOdontologoPorId(id) != null){
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el odontologo con id {}", id);
        }else{
            throw new ResourceNotFoundException("No existe el odontologo con id " + id);
        }
    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id) {
        LOGGER.info("Odontolgo recibido {} {}", id, JsonPrinter.toString(odontologoEntradaDto));
        OdontologoSalidaDto odontologoSalidaDto = null;

        if(buscarOdontologoPorId(id) != null) {
            Odontologo entidadOdonologo = modelMapper.map(odontologoEntradaDto, Odontologo.class);
            entidadOdonologo.setId(id);
            Odontologo odontologoActualizado = odontologoRepository.save(entidadOdonologo);

            odontologoSalidaDto = modelMapper.map(odontologoActualizado, OdontologoSalidaDto.class);
        }
        LOGGER.info("odontologoSalidaDto {}", JsonPrinter.toString(odontologoSalidaDto));
        return odontologoSalidaDto;

    }

    private void configureMapping(){
        modelMapper.typeMap(OdontologoEntradaDto.class, Odontologo.class);
        modelMapper.typeMap(Odontologo.class, OdontologoSalidaDto.class);
    }

}
