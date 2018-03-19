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
            <c:set var="action" value="saveInfPre.html" />
            <c:set var="readonly" value="false" />
        </c:if>
        <c:if test="${saveorupdate == 'update'}">
            <c:set var="action" value="updateInfPre.html" />
            <c:set var="readonly" value="true" />
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
            
            <form:form action="${action}" method="POST" modelAttribute="informepresupuestal" id="form1">
                
                <form:hidden class="input" path="id"/>           
                    
                <div class="input-group">
                    <label for="identificadorPIP" class="col-form-label">Proyecto Pre-Inversión:&nbsp;&nbsp;&nbsp;</label>
                    <form:input type="text" class="form-control form-control-sm" path="identificadorPIP" disabled="${readonly}"/>
                    <c:if test="${readonly == 'false'}">
                        <button type="button" class="btn btn-default btn-sm" onclick="location.href='${pageContext.request.contextPath}/obtenerPIPforIP.html?iPIP=' + encodeURI(document.getElementById('identificadorPIP').value)">Buscar</button>
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
                        <a class="nav-link active" data-toggle="tab" href="#1">Información General</a>
                    </li>
                    <li>
                        <a class="nav-link" data-toggle="tab" href="#2">Actividades</a>
                    </li>                   
                </ul>
  

                    <div class="tab-content">
                        
                        <div class="tab-pane active" id="1">
                            <div class="card"><div class="card-body">
                            <div class="container">
                                <br/>
                                
                                <div class="form-group">
                                    <label for="codigo">Nombre (*)</label>
                                    <form:input type="text" class="form-control form-control-sm" path="codigo" disabled="${readonly}"/>
                                    <span style="color: red; font-weight: bold; "><form:errors path="codigo" cssClass="error" /></span>
                                </div>                               
                                
                                <div class="form-group">
                                    <label for="descripcion">Descripción: (*)</label>
                                    <form:input type="text" class="form-control form-control-sm" path="descripcion" disabled="${readonly}"/>
                                    <span style="color: red; font-weight: bold; "><form:errors path="descripcion" cssClass="error" /></span>
                                </div>                              
                                
                                <div class="form-group">
                                    <label for="nombreSector">Sector: (*)</label>
                                    <form:select class="form-control form-control-sm" path="nombreSector" disabled="${readonly}">
                                        <c:if test="${saveorupdate == 'save'}">
                                            <form:option class="form-control form-control-sm" value="">[Seleccione Sector]</form:option>
                                        </c:if>                                        
                                        <c:forEach var="item" items="${sectorList}" varStatus="count"> 
                                            <form:option class="form-control form-control-sm" value="${item.id}">${item.codigo}</form:option>
                                        </c:forEach>
                                    </form:select>
                                    <span style="color: red; font-weight: bold; "><form:errors path="nombreSector" cssClass="error" /></span>
                                </div>                                  
                                
                                <div class="form-group">
                                    <label for="fechaInforme">Fecha de Informe: (*)</label>
                                    <form:input type="text" class="form-control form-control-sm" path="fechaInforme" placeholder="YYYY-MM-DD" disabled="${readonly}"/>
                                    <span style="color: red; font-weight: bold; "><form:errors path="fechaInforme" cssClass="error" /></span>
                                </div>
                                
                                <div class="form-group">
                                    <label for="montoAsignado">Monto Asignado: (*)</label>
                                    <form:input type="text" class="form-control form-control-sm" path="montoAsignado" placeholder="#,###,###.00" disabled="${readonly}"/>
                                    <span style="color: red; font-weight: bold; "><form:errors path="montoAsignado" cssClass="error" /></span>
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
                                    <span style="color: red; font-weight: bold; "><form:errors path="moneda" cssClass="error" /></span>
                                </div>
                            </div>
                            </div></div>
                        </div>

                        <div class="tab-pane" id="2">

                            <div class="card">
                                <div class="card-body">
                                    <div class="container">

                                        <c:if test="${saveorupdate == 'save'}">
                                            
                                            <table class="table table-striped" style="font-size: smaller; padding-left: 10px; padding-right: 10px;">
                                                <thead>
                                                    <tr style="text-align: center;">
                                                        <th>&nbsp;</th>
                                                        <th>Actividad</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="actividad" items="${actividadList}">
                                                        <tr style="text-align: center;">
                                                            <td><input type="checkbox" id="_checkbox" value="${actividad.id}"</td>
                                                            <td>${actividad.codigo}<input type="hidden" id="_hidden_${actividad.id}" value="${actividad.codigo}"</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                            <input type="button" value="Grabar Actividades" onclick="javascript:grabarActividades();">
                                            
                                        </c:if>
                                        <c:if test="${saveorupdate == 'update'}">
                                                                                    
                                            <table class="table table-striped" style="font-size: smaller; padding-left: 10px; padding-right: 10px;">
                                                <thead>
                                                    <tr style="text-align: center;">
                                                        <th>Actividad</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="detalle" items="${detalleList}">
                                                        <tr style="text-align: center;">
                                                            <td>${detalle.codigo}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>                                         
                                        
                                        </c:if>                                        
    
                                            
                                    </div>
                                </div>
                            </div>
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
                <button type="button" class="btn btn-default" onclick="location.href='${pageContext.request.contextPath}/cancelIP.html'">Cancelar</button> 
                <br /><br /><br /><br />
            </form:form>

       
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
        <!-- Modal content-->
        <div class="modal-content">

        <div class="modal-header">
          <h4 class="modal-title" >Alerta</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
          
        <div class="modal-body" id="header1">
          <p>Some text in the modal.</p>
        </div>
            
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>                
       
        </div>                
                
    </body>
    
    <script type="text/javascript">
        function grabarActividades() {

            var chk = document.getElementById("form1");
            var checkboxlist = chk._checkbox;
            var cadena = "";
        
            for (var i = 0; i < checkboxlist.length; i++) {
                //alert(checkboxlist[i].checked);
                if( checkboxlist[i].checked ) {
                    //if (document.getElementById("_text_" + checkboxlist[i].value).value == "") {
                        //document.getElementById("header1").innerHTML = "La fecha de la actividad "+ document.getElementById("_hidden_" + checkboxlist[i].value).value +" marcada se encuentra vacía.Por favor verifique.";
                    //    document.getElementById("header1").innerHTML = "Hay Fechas de Actividad activas se encuentran vacías. Por favor verifique.";
                    //    cadena = "";
                    //    $("#myModal").modal();
                    //    break;
                    //} else {
                        cadena = cadena + checkboxlist[i].value + "_20170101_" + document.getElementById("identificadorPIP").value + ",";
                    //}
                }
            }
            
            if (cadena == "") {
               //alert("cadena"); 
            } else {

                $.ajax({
                    type: "GET",
                    url: "ajaxIP/" + cadena + ".html",
                    success: function(result){
                        //alert(result);
                        document.getElementById("header1").innerHTML = "Registro de Actividades Grabado.";
                        $("#myModal").modal();
                }});               
               
            }
             

            
        }
    </script>
    
</html>
