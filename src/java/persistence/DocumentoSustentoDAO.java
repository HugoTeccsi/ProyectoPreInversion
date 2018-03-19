/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.bean.BeanDocumentoSustento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juanka
 */
public class DocumentoSustentoDAO {

    JdbcTemplate template;
    
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }     
    
    public int save(BeanDocumentoSustento p) {
        String sql = "INSERT INTO documentosustento(nombreDocumento, identificadorPIP) "
                    + " values('" + p.getNombre()+ "', '"+p.getIdentificadorPIP()+"') ";

        return template.update(sql);
    }
    
    public List<BeanDocumentoSustento> getDocumentoSustentoByCode(String code){ 
    
        String sql=" SELECT id, nombreDocumento" +
                   " FROM documentosustento ds" +                
                   " WHERE identificadorPIP = '" + code + "'";
        
        
        return template.query(sql, new RowMapper<BeanDocumentoSustento>() {
            public BeanDocumentoSustento mapRow(ResultSet rs, int row) throws SQLException {
                BeanDocumentoSustento e = new BeanDocumentoSustento();
                e.setId(rs.getString(1));
                e.setNombre(rs.getString(2));
                return e;
            }
        });
    }
}
