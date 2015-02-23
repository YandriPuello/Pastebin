<%-- 
    Document   : manage
    Created on : Jul 2, 2014, 11:35:01 PM
    Author     : fmesa
--%>

<%@page import="edu.pucmm.pw.parcial2.entities.Codigo"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Usuario"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <link rel="stylesheet" type="text/css" href="css/style2.css">
        <script type="text/javascript" src="js/jquery-2.1.1.js"></script>

        <title>Permisos de Usuario</title>
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
                            <%if(usuario.getIdusuario() != null && usuario.getIdtipousuario().getNombre().equals("administrador")) {%>
                                    <a href="manage.jsp">permisos de usuarios</a>
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
                        <% if (usuario != null && usuario.getIdusuario() != null
                                && !usuario.getUsuario().equals("guest")) {%>
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
                    Permisos de Usuario
                </div>
                <form id="formLoginUser" name="formManageUser" method="POST" action="./PermisosUsuarios">
                    <div class="table_content" >
                        <table >
                            <tr>
                                <td>
                                    Usuario
                                </td>
                                <td >
                                    Tipo Usuario
                                </td>
                            </tr>
                            <%  List<Usuario> usuarioList = (List<Usuario>) application.getAttribute("usuarioList");
                                int count = 0;
                                for (Usuario user : usuarioList) {
                                    if (!user.getUsuario().equals("admin")) {%>
                            <tr>
                                <td><input name="<%="userName" + (++count)%>" value="<%=user.getUsuario()%>" readonly/></td>
                                <td>
                                    <select name="<%="tipoUsuarioList" + (count)%>">
                                        <% if (user.getIdtipousuario().getNombre().equals("administrador")) {%>
                                        <option selected="selected" value="administrador">Administrador</option>
                                        <% } else { %>
                                        <option value="administrador">Administrador</option>
                                        <% } %>
                                        <% if (user.getIdtipousuario().getNombre().equals("normal")) {%>
                                        <option selected="selected" value="normal">Normal</option>
                                        <% } else { %>
                                        <option value="normal">Normal</option>
                                        <%}%>
                                        <% if (user.getIdtipousuario().getNombre().equals("guest")) {%>
                                        <option selected="selected" value="guest">Guest</option>
                                        <% } else { %>
                                        <option value="guest">Guest</option>
                                        <%}%>
                                    </select>
                                </td>
                            </tr>
                            <%      }%>
                            <%  }%>
                        </table>
                    </div>
                    <div class="form_frame">
                        <div class="form_left">
                            &nbsp;
                        </div>
                        <div class="form_right">
                            <input name="submit" type="submit" value="Salvar" id="submit" accesskey="s" class="button1 btnbold" />
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
