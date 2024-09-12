package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.repository.OdontologoRepository;
import com.backend.clinicaOdontologica.service.impl.OdontologoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OdontologoServiceTest {

    private OdontologoRepository odontologoRepositoryMock = mock(OdontologoRepository.class);
    private ModelMapper modelMapper = new ModelMapper();
    private OdontologoService odontologoService = new OdontologoService(odontologoRepositoryMock, modelMapper);

    private Odontologo odontologo;
    private OdontologoEntradaDto odontologoEntradaDto;

    @BeforeEach
    void setUp() {
        odontologo = new Odontologo(1L, "A654321", "Ana","Sanchez" );
        odontologoEntradaDto = new OdontologoEntradaDto("A654321", "Ana", "Sanchez");
    }

    @Test
    void deberiaRegistrarOdontologo() {
        when(odontologoRepositoryMock.save(any(Odontologo.class))).thenReturn(odontologo);

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        assertNotNull(odontologoSalidaDto);
        assertEquals("Ana", odontologoSalidaDto.getNombre());
        verify(odontologoRepositoryMock, times(1)).save(any(Odontologo.class));
    }

    @Test
    void deberiaBuscarOdontologoPorId() {
        when(odontologoRepositoryMock.findById(1L)).thenReturn(Optional.of(odontologo));

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(1L);

        assertNotNull(odontologoSalidaDto);
        assertEquals("Ana", odontologoSalidaDto.getNombre());
        verify(odontologoRepositoryMock, times(1)).findById(1L);
    }

    @Test
    void deberiaEliminarOdontologo() {
        when(odontologoRepositoryMock.findById(1L)).thenReturn(Optional.of(odontologo));
        doNothing().when(odontologoRepositoryMock).deleteById(1L);

        assertDoesNotThrow(() -> odontologoService.eliminarOdontologo(1L));
        verify(odontologoRepositoryMock, times(1)).deleteById(1L);
    }
}
