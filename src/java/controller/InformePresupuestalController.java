/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import model.bean.BeanAnexo;
import model.bean.BeanBusquedaPIP;
import model.bean.BeanDocumentoSustento;
import model.bean.BeanEstudioPIP;
import model.bean.BeanInformePresupuestal;
import model.bean.BeanListaValores;
import model.bean.BeanRequerimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import persistence.EstudioPIPDAO;
import persistence.InformePresupuestalDAO;
import persistence.RequerimientoDAO;

/**
 *
 * @author Juanka
 */
@Controller
public class InformePresupuestalController {
    
    @Autowired
    InformePresupuestalDAO informepresupuestaldao; 
    
    @Autowired
    RequerimientoDAO requerimientodao;

    @Autowired
    EstudioPIPDAO estudiopipdao;    
    
    @RequestMapping(value="/mant_informePresupuestal", method = RequestMethod.GET)  
    public String mant_estudioPIP(Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //List<BeanEstudioPIP> list = estudiopipdao.getEstudiosPIP();
        //BeanEstudioPIP estudiopip = new BeanEstudioPIP();
        BeanBusquedaPIP busquedapip = new BeanBusquedaPIP();
        //model.addAttribute("estudiopip", estudiopip);
        model.addAttribute("busquedapip", busquedapip);
        //model.addAttribute("list", list);        

        return "mant_informePresupuestal";
    }    
    
    @RequestMapping(value = "/buscarPIPforIP", method = RequestMethod.POST)  
    public String buscarPIPforIP(@ModelAttribute("busquedapip") BeanBusquedaPIP busquedapip,
                                 BindingResult result, Model model)  {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (busquedapip.getFechaInicio() == null || busquedapip.getFechaFinal()== null) {
            model.addAttribute("errorPIP", "Las fechas no pueden estar vacías");
        } else {
            if (busquedapip.getFechaFinal().getTime() < busquedapip.getFechaInicio().getTime()) {
                model.addAttribute("errorPIP", "El rango final debe ser superior al rango inicial");
            } else {
                List<BeanInformePresupuestal> list = informepresupuestaldao.getInformesPresupuestales(df.format(busquedapip.getFechaInicio()), df.format(busquedapip.getFechaFinal()));
                if (list.isEmpty()) {
                    model.addAttribute("errorPIP", "No se encontraron registros para el rango de fechas establecido.");
                }
                model.addAttribute("busquedapip", busquedapip);
                model.addAttribute("list", list);             
            }
        }

        return "mant_informePresupuestal";
    } 

    @RequestMapping(value = "/editInformePresupuestal/{id_estudio}", method = RequestMethod.GET)
    public String editInformePresupuestal(Model model, HttpServletRequest request, HttpServletResponse response,
            @PathVariable String id_estudio) {
        
        BeanInformePresupuestal informepresupuestal = informepresupuestaldao.getInformePresupuestalByCode(id_estudio);
        BeanRequerimiento requerimiento = requerimientodao.getRequerimientoForIPByCode(informepresupuestal.getIdentificadorPIP());
                
        List<BeanListaValores> monedaList = estudiopipdao.getMonedas();
        model.addAttribute("monedaList", monedaList);
    
        List<BeanListaValores> contratoList = informepresupuestaldao.getContratos();
        model.addAttribute("contratoList", contratoList);
        
        List<BeanListaValores> detalleList = informepresupuestaldao.getDetalleInfPresup(informepresupuestal.getIdentificadorPIP());
        model.addAttribute("detalleList", detalleList); 
        
        List<BeanListaValores> sectorList = informepresupuestaldao.getSectores();
        model.addAttribute("sectorList", sectorList);         
        
        model.addAttribute("saveorupdate", "update");
        model.addAttribute("informepresupuestal", informepresupuestal);
        model.addAttribute("requerimiento", requerimiento);
                
        model.addAttribute("estudiopip_err", null);

        model.addAttribute("iPIP", informepresupuestal.getIdentificadorPIP());        
        
        return "informePresupuestal_form";
    }    
    
    
    @RequestMapping(value = "/cancelIP", method = RequestMethod.GET)
    public String cancelEstudioPIP() {
        return "redirect:/mant_informePresupuestal.html";
    }    
    
    @RequestMapping(value = "/ajaxIP/{cadena}", method = RequestMethod.GET)
    public @ResponseBody String ajaxIP(Model model, HttpServletRequest request, HttpServletResponse response,
                                       @PathVariable String cadena) {
        
        //System.out.println(cadena);
        String[] parts = cadena.split(",");
        //System.out.println(parts.length);
        
        for(int i=0; i<parts.length; i++) {
            
            String[] partsint = parts[i].split("_");
            
            if (i==0) {
                informepresupuestaldao.borrarActividades(partsint[2]);
            }
            
            informepresupuestaldao.grabarActividades(partsint[0], partsint[1], partsint[2]);
        }
        
        return "OK";
    }      
    
    @RequestMapping(value = "/newInformePresupuestal", method = RequestMethod.GET)
    public String newInformePresupuestal(Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BeanInformePresupuestal informepresupuestal = new BeanInformePresupuestal();
        
        List<BeanListaValores> monedaList = estudiopipdao.getMonedas();
        model.addAttribute("monedaList", monedaList);
    
        List<BeanListaValores> contratoList = informepresupuestaldao.getContratos();
        model.addAttribute("contratoList", contratoList);    
        
        List<BeanListaValores> sectorList = informepresupuestaldao.getSectores();
        model.addAttribute("sectorList", sectorList);  
        
        List<BeanListaValores> actividadList = informepresupuestaldao.getActividades();
        model.addAttribute("actividadList", actividadList);         
        
        model.addAttribute("saveorupdate", "save");
        model.addAttribute("informepresupuestal", informepresupuestal);
        model.addAttribute("estudiopip_err", null);
        
        return "informePresupuestal_form";
    }    
    
    @RequestMapping(value = "/obtenerPIPforIP", method = RequestMethod.GET, params = {"iPIP"})
    public String obtenerPIP(@RequestParam(value="iPIP") String iPIP, @ModelAttribute("informepresupuestal") BeanInformePresupuestal informepresupuestal, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        if (iPIP.equals("")){            
            model.addAttribute("errorPIP", "Se requiere del ingreso de información para el código PIP");          
        } else {
            
            int valor = Integer.parseInt(requerimientodao.getProyectoPIPByInfPresup(iPIP));
            if (valor > 0) {
                model.addAttribute("errorPIP", "Ya existe Informe Presupuestal asociado al PIP");
            } else {
                String numeroPIP = requerimientodao.getProyectoPIPByCode(iPIP);
                valor = Integer.parseInt(numeroPIP);
                if (valor == 1) {
                    BeanRequerimiento requerimiento = requerimientodao.getRequerimientoForIPByCode(iPIP);
                    System.out.println("Demostenes: " + requerimiento);
                    informepresupuestal.setIdentificadorPIP(iPIP);
                    model.addAttribute("requerimiento", requerimiento);
                    model.addAttribute("errorPIP", "");
                } else if (valor > 1) {
                    model.addAttribute("errorPIP", "No es posible consultar el PIP debido a que existe más de un PIP asignado al código ingresado");
                } else if (valor == 0) {
                    model.addAttribute("errorPIP", "No existe proyecto de Pre Inversión");
                }
            }
            
            BeanRequerimiento requerimiento = requerimientodao.getRequerimientoForIPByCode(iPIP);
            informepresupuestal.setIdentificadorPIP(iPIP);
            model.addAttribute("requerimiento", requerimiento);
            model.addAttribute("errorPIP", "");
        }
            
        List<BeanListaValores> monedaList = estudiopipdao.getMonedas();
        model.addAttribute("monedaList", monedaList);
    
        List<BeanListaValores> contratoList = informepresupuestaldao.getContratos();
        model.addAttribute("contratoList", contratoList);    
        
        List<BeanListaValores> sectorList = informepresupuestaldao.getSectores();
        model.addAttribute("sectorList", sectorList); 
        
        List<BeanListaValores> actividadList = informepresupuestaldao.getActividades();
        model.addAttribute("actividadList", actividadList);         
        
        model.addAttribute("saveorupdate", "save");
        model.addAttribute("informepresupuestal", informepresupuestal);
        model.addAttribute("estudiopip_err", null);
        
        model.addAttribute("iPIP", iPIP);

        return "informePresupuestal_form";        
        
    }    
    
    @RequestMapping(value = "/saveInfPre", method = RequestMethod.POST)
    public String saveInfPre (@ModelAttribute("informepresupuestal") @Valid BeanInformePresupuestal informepresupuestal,
                              BindingResult result, Model model, RedirectAttributes redir) {
        
        
        if (result.hasErrors()) {

            List<BeanListaValores> monedaList = estudiopipdao.getMonedas();
            model.addAttribute("monedaList", monedaList);

            List<BeanListaValores> contratoList = informepresupuestaldao.getContratos();
            model.addAttribute("contratoList", contratoList);    

            List<BeanListaValores> sectorList = informepresupuestaldao.getSectores();
            model.addAttribute("sectorList", sectorList); 
            
            List<BeanListaValores> actividadList = informepresupuestaldao.getActividades();
            model.addAttribute("actividadList", actividadList);
            
            BeanRequerimiento requerimiento = requerimientodao.getRequerimientoForIPByCode(informepresupuestal.getIdentificadorPIP());
            model.addAttribute("requerimiento", requerimiento);
            
            model.addAttribute("errorPIP", "Faltan llenar datos obligatorios. Por favor verifique");
            model.addAttribute("saveorupdate", "save");
            
            model.addAttribute("iPIP", informepresupuestal.getIdentificadorPIP());

            return "informePresupuestal_form";
            

        } else {
            
            if (informepresupuestaldao.getDetalleInfPre(informepresupuestal.getIdentificadorPIP()) == 0) {
            
                List<BeanListaValores> monedaList = estudiopipdao.getMonedas();
                model.addAttribute("monedaList", monedaList);

                List<BeanListaValores> contratoList = informepresupuestaldao.getContratos();
                model.addAttribute("contratoList", contratoList);    

                List<BeanListaValores> sectorList = informepresupuestaldao.getSectores();
                model.addAttribute("sectorList", sectorList); 
                
                List<BeanListaValores> actividadList = informepresupuestaldao.getActividades();
                model.addAttribute("actividadList", actividadList);                

                BeanRequerimiento requerimiento = requerimientodao.getRequerimientoForIPByCode(informepresupuestal.getIdentificadorPIP());
                model.addAttribute("requerimiento", requerimiento);

                model.addAttribute("errorPIP", "No existen actividades registradas al Informe Presupuestal.");
                model.addAttribute("saveorupdate", "save");

                model.addAttribute("iPIP", informepresupuestal.getIdentificadorPIP());

                return "informePresupuestal_form";                
                
            } else {
                informepresupuestaldao.save(informepresupuestal);
                redir.addFlashAttribute("successPIP", "Registro grabado correctamente.");
                return "redirect:/mant_informePresupuestal.html";                 
            }    

        }             
        
    }

    @ExceptionHandler({IOException.class, java.sql.SQLException.class, CannotGetJdbcConnectionException.class})
    public ModelAndView handleIOException(Exception ex) {
        ModelAndView model = new ModelAndView("IOError");
 
        model.addObject("exception", ex.getMessage());
         
        return model;
    } 
    
}
