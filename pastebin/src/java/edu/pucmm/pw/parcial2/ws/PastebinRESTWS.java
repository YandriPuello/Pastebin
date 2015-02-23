/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.pucmm.pw.parcial2.ws;

import edu.pucmm.pw.parcial2.ejb.WebServiceEJB;
import edu.pucmm.pw.parcial2.entities.Codigo;
import edu.pucmm.pw.parcial2.entities.Expiracion;
import edu.pucmm.pw.parcial2.entities.Sintaxis;
import edu.pucmm.pw.parcial2.entities.Usuario;
import edu.pucmm.pw.parcial2.entities.Visibilidad;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Yandri
 */
@Path("PastebinRESTWS")
public class PastebinRESTWS {

    @Context
    private UriInfo context;
    
    @EJB
    private WebServiceEJB ws;

    /**
     * Creates a new instance of PastebinRESTWS
     */
    public PastebinRESTWS() {
    }

    /**
     * Retrieves representation of an instance of edu.pucmm.pw.parcial2.ws.PastebinRESTWS
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        return "{\"webservice working\" : \"REST\"}";
    }
   
    @Path("getPastes")
    @GET
    @Produces("application/json")
    public String getPastes(@QueryParam("usuario") @DefaultValue("guest") String usuario,
            @QueryParam("password") @DefaultValue("guest") String password){
        Usuario user = ws.validarUsuario(usuario, password);
        if(user == null)
            return "ERROR: No se pudieron encontrar los elementos. Usuario o contrasena invalidos.";
        List<Codigo> codigos = ws.getUsuarioCodigos(usuario);
        String json = "{\"elementos\" : [";
        for(Codigo c : codigos){
            validate(c);
            json += ("{\"usuario\": \"" + c.getIdusuario().getUsuario() + "\", \"titulo\" : \"" + c.getTitulo() +  "\", \"texto\" : \"" + c.getTexto() + "\", \"sintaxis\" : \"" + c.getIdsintaxis().getNombre() + "\", \"expiracion\" : \"" + c.getIdexpiracion().getDescripcion() + "\", \"tipoVisibilidad\" : \"" + c.getIdvisibilidad().getNombre() + "\", \"url\" : \"" + c.getUrl() + "\", \"cantidadViews\" : \"" + c.getCantidadviews() + "\", \"fechaCreacion\" : \"" + c.getFechacreacion() + "\", \"tamano\" : \"" + c.getTamanoarchivo() + "\", \"ultimaActualizacion\" : \"" + c.getFechaactualizacion() + "\", \"tiempoRestante\" : \"" + c.getTimeLeft() + "\"},");
        }
        json = json.substring(0, json.length()-1);
        json += "]}";

        return json;
    }
   
    @Path("getPublicPastes")
    @GET
    @Produces("application/json")
    public String getPublicPastes(@QueryParam("usuario") String usuario){
        List<Codigo> codigos = ws.getUsuarioPublicCodigos(usuario);
        if(codigos == null)
            return "";
        String json = "{\"elementos\" : [";
        for(Codigo c : codigos){
            validate(c);
            json += ("{\"usuario\": \"" + c.getIdusuario().getUsuario() + "\", \"titulo\" : \"" + c.getTitulo() +  "\", \"texto\" : \"" + c.getTexto() + "\", \"sintaxis\" : \"" + c.getIdsintaxis().getNombre() + "\", \"expiracion\" : \"" + c.getIdexpiracion().getDescripcion() + "\", \"tipoVisibilidad\" : \"" + c.getIdvisibilidad().getNombre() + "\", \"url\" : \"" + c.getUrl() + "\", \"cantidadViews\" : \"" + c.getCantidadviews() + "\", \"fechaCreacion\" : \"" + c.getFechacreacion() + "\", \"tamano\" : \"" + c.getTamanoarchivo() + "\", \"ultimaActualizacion\" : \"" + c.getFechaactualizacion() + "\", \"tiempoRestante\" : \"" + c.getTimeLeft() + "\"},");
        }
        json = json.substring(0, json.length()-1);
        json += "]}";

        return json;
    }
    
    @Path("getPaste")
    @GET
    @Produces("application/json")
    public String getPaste(@QueryParam("url") String url){
         String json = "";
        Codigo code = ws.findCodigo(url);
        if(code != null){
            validate(code);
            json = ("{\"usuario\": \"" + code.getIdusuario().getUsuario() + "\", \"titulo\" : \"" + code.getTitulo() +  "\", \"texto\" : \"" + code.getTexto() + "\", \"sintaxis\" : \"" + code.getIdsintaxis().getNombre() + "\", \"expiracion\" : \"" + code.getIdexpiracion().getDescripcion() + "\", \"tipoVisibilidad\" : \"" + code.getIdvisibilidad().getNombre() + "\", \"url\" : \"" + code.getUrl() + "\", \"cantidadViews\" : \"" + code.getCantidadviews() + "\", \"fechaCreacion\" : \"" + code.getFechacreacion() + "\", \"tamano\" : \"" + code.getTamanoarchivo()/1000 + "\", \"ultimaActualizacion\" : \"" + code.getFechaactualizacion() + "\", \"tiempoRestante\" : \"" + code.getTimeLeft() + "\"}");
        }
        return json;
    }
    
    @Path("validarUsuario")
    @GET
    @Produces("text/plain")
    public String validateUser(@QueryParam("usuario") String usuario, @QueryParam("password") String password){
        Usuario user;
        if(usuario != null && !usuario.equals("")){
            user = ws.validarUsuario(usuario, password);
            if(user == null)
                return "1";
        }
        else
            return "1";
        return "0";
    }
    
    @Path("createPaste")
    @GET
    @Produces("text/plain")
    public String createPaste(@QueryParam("usuario") String usuario, @QueryParam("password") String password, 
            @QueryParam("paste_format") int idSintaxis, @QueryParam("paste_expire_date") int idExpiracion, 
            @QueryParam("paste_visibility") int idVisibilidad, @QueryParam("paste_title") String titulo,
            @QueryParam("code") String code){
        Usuario user;
        if(usuario != null && !usuario.equals(""))
            user = ws.validarUsuario(usuario, password);
        else
            user = ws.findUsuario("guest");
        if(user == null)
            return null;
        String url = ws.createPaste(user, password, idSintaxis, idExpiracion, idVisibilidad, titulo, code);
        
        return url;
    }
    
    public void validate(Codigo codigo){
        String code = codigo.getTexto();
        String newCode = "";
        for(int i = 0; i < code.length(); i++){
            if(code.charAt(i) == '"'){
                newCode += "\\\"";
            }else{
                newCode += code.charAt(i);
            }
        }
        codigo.setTexto(newCode);
    }
}
