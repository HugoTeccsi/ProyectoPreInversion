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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import model.bean.BeanAnexo;
import model.bean.BeanBusquedaPIP;
import model.bean.BeanDocumentoSustento;
import model.bean.BeanEstudioPIP;
import model.bean.BeanRequerimiento;
import model.bean.BeanListaValores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import persistence.AnexoDAO;
import persistence.DocumentoSustentoDAO;
import persistence.EstudioPIPDAO;
import persistence.RequerimientoDAO;

/**
 *
 * @author Juanka
 */
@Controller
public class EstudioPIPController extends HttpServlet {
    
    @Autowired
    EstudioPIPDAO estudiopipdao; 
    
    @Autowired
    RequerimientoDAO requerimientodao;    
    
    @Autowired
    DocumentoSustentoDAO documentosustentodao;
    
    @Autowired
    AnexoDAO anexodao;         
    
    @RequestMapping(value="/mant_estudioPIP", method = RequestMethod.GET)  
    public String mant_estudioPIP(Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //List<BeanEstudioPIP> list = estudiopipdao.getEstudiosPIP();
        //BeanEstudioPIP estudiopip = new BeanEstudioPIP();
        BeanBusquedaPIP busquedapip = new BeanBusquedaPIP();
        //model.addAttribute("estudiopip", estudiopip);
        model.addAttribute("busquedapip", busquedapip);
        //model.addAttribute("list", list);        

        return "mant_estudioPIP";
    }     
    
    @RequestMapping(value="/doc_sustento", method = RequestMethod.GET)  
    public String doc_sustento(Model model,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value="iPIP") String iPIP) throws ServletException, IOException {
        
        List<BeanDocumentoSustento> list = documentosustentodao.getDocumentoSustentoByCode(iPIP);
        model.addAttribute("list", list);
        return "doc_sustento";
    }    
    
    @RequestMapping(value="/anexo", method = RequestMethod.GET)  
    public String anexo(Model model,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(value="iPIP") String iPIP) throws ServletException, IOException {
        
        List<BeanAnexo> list = anexodao.getAnexoByCode(iPIP);
        model.addAttribute("list", list);
        return "anexo";
    }
    
    @RequestMapping(value = "/buscarEstudioPIP", method = RequestMethod.POST)  
    public String buscarEstudioPIP(@ModelAttribute("busquedapip") BeanBusquedaPIP busquedapip,
                                   BindingResult result, Model model)  {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (busquedapip.getFechaInicio() == null || busquedapip.getFechaFinal()== null) {
            model.addAttribute("errorPIP", "Las fechas no pueden estar vacías");
        } else {
            if (busquedapip.getFechaFinal().getTime() < busquedapip.getFechaInicio().getTime()) {
                model.addAttribute("errorPIP", "El rango final debe ser superior al rango inicial");
            } else {
                List<BeanEstudioPIP> list = estudiopipdao.getEstudiosPIP(df.format(busquedapip.getFechaInicio()), df.format(busquedapip.getFechaFinal()));
                if (list.isEmpty()) {
                    model.addAttribute("errorPIP", "No se encontraron registros para el rango de fechas establecido.");
                }
                model.addAttribute("busquedapip", busquedapip);
                model.addAttribute("list", list);             
            }
        }

        return "mant_estudioPIP";
    }    
    
    @RequestMapping(value = "/editEstudioPIP/{id_estudio}", method = RequestMethod.GET)
    public String editEstudioPIP(Model model, HttpServletRequest request, HttpServletResponse response,
            @PathVariable String id_estudio) {
        
        BeanEstudioPIP estudiopip = estudiopipdao.getEstudioPIPByCode(id_estudio);
        BeanRequerimiento requerimiento = requerimientodao.getRequerimientoByCode(estudiopip.getIdentificadorPIP());
        
        List<BeanListaValores> estadoViabilidadList = estudiopipdao.getEstadosViabilidad();
        model.addAttribute("estadoViabilidadList", estadoViabilidadList);
        
        List<BeanListaValores> monedaList = estudiopipdao.getMonedas();
        model.addAttribute("monedaList", monedaList); 
        
        /*List<BeanListaValores> ffList = estudiopipdao.getFFs();
        model.addAttribute("ffList", ffList); */      
        
        model.addAttribute("saveorupdate", "update");
        model.addAttribute("estudiopip", estudiopip);
        model.addAttribute("requerimiento", requerimiento);
        
        List<BeanAnexo> anexoList = anexodao.getAnexoByCode(estudiopip.getIdentificadorPIP());
        model.addAttribute("listAnexos", anexoList);

        List<BeanDocumentoSustento> documentosustentoList = documentosustentodao.getDocumentoSustentoByCode(estudiopip.getIdentificadorPIP());
        model.addAttribute("listDocumentosSustento", documentosustentoList);        
        
        model.addAttribute("estudiopip_err", null);

        model.addAttribute("iPIP", estudiopip.getIdentificadorPIP());        
        
        return "estudioPIP_form";
    }    
    
    @RequestMapping(value = "/newEstudioPIP", method = RequestMethod.GET)
    public String newEstudioPIP(Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BeanEstudioPIP estudiopip = new BeanEstudioPIP();
        Date date = new Date();
        estudiopip.setFechaDeclaracion(date);
        
        List<BeanListaValores> estadoViabilidadList = estudiopipdao.getEstadosViabilidad();
        model.addAttribute("estadoViabilidadList", estadoViabilidadList);
        
        List<BeanListaValores> monedaList = estudiopipdao.getMonedas();
        model.addAttribute("monedaList", monedaList); 
        
        /*List<BeanListaValores> ffList = estudiopipdao.getFFs();
        model.addAttribute("ffList", ffList);   */     
        
        model.addAttribute("saveorupdate", "save");
        model.addAttribute("estudiopip", estudiopip);
        model.addAttribute("estudiopip_err", null);
        
        return "estudioPIP_form";
    }
    
    @RequestMapping(value = "/editEstudioPIP/updateEstudioPIP", method = RequestMethod.POST)
    //public String updateEtudioPIP(@ModelAttribute("estudiopip") BeanEstudioPIP estudiopip,
    //        Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    public String updateEstudioPIP (@ModelAttribute("estudiopip") @Valid BeanEstudioPIP estudiopip,
                                   BindingResult result, Model model) {
        
        if (result.hasErrors()) {

            BeanRequerimiento requerimiento = requerimientodao.getRequerimientoByCode(estudiopip.getIdentificadorPIP());
            
            List<BeanListaValores> estadoViabilidadList = estudiopipdao.getEstadosViabilidad();
            model.addAttribute("estadoViabilidadList", estadoViabilidadList);

            List<BeanListaValores> monedaList = estudiopipdao.getMonedas();
            model.addAttribute("monedaList", monedaList); 

            /*List<BeanListaValores> ffList = estudiopipdao.getFFs();
            model.addAttribute("ffList", ffList);*/
            
            model.addAttribute("requerimiento", requerimiento);
            model.addAttribute("saveorupdate", "update");

            return "estudioPIP_form";
        } else {
            estudiopipdao.update(estudiopip);
            return "redirect:/mant_estudioPIP.html";
        }
        
    }    
    
    @RequestMapping(value = "/saveEstudioPIP", method = RequestMethod.POST)
    //public String saveComplejo(@ModelAttribute("estudiopip") BeanEstudioPIP estudiopip,
    //        Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    public String saveEstudioPIP (@ModelAttribute("estudiopip") @Valid BeanEstudioPIP estudiopip,
                                        BindingResult result, Model model, RedirectAttributes redir) {
        
        if (result.hasErrors()) {

            List<BeanListaValores> estadoViabilidadList = estudiopipdao.getEstadosViabilidad();
            model.addAttribute("estadoViabilidadList", estadoViabilidadList);

            List<BeanListaValores> monedaList = estudiopipdao.getMonedas();
            model.addAttribute("monedaList", monedaList); 

            /*List<BeanListaValores> ffList = estudiopipdao.getFFs();
            model.addAttribute("ffList", ffList);*/
            
            BeanRequerimiento requerimiento = requerimientodao.getRequerimientoByCode(estudiopip.getIdentificadorPIP());
            model.addAttribute("requerimiento", requerimiento);
            
            model.addAttribute("errorPIP", "Faltan llenar datos obligatorios. Por favor verifique");
            model.addAttribute("saveorupdate", "save");
            
            model.addAttribute("iPIP", estudiopip.getIdentificadorPIP());

            return "estudioPIP_form";
            
            /*
            ModelAndView miMAV = new ModelAndView();
            miMAV.setViewName("estudioPIP_form");
            miMAV.addObject("estadoViabilidadList", estadoViabilidadList);
            miMAV.addObject("monedaList", monedaList);
            miMAV.addObject("requerimiento", requerimiento);
            miMAV.addObject("errorPIP", "Faltan llenar datos obligatorios. Por favor verifique");
            miMAV.addObject("saveorupdate", "save");

            return miMAV;                
            */
        } else {
            /*
            ModelAndView miMAV = new ModelAndView();
            miMAV.setViewName("redirect:/mant_estudioPIP.html");
            redir.addFlashAttribute("successPIP", "Registro grabado correctamente.");
            return miMAV;
            */
            
            if (estudiopipdao.getAnexos(estudiopip.getIdentificadorPIP()) == 0) {
                
                List<BeanListaValores> estadoViabilidadList = estudiopipdao.getEstadosViabilidad();
                model.addAttribute("estadoViabilidadList", estadoViabilidadList);
                List<BeanListaValores> monedaList = estudiopipdao.getMonedas();
                model.addAttribute("monedaList", monedaList); 
                BeanRequerimiento requerimiento = requerimientodao.getRequerimientoByCode(estudiopip.getIdentificadorPIP());
                model.addAttribute("requerimiento", requerimiento);
                model.addAttribute("errorPIP", "No se tienen anexos asociados. Por favor verifique.");
                model.addAttribute("saveorupdate", "save"); 
                
                model.addAttribute("iPIP", estudiopip.getIdentificadorPIP());
                
                return "estudioPIP_form";
                
            } else if (estudiopipdao.getDocumentoSustento(estudiopip.getIdentificadorPIP()) < 2) {
                
                List<BeanListaValores> estadoViabilidadList = estudiopipdao.getEstadosViabilidad();
                model.addAttribute("estadoViabilidadList", estadoViabilidadList);
                List<BeanListaValores> monedaList = estudiopipdao.getMonedas();
                model.addAttribute("monedaList", monedaList); 
                BeanRequerimiento requerimiento = requerimientodao.getRequerimientoByCode(estudiopip.getIdentificadorPIP());
                model.addAttribute("requerimiento", requerimiento);
                model.addAttribute("errorPIP", "Falta el Informe Técnico y/o Presupuesto. Por favor verifique.");
                model.addAttribute("saveorupdate", "save");
                
                model.addAttribute("iPIP", estudiopip.getIdentificadorPIP());
                
                return "estudioPIP_form";            
            
            } else {
                estudiopipdao.save(estudiopip);
                redir.addFlashAttribute("successPIP", "Registro grabado correctamente.");
                return "redirect:/mant_estudioPIP.html";            
            }
            

        }        
        
    }
    
    @RequestMapping(value = "/cancelEstudioPIP", method = RequestMethod.GET)
    public String cancelEstudioPIP() {
        return "redirect:/mant_estudioPIP.html";
    }

    @RequestMapping(value = "/obtenerPIP", method = RequestMethod.GET, params = {"iPIP"})
    public String obtenerPIP(@RequestParam(value="iPIP") String iPIP, @ModelAttribute("estudiopip") BeanEstudioPIP estudiopip, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        if (iPIP.equals("")){            
            model.addAttribute("errorPIP", "Se requiere del ingreso de información para el código PIP");          
        } else {
            int valor = Integer.parseInt(requerimientodao.getProyectoPIPByEstudioPIP(iPIP));
            if (valor > 0) {
                model.addAttribute("errorPIP", "Existe estudio de Pre Inversión asociado al PIP");
            } else {
                
                valor = Integer.parseInt(requerimientodao.getProyectoPIPForITByCode(iPIP));
                if (valor == 0) {
                    model.addAttribute("errorPIP", "PIP no tiene Informe Técnico asociado.");
                } else {
                    
                    valor = Integer.parseInt(requerimientodao.getProyectoPIPForIPByCode(iPIP));
                    if (valor == 0) {
                        model.addAttribute("errorPIP", "PIP no tiene Informe Presupuestal asociado.");
                    } else {
                        String numeroPIP = requerimientodao.getProyectoPIPByCode(iPIP);
                        valor = Integer.parseInt(numeroPIP);
                        if (valor == 1) {
                            BeanRequerimiento requerimiento = requerimientodao.getRequerimientoByCode(iPIP);
                            estudiopip.setIdentificadorPIP(iPIP);
                            model.addAttribute("requerimiento", requerimiento);
                            model.addAttribute("errorPIP", "");
                        } else if (valor > 1) {
                            model.addAttribute("errorPIP", "No es posible consultar el PIP debido a que existe más de un PIP asignado al código ingresado");
                        } else if (valor == 0) {
                            model.addAttribute("errorPIP", "No existe proyecto de Pre Inversión");
                        }                       
                    }
                    
             
                }
                

            }  
        }
            
        List<BeanListaValores> estadoViabilidadList = estudiopipdao.getEstadosViabilidad();
        model.addAttribute("estadoViabilidadList", estadoViabilidadList);

        List<BeanListaValores> monedaList = estudiopipdao.getMonedas();
        model.addAttribute("monedaList", monedaList);

        Date date = new Date();
        estudiopip.setFechaDeclaracion(date);
        
        model.addAttribute("estudiopip", estudiopip);
        model.addAttribute("saveorupdate", "save");
        
        model.addAttribute("iPIP", iPIP);

        return "estudioPIP_form";        
        
    }
    
    
    @RequestMapping(value = "/deleteDocSustento/{id_doc}/{identificadorPIP}", method = RequestMethod.GET)
    public String deleteDocSustento(Model model, HttpServletRequest request, HttpServletResponse response,
            @PathVariable String id_doc, @PathVariable String identificadorPIP) {
        
        estudiopipdao.deleteDocSustento(id_doc);
        
        List<BeanDocumentoSustento> documentosustentoList = documentosustentodao.getDocumentoSustentoByCode(identificadorPIP);
        model.addAttribute("list", documentosustentoList);
        model.addAttribute("iPIP", identificadorPIP);
        
        return "doc_sustento";

    } 

    @RequestMapping(value = "/deleteAnexo/{id_doc}/{identificadorPIP}", method = RequestMethod.GET)
    public String deleteAnexo(Model model, HttpServletRequest request, HttpServletResponse response,
            @PathVariable String id_doc, @PathVariable String identificadorPIP) {
        
        estudiopipdao.deleteAnexo(id_doc);
        
        List<BeanAnexo> anexoList = anexodao.getAnexoByCode(identificadorPIP);
        model.addAttribute("list", anexoList);
        model.addAttribute("iPIP", identificadorPIP);
        
        return "anexo";

    }

    @ExceptionHandler({IOException.class, java.sql.SQLException.class, CannotGetJdbcConnectionException.class})
    public ModelAndView handleIOException(Exception ex) {
        ModelAndView model = new ModelAndView("IOError");
 
        model.addObject("exception", ex.getMessage());
         
        return model;
    } 
        
}
