/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.filters;

import edu.pucmm.pw.parcial2.ejb.JpaServiceEJB;
import edu.pucmm.pw.parcial2.entities.Codigo;
import edu.pucmm.pw.parcial2.entities.Usuario;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Yandri
 */
public class CodigoFilter implements Filter {

    @EJB
    JpaServiceEJB jpaService;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest requestServlet = (HttpServletRequest) request;
        HttpServletResponse responseSerlvet = (HttpServletResponse) response;
        String url = requestServlet.getParameter("id");
        
        if(url == null){
            responseSerlvet.sendRedirect("./");
        }else{
            List<Codigo> codigos = ((List<Codigo>)jpaService.findObject("Codigo.findByUrl", CodigoFilter.class, "url", url));
            if(codigos.isEmpty()){
                responseSerlvet.sendRedirect("./");
            }else{
                Usuario user = (Usuario)requestServlet.getSession().getAttribute("usuario");
                Codigo code = codigos.get(0);
                
                if(code.getIdvisibilidad().getNombre().equals("Público") || code.getIdvisibilidad().getNombre().equals("Sin Listar") || (code.getIdvisibilidad().getNombre().equals("Privado") && user.getIdusuario() == code.getIdusuario().getIdusuario())){
                    jpaService.updateCodigo(code);
                    validate(code);
                    requestServlet.getSession().setAttribute("codigo", code);
                    chain.doFilter(request, response);
                }else{
                    responseSerlvet.sendRedirect("./");
                }
            }
        }
    }
    
    public void validate(Codigo codigo){
        String code = codigo.getTexto();
        String newCode = "";
        for(int i = 0; i < code.length(); i++){
            if(code.charAt(i) == '<'){
                newCode += "&lt";
            }else if (code.charAt(i) == '>'){
                newCode += "&gt";
            }else{
                newCode += code.charAt(i);
            }
        }
        codigo.setTexto(newCode);
    }

    @Override
    public void destroy() {
    }
    
}
