/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;
import model.bean.BeanEstudioPIP;
import model.bean.BeanInformePresupuestal;
import model.bean.BeanListaValores;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juanka
 */
public class InformePresupuestalDAO {
    
    JdbcTemplate template;
    
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }    
    
    public List<BeanInformePresupuestal> getInformesPresupuestales(String fechaInicio, String fechaFinal) {
        return template.query(" SELECT ip.id, ip.codigo, s.nombre, ip.fechaInforme" +
                              " FROM informepresupuestal ip" +
                              " INNER JOIN sector s ON ip.idsector = s.id" +
                              " WHERE fechaInforme >= '"+fechaInicio+"' and fechaInforme <= '"+fechaFinal+"'", new RowMapper<BeanInformePresupuestal>() {
            public BeanInformePresupuestal mapRow(ResultSet rs, int row) throws SQLException {
                BeanInformePresupuestal e = new BeanInformePresupuestal();
                e.setId(rs.getString(1));
                e.setCodigo(rs.getString(2));
                e.setNombreSector(rs.getString(3));
                e.setFechaInforme(rs.getDate(4));
                return e;
            }
        });
    }
    
    public BeanInformePresupuestal getInformePresupuestalByCode(String code){  
        String sql=" SELECT identificadorPIP, descripcion, s.id," +
                   "        DATE_FORMAT(fechaInforme, '%Y-%m-%d'), montoAsignado, m.id, ip.codigo" +
                   " FROM informepresupuestal ip" +
                   " inner join sector s on s.id = ip.idsector" +
                   //" inner join contratoordenservicio c on c.id = ip.idcontratoOS" +
                   " inner join moneda m on m.id = ip.idmoneda" +
                   " WHERE ip.id=?"; 

        return template.queryForObject(sql, new Object[]{code}, new BeanPropertyRowMapper<BeanInformePresupuestal>(BeanInformePresupuestal.class) {
            public BeanInformePresupuestal mapRow(ResultSet rs, int row) throws SQLException {
                BeanInformePresupuestal e = new BeanInformePresupuestal();
                e.setIdentificadorPIP(rs.getString(1));
                e.setDescripcion(rs.getString(2));
                e.setNombreSector(rs.getString(3));
                e.setFechaInforme(rs.getDate(4));
                e.setMontoAsignado(rs.getBigDecimal(5));
                e.setMoneda(rs.getString(6));
                e.setCodigo(rs.getString(7));
                return e;
            }
        }); 
    }    
   
    public List<BeanListaValores> getContratos(){
        String sql = "SELECT id, nombre FROM contratoordenservicio";
        return template.query(sql, new RowMapper<BeanListaValores>() {  
            public BeanListaValores mapRow(ResultSet rs, int row) throws SQLException {
                BeanListaValores e = new BeanListaValores();
                e.setId(rs.getString(1));
                e.setCodigo(rs.getString(2));
                return e;
            }  
        });                
    }    
 
    public List<BeanListaValores> getSectores(){
        String sql = "SELECT id, nombre FROM sector";
        return template.query(sql, new RowMapper<BeanListaValores>() {  
            public BeanListaValores mapRow(ResultSet rs, int row) throws SQLException {
                BeanListaValores e = new BeanListaValores();
                e.setId(rs.getString(1));
                e.setCodigo(rs.getString(2));
                return e;
            }  
        });                
    } 
   
    public List<BeanListaValores> getDetalleInfPresup(String id) {
        String sql =    "SELECT a.id, a.nombre, d.fechaActividad" +
                        " FROM detalleinformepresupuestal d" +
                        " inner join actividad a on d.idactividad = a.id" +
                        " where d.identificadorPIP = '" + id + "'";
        return template.query(sql, new RowMapper<BeanListaValores>() {  
            public BeanListaValores mapRow(ResultSet rs, int row) throws SQLException {
                BeanListaValores e = new BeanListaValores();
                e.setId(rs.getString(1));
                e.setCodigo(rs.getString(2));
                e.setFechaInforme(rs.getDate(3));
                return e;
            }  
        });                
    }
    
    public List<BeanListaValores> getActividades() {
        String sql = "SELECT id, concat(codigo, ' - ', nombre) FROM actividad a";
        return template.query(sql, new RowMapper<BeanListaValores>() {  
            public BeanListaValores mapRow(ResultSet rs, int row) throws SQLException {
                BeanListaValores e = new BeanListaValores();
                e.setId(rs.getString(1));
                e.setCodigo(rs.getString(2));
                return e;
            }  
        });                
    }    
    
    public int save(BeanInformePresupuestal p) {
        String sql = "INSERT INTO informepresupuestal(descripcion, idSector, "
                + " fechaInforme, montoAsignado, idMoneda, codigo, identificadorPIP) "
                + " values('" + p.getDescripcion() + "', "
                + "        '" + p.getNombreSector() + "', "
                + "         STR_TO_DATE('" + DateFormat.getDateInstance().format(p.getFechaInforme())+ "', '%d/%m/%Y'), "
                + "         " + p.getMontoAsignado() + ", "
                + "        '" + p.getMoneda() + "', "
                + "        '" + p.getCodigo() + "', "                
                + "        '" + p.getIdentificadorPIP() + "' ) ";
        
        return template.update(sql);
    }   
   
    public int grabarActividades(String idActividad, String fechaActividad, String identificadorPIP) {
        
        String sql = "INSERT INTO detalleinformepresupuestal(idActividad, fechaActividad, identificadorPIP) "
                + " values( " + idActividad + ", "
                //+ " STR_TO_DATE('" + fechaActividad + "', '%d/%m/%Y'), "
                + " '" + fechaActividad + "', "
                + " '" + identificadorPIP + "') ";
        
        return template.update(sql);
    }       
  
    public int borrarActividades(String identificadorPIP) {
        
        String sql = "DELETE FROM detalleinformepresupuestal WHERE identificadorPIP = '"+identificadorPIP+"' ";
        
        return template.update(sql);
    }     
    
    public int getDetalleInfPre(String code){
        String sql = "SELECT count(1) from detalleinformepresupuestal WHERE identificadorPIP=?";
        return template.queryForObject(sql, new Object[] { code }, Integer.class);               
    }  
}
