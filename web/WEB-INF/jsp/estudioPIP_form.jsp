<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>       
        
        <title>Sistema de Gestión Municipal - Versión 1.0</title>
    </head>
    
    <body>        
        
        <%@include file="header.jsp" %>
        <c:if test="${saveorupdate == 'save'}">
            <c:set var="action" value="saveEstudioPIP.html" />
            <c:set var="readonly" value="false" />
            <c:set var="documentosustento" value="doc_sustento" />
        </c:if>
        <c:if test="${saveorupdate == 'update'}">
            <c:set var="action" value="updateEstudioPIP.html" />
            <c:set var="readonly" value="true" />
            <c:set var="documentosustento" value="doc_sustento_read" />
        </c:if>
        <div class="container col-sm-8" style="font-size: small">
            
            <c:if test="${not empty errorPIP}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert"
                            aria-label="Close">
                            <span aria-hidden="true">×</span>
                    </button>
                    <strong>${errorPIP}</strong>
                </div>
            </c:if>              
            
            <br /><br />
            
            <form:form action="${action}" method="POST" modelAttribute="estudiopip">
                
                <form:hidden class="input" path="id"/>           
                    
                <div class="input-group">
                    <label for="identificadorPIP" class="col-form-label">Proyecto Pre-Inversión:&nbsp;&nbsp;&nbsp;</label>
                    <form:input type="text" class="form-control form-control-sm" path="identificadorPIP" disabled="${readonly}"/>
                    <c:if test="${readonly == 'false'}">
                        <button type="button" class="btn btn-default btn-sm" onclick="location.href='${pageContext.request.contextPath}/obtenerPIP.html?iPIP=' + encodeURI(document.getElementById('identificadorPIP').value)">Buscar</button>
                    </c:if>
                </div>
                
                <br/>
                
                <div class="card">
                    <div class="card-body">   
                        <div class="row">
                            <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                              <label for="nombrePIP">Nombre del PIP:</label>
                              <input type="text" class="form-control form-control-sm" id="identificadorPIP" disabled="true" value="${requerimiento.nombrePIP}" />
                            </div>

                            <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                              <label for="nivelEstudio">Nivel de Estudio:</label>
                              <input type="text" class="form-control form-control-sm" id="nivelEstudio" disabled="true" value="${requerimiento.nivelEstudio}" />
                            </div> 

                            <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                              <label for="estadoPIP">Estado del PIP:</label>
                              <input type="text" class="form-control form-control-sm" id="estadoPIP" disabled="true" value="${requerimiento.estadoPIP}" />
                            </div>

                            <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                              <label for="plazoEvaluacion">Plazo Evaluación OPI:</label>
                              <input type="text" class="form-control form-control-sm" id="plazoEvaluacion" disabled="true" value="${requerimiento.plazoEvaluacion}" />
                            </div> 

                            <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                              <label for="numeroRequerimiento">Número de Requerimiento:</label>
                              <input type="text" class="form-control form-control-sm" id="numeroRequerimiento" disabled="true" value="${requerimiento.numeroRequerimiento}" />
                            </div>                       

                            <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                              <label for="fechaInicio">Fecha Inicio Expediente Técnico:</label>
                              <input type="text" class="form-control form-control-sm" id="fechaInicio" disabled="true" value="${requerimiento.fechaInicio}" />
                            </div>                       

                            <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                              <label for="descripcion">Descripción del Requerimiento:</label>
                              <input type="text" class="form-control form-control-sm" id="descripcion" disabled="true" value="${requerimiento.descripcion}" />
                            </div>

                            <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                              <label for="fechaCulminacion">Fecha Culminación Expediente Técnico:</label>
                              <input type="text" class="form-control form-control-sm" id="fechaCulminacion" disabled="true" value="${requerimiento.fechaCulminacion}" />
                            </div>

                            <div class="clearfix"></div>
                        </div>

                    </div>        
                </div>
                    
                <br/>   
                                
                <ul class="nav nav-tabs nav-justified" role="tablist">
                    <li class="active">
                        <a class="nav-link active" data-toggle="tab" href="#1">Información Viabilidad</a>
                    </li>
                    <li>
                        <a class="nav-link" data-toggle="tab" href="#2">Documentos de Sustento</a>
                    </li>
                    <li>
                        <a class="nav-link" data-toggle="tab" href="#3">Anexos y Formatos SNIP</a>
                    </li>
                    <li>
                        <a class="nav-link" data-toggle="tab" href="#4">Detalle Estudio PIP</a>
                    </li>                    
                </ul>
  

                    <div class="tab-content">
                        
                        <div class="tab-pane active" id="1">
                            <div class="card"><div class="card-body">
                            <div class="container">
                                <br/>
                                <div class="form-group">
                                    <label for="leccionesAprendidas">Recomendaciones y Lecciones Aprendidas: (*)</label>
                                    <form:textarea type="text" class="form-control form-control-sm" path="leccionesAprendidas" rows="5" disabled="${readonly}" />
                                    <form:errors path="leccionesAprendidas" cssClass="error"><span style="color: red; font-weight: bold; ">Campo requerido</span></form:errors>
                                </div>

                                <div class="form-group">
                                    <label for="estadoViabilidad">Estado de Viabilidad: (*)</label>
                                    <form:select class="form-control form-control-sm" path="estadoViabilidad" disabled="${readonly}">
                                        <c:if test="${saveorupdate == 'save'}">
                                            <form:option class="form-control form-control-sm" value="">[Seleccione Estado Viabilidad]</form:option>
                                        </c:if>
                                        <c:forEach var="item" items="${estadoViabilidadList}" varStatus="count"> 
                                            <form:option class="form-control form-control-sm" value="${item.id}">${item.codigo}</form:option>
                                        </c:forEach>
                                    </form:select>
                                    <form:errors path="estadoViabilidad" cssClass="error"><span style="color: red; font-weight: bold; ">Campo requerido</span></form:errors>
                                </div>

                                <div class="form-group">
                                    <label for="fechaDeclaracion">Fecha Declaración de Viabilidad: (*)</label>
                                    <form:input type="text" class="form-control form-control-sm" path="fechaDeclaracion" placeholder="YYYY-MM-DD" disabled="${readonly}"/>
                                    <span style="color: red; font-weight: bold; "><form:errors path="fechaDeclaracion" cssClass="error" /></span>
                                </div>

                                <div class="form-group">
                                    <label for="montoInversionViable">Monto Pre-Inversion Viable: (*)</label>
                                    <form:input type="text" class="form-control form-control-sm" path="montoInversionViable" placeholder="#,###,###.00" disabled="${readonly}"/>
                                    <form:errors path="montoInversionViable" cssClass="error"><span style="color: red; font-weight: bold; ">Campo requerido</span></form:errors>
                                </div>                          

                                <div class="form-group">
                                    <label for="moneda">Moneda: (*)</label>
                                    <form:select class="form-control form-control-sm" path="moneda" disabled="${readonly}">
                                        <c:if test="${saveorupdate == 'save'}">
                                            <form:option class="form-control form-control-sm" value="">[Seleccione Moneda]</form:option>
                                        </c:if>                                        
                                        <c:forEach var="item" items="${monedaList}" varStatus="count"> 
                                            <form:option class="form-control form-control-sm" value="${item.id}">${item.codigo}</form:option>
                                        </c:forEach>
                                    </form:select>
                                    <form:errors path="moneda" cssClass="error"><span style="color: red; font-weight: bold; ">Campo requerido</span></form:errors>
                                </div>
                            </div>
                            </div></div>
                        </div>

                        <div class="tab-pane fade" id="2">
                            <div class="card"><div class="card-body">
                            <div class="container">
                                
                                <ul class="nav nav-tabs nav-justified" role="tablist">
                                    <li class="active">
                                        <a class="nav-link active" data-toggle="tab" href="#A">Informe Técnico</a>
                                    </li>
                                    <li>
                                        <a class="nav-link" data-toggle="tab" href="#B">Informe Presupuestal</a>
                                    </li>                    
                                </ul>                                
                                
                                <div class="tab-content">
                                    <div class="tab-pane active" id="A">
                                        
                                        <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                                          <label for="it_codigo">Nombre Informe Técnico:</label>
                                          <input type="text" class="form-control form-control-sm" id="identificadorPIP" disabled="true" value="${requerimiento.it_codigo}" />
                                        </div>

                                        <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                                          <label for="it_fechaCreacion">Descripción Informe Técnico:</label>
                                          <input type="text" class="form-control form-control-sm" id="nivelEstudio" disabled="true" value="${requerimiento.it_descripcion}" />
                                        </div> 
                                        
                                        <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                                          <label for="it_fechaCreacion">Fecha de Creación:</label>
                                          <input type="text" class="form-control form-control-sm" id="nivelEstudio" disabled="true" value="${requerimiento.it_fechaCreacion}" />
                                        </div>                                         

                                        <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                                          <label for="it_terminoAprobacion">Término de Aprobación:</label>
                                          <input type="text" class="form-control form-control-sm" id="estadoPIP" disabled="true" value="${requerimiento.it_terminoAprobacion}" />
                                        </div>
                                        
                                    </div>

                                    <div class="tab-pane" id="B">
                                        
                                        <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                                          <label for="ip_descripcion">Nombre:</label>
                                          <input type="text" class="form-control form-control-sm" id="identificadorPIP" disabled="true" value="${requerimiento.ip_codigo}" />
                                        </div>                                    

                                        <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                                          <label for="ip_descripcion">Descripción:</label>
                                          <input type="text" class="form-control form-control-sm" id="identificadorPIP" disabled="true" value="${requerimiento.ip_descripcion}" />
                                        </div>

                                        <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                                          <label for="ip_fechaInforme">Fecha de Informe:</label>
                                          <input type="text" class="form-control form-control-sm" id="nivelEstudio" disabled="true" value="${requerimiento.ip_fechaInforme}" />
                                        </div> 

                                        <div class="form-group col-xs-10 col-sm-6 col-md-6 col-lg-6">
                                          <label for="ip_montoAsignado">Monto Asignado:</label>
                                          <input type="text" class="form-control form-control-sm" id="estadoPIP" disabled="true" value="${requerimiento.ip_montoAsignado}" />
                                        </div>

                                    </div>                                    
                                </div>
                                
                            </div>    
                            </div></div>
                                
                        </div>

                        <div class="tab-pane" id="3">
                            <div class="card"><div class="card-body">
                            <div class="container">
                                
                                <c:if test="${saveorupdate == 'save'}">
                                    <iframe src="anexo.html?iPIP=${iPIP}" height="400" width="800" frameBorder="0"></iframe>
                                </c:if>
                                <c:if test="${saveorupdate == 'update'}">
                                
                                    <table class="table table-striped" style="font-size: smaller; padding-left: 10px; padding-right: 10px;">
                                        <thead>
                                            <tr style="text-align: center;">
                                                <th>Nombre del Documento</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="anexos" items="${listAnexos}">
                                                <tr style="text-align: center;">
                                                    <td>${anexos.nombre}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>                                
                                
                                </c:if>                                
                                
                            </div>
                            </div></div>
                        </div>

                        <div class="tab-pane" id="4">
                            <div class="card"><div class="card-body">
                            <div class="container">
                                <br/><br/>

                                <div class="form-group">
                                    <label for="encargadoOperacion">Encargado de la Operación: (*)</label>
                                    <form:input type="text" class="form-control form-control-sm" path="encargadoOperacion" disabled="${readonly}"/>
                                    <form:errors path="encargadoOperacion" cssClass="error"><span style="color: red; font-weight: bold; ">Campo requerido</span></form:errors>
                                </div> 

                                <div class="form-group">
                                    <label for="responsableInforme">Responsable Elaboración Informe: (*)</label>
                                    <form:input type="text" class="form-control" path="responsableInforme" disabled="${readonly}"/>
                                    <form:errors path="responsableInforme" cssClass="error"><span style="color: red; font-weight: bold; ">Campo requerido</span></form:errors>
                                </div>
                            
                            </div>
                            </div></div>
                        </div> 
                            
                        
                    </div>
                
                <br />
                <c:if test="${empty requerimiento.nombrePIP}">
                    <button type="submit" class="btn btn-default" disabled="disabled">Grabar</button>
                </c:if>
                <c:if test="${not empty requerimiento.nombrePIP}">
                    <c:if test="${readonly == 'true'}">
                        <!--<button type="submit" class="btn btn-default" disabled="disabled">Grabar</button>-->
                    </c:if>
                    <c:if test="${readonly == 'false'}">
                        <button type="submit" class="btn btn-default">Grabar</button>
                    </c:if>                    
                </c:if>                    
                <!--<button type="button" class="btn btn-default" onclick="javascript:submit1();">Grabar</button>-->
                <button type="button" class="btn btn-default" onclick="location.href='${pageContext.request.contextPath}/cancelEstudioPIP.html'">Cancelar</button> 
                <br /><br /><br /><br />
            </form:form>   
       
    </body>
</html>
