<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
            
            <c:if test="${not empty successPIP}">
                <div class="alert alert-success alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert"
                            aria-label="Close">
                            <span aria-hidden="true">×</span>
                    </button>
                    <strong>${successPIP}</strong>
                </div>
            </c:if>             
            
            </br>          
            
            
            <form:form action="buscarEstudioPIP.html" method="POST" modelAttribute="busquedapip">
            
                <div class="input-group">
                    <label for="fechaInicio" class="col-form-label">Fecha Inicio:&nbsp;&nbsp;&nbsp;</label>
                    <form:input type="text" class="form-control-sm" path="fechaInicio" placeholder="YYYY-MM-DD" />
                    <form:errors path="fechaInicio" cssClass="error"><span style="color: red; font-weight: bold; ">Campo requerido</span></form:errors>
                    <label for="fechaInicio" class="col-form-label">&nbsp;&nbsp;&nbsp;Fecha Final:&nbsp;&nbsp;&nbsp;</label>
                    <form:input type="text" class="form-control-sm" path="fechaFinal" placeholder="YYYY-MM-DD" />
                    <form:errors path="fechaFinal" cssClass="error"><span style="color: red; font-weight: bold; ">Campo requerido</span></form:errors>
                    <button type="submit" class="btn btn-primary  btn-sm">Buscar</button>
            
                </div>
            
            <br/>
            <table class="table table-striped" style="font-size: smaller; padding-left: 10px; padding-right: 10px;">
                <thead>
                    <tr style="text-align: center;">
                        <th>Identificador PIP</th>
                        <th style="text-align: left;">Descripción</th>
                        <th style="text-align: left;">Nombre PIP</th>
                        <th>Estado PIP</th>
                        <th>Monto Pre-Inversión</th>
                        <th>Fecha Viabilidad</th>
                        <th>Estado Viabilidad</th>                        
                        <th>&nbsp;</th>
                    </tr>
                </thead>
                <c:forEach var="epip" items="${list}">
                    <tr style="text-align: center;">
                        <td>${epip.identificadorPIP}</td>
                        <td style="text-align: left;">${epip.descripcion}</td>
                        <td style="text-align: left;">${epip.nombrePIP}</td>
                        <td>${epip.estadoPIP}</td>
                        <td style="text-align: right;">
                            <fmt:formatNumber pattern="#,##0.00" value = "${epip.montoInversionViable}" />
                        </td>
                        <td style="text-align: center;">${epip.fechaDeclaracion}</td>
                        <td>${epip.estadoViabilidad}</td>
                        <td><a href="editEstudioPIP/${epip.id}.html">Ver</a></td>
                    </tr>
                </c:forEach>
            </table>
            </form:form>

        </div>        
        
    </body>
</html>
