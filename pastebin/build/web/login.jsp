<%-- 
    Document   : login
    Created on : Jul 2, 2014, 11:30:49 PM
    Author     : fmesa
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! Map<String, String> errors; %>
<%
        errors = (Map<String, String>) request.getSession().getAttribute("errors");
        if(errors == null){
            errors = new HashMap<String, String>();
            errors.put("username", "");
            errors.put("password", "");
        }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear Usuario</title>
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <link rel="stylesheet" type="text/css" href="css/style2.css">
        <script type="text/javascript" src="js/jquery-2.1.1.js"></script>
    </head>
    <body id="body">
        <div class="layout">
            <div id="logo" onclick="location.href = 'index.jsp'" title="Crear nuevo texto"></div>
            <div id="header">
                <div id="header_top">
                    <span class="span_left more">PASTEBIN</span>
                    <ul class="top_menu">
                        <li>

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
                        <li class="no_border_li"><a href="signup.jsp">registrarse</a></li>
                        <li><a href="login.jsp">logearse</a></li>				
                    </ul>		
                </div>			
            </div>
        </div>
        <div id="content" class="layout">
            <div class="left">
                <div class="layout_clear"></div>
                <div class="content_title">
                    Crear Usuario
                </div>
                <form id="formLoginUser" name="formCreateUser" method="POST" action="./LoginUsuario">
                    <div class="form_frame_left">
			<div class="form_frame">
                            <div class="form_left">
                                Nombre de Usuario:
                            </div>
                            <div class="form_right">
                                <input type="text" name="username" id="username"/>
                            </div>
			</div>
                        <div class="form_info">
                            <font color="red"><%= errors.get("username") %></font>
                        </div>
                        <div class="form_frame">
                            <div class="form_left">
                                Contraseña:
                            </div>
                            <div class="form_right">
                                <input type="password" name="password" id="password"/>
                            </div>
			</div>
                        <div class="form_info">
                            <font color="red"><%= errors.get("password") %></font>
                        </div>
			<div class="form_frame">
                            <div class="form_left">
                                &nbsp;
                            </div>
                            <div class="form_right">
                                <input name="submit" type="submit" value="Logearse" id="submit" accesskey="l" class="button1 btnbold" />
                            </div>
			</div>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
