package com.backend.clinicaOdontologica.service.impl;
import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
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

        LOGGER.info("OdontologoEntradaDto: {}", JsonPrinter.toString(odontologo));
        Odontologo entidadOdonologo = modelMapper.map(odontologo, Odontologo.class);
        LOGGER.info("EntidadOdontologo: {}", JsonPrinter.toString(entidadOdonologo));
        Odontologo odontologoRegistrado = odontologoRepository.save(entidadOdonologo);
        LOGGER.info("OdontologoRegistrado: {}", JsonPrinter.toString(odontologoRegistrado));
        OdontologoSalidaDto odontologoSalidaDto = modelMapper.map(odontologoRegistrado, OdontologoSalidaDto.class);
        LOGGER.info("OdontologoSalidaDto: {}", JsonPrinter.toString(odontologoSalidaDto));
        return odontologoSalidaDto;
    }

    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(Long id){
        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        LOGGER.info("OdontologoBuscado: {}", JsonPrinter.toString(odontologoBuscado));
        OdontologoSalidaDto odontologoEncontrado = null;
            if(odontologoBuscado != null){
                odontologoEncontrado = modelMapper.map(odontologoBuscado, OdontologoSalidaDto.class);
                LOGGER.info("OdontologoEncontrado: {}", JsonPrinter.toString(odontologoEncontrado));
            }else LOGGER.error("No se ha encontrado el odontologo con id {}", id);

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

    public void eliminarOdontologo(Long id) {
        if(buscarOdontologoPorId(id) != null){
            odontologoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el odontologo con id {}", id);
        }else{
            //excepcion a futuro not found
        }
    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoEntradaDto odontologoEntradaDto, Long id) { return null;}


    private void configureMapping(){
        modelMapper.typeMap(OdontologoEntradaDto.class, Odontologo.class);
        modelMapper.typeMap(Odontologo.class, OdontologoSalidaDto.class);
    }

}
