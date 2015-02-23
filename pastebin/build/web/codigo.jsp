<%-- 
    Document   : index
    Created on : Jun 28, 2014, 7:12:28 PM
    Author     : fmesa
--%>

<%@page import="java.util.Date"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Codigo"%>
<%@page import="javax.ejb.EJB"%>
<%@page import="edu.pucmm.pw.parcial2.ejb.JpaServiceEJB"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Expiracion"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Usuario"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Visibilidad"%>
<%@page import="java.util.List"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Sintaxis"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Código</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <link rel="stylesheet" type="text/css" href="css/style2.css">
        <link rel="stylesheet" type="text/css" href="css/jquery-linedtextarea.css">
        <link rel="stylesheet" href="http://yandex.st/highlightjs/8.0/styles/default.min.css">
        <script src="http://yandex.st/highlightjs/8.0/highlight.min.js"></script>
	<script>hljs.initHighlightingOnLoad();</script>
        <script type="text/javascript" src="js/jquery-linedtextarea.js"></script>
        <script type="text/javascript" src="js/jquery-2.1.1.js"></script>
        <script type="text/javascript" src="js/index.js"></script>
    </head>
    <jsp:useBean id="usuario"
                 class="edu.pucmm.pw.parcial2.entities.Usuario" 
                 scope="session" />
       
    <jsp:useBean id="codigo"
                 class="edu.pucmm.pw.parcial2.entities.Codigo" 
                 scope="session" />
    
    <body id="body">
        <div class="layout">
            <div id="logo" onclick="location.href = './index.jsp'" title="Create New Paste"></div>
            <div id="header">
                <div id="header_top">
                    <span class="span_left more">PASTEBIN</span>
                    <ul class="top_menu">
                        <li>
                            <%if(usuario.getIdusuario() != null && usuario.getIdtipousuario().getNombre().equals("administrador")) {%>
                                    <a href="./manage.jsp">permisos de usuarios</a>
                            <%}%>   
                        </li>
                    </ul>
                </div>
                <div id="header_middle">
                    <span class="span_left big"><a href="index.jsp">PASTEBIN</a></span> 

                    <div id="header_middle_search">
                        <form class="search_form" name="search_form" method="get" action="/search" id="search_box">
                            <input class="search_input" type="text" name="q" size="5" value="" placeholder="search..." x-webkit-speech/>
                            <button type="submit" class="search_btn"><img src="assets/search_icon.png" title="Search" /></button>
                        </form>
                    </div>					
                </div>

                <div id="header_bottom">
                    <div class="div_top_menu">
                        <img src="assets/t.gif" class="i_n" width="16" height="16" alt="" border="0" /> <a href="index.jsp">crear un nuevo texto</a>
                        &nbsp;&nbsp;&nbsp; 
                        <img src="assets/t.gif" class="i_t" width="16" height="16" alt="" border="0" /> <a href="./trends">textos trending</a>
                    </div>
                    <ul class="top_menu">
                        <% if (usuario.getIdusuario() != null && !usuario.getUsuario().equals("guest")) {%>
                        <li class="no_border_li" style="font-weight: bold"><a>Hola, <%=usuario.getUsuario()%></a></li>
                        <li><a href="u/user.jsp?user=<%=usuario.getUsuario()%>">mis textos</a></li>
                        <li><a href="./alertas.jsp">mis alertas</a></li>
                        <li><a href="setting.jsp">mis preferencias</a></li>
                        <li><a href="profile.jsp">mi perfil</a></li>
                        <li><a href="./LogoutUsuario">salir</a></li>
                        <%} else {%>
                        <li class="no_border_li"><a href="signup.jsp">registrarse</a></li>
                        <li><a href="login.jsp">logearse</a></li>
                        <%}%>				
                    </ul>		
                </div>			
            </div>
        </div>
        <div id="content" class="layout">
            
            <div class="left">
                
                <div class="layout_clear"></div>
                
                <div class="content_title">
                    
                    <%= codigo.getTitulo()%>
                    
                </div>
                
                    
                    
                <div class="paste_info">
                    POR: <var> <%= codigo.getIdusuario().getUsuario()%></var> | CREADO EL: <var><%= codigo.getFechacreacion()%></var>  |  SINTAXIS: <var><%= codigo.getIdsintaxis().getNombre() %> </var> |  TAMAÑO: <var><%= codigo.getTamanoarchivo()/1024 %> KB</var>  |  VISTAS: <var><%= codigo.getCantidadviews()%> </var> |  EXPIRACIÓN: <var><%= codigo.getTimeLeft()%></var>
                </div>
                
                
                <div class="paste_info">
                    <a href="./editpaste.jsp?id=<%=codigo.getUrl()%>"> EDIT</a>  |  
                    <a href="./DeletePaste?id=<%=codigo.getIdcodigo()%>"> DELETE</a>  |  
                    <a href="./RawPaste?id=<%=codigo.getUrl() %>"> RAW</a>  |  
                    <a href="./embed.jsp?id=<%=codigo.getUrl() %>"> EMBED</a>  |  
                </div>
                
                <div>
                    <pre style="word-wrap:break-word;" class="textarea_border" ><code class=<%= codigo.getIdsintaxis().getNombre() %>> <%= codigo.getTexto()%> </code></pre>
                </div>
                 <div class="content_title">
                    RAW Paste Data
                </div>
                <form name="formCreateText" action="./CreatePaste" method="POST">
                    <div class="textarea_border">
                        <textarea  class="paste_textarea" id="paste_code" name="code"><%= codigo.getTexto()%></textarea>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>

