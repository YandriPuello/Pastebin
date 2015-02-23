<%-- 
    Document   : user
    Created on : Jul 3, 2014, 9:35:04 PM
    Author     : fmesa
--%>

<%@page import="java.util.Date"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="javax.imageio.stream.ImageInputStream"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="edu.pucmm.pw.parcial2.ejb.JpaServiceEJB"%>
<%@page import="javax.ejb.EJB"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Sintaxis"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Visibilidad"%>
<%@page import="edu.pucmm.pw.parcial2.entities.Usuario"%>
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
        <link rel="stylesheet" type="text/css" href="../css/styles.css">
        <link rel="stylesheet" type="text/css" href="../css/style2.css">
        <script type="text/javascript" src="../js/jquery-2.1.1.js"></script>
        <title>User</title>
    </head>
    <%! List<Codigo> codigoList;
        List<Visibilidad> visibilidadList;
        List<Sintaxis> sintaxisList;
        String userParameter;
        Usuario usuarioVisitado;
        int countPublic, countList, countPrivate, countView;
        boolean isUser;
    %>
    <%  
        userParameter = request.getParameter("user");
        isUser = usuario.getUsuario().equals(userParameter);
        if(!isUser){
            usuarioVisitado = (Usuario) request.getSession().getAttribute("usuarioVisitado");
        }
        
        codigoList = (List<Codigo>) request.getSession().getAttribute("codigos");
        visibilidadList = (List<Visibilidad>) request.getServletContext().getAttribute("visibilidad");
        sintaxisList = (List<Sintaxis>) request.getServletContext().getAttribute("sintaxis");
        
        countPublic = 0; 
        countList = 0; 
        countPrivate = 0; 
        countView = 0;
        
        for (Codigo codigo : codigoList) {
            if (codigo.getIdvisibilidad().getIdvisibilidad().equals(1)) {
                countPublic++;
            } else if (codigo.getIdvisibilidad().getIdvisibilidad().equals(2)) {
                countList++;
            } else {
                countPrivate++;
            }
            countView += codigo.getCantidadviews();
        }
    %>
    <body id="body">
        <div class="layout">
            <div id="logo" onclick="location.href = '../index.jsp'" title="Crear un nuevo texto"></div>
            <div id="header">
                <div id="header_top">
                    <span class="span_left more">PASTEBIN</span>
                    <ul class="top_menu">
                        <li>
                            <%if (usuario.getIdusuario() != null && usuario.getIdtipousuario().getNombre().equals("administrador")) {%>
                            <a href="../manage.jsp">permisos de usuarios</a>
                            <%}%>   
                        </li>
                    </ul>
                </div>
                <div id="header_middle">
                    <span class="span_left big"><a href="../index.jsp">PASTEBIN</a></span> 

                    <div id="header_middle_search">
                        <form class="search_form" name="search_form" method="get" action="../search" id="search_box">
                            <input class="search_input" type="text" name="q" size="5" value="" placeholder="search..." x-webkit-speech/>
                            <button type="submit" class="search_btn"><img src="../assets/search_icon.png" title="Search" /></button>
                        </form>
                    </div>					
                </div>

                <div id="header_bottom">
                    <div class="div_top_menu">
                        <img src="../assets/t.gif" class="i_n" width="16" height="16" alt="" border="0" /> <a href="../index.jsp">crear un nuevo texto</a> 
                        &nbsp;&nbsp;&nbsp; 
                        <img src="../assets/t.gif" class="i_t" width="16" height="16" alt="" border="0" /> <a href="../trends">textos trending</a>
                    </div>
                    <ul class="top_menu">
                        <% if (usuario.getIdusuario() != null && !usuario.getUsuario().equals("guest")) {%>
                        <li class="no_border_li" style="font-weight: bold">Hola, <%=usuario.getUsuario()%></li>
                        <li><a href="user.jsp?user=<%=usuario.getUsuario()%>">mis textos</a></li>
                        <li><a href="../alertas.jsp">mis alertas</a></li>
                        <li><a href="../setting.jsp">mis preferencias</a></li>
                        <li><a href="../profile.jsp">mi perfil</a></li>
                        <li><a href="../LogoutUsuario">salir</a></li>
                            <%} else {%>
                        <li class="no_border_li"><a href="../signup.jsp">registrarse</a></li>
                        <li><a href="../login.jsp">logearse</a></li>
                            <%}%>				
                    </ul>		
                </div>			
            </div>
        </div>
        <div id="content" class="layout">
            <div class="left">
                <div class="layout_clear"></div>
                <div class="paste_box_frame">
                    <div class="paste_box_icon">
                        <%  if(isUser) { %>
                            <%  if(usuario.getImagenurl() == null || usuario.getImagenurl().equals("")) { %>
                        <img src="../assets/t.gif" class="i_gb" width="50" height="50" alt="Guest" border="0" />
                            <%  } else { %>
                        <img src="<%= usuario.getImagenurl() %>" width="50" height="50" alt="Guest" border="0" />
                        <%      } %>
                        <%  } else { %>
                            <%  if(usuarioVisitado.getImagenurl() == null || usuarioVisitado.getImagenurl().equals("")) { %>
                        <img src="../assets/t.gif" class="i_gb" width="50" height="50" alt="Guest" border="0" />
                            <%  } else { %>
                        <img src="<%= usuarioVisitado.getImagenurl() %>" width="50" height="50" alt="Guest" border="0" />
                        <%      } %>
                        <%  } %>
                    </div>
                    <div class="paste_box_info">
                        <%  if(isUser) {%>
                        <div class="paste_box_line1"><h1>Textos de <%=usuario.getUsuario()%></h1></div>
                        <div class="paste_box_line2">Total Pastes: <%= codigoList.size() %> &nbsp;|&nbsp; 
                            Total Pastes Hits: <%= countView %> &nbsp;|&nbsp; 
                            <%  if(usuario.getProfileview() != null) { %>
                            Pastebin Hits: <%=usuario.getProfileview() %> </div>
                            <%  } else { %>
                            Pastebin Hits: 0 </div>
                            <%  } %>
                        <%  } else { %>
                        <div class="paste_box_line1"><h1>Textos de <%=usuarioVisitado.getUsuario()%></h1></div>
                        <div class="paste_box_line2">Total Pastes: <%= codigoList.size() %> &nbsp;|&nbsp; 
                            Total Pastes Hits: <%= countView %> &nbsp;|&nbsp;
                            <%  if(usuarioVisitado.getProfileview() != null) { %>
                            Pastebin Hits: <%=usuarioVisitado.getProfileview() %> </div>
                            <%  } else { %>
                            Pastebin Hits: 0 </div>
                            <%  } %>
                        <%  } %>
                    </div>
                </div>
                <table class="maintable" cellspacing="0">
                    <tr class="top">
                        <th scope="col" align="left"><a href="#">Nombre / Título</a></th>
                        <th scope="col" align="left"><a href="#">Añadido</a></th>
                        <th scope="col" align="left"><a href="#">Expiración</a></th>				
                        <th scope="col" align="left"><a href="#">Cantidad de Vistas</a></th>	
                        <th scope="col" align="left"><a href="#">Sintaxis</a></th>
                        <th scope="col" align="right"><a href=#"">Acción</a></th>
                    </tr>


                    <%  for (Codigo codigo : codigoList) { %>
                    <tr>
                        
                        <%      if (codigo.getIdvisibilidad().getIdvisibilidad().equals(1)) {%>
                        <td>
                            <img src="../assets/t.gif"
                                 class="i_p<%=codigo.getIdvisibilidad().getIdvisibilidad()%>" 
                                 title="Texto Público, todos pueden ver el texto." alt="" border="0" />
                            <a href="../codigo.jsp?id=<%= codigo.getUrl()%>"><%= codigo.getTitulo()%></a>
                        </td>
                        <td><%= new SimpleDateFormat("dd-MM-yyyy").format(codigo.getFechacreacion())%></td>
                        <td><%= codigo.getTimeLeft() %></td> 
                        <td><%= codigo.getCantidadviews()%></td>   
                        <td><%= codigo.getIdsintaxis().getNombre()%></td>
                        <td align="right">
                            <a href="../codigo.jsp?id=<%= codigo.getUrl() %>"><img src="../assets/t.gif" class="i_ed" alt="Editar Texto" border="0" /></a> 
                            <a href="../eliminar?id=<%= codigo.getUrl() %>"><img src="../assets/t.gif" class="i_dl" alt="Eliminar Texto" border="0" /></a>
                        </td>                        
                            <%  } else { 
                                    if(isUser) { %>
                        <td>
                            <img src="../assets/t.gif"
                                 class="i_p<%=codigo.getIdvisibilidad().getIdvisibilidad() %>"        
                            <%  if (codigo.getIdvisibilidad().getIdvisibilidad().equals(2)) { %>
                                 title="Texto Sin Listar, pueden ver el texto a quienes le doy el URL."
                            <%  } else { %>
                                 title="Texto Privado, solo el usuario puede ver el texto."
                            <%  }%>
                                 alt="" border="0" />
                            <a href="../codigo.jsp?id=<%= codigo.getUrl()%>"><%= codigo.getTitulo()%></a>
                        </td>
                        <td><%= new SimpleDateFormat("dd-MM-yyyy").format(codigo.getFechacreacion())%></td>
                        <td><%= codigo.getTimeLeft() %></td> 
                        <td><%= codigo.getCantidadviews()%></td>   
                        <td><%= codigo.getIdsintaxis().getNombre()%></td>
                        <td align="right">
                            <a href="../codigo.jsp?id=<%= codigo.getUrl() %>"><img src="../assets/t.gif" class="i_ed" alt="Editar Texto" border="0" /></a> 
                            <a href="../eliminar?id=<%= codigo.getUrl() %>"><img src="../assets/t.gif" class="i_dl" alt="Eliminar Texto" border="0" /></a>
                        </td>   
                            <%      }  
                                } %>
                    </tr>
                    <%  } %>
                </table>
                <%  if (isUser) {%>
                <div class="content_text"> 
                    <br/><br/>
                    <b>Your stats:</b><br/>
                    Cantidad de Textos activos: <%= codigoList.size()%> <br/>
                    Cantidad de Textos públicos: <%=countPublic%><br />
                    Cantidad de Textos sin listar: <%=countList%><br />
                    Cantidad de Textos privados: <%=countPrivate%><br />
                    Cantidad de entradas al profile de <%= usuario.getUsuario() %> : <%= usuario.getProfileview() %><br />
                    Cantidad total de vistas de todos los textos activos: <%=countView%><br/><br/>
                </div>
                <%  }%>
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
                                <li>
                                    <a href="codigo.jsp?id=<%= codigo.getUrl() %>" ><%= codigo.getTitulo() %></a>
                                    <span><%= codigo.getTimeCreatedElapsed() %></span>
                                </li>
                                <%  } else { %>
                                <li class="xtra_<%=codigo.getIdvisibilidad().getIdvisibilidad()%>" >
                                    <a href="codigo.jsp?id=<%= codigo.getUrl() %>" ><%= codigo.getTitulo() %></a>
                                    <span><%= codigo.getTimeCreatedElapsed() %></span>
                                <li>
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
