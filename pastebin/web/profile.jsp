<%-- 
    Document   : profile
    Created on : Jul 5, 2014, 6:26:16 PM
    Author     : fmesa
--%>

<%@page import="java.util.List"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Codigo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:useBean id="usuario"
                 class="edu.pucmm.pw.parcial2.entities.Usuario" 
                 scope="session" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <link rel="stylesheet" type="text/css" href="css/style2.css">
        <script type="text/javascript" src="js/jquery-2.1.1.js"></script>
        <title>Perfil de <%= usuario.getUsuario()%></title>
    </head>

    <body id="body">
        <div class="layout">
            <div id="logo" onclick="location.href = '/index.jsp'" title="Crear un nuevo texto"></div>
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
                        <li class="no_border_li" style="font-weight: bold">Hola, <%=usuario.getUsuario()%></li>
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
                    Editar Perfil
                </div>
                <form name="formProfileUser" enctype="multipart/form-data" method="post" action="./profile">
                    <div class="form_frame">
                        <div class="form_left">
                            Email Address:
                        </div>
                        <div class="form_right">
                            <%  if(usuario.getEmail()!= null) { %>
                            <input type="text" name="email" size="25" value="<%= usuario.getEmail()%>" class="post_input" /> 
                            <%  } else { %>
                            <input type="text" name="email" size="25" value="" class="post_input" /> 
                            <%  } %>
                        </div>
                    </div>

                    <div class="form_frame">
                        <div class="form_left">
                            Nombre:
                        </div>
                        <div class="form_right" style="margin-top: 2px">
                            <%  if(usuario.getNombre() != null) { %>
                            <input type="text" name="nombre" size="25" value="<%= usuario.getNombre()%>" class="post_input" /> 
                            <%  } else { %>
                            <input type="text" name="nombre" size="25" value="" class="post_input" /> 
                            <%  } %>
                        </div>
                    </div>

                    <div class="form_frame">
                        <div class="form_left">
                            Apellidos:
                        </div>
                        <div class="form_right" style="margin-top: 2px">
                            <%  if(usuario.getApellidos() != null) { %>
                            <input type="text" name="apellidos" size="50" value="<%= usuario.getApellidos()%>" class="post_input" /> 
                            <%  } else { %>
                            <input type="text" name="apellidos" size="50" value="" class="post_input" /> 
                            <%  } %>
                        </div>
                    </div>

                    <div class="form_frame">
                        <div class="form_left">
                            Change Password:
                        </div>
                        <div class="form_right">
                            <input type="password" autocomplete="off" name="password" size="20" value="" class="post_input" /> 
                        </div>
                    </div>

                    <div class="form_frame">
                        <div class="form_left">
                            Confirm Password:
                        </div>
                        <div class="form_right">
                            <input type="password" autocomplete="off" name="password2" size="20" value="" class="post_input" /> 
                        </div>
                    </div>

                    <div class="form_frame">
                        <div class="form_left">
                            Actualizar Avatar:
                        </div>
                        <div class="form_input">
                            <input type="file" name="imagen" value="" class="post_input" /> 
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
