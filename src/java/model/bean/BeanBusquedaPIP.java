/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Juanka
 */
public class BeanBusquedaPIP {
    
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date fechaInicio;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date fechaFinal;

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }



    
    
}
