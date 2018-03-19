/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bean.BeanBusquedaPIP;
import model.bean.BeanDocumentoSustento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import persistence.DocumentoSustentoDAO;

/**
 *
 * @author Juanka
 */
public class FileUploadController extends AbstractController {
    
    @Autowired
    DocumentoSustentoDAO documentosustentodao;     
    
    private static final String destinationDir = "C:/TEMP/";

    @Override
    //@RequestMapping(value="/fileUpload", method = RequestMethod.GET)  
    protected ModelAndView handleRequestInternal(HttpServletRequest req,
            HttpServletResponse res) throws Exception {
                                
        res.setContentType("text/html");
        if (!(req instanceof MultipartHttpServletRequest)) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expected multipart request");
            return null;
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        MultipartFile file = multipartRequest.getFile("fileUploaded");
        File destination = new File(destinationDir + file.getOriginalFilename());
        file.transferTo(destination);
        //res.getWriter().write("Carga satisfactoria: " + destination.getAbsolutePath());
        //res.getWriter().write("<a href='doc_sustento.html'>Volver</a>");
        res.flushBuffer();

        
        BeanDocumentoSustento documentosustento = new BeanDocumentoSustento();
        documentosustento.setNombre(file.getOriginalFilename());
        documentosustento.setIdentificadorPIP(req.getParameter("iPIP"));
        documentosustentodao.save(documentosustento);
        
        List<BeanDocumentoSustento> list = documentosustentodao.getDocumentoSustentoByCode(req.getParameter("iPIP"));

        ModelAndView miMAV = new ModelAndView();
        miMAV.setViewName("doc_sustento");
        miMAV.addObject("list", list);

        return miMAV;        
    }
    
    
}
