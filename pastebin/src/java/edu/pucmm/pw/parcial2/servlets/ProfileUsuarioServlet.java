/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pucmm.pw.parcial2.servlets;

import edu.pucmm.pw.parcial2.ejb.JpaServiceEJB;
import edu.pucmm.pw.parcial2.entities.Usuario;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;

/**dd
 *
 * @author fmesa
 */
@WebServlet(name = "ProfileUsuarioServlet", urlPatterns = {"/ProfileUsuarioServlet", "/profile"})
@MultipartConfig
public class ProfileUsuarioServlet extends HttpServlet {

    @EJB
    JpaServiceEJB JPAService;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String> errors = new HashMap<>();

        String nombre = getcontentPartText(request.getPart("nombre"));
        String apellidos = getcontentPartText(request.getPart("apellidos"));
        String email = getcontentPartText(request.getPart("email"));
        String password = getcontentPartText(request.getPart("password"));
        String password2 = getcontentPartText(request.getPart("password2"));

        Usuario user = (Usuario) request.getSession().getAttribute("usuario");
        
        try {
            
            user.setNombre(nombre);
            user.setApellidos(apellidos);
            
            String relativaPathImage = null;
            if(request.getPart("imagen").getSize() > 0) {
                try (InputStream is = request.getPart("imagen").getInputStream()) {
                    int i = is.available();
                    byte[] b  = new byte[i];
                    is.read(b);
                    String directory = getServletContext().getRealPath("/");
                    String fileMIME = getFileName(request.getPart("imagen"));
                    fileMIME = fileMIME.substring(fileMIME.indexOf("."), fileMIME.length());
                    relativaPathImage = "u/image/" + user.getUsuario() + fileMIME;
                    directory = directory.substring(0, directory.length()-10) + "web/"+ relativaPathImage;
                    FileOutputStream os = new FileOutputStream(directory);
                    os.write(b);
                }
            }

            if (!email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+"
                    + "/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
                    + "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")) {
                errors.put("email", "No es un email válido");
            } else {
                user.setEmail(email);
            }

            if (!password.equals("")){
                if(!password.equals(password2)) {
                    errors.put("password", "Las contraseñas no coinciden");
                    password = user.getContrasena();
                } else {
                    user.setContrasena(password);
                }         
            } 
            
            String imagenURL = null;
            if(relativaPathImage != null) {
                imagenURL = "http://localhost:8080/parcial2/"+relativaPathImage;
                user.setImagenurl(imagenURL);
            }
          
            
            JPAService.updateUsuario(user);
            user = (Usuario) JPAService.findObject(Usuario.class, user.getIdusuario());
            
            request.getSession().setAttribute("usuario", user);
            
            response.sendRedirect(request.getContextPath() + "/u/user.jsp?user=" + user.getUsuario());
            
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    private String getcontentPartText(Part input) {
        Scanner sc = null;
        String content = null;
        try {
            sc = new Scanner(input.getInputStream(), "UTF-8");
            if (sc.hasNext()) {
                content = sc.nextLine();
            } else {
                content = "";
            }
            return content;
        } catch (IOException ex) {
            Logger.getLogger(ProfileUsuarioServlet.class.getName()).log(Level.SEVERE, ex.getMessage());
            content = null;
        } finally {
            sc.close();
        }
        return content;
    }

    private byte[] getImageBytes(Part input) {
        InputStream is;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            is = input.getInputStream();
            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
        } catch (IOException ex) {
            Logger.getLogger(ProfileUsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return buffer.toByteArray();
    }
    
    private String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
              return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
