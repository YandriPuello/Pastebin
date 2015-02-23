<%-- 
    Document   : index
    Created on : Jun 28, 2014, 7:12:28 PM
    Author     : fmesa
--%>

<%@page import="edu.pucmm.pw.parcial2.entities.Codigo"%>
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
        <title>Edit</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <link rel="stylesheet" type="text/css" href="css/style2.css">
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
                            <%if (usuario.getIdusuario() != null && usuario.getIdtipousuario().getNombre().equals("administrador")) {%>
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
                        <img src="/i/t.gif" class="i_n" width="16" height="16" alt="" border="0" /> <a href="./">crear un nuevo texto</a> 
                        &nbsp;&nbsp;&nbsp; 
                        <img src="/i/t.gif" class="i_t" width="16" height="16" alt="" border="0" /> <a href="./trends">textos trending</a>
                    </div>
                    <ul class="top_menu">
                        <% if (usuario != null && usuario.getIdusuario() != null
                                    && !usuario.getUsuario().equals("guest")) {%>
                        <li class="no_border_li" style="font-weight: bold">Hola, <%=usuario.getUsuario()%></li>
                        <li><a href="u/user.jsp?user=<%= usuario.getUsuario()%>">mis textos</a></li>
                        <li><a href="">mis alertas</a></li>
                        <li><a href="./alertas.jsp">mis preferencias</a></li>
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
                    Editar Paste: <%=codigo.getTitulo() %>
                </div>
                <form name="formCreateText" action="./UpdatePaste" method="POST">
                    <input type="hidden" name="id" value=<%= codigo.getUrl()%> />
                    <div class="textarea_border">
                        <textarea  class="paste_textarea" id="paste_code" name="code"><%=codigo.getTexto() %></textarea>
                    </div>
                    <div class="content_title">Configuración de Texto</div>
                    <div class="form_frame_left">
                        <div class="form_frame">
                            <div class="form_left">
                                Tipo de Sintaxis:
                            </div>
                            <div class="form_right">
                                <select class="post_select" name="paste_format">
                                    <%
                                        List<Sintaxis> sintaxisList = (List<Sintaxis>) application.getAttribute("sintaxis");
                                        for (Sintaxis s : sintaxisList) {
                                            if(codigo.getIdsintaxis().getIdsintaxis() != s.getIdsintaxis()){
                                                %>
                                                <option value=<%= s.getIdsintaxis()%>> <%= s.getNombre()%> </option>
                                                <%
                                            }else{%>
                                                <option selected="selected" value=<%= s.getIdsintaxis()%>> <%= s.getNombre()%> </option>
                                           <%}
                                        }
                                    %>
                                </select>
                            </div>
                        </div>
                        <div class="form_frame">
                            <div class="form_left">
                                Expiración:
                            </div>
                            <div class="form_right">
                                <select class="post_select" name="paste_expire_date">
                                    <option selected="selected" value="-1"> No Cambiar </option>
                                    <%
                                        List<Expiracion> expiracionList = (List<Expiracion>) application.getAttribute("expiracion");
                                        for (Expiracion e : expiracionList) {
                                    %>
                                            <option value=<%= e.getTiempo()%>> <%= e.getDescripcion()%> </option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                        </div>
                        <div class="form_frame">
                            <div class="form_left">
                                Acceso:
                            </div>
                            <div class="form_right">
                                <select class="post_select" name="paste_visibility">
                                    <%
                                        List<Visibilidad> visibilidadList = (List<Visibilidad>) application.getAttribute("visibilidad");
                                        for (Visibilidad v : visibilidadList) {
                                            if(codigo.getIdvisibilidad().getIdvisibilidad() != v.getIdvisibilidad()){
                                                
                                    %>
                                    <option value=<%= v.getIdvisibilidad()%>> <%= v.getNombre()%> </option>
                                    <%
                                        }else{%>
                                              <option selected="selected" value=<%= v.getIdvisibilidad()%>> <%= v.getNombre()%> </option>  
                                            <%}
                                        }
                                    %>
                                </select>
                            </div>
                        </div>						
                        <div class="form_frame">
                            <div class="form_left">
                                Nombre / Título:
                            </div>
                            <div class="form_right">
                                <input type="text" name="paste_name" size="20" maxlength="60" value=<%=codigo.getTitulo() %> class="post_input" /> 
                            </div>
                        </div>
                        <div class="form_frame">
                            <div class="form_left">
                                &nbsp;
                            </div>
                            <div class="form_right">
                                <input name="submit" type="submit" value="Actualizar" id="submit" accesskey="s" class="button1 btnbold" />
                            </div>
                        </div>
                    </div>
                </form>
            </div> 
        </div>
    </body>
</html>

