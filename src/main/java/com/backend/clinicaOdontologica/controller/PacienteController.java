package com.backend.clinicaOdontologica.controller;

import com.backend.clinicaOdontologica.dto.entrada.PacienteEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.PacienteSalidaDto;
import com.backend.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaOdontologica.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("pacientes")
public class PacienteController {

    private IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<PacienteSalidaDto> registrarPaciente(@RequestBody @Valid PacienteEntradaDto pacienteEntradaDto){
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);
        return new ResponseEntity<>(pacienteSalidaDto, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PacienteSalidaDto>> listarPacientes(){
        return new ResponseEntity<>(pacienteService.listarPacientes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")//localhost:8080/pacientes/x
    public ResponseEntity<PacienteSalidaDto> buscarPacientePorId(@PathVariable Long id){
        return new ResponseEntity<>(pacienteService.buscarPacientePorId(id), HttpStatus.OK);
    }

    //PUT
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<PacienteSalidaDto> actualizarPaciente(@RequestBody @Valid PacienteEntradaDto paciente, @PathVariable Long id) throws ResourceNotFoundException{
        return new ResponseEntity<>(pacienteService.actualizarPaciente(paciente, id), HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/eliminar")//localhost:8080/pacientes/eliminar?id=x
    public ResponseEntity<String> eliminarPaciente(@RequestParam Long id) throws ResourceNotFoundException{
        pacienteService.eliminarPaciente(id);
        return new ResponseEntity<>("Paciente eliminado correctamente", HttpStatus.NO_CONTENT);
    }



}
