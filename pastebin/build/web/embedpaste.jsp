<%-- 
    Document   : embedpaste
    Created on : Jul 7, 2014, 2:02:31 AM
    Author     : Yandri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <link rel="stylesheet" type="text/css" href="css/style2.css">
        <link rel="stylesheet" href="http://yandex.st/highlightjs/8.0/styles/default.min.css">
        <script src="http://yandex.st/highlightjs/8.0/highlight.min.js"></script>
	<script>hljs.initHighlightingOnLoad();</script>
        <script type="text/javascript" src="js/jquery-2.1.1.js"></script>
        <script type="text/javascript" src="js/index.js"></script>
        <title>JSP Page</title>
    </head>
    <jsp:useBean id="codigoEmbed"
                 class="edu.pucmm.pw.parcial2.entities.Codigo" 
                 scope="session" />
    <body>
        <%= codigoEmbed.getTitulo()%>
        <pre style="word-wrap:break-word;" class="textarea_border" ><code class=<%= codigoEmbed.getIdsintaxis().getNombre() %>> <%= codigoEmbed.getTexto()%> </code></pre>
    </body>
</html>
