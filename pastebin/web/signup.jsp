<%-- 
    Document   : login
    Created on : Jun 28, 2014, 11:01:05 PM
    Author     : fmesa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <div id="logo" onclick="location.href = 'index.jsp'" title="Create New Paste"></div>
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
                        <form class="search_form" name="search_form" method="get" action="./search" id="search_box">
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
                <form id="formCreateUser" name="formCreateUser" method="POST" action="./RegistrarUsuario">
                    <div class="form_frame_left">
                        <div class="form_frame">
                            <div class="form_left">
                                Nombre de Usuario:
                            </div>
                            <div class="form_right">
                                <input type="text" name="username" id="username"/>
                            </div>
                        </div>
                        <div class="form_frame">
                            <div class="form_left">
                                Email:
                            </div>
                            <div class="form_right">
                                <input type="email" name="email" id="email"/>
                            </div>
                        </div>
                        <div class="form_frame">
                            <div class="form_left">
                                Contraseña:
                            </div>
                            <div class="form_right">
                                <input type="password" name="password" id="password"/>
                            </div>
                        </div>
                        <div class="form_frame">
                            <div class="form_left">
                                Repita la Contraseña:
                            </div>
                            <div class="form_right">
                                <input type="password" name="password2" id="password2"/>
                            </div>
                        </div>
                        <div class="form_frame">
                            <div class="form_left">
                                &nbsp;
                            </div>
                            <div class="form_right">
                                <input name="submit" type="submit" value="Crear" id="submit" accesskey="s" class="button1 btnbold" />
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="right"></div>
        </div>
        <div id="footer"></div>
        <script type="text/javascript">
        $(document).ready(function() {
            $("#formCreateUser").submit(function() {
                var username = $('#username').val();
                var email = $('#email').val();
                var password = $('#password').val();
                var password2 = $('#password2').val();
                
                if(username === ""){
                    alert('Debe tener tener username');
                    return false;
                }
                
                if(!(password === password2)){
                    alert('No coinciden las contraseñas');
                    return false;
                }
                
                if(!email.test("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")){
                    alert('El email no es valido');
                    return false;
                }
            });
        });
        </script>
    </body>
</html>
