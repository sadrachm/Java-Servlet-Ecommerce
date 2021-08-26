/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
 
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;


@WebServlet(urlPatterns = {"/orderDetails"})
public class orderDetails extends HttpServlet {

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
      // JDBC driver name and database URL
      //final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
      HttpSession session = request.getSession();
      final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
      //final String DB_URL="jdbc:mysql://localhost:3306/testdb";
      final String DB_URL="jdbc:mysql://localhost:3306/products?useSSL=false";

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
        String title = "The Best Company";
        String docType =
        "<!doctype html public \"-//w3c//dtd html 4.0 " +
         "transitional//en\">\n";
        out.println(docType + "<html>\n" +
         "<head>  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>"
                 + title + "</title><link rel=\"stylesheet\" href=\"styles.css\"></head>\n");
         
        out.println("<body style = \"text-align: center;\"> <div class=\"nav\">" +"<a href=\"goHome\" class=\"nav-left\">Home</a>"
                +   "<a href=\"goCart\" class=\"nav-right\">Cart</a>\n" 
            +   "</div><hr>"
            +   "<div class = \"header\"><h1>" + title + "</h1> </div> <hr>");
         Connection conn = null;
         Statement stmt = null;
      try{    
          List<String> names = Collections.list(session.getAttributeNames());
          for (String x : names) {
              session.removeAttribute(x);
          }
         // Register JDBC driver
         Class.forName(JDBC_DRIVER);
         // Open a connection
         conn = DriverManager.getConnection(DB_URL, DBCredentials.USER, DBCredentials.PASS);
         // Execute SQL query
         stmt = conn.createStatement();
         String sql;
         sql = "SELECT name FROM guitars";
         ResultSet rs = stmt.executeQuery(sql);
         // Extract data from result set
         String guitars = request.getParameter("ID");
         String quantities = request.getParameter("quantity");
         String[] guit = guitars.split("\\s+");
         String[] quan = quantities.split("\\s+");
         out.println("<h2>Your Order was successful</h2>");
         out.println("<h2>Thank You!</h2>");
         int count = 0;
         String name;
         double price;
         Double total = 0.0;
         double subtotal;
         for (String a : guit) {
             subtotal = 0;
             if (a.isEmpty()) {count++;}
             else {
                Integer id = Integer.parseInt(a)+1;
                sql = "SELECT name, price FROM guitars where idguitars = " + id.toString();
                rs = stmt.executeQuery(sql);
                rs.next();
                name = rs.getString("name");
                price = Double.parseDouble(rs.getString("price"));
                subtotal += Integer.parseInt(quan[count]) * price;
                out.println("<h3>" + quan[count] + " x " + name + " = $"+ subtotal + "</h3>");
                count++;
                total += subtotal;
             }
         }
        String method = request.getParameter("Shipping_Method");
          switch (method) {
              case "Overnight":
                  out.println("<h3> Shipping Method: Overnight = $10</h3>");
                  total += 10;
                  break;
              case "2-days expedited":
                  out.println("<h3> Shipping Method: 2-days expedited = $5</h3>");
                  total += 5;
                  break;
              default:
                  out.println("<h3> Shipping Method: 6-days expedited = $0</h3>");
                  break;
          }
        out.println("<h2>Total: $" + total + "</h2>");
         out.println("</body></html>");
         // Clean-up environment
         rs.close();
         stmt.close();
         conn.close();
      }catch(SQLException se){
         //Handle errors for JDBC
         out.println(se);
      }catch(Exception e){
         //Handle errors for Class.forName
         out.println(e);
      }finally{
         //finally block used to close resources
         try{
            if(stmt!=null)
               stmt.close();
         }catch(SQLException se2){
         }// nothing we can do
         try{
            if(conn!=null)
            conn.close();
         }catch(SQLException se){
            se.printStackTrace();
         }//end finally try
      } //end try
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


