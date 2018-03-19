/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import model.bean.BeanEstudioPIP;
import model.bean.BeanListaValores;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juanka
 */
public class EstudioPIPDAO {
    
    JdbcTemplate template;
    
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }  
    
    public List<BeanEstudioPIP> getEstudiosPIP(String fechaInicio, String fechaFinal) {
        return template.query(" select epi.id, epi.identificadorPIP, ppip.descripcion, ppip.nombrePIP, epip.nombre estado, ev.nombre, montoInversionViable, fechaDeclaracion, moneda" +
                              " from estudiopreinversion epi" +
                              " inner join proyectopip ppip on epi.identificadorPIP = ppip.identificadorPIP" +
                              " inner join estadoproyectopip epip on epip.id = ppip.idEstado" +
                              " inner join estadoviabilidad ev on epi.estadoviabilidad = ev.id" +
                              " WHERE fechaDeclaracion >= '"+fechaInicio+"' and fechaDeclaracion <= '"+fechaFinal+"'", new RowMapper<BeanEstudioPIP>() {
            public BeanEstudioPIP mapRow(ResultSet rs, int row) throws SQLException {
                BeanEstudioPIP e = new BeanEstudioPIP();
                e.setId(rs.getString(1));
                e.setIdentificadorPIP(rs.getString(2));
                e.setDescripcion(rs.getString(3));
                e.setNombrePIP(rs.getString(4));
                e.setEstadoPIP(rs.getString(5));
                e.setEstadoViabilidad(rs.getString(6));
                e.setMontoInversionViable(rs.getBigDecimal(7));
                e.setFechaDeclaracion(rs.getDate(8));
                e.setMoneda(rs.getString(9));
                return e;
            }
        });
    }  
    
    public BeanEstudioPIP getEstudioPIPByCode(String code){  
        String sql=" SELECT identificadorPIP, descripcion, montoInversionViable, DATE_FORMAT(fechaDeclaracion, '%Y-%m-%d')," +
                   "        id, estadoViabilidad, moneda," +
                   "        encargadoOperacion, responsableInforme, leccionAprendida " +
                   " FROM estudiopreinversion where id=?";  
        return template.queryForObject(sql, new Object[]{code}, new BeanPropertyRowMapper<BeanEstudioPIP>(BeanEstudioPIP.class) {
            public BeanEstudioPIP mapRow(ResultSet rs, int row) throws SQLException {
                BeanEstudioPIP e = new BeanEstudioPIP();
                e.setIdentificadorPIP(rs.getString(1));
                e.setDescripcion(rs.getString(2));
                e.setMontoInversionViable(rs.getBigDecimal(3));
                //e.setFechaDeclaracion(util.Utils.formatGetDate(rs.getString(4)));
                e.setFechaDeclaracion(rs.getDate(4));
                e.setId(rs.getString(5));
                e.setEstadoViabilidad(rs.getString(6));
                e.setMoneda(rs.getString(7));
                //e.setFuenteFinanciamiento(rs.getString(8));
                e.setEncargadoOperacion(rs.getString(8));
                e.setResponsableInforme(rs.getString(9));
                e.setLeccionesAprendidas(rs.getString(10));
                return e;
            }
        }); 
    } 
    
    public int update(BeanEstudioPIP p) throws DuplicateKeyException {
        
        String sql = "update estudiopreinversion"
                + " set identificadorPIP='" + p.getIdentificadorPIP()+ "', "
                + " descripcion='" + p.getDescripcion()+ "', "
                + " estadoViabilidad='" + p.getEstadoViabilidad()+ "', "
                + " moneda='" + p.getMoneda()+ "', "
                + " montoInversionViable='" + p.getMontoInversionViable()+ "', "
                + " fechaDeclaracion = STR_TO_DATE('" + p.getFechaDeclaracion() + "', '%d/%m/%Y'), "
                //+ " fuenteFinanciamiento='" + p.getFuenteFinanciamiento()+ "', "
                + " encargadoOperacion='" + p.getEncargadoOperacion()+ "', "
                + " responsableInforme='" + p.getResponsableInforme()+ "', "
                + " leccionAprendida='" + p.getLeccionesAprendidas()+ "' "
                + " where id=" + p.getId();
        
        return template.update(sql);
    }    
 
    public int save(BeanEstudioPIP p) throws DuplicateKeyException{
        //SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
        String sql = "INSERT INTO estudiopreinversion(identificadorPIP, descripcion, estadoViabilidad, "
                + " moneda, montoInversionViable, fechaDeclaracion, "
                + " encargadoOperacion, responsableInforme, leccionAprendida) "
                + " values('" + p.getIdentificadorPIP() + "', "
                + "        '" + p.getDescripcion() + "', "
                + "        '" + p.getEstadoViabilidad()+ "', "
                + "        '" + p.getMoneda()+ "', "
                + "         " + p.getMontoInversionViable() + ", "
                + "         STR_TO_DATE('" + DateFormat.getDateInstance().format(p.getFechaDeclaracion()) + "', '%d/%m/%Y'), "
                //+ "         '" + p.getFuenteFinanciamiento() + "', "
                + "        '" + p.getEncargadoOperacion()+ "', "
                + "        '" + p.getResponsableInforme()+ "', "
                + "        '" + p.getLeccionesAprendidas() + "') ";
        
        System.out.println(sql);
        
        return template.update(sql);
    }
    
    public int deleteDocSustento(String id) {
        
        String sql = "DELETE FROM documentosustento WHERE id=" + id;
        
        return template.update(sql);
    }   
    
    public int deleteAnexo(String id) {
        
        String sql = "DELETE FROM anexo WHERE id=" + id;
        
        return template.update(sql);
    }       
    
    public List<BeanListaValores> getEstadosViabilidad(){              
        
        String sql = "SELECT id, CONCAT(codigo, ' - ', nombre) FROM estadoviabilidad";
        return template.query(sql, new RowMapper<BeanListaValores>() {  
            public BeanListaValores mapRow(ResultSet rs, int row) throws SQLException {  
                BeanListaValores e = new BeanListaValores();
                e.setId(rs.getString(1));
                e.setCodigo(rs.getString(2));
                return e;
            }  
        });  
    }  
    
    public List<BeanListaValores> getMonedas(){
        String sql = "SELECT id, nombre FROM moneda WHERE estado = 1";
        return template.query(sql, new RowMapper<BeanListaValores>() {  
            public BeanListaValores mapRow(ResultSet rs, int row) throws SQLException {
                BeanListaValores e = new BeanListaValores();
                e.setId(rs.getString(1));
                e.setCodigo(rs.getString(2));
                return e;
            }  
        });                
    } 

    public int getAnexos(String code){
        String sql = "SELECT count(1) from anexo WHERE identificadorPIP=?";
        return template.queryForObject(sql, new Object[] { code }, Integer.class);               
    }    
    
    public int getDocumentoSustento(String code){
        String sql =    " SELECT COUNT(1) FROM (" +
                        " SELECT max(1) cont FROM documentosustento d" +
                        " where nombreDocumento like 'INFTECNICO_%' and identificadorPIP = ?" +
                        " UNION ALL" +
                        " SELECT max(1) cont FROM documentosustento d" +
                        " where nombreDocumento like 'PRESUPUESTO_%' and identificadorPIP = ?) dest" +
                        " where cont is not null";
        return template.queryForObject(sql, new Object[] { code, code }, Integer.class);               
    }    
    
    /*public List<BeanListaValores> getFFs(){
        String sql = "SELECT id, nombre FROM fuentefinanciamiento";
        return template.query(sql, new RowMapper<BeanListaValores>() {  
            public BeanListaValores mapRow(ResultSet rs, int row) throws SQLException {  
                //String e = new String();
                BeanListaValores e = new BeanListaValores();
                e.setId(rs.getString(1));
                e.setCodigo(rs.getString(2));
                return e;
            }  
        });
        
    }*/   
    
}
