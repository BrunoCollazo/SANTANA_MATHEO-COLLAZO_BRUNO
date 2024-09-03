package com.backend.clinicaOdontologica.controller;


import com.backend.clinicaOdontologica.dto.entrada.OdontologoEntradaDto;
import com.backend.clinicaOdontologica.dto.salida.OdontologoSalidaDto;
import com.backend.clinicaOdontologica.service.IOdontologoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("odontologos")
public class OdontologoController {

    private final IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {this.odontologoService = odontologoService;}

    @PostMapping("/registrar")
    public ResponseEntity<OdontologoSalidaDto> registrarOdontologo(@RequestBody @Valid OdontologoEntradaDto odontologoEntradaDto){
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);
        return new ResponseEntity<>(odontologoSalidaDto, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<OdontologoSalidaDto>> listarOdontologoes(){
        return new ResponseEntity<>(odontologoService.listarOdontologos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OdontologoSalidaDto> buscarPacientePorId(@PathVariable Long id){
        return new ResponseEntity<>(odontologoService.buscarOdontologoPorId(id), HttpStatus.OK);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<OdontologoSalidaDto> actualizarOdontologo(@RequestBody @Valid OdontologoEntradaDto odontologoEntradaDto, @PathVariable Long id){
        return new ResponseEntity<>(odontologoService.actualizarOdontologo(odontologoEntradaDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarOdontologo(@RequestParam Long id){
        odontologoService.eliminarOdontologo(id);
        return new ResponseEntity<>("Odontologo eliminado correctamente", HttpStatus.NO_CONTENT);
    }


}
