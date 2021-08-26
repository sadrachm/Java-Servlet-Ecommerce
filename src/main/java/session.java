/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class session extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, InterruptedException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();
            String x = request.getParameter("ID");
            if (session.getAttribute(x) != null){
                String w = session.getAttribute(x).toString();
                session.setAttribute(x, w+1);
            }
            else {
                String y = new String("1");
                session.setAttribute(x, y);
            }
            
        String title = "The Best Company";
        String docType =
        "<!doctype html public \"-//w3c//dtd html 4.0 " +
         "transitional//en\">\n";
        out.println(docType + "<html>\n" +
         "<head>  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>"
                 + title + "</title><link rel=\"stylesheet\" href=\"styles.css\"></head>\n");
         
        out.println("<body> <div class=\"nav\">" +"<a href=\"goHome\" class=\"nav-left\">Home</a>"
                +   "<a href=\"goCart\" class=\"nav-right\">Cart</a>\n" 
            +   "</div><hr>"
            +   "<div class = \"header\"><h1>" + title + "</h1> </div> <hr>");
        out.println("<h1 style = \"text-align: center;\">Your item has been added to cart</h1>");
           out.println("<form action=\"goHome\" method=\"GET\" enctype=\"text/plain\" style=\"font-size: 1.5em;\">");
           out.println("<button type= \"submit\">Home</button>");
           out.println("</form>");
        out.println("</body>");
        out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
            Logger.getLogger(session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
        try {
            processRequest(request, response);
        } catch (InterruptedException ex) {
            Logger.getLogger(session.class.getName()).log(Level.SEVERE, null, ex);
        }
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
