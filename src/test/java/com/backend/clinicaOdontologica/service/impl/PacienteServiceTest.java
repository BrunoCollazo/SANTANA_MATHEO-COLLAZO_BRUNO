package com.backend.clinicaOdontologica.service.impl;

import com.backend.clinicaOdontologica.dto.entrada.DomicilioEntradaDto;
import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.entity.Domicilio;
import com.backend.clinicaOdontologica.entity.Paciente;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PacienteServiceTest {

    private final PacienteRepository pacienteRepositoryMock = mock(PacienteRepository.class);
    private final ModelMapper modelMapper = new ModelMapper();

    private final PacienteService pacienteService = new PacienteService(pacienteRepositoryMock, modelMapper);

    private static PacienteEntradaDto pacienteEntradaDto;
    private static Paciente paciente;

    @BeforeAll
    static void setUp(){

        // Inicializa el objeto común antes de cada prueba
        paciente = new Paciente(1L, "Pedro", "Perez", 123456, LocalDate.of(2024, 6, 22),
                new Domicilio(1L, "Calle", 123, "Localidad", "Provincia"));

        pacienteEntradaDto = new PacienteEntradaDto("Juan", "Perez", 123456, LocalDate.of(2024, 6, 22), new DomicilioEntradaDto("Calle", 123, "Localidad", "Provincia"));
    }

    @Test
    void deberiaMandarAlRepositorioUnPacienteDeNombrePedro_yRetornarUnSalidaDtoConSuId(){
        when(pacienteRepositoryMock.save(any(Paciente.class))).thenReturn(paciente);

        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        assertNotNull(pacienteSalidaDto);
        assertNotNull(pacienteSalidaDto.getId());
        assertEquals("Pedro", pacienteSalidaDto.getNombre());
        verify(pacienteRepositoryMock, times(1)).save(any(Paciente.class));
    }

    @Test
    void deberiaLanzarUnaResourceNotFoundExceptionCuandoIntentaEliminarUnPacienteQueNoExiste(){
        when(pacienteRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pacienteService.eliminarPaciente(1L));

        verify(pacienteRepositoryMock, never()).deleteById(1L);

    }
    @Test
    void deberiaDevolverUnListadoNoVacioDePacientes(){
        List<Paciente> pacientes = new java.util.ArrayList<>(List.of(paciente));
        when(pacienteRepositoryMock.findAll()).thenReturn(pacientes);

        List<PacienteSalidaDto> listadoDePacientes = pacienteService.listarPacientes();
        assertFalse(listadoDePacientes.isEmpty());

    }




}