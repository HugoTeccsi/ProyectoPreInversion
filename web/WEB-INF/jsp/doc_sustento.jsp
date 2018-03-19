<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%
    String iPIP = request.getParameter("iPIP");
    if (iPIP == null) {
        iPIP = request.getAttribute("iPIP").toString();
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>  
        
        <title>JSP Page</title>
    </head>
    <body>

        
        <form action="fileUpload.html" method="post" enctype="multipart/form-data">
            <div class="input-group">
                <label for="fileUploaded" class="form-control-sm">Subir Documento Sustento:</label>
                <input type="hidden" name ="iPIP" value="<%=iPIP%>">
                <input type="file" class="form-control-file form-control-sm" name="fileUploaded" accept="application/pdf, application/zip">                        
                <button type="submit" class="btn btn-default btn-sm">UPLOAD</button>
            </div>

        <br/>
                
        <table class="table table-striped" style="font-size: smaller; padding-left: 10px; padding-right: 10px;">
            <thead>
                <tr style="text-align: center;">
                    <th>Nombre del Documento</th>
                    <th>&nbsp;</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="docs" items="${list}">
                    <tr style="text-align: center;">
                        <td>${docs.nombre}</td>
                        <td><a href="${pageContext.request.contextPath}/deleteDocSustento/${docs.id}/<%=iPIP%>.html">Borrar</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
                
        </form>                
                
    </body>
</html>