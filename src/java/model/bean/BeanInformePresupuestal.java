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
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author Juanka
 */
public class BeanInformePresupuestal {
    
    String identificadorPIP;
    String id;
    @NotEmpty
    String codigo;
    @NotEmpty
    String nombreSector;
    
    @NotNull(message="Fecha no puede estar vacía")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message="Fecha no puede ser mayor a la actual")
    Date fechaInforme;
    
    String codigoContrato;
    @NotEmpty
    String descripcion;
    
    @NotNull
    @NumberFormat(pattern = "#,###,###,###.00")
    BigDecimal montoAsignado;
    @NotEmpty
    String moneda;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreSector() {
        return nombreSector;
    }

    public void setNombreSector(String nombreSector) {
        this.nombreSector = nombreSector;
    }

    public Date getFechaInforme() {
        return fechaInforme;
    }

    public void setFechaInforme(Date fechaInforme) {
        this.fechaInforme = fechaInforme;
    }

    public String getIdentificadorPIP() {
        return identificadorPIP;
    }

    public void setIdentificadorPIP(String identificadorPIP) {
        this.identificadorPIP = identificadorPIP;
    }

    public String getCodigoContrato() {
        return codigoContrato;
    }

    public void setCodigoContrato(String codigoContrato) {
        this.codigoContrato = codigoContrato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMontoAsignado() {
        return montoAsignado;
    }

    public void setMontoAsignado(BigDecimal montoAsignado) {
        this.montoAsignado = montoAsignado;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    
}
