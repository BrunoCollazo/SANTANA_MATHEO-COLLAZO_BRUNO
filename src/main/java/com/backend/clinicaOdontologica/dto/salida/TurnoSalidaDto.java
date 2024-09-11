package com.backend.clinicaOdontologica.dto.salida;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TurnoSalidaDto {

    private Long id;
    private TurnoPacienteSalidaDto turnoPacienteSalidaDto;
    private OdontologoSalidaDto odontologoSalidaDto;
    private LocalDateTime fechaHora;

    public TurnoSalidaDto() {
    }

    public TurnoSalidaDto(Long id, TurnoPacienteSalidaDto turnoPacienteSalidaDto, OdontologoSalidaDto odontologoSalidaDto, LocalDateTime fechaHora) {
        this.id = id;
        this.turnoPacienteSalidaDto = turnoPacienteSalidaDto;
        this.odontologoSalidaDto = odontologoSalidaDto;
        this.fechaHora = fechaHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TurnoPacienteSalidaDto getTurnoPacienteSalidaDto() {
        return turnoPacienteSalidaDto;
    }

    public void setTurnoPacienteSalidaDto(TurnoPacienteSalidaDto turnoPacienteSalidaDto) {
        this.turnoPacienteSalidaDto = turnoPacienteSalidaDto;
    }

    public OdontologoSalidaDto getOdontologoSalidaDto() {
        return odontologoSalidaDto;
    }

    public void setOdontologoSalidaDto(OdontologoSalidaDto odontologoSalidaDto) {
        this.odontologoSalidaDto = odontologoSalidaDto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

}
