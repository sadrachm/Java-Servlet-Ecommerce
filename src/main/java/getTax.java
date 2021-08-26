/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet(urlPatterns = {"/getTax"})
public class getTax extends HttpServlet {
   
    HashMap<String, String[]> map = new HashMap<String, String[]>();
    public void init (ServletConfig config)
    {      Scanner in;
        try {
            in = new Scanner(new File("C:\\Users\\sadra\\Desktop\\CS 137\\HW3\\src\\java\\tax_rates2.csv"));
            in.useDelimiter(",");
    String y = "";
    in.next();
    in.next();
    in.next();
    String state = in.next().split("\\s+")[1];
    String zip = in.next();
    String county = in.next();
    String[] taxState = in.next().split("\\s+");
    String tax = String.valueOf(Math.round(Double.parseDouble(taxState[0])*100 * 100.0) / 100.0);
    String[] arr = {state, county, tax};
    map.put(zip, arr);
    while (in.hasNext()){
        state = taxState[1];
        zip = in.next();
        county = in.next();
        taxState = in.next().split("\\s+");
        tax = String.valueOf( Math.round(Double.parseDouble(taxState[0])*100 * 100.0) / 100.0);
        String[] qwe = {state, county, tax};
        map.put(zip, qwe);
    }
            in.close();

    }catch (FileNotFoundException ex) {
            Logger.getLogger(getTax.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
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
            throws ServletException, IOException {
    
        String zip = request.getParameter("zip"); 
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (map.containsKey(zip))
              out.write(map.get(zip)[0] + ","+ map.get(zip)[1] + ","+ map.get(zip)[2]);
            else 
              out.write(" not, found, lol");
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
        processRequest(request, response);
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
        processRequest(request, response);
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
