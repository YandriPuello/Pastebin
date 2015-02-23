<%-- 
    Document   : trends
    Created on : Jul 7, 2014, 2:46:01 AM
    Author     : fmesa
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Codigo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Textos Trending</title>
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

    <%! List<Codigo> codigoList;
                 %>
    <%  codigoList = (List<Codigo>) request.getSession().getAttribute("codigos"); %>

    <body id="body">
        <div class="layout">
            <div id="logo" onclick="location.href = './index.jsp'" title="Create New Paste"></div>
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
                        <% if (usuario.getIdusuario() != null && !usuario.getUsuario().equals("guest")) {%>
                        <li class="no_border_li" style="font-weight: bold">Hola, <%=usuario.getUsuario()%></li>
                        <li><a href="u/user.jsp?user=<%= usuario.getUsuario()%>">mis textos</a></li>
                        <li><a href="">mis alertas</a></li>
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
                <div class="paste_box_frame">
                    <div class="paste_box_frame">
                        <div class="paste_box_icon">
                            <img src="assets/trend_big.png" width="50" height="50" alt="" border="0" />	
                        </div>
                        <div class="paste_box_info">
                            <div class="paste_box_line1"><h1>Textos Trending</h1></div>
                            <div class="paste_box_line2" style="text-transform:none;font-size:1.0em">Esta página contiene los textos más populares (top 20) de pastebin</div>
                        </div>
                    </div>
                </div>
                <table class="maintable" cellspacing="0">
                    <tr class="top">
                        <th scope="col" align="left"><a href="#">Nombre / Título</a></th>
                        <th scope="col" align="left"><a href="#">Añadido</a></th>				
                        <th scope="col" align="left"><a href="#">Cantidad de Vistas</a></th>	
                        <th scope="col" align="right"><a href="#">Usuario</a></th>
                    </tr>


                    <%  for (Codigo codigo : codigoList) { %>
                    <tr>
                        <td>
                            <img src="assets/t.gif"
                                 class="i_p<%=codigo.getIdvisibilidad().getIdvisibilidad()%>" 
                                 title="Texto Público, todos pueden ver el texto." alt="" border="0" />
                            <a href="codigo.jsp?id=<%= codigo.getUrl()%>"><%= codigo.getTitulo()%></a>
                        </td>
                        <td><%= new SimpleDateFormat("dd-MM-yyyy").format(codigo.getFechacreacion())%></td>			
                        <td><%= codigo.getCantidadviews()%></td>
                    <%      if(!codigo.getIdusuario().getUsuario().equals("guest")) { %>
                        <td align="right"><a href="u/user.jsp?user=<%= codigo.getIdusuario().getUsuario() %>"><%= codigo.getIdusuario().getUsuario() %></a></td>                       
                    <%      } else { %>
                        <td align="right"><%= codigo.getIdusuario().getUsuario() %></td>
                    <%      } %>
                    </tr>
                    <%  } %>
                </table>
                <div style="margin:10px 0;clear:left"></div>
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
