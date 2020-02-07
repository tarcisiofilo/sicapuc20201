package com.sica.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sica.domain.NivelIncidente} entity.
 */
public class NivelIncidenteDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Boolean notificaDNPM;

    @NotNull
    private Boolean notificaEmail;

    @NotNull
    private Boolean notificacaoSms;

    @NotNull
    private Boolean notificacaoWhatsapp;

    @NotNull
    private Boolean notificacaoDispositivoSeguranca;

    @NotNull
    private Boolean notificaSirene;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean isNotificaDNPM() {
        return notificaDNPM;
    }

    public void setNotificaDNPM(Boolean notificaDNPM) {
        this.notificaDNPM = notificaDNPM;
    }

    public Boolean isNotificaEmail() {
        return notificaEmail;
    }

    public void setNotificaEmail(Boolean notificaEmail) {
        this.notificaEmail = notificaEmail;
    }

    public Boolean isNotificacaoSms() {
        return notificacaoSms;
    }

    public void setNotificacaoSms(Boolean notificacaoSms) {
        this.notificacaoSms = notificacaoSms;
    }

    public Boolean isNotificacaoWhatsapp() {
        return notificacaoWhatsapp;
    }

    public void setNotificacaoWhatsapp(Boolean notificacaoWhatsapp) {
        this.notificacaoWhatsapp = notificacaoWhatsapp;
    }

    public Boolean isNotificacaoDispositivoSeguranca() {
        return notificacaoDispositivoSeguranca;
    }

    public void setNotificacaoDispositivoSeguranca(Boolean notificacaoDispositivoSeguranca) {
        this.notificacaoDispositivoSeguranca = notificacaoDispositivoSeguranca;
    }

    public Boolean isNotificaSirene() {
        return notificaSirene;
    }

    public void setNotificaSirene(Boolean notificaSirene) {
        this.notificaSirene = notificaSirene;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NivelIncidenteDTO nivelIncidenteDTO = (NivelIncidenteDTO) o;
        if (nivelIncidenteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nivelIncidenteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NivelIncidenteDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", notificaDNPM='" + isNotificaDNPM() + "'" +
            ", notificaEmail='" + isNotificaEmail() + "'" +
            ", notificacaoSms='" + isNotificacaoSms() + "'" +
            ", notificacaoWhatsapp='" + isNotificacaoWhatsapp() + "'" +
            ", notificacaoDispositivoSeguranca='" + isNotificacaoDispositivoSeguranca() + "'" +
            ", notificaSirene='" + isNotificaSirene() + "'" +
            "}";
    }
}
