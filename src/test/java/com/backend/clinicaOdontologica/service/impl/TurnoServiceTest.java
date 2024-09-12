package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.TurnoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.DomicilioSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.dto.salida.TurnoSalidaDto;
import com.backend.clinicaOdontologica.entity.Domicilio;
import com.backend.clinicaOdontologica.entity.Odontologo;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.entity.Turno;
import com.backend.clinicaOdontologica.exceptions.BadRequestException;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.repository.TurnoRepository;
import com.backend.clinicaOdontologica.service.impl.OdontologoService;
import com.backend.clinicaOdontologica.service.impl.PacienteService;
import com.backend.clinicaOdontologica.service.impl.TurnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TurnoServiceTest {

    private final TurnoRepository turnoRepositoryMock = mock(TurnoRepository.class);
    private final PacienteService pacienteServiceMock = mock(PacienteService.class);;
    private final OdontologoService odontologoServiceMock = mock(OdontologoService.class);
    private final ModelMapper modelMapper = new ModelMapper();
    private final TurnoService turnoService = new TurnoService(turnoRepositoryMock, pacienteServiceMock, odontologoServiceMock, modelMapper);

    private Turno turno;
    private TurnoEntradaDto turnoEntradaDto;
    private Paciente paciente;
    private Odontologo odontologo;
    private PacienteSalidaDto pacienteSalidaDto;
    private OdontologoSalidaDto odontologoSalidaDto;

    @BeforeEach
    void setUp() {
        paciente = new Paciente(1L, "Juan", "Pérez", 12345678, LocalDate.of(2024, 6, 22),
                new Domicilio(1L, "Calle", 123, "Localidad", "Provincia"));
        pacienteSalidaDto = new PacienteSalidaDto(1L, "Juan", "Perez", 123456, LocalDate.of(2024, 6, 22),
                new DomicilioSalidaDto(1L, "Calle", 123, "Localidad", "Provincia"));

        odontologo = new Odontologo(1L, "1234", "Ana", "García");
        odontologoSalidaDto = new OdontologoSalidaDto(1L, "A654321", "Ana", "Sanchez");

        turno = new Turno(1L, paciente, odontologo, LocalDateTime.of(2024, 6, 22, 11, 11, 11));
        turnoEntradaDto = new TurnoEntradaDto(1L, 1L, LocalDateTime.of(2024, 6, 22, 11, 11, 11));
    }

    @Test
    void deberiaRegistrarTurno() throws BadRequestException {
        when(pacienteServiceMock.buscarPacientePorId(1L)).thenReturn(pacienteSalidaDto);
        when(odontologoServiceMock.buscarOdontologoPorId(1L)).thenReturn(odontologoSalidaDto);
        when(turnoRepositoryMock.save(any(Turno.class))).thenReturn(turno);

        TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);

        assertNotNull(turnoSalidaDto);
        verify(turnoRepositoryMock, times(1)).save(any(Turno.class));
    }

    @Test
    void deberiaLanzarBadRequestExceptionSiDatosInvalidos() {
        when(pacienteServiceMock.buscarPacientePorId(1L)).thenReturn(null);
        BadRequestException exception = assertThrows(BadRequestException.class, () -> turnoService.registrarTurno(turnoEntradaDto));
        assertEquals("El paciente y el odontologo no se encuentran en la base de datos", exception.getMessage());
        verify(turnoRepositoryMock, times(0)).save(any(Turno.class));
    }
    @Test

    void deberiaBuscarTurnoPorId() {
        when(turnoRepositoryMock.findById(1L)).thenReturn(Optional.of(turno));

        TurnoSalidaDto turnoSalidaDto = turnoService.buscarTurnoPorId(1L);

        assertNotNull(turnoSalidaDto);
        verify(turnoRepositoryMock, times(1)).findById(1L);
    }

    @Test
    void deberiaEliminarTurno() {
        when(turnoRepositoryMock.findById(1L)).thenReturn(Optional.of(turno));
        doNothing().when(turnoRepositoryMock).deleteById(1L);

        assertDoesNotThrow(() -> turnoService.eliminarTurno(1L));
        verify(turnoRepositoryMock, times(1)).deleteById(1L);
    }
}