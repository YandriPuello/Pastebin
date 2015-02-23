<%-- 
    Document   : index
    Created on : Jun 28, 2014, 7:12:28 PM
    Author     : fmesa
--%>

<%@page import="edu.pucmm.pw.parcial2.entities.Alerta"%>
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
        <title>Alertas</title>
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
    
    <% List<Alerta> alertas = (List<Alerta>)application.getAttribute("alertaList");
       Alerta alerta = null;
       for(Alerta a : alertas){
           if(a.getIdusuario().getIdusuario() == usuario.getIdusuario()){
               alerta = a;
               break;
           }
       }
    %>
    
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
                        <img src="/i/t.gif" class="i_n" width="16" height="16" alt="" border="0" /> <a href="./">crear un nuevo texto</a> 
                        &nbsp;&nbsp;&nbsp; 
                        <img src="/i/t.gif" class="i_t" width="16" height="16" alt="" border="0" /> <a href="./trends">textos trending</a>
                    </div>
                    <ul class="top_menu">
                        <% if (usuario != null && usuario.getIdusuario() != null) {%>
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
                    Mis Alertas
                </div>
                <div class="domain_frame">
                    Se le permite añadir hasta 2 palabras de alertas. Cuando alguien cree un documento <b>público</b> que contenga su palabra, se le enviará una notificación via email.
                </div>
                </br>
                <form name="formAlerta" action="./Alertas" method="POST">
                    
                    <div class="form_frame_left">
                        <div class="form_frame">
                            <div class="form_left">
                                Email:
                            </div>
                            <div class="form_right">
                                <label><%=usuario.getEmail() %></label>
                            </div>
                        </div>
                        <div class="form_frame">
                            <div class="form_left">
                                Keyword 1:
                            </div>
                            <div class="form_right">
                                <input type="text" name="keyword1" size="20" maxlength="60" value="<%=alerta.getPrimerkeyword()%>" class="post_input" /> 
                            </div>
                        </div>
                        				
                        <div class="form_frame">
                            <div class="form_left">
                                Keyword 2:
                            </div>
                            <div class="form_right">
                                <input type="text" name="keyword2" size="20" maxlength="60" value="<%=alerta.getSegundokeyword() %>" class="post_input" /> 
                            </div>
                        </div>
                        <div class="form_frame">
                            <div class="form_left">
                                &nbsp;
                            </div>
                            <div class="form_right">
                                <input name="submitAlerta" type="submit" value="Actualizar" id="submit" accesskey="s" class="button1 btnbold" />
                            </div>
                        </div>
                    </div>
                </form>
                
                
                
                
                
                
                
                
                
                
                

        </div>
            <div class="right">
                <div id="content_right">
                    <div class="content_right_menu">
                        <div class="content_right_title"><a href="/archive">Textos Públicos</a></div>	
                        <div id="menu_2">
                            <ul class="right_menu"><li><a href="/xVAV41DC">Untitled</a><span>10 sec ago</span></li>
                                <li><a href="/BjNCM0KG">Untitled</a><span>C# | 14 sec ago</span></li>
                                <li><a href="/eYYrsUCB">Wiselista</a><span>42 sec ago</span></li>
                                <li><a href="/AEBtLzBK">Untitled</a><span>18 sec ago</span></li>
                                <li><a href="/MRDqFpVD">Untitled</a><span>30 sec ago</span></li>
                                <li><a href="/tEq6sgD4">Untitled</a><span>HTML | 31 sec ago</span></li>
                                <li><a href="/NPD1EjTN">Untitled</a><span>35 sec ago</span></li>
                                <li><a href="/dccsWGCr">genau</a><span>Lua | 44 sec ago</span></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

