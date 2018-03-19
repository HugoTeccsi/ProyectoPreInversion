/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.bean.BeanRequerimiento;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Juanka
 */
public class RequerimientoDAO {
    
    JdbcTemplate template;
    
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }      
    
    public BeanRequerimiento getRequerimientoByCode(String code){ 
        
        String sql=" SELECT r.identificadorPIP, r.numeroRequerimiento, ne.nombre," +
                   "        ppip.nombrePIP, pe.nombre, epi.nombre, r.descripcion," +
                   "        DATE_FORMAT(it.fechaCreacion, '%Y-%m-%d'), DATE_FORMAT(it.fechaActualizacion, '%Y-%m-%d')," +
                   "        it.codigo, it.observacion, DATE_FORMAT(it.fechaCreacion, '%Y-%m-%d'), it.terminoAprobacion," +
                   "        ip.codigo, ip.descripcion, DATE_FORMAT(ip.fechaInforme, '%Y-%m-%d'), ip.montoAsignado" +
                   " FROM requerimiento r" +
                   "      inner join proyectopip ppip on r.idProyectoPIP = ppip.id " +
                   "      inner join nivelestudio ne on ppip.idNivelEstudio = ne.id " +
                   "      inner join plazoevaluacion pe on ppip.idPlazoEvaluacion = pe.id " +
                   "      inner join estadoproyectopip epi on ppip.idEstado = epi.id " +
                   "      inner join informetecnico it on ppip.idInformeTecnico = it.id " +
                   "      inner join informepresupuestal ip on ppip.identificadorPIP = ip.identificadorPIP" +
                   " WHERE ppip.identificadorPIP = ?"; 
        System.out.println(sql);
        return template.queryForObject(sql, new Object[]{code}, new BeanPropertyRowMapper<BeanRequerimiento>(BeanRequerimiento.class) {
            public BeanRequerimiento mapRow(ResultSet rs, int row) throws SQLException {
                BeanRequerimiento e = new BeanRequerimiento();
                e.setIdentificadorPIP(rs.getString(1));
                e.setNumeroRequerimiento(rs.getString(2));
                e.setNivelEstudio(rs.getString(3));
                e.setNombrePIP(rs.getString(4));
                e.setPlazoEvaluacion(rs.getString(5));
                e.setEstadoPIP(rs.getString(6));
                e.setDescripcion(rs.getString(7));
                e.setFechaInicio(rs.getString(8));
                e.setFechaCulminacion(rs.getString(9));
                e.setIt_codigo(rs.getString(10));
                e.setIt_descripcion(rs.getString(11));
                e.setIt_fechaCreacion(rs.getString(12));
                e.setIt_terminoAprobacion(rs.getString(13));
                e.setIp_codigo(rs.getString(14));
                e.setIp_descripcion(rs.getString(15));
                e.setIp_fechaInforme(rs.getString(16));
                e.setIp_montoAsignado(rs.getString(17));
                return e;
            }
        }); 
    }
    
    public String getProyectoPIPByCode(String code) {
    
        //String sql = "select count(1) from proyectopip ppip where identificadorPIP = '" + code + "'";
        String sql =    "select count(1) from proyectopip ppip" +
                        " inner join informetecnico it on ppip.idinformetecnico = it.id" +
                        //" inner join informepresupuestal ip on ppip.idinformepresupuestal = ip.id" +
                        " inner join informepresupuestal ip on ppip.identificadorPIP = ip.identificadorPIP" +
                        " where ppip.identificadorPIP = '" + code + "'";
        String result = (String)template.queryForObject(sql, String.class);
        return result;      
    }
    
    public String getProyectoPIPForITByCode(String code) {
    
        //String sql = "select count(1) from proyectopip ppip where identificadorPIP = '" + code + "'";
        String sql =    "select count(1) from proyectopip ppip" +
                        " inner join informetecnico it on ppip.idinformetecnico = it.id" +
                        //" inner join informepresupuestal ip on ppip.idinformepresupuestal = ip.id" +
                        " where ppip.identificadorPIP = '" + code + "'";
        String result = (String)template.queryForObject(sql, String.class);
        return result;      
    }    
    
    public String getProyectoPIPForIPByCode(String code) {
    
        //String sql = "select count(1) from proyectopip ppip where identificadorPIP = '" + code + "'";
        String sql =    "select count(1) from proyectopip ppip" +
                        //" inner join informetecnico it on ppip.idinformetecnico = it.id" +
                        " inner join informepresupuestal ip on ppip.identificadorPIP = ip.identificadorPIP" +
                        " where ppip.identificadorPIP = '" + code + "'";
        String result = (String)template.queryForObject(sql, String.class);
        return result;      
    }      
    
    public String getProyectoPIPByEstudioPIP(String code) {
        
        String sql = "select count(1) from estudiopreinversion epi where identificadorPIP = '" + code + "'";
        String result = (String)template.queryForObject(sql, String.class);
        return result;
    
    }
    
    public String getProyectoPIPByInfPresup(String code) {
        
        String sql = "select count(1) from informepresupuestal epi where identificadorPIP = '" + code + "'";
        String result = (String)template.queryForObject(sql, String.class);
        return result;
    
    }       
    
    public BeanRequerimiento getRequerimientoForIPByCode(String code){ 
        
        String sql=" SELECT r.identificadorPIP, r.numeroRequerimiento, ne.nombre," +
                   "        ppip.nombrePIP, pe.nombre, epi.nombre, r.descripcion," +
                   "        DATE_FORMAT(it.fechaCreacion, '%Y-%m-%d'), DATE_FORMAT(it.fechaActualizacion, '%Y-%m-%d')" +
                   " FROM requerimiento r" +
                   "      inner join proyectopip ppip on r.idProyectoPIP = ppip.id " +
                   "      inner join nivelestudio ne on ppip.idNivelEstudio = ne.id " +
                   "      inner join plazoevaluacion pe on ppip.idPlazoEvaluacion = pe.id " +
                   "      inner join estadoproyectopip epi on ppip.idEstado = epi.id " +
                   "      inner join informetecnico it on ppip.idInformeTecnico = it.id " +
                   " WHERE ppip.identificadorPIP = ?"; 

        try {
                
        return template.queryForObject(sql, new Object[]{code}, new BeanPropertyRowMapper<BeanRequerimiento>(BeanRequerimiento.class) {
            public BeanRequerimiento mapRow(ResultSet rs, int row) throws SQLException {
                BeanRequerimiento e = new BeanRequerimiento();
                e.setIdentificadorPIP(rs.getString(1));
                e.setNumeroRequerimiento(rs.getString(2));
                e.setNivelEstudio(rs.getString(3));
                e.setNombrePIP(rs.getString(4));
                e.setPlazoEvaluacion(rs.getString(5));
                e.setEstadoPIP(rs.getString(6));
                e.setDescripcion(rs.getString(7));
                e.setFechaInicio(rs.getString(8));
                e.setFechaCulminacion(rs.getString(9));
                return e;
            }
        });
        
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }    
    
    @ExceptionHandler({IOException.class, java.sql.SQLException.class, CannotGetJdbcConnectionException.class})
    public ModelAndView handleIOException(Exception ex) {
        ModelAndView model = new ModelAndView("IOError");
 
        model.addObject("exception", ex.getMessage());
         
        return model;
    }     
    
}
