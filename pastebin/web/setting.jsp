<%-- 
    Document   : setting
    Created on : Jul 6, 2014, 4:40:05 PM
    Author     : fmesa
--%>

<%@page import="edu.pucmm.pw.parcial2.entities.Codigo"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Visibilidad"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Expiracion"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Sintaxis"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Pastebin</title>
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

    <body id="body">
        <div class="layout">
            <div id="logo" onclick="location.href = 'index.jsp'" title="Create New Paste"></div>
            <div id="header">
                <div id="header_top">
                    <span class="span_left more">PASTEBIN</span>
                    <ul class="top_menu">
                        <li>
                            <%if (usuario.getIdusuario() != null && usuario.getIdtipousuario().getNombre().equals("administrador")) {%>
                            <a href="manage.jsp">permisos de usuarios</a>
                            <%}%>   
                        </li>
                    </ul>
                </div>
                <div id="header_middle">
                    <span class="span_left big"><a href="index.jsp">PASTEBIN</a></span> 

                    <div id="header_middle_search">
                        <form class="search_form" name="search_form" method="get" action="#" id="search_box">
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
                        <% if (usuario != null && usuario.getIdusuario() != null
                                    && !usuario.getUsuario().equals("guest")) {%>
                        <li class="no_border_li" style="font-weight: bold">Hola, <%=usuario.getUsuario()%></li>
                        <li><a href="u/user.jsp?user=<%= usuario.getUsuario()%>">mis textos</a></li>
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
                    Preferencia de los Textos
                </div>
                <form name="formCreatePreference" action="./preferencia" method="POST">
                    <div class="form_frame_left">
                        <div class="form_frame">
                            <div class="form_left">
                                Sintaxis:
                            </div>
                            <div class="form_right">
                                <select class="post_select" name="paste_format">
                                    <%
                                        List<Sintaxis> sintaxisList = (List<Sintaxis>) application.getAttribute("sintaxis");
                                        for (Sintaxis s : sintaxisList) {     
                                            if( usuario.getIdusuario() != null &&
                                                usuario.getPreferencias() != null &&
                                                usuario.getPreferencias().getIdsintaxis()!= null &&
                                                usuario.getPreferencias().getIdsintaxis().getIdsintaxis().equals(s.getIdsintaxis())){ %>
                                    <option selected="selected" value=<%= s.getIdsintaxis()%>> <%= s.getNombre()%> </option>
                                    <%      }  else { %>
                                    <option value=<%= s.getIdsintaxis()%>> <%= s.getNombre()%> </option>
                                    <%      }   
                                        } %>
                                </select>
                            </div>
                        </div>
                        <div class="form_frame">
                            <div class="form_left">
                                Expiración:
                            </div>
                            <div class="form_right">
                                <select class="post_select" name="paste_expire_date">
                                    <%
                                        List<Expiracion> expiracionList = (List<Expiracion>) application.getAttribute("expiracion");
                                        for (Expiracion e : expiracionList) {
                                            if( usuario.getIdusuario() != null &&
                                                usuario.getPreferencias() != null &&
                                                usuario.getPreferencias().getIdexpiracion() != null &&
                                                usuario.getPreferencias().getIdexpiracion().getIdexpiracion().equals(e.getIdexpiracion())){ %>
                                    <option selected="selected" value=<%= e.getIdexpiracion()  %>> <%= e.getDescripcion()%> </option>
                                    <%      }  else { %>
                                    <option value=<%= e.getIdexpiracion() %>> <%= e.getDescripcion()%> </option>
                                    <%      }   
                                        } %>
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
                                            if( usuario.getIdusuario() != null &&
                                                usuario.getPreferencias() != null &&
                                                usuario.getPreferencias().getIdvisibilidad() != null &&
                                                usuario.getPreferencias().getIdvisibilidad().getIdvisibilidad().equals(v.getIdvisibilidad())){ %>
                                    <option selected="selected"value=<%= v.getIdvisibilidad()%>> <%= v.getNombre()%> </option>
                                    <%      }  else { %>
                                    <option value=<%= v.getIdvisibilidad()%>> <%= v.getNombre()%> </option>
                                    <%      }   
                                        } %>
                                </select>
                            </div>
                        </div>						
                        <div class="form_frame">
                            <div class="form_left">
                                &nbsp;
                            </div>
                            <div class="form_right">
                                <input name="submit" type="submit" value="Salvar" id="submit" accesskey="s" class="button1 btnbold" />
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="right">
                <%  if(usuario.getIdusuario() != null && !usuario.getUsuario().equals("guest")) { %>
                <div class="content_right_menu">
                    <div class="content_right_title"><a href="u/user.jsp?user=<%= usuario.getUsuario() %>">Mis Textos</a></div>
                    <div id="menu_1">
                        <ul class="right_menu">
                            <%  for(Codigo codigo : usuario.getCodigoList()) { %>
                                <%  if(codigo.getIdvisibilidad().getIdvisibilidad().equals(1)){ %>
                                <li><a href="codigo.jsp?id=<%= codigo.getUrl() %>"><%= codigo.getTitulo() %></a>
                                <span><%= codigo.getTimeCreatedElapsed() %></span></li>
                                <%  } else { %>
                                <li class="xtra_<%=codigo.getIdvisibilidad().getIdvisibilidad()%>"><a href="codigo.jsp?id=<%= codigo.getUrl() %>"><%= codigo.getTitulo() %></a>
                                <span><%= codigo.getTimeCreatedElapsed() %></span><li>
                                <%  } %>
                            
                            <%  } %>
                        </ul>
                    </div>
                </div>
                <%  } %>
                <div class="content_right_menu">
                    <div class="content_right_title"><a href="/archive">Public Pastes</a></div>	
                    <div id="menu_2">
                        <ul class="right_menu">
                            <%  List<Codigo> codigosPublicos = (List<Codigo>) application.getAttribute("codigosPublicos");
                                for(Codigo codigo : codigosPublicos) { %>
                            <li>
                                <a href="codigo.jsp?id=<%= codigo.getUrl() %>"><%= codigo.getTitulo() %></a>
                                <span><%= codigo.getTimeCreatedElapsed() %></span>
                            </li>
                            <%  } %>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
