/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author Juanka
 */
public class BeanEstudioPIP {
    String id;
    
    //@Pattern(regexp = "^[A-Z]+$")
    String identificadorPIP;
    
    String descripcion;
    @NotNull
    @NumberFormat(pattern = "#,###,###,###.00")
    BigDecimal montoInversionViable;
    //String montoInversionViable;
    @NotNull(message="Fecha no puede estar vacía")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message="Fecha no puede ser mayor a la actual")
    Date fechaDeclaracion;
    @NotEmpty
    String leccionesAprendidas;
    @NotEmpty
    String estadoViabilidad;
    @NotEmpty
    String moneda;
    @NotEmpty
    String encargadoOperacion;
    @NotEmpty
    String responsableInforme;
    String estadoPIP;
    String nombrePIP;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentificadorPIP() {
        return identificadorPIP;
    }

    public void setIdentificadorPIP(String identificadorPIP) {
        this.identificadorPIP = identificadorPIP;
    }

    public BigDecimal getMontoInversionViable() {
        return montoInversionViable;
    }

    public void setMontoInversionViable(BigDecimal montoInversionViable) {
        this.montoInversionViable = montoInversionViable;
    }

    public String getLeccionesAprendidas() {
        return leccionesAprendidas;
    }

    public void setLeccionesAprendidas(String leccionesAprendidas) {
        this.leccionesAprendidas = leccionesAprendidas;
    }

    public String getEstadoViabilidad() {
        return estadoViabilidad;
    }

    public void setEstadoViabilidad(String estadoViabilidad) {
        this.estadoViabilidad = estadoViabilidad;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    /*public String getFuenteFinanciamiento() {
        return fuenteFinanciamiento;
    }

    public void setFuenteFinanciamiento(String fuenteFinanciamiento) {
        this.fuenteFinanciamiento = fuenteFinanciamiento;
    }*/

    public String getEncargadoOperacion() {
        return encargadoOperacion;
    }

    public void setEncargadoOperacion(String encargadoOperacion) {
        this.encargadoOperacion = encargadoOperacion;
    }

    public String getResponsableInforme() {
        return responsableInforme;
    }

    public void setResponsableInforme(String responsableInforme) {
        this.responsableInforme = responsableInforme;
    }

    public String getEstadoPIP() {
        return estadoPIP;
    }

    public void setEstadoPIP(String estadoPIP) {
        this.estadoPIP = estadoPIP;
    }

    public String getNombrePIP() {
        return nombrePIP;
    }

    public void setNombrePIP(String nombrePIP) {
        this.nombrePIP = nombrePIP;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaDeclaracion() {
        return fechaDeclaracion;
    }

    public void setFechaDeclaracion(Date fechaDeclaracion) {
        this.fechaDeclaracion = fechaDeclaracion;
    }
    
    
}
