/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.bean.BeanAnexo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Juanka
 */
public class AnexoDAO {
    //dao anexo
    JdbcTemplate template;
    
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }     
    
    public int save(BeanAnexo p) {
        String sql = "INSERT INTO anexo(nombreAnexo, identificadorPIP) "
                    + " values('" + p.getNombre()+ "', '"+p.getIdentificadorPIP()+"') ";

        return template.update(sql);
    }
    
    public List<BeanAnexo> getAnexoByCode(String code){ 
    
        String sql=" SELECT id, nombreAnexo" +
                   " FROM anexo ds" +                
                   " WHERE identificadorPIP = '" + code + "'";
        
        
        return template.query(sql, new RowMapper<BeanAnexo>() {
            public BeanAnexo mapRow(ResultSet rs, int row) throws SQLException {
                BeanAnexo e = new BeanAnexo();
                e.setId(rs.getString(1));
                e.setNombre(rs.getString(2));
                return e;
            }
        });
    }    
}
