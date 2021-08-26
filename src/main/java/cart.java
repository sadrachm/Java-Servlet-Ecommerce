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


@WebServlet(urlPatterns = {"/cart"})
public class cart extends HttpServlet {

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
      final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
      //final String DB_URL="jdbc:mysql://localhost:3306/testdb";
      final String DB_URL="jdbc:mysql://localhost:3306/products?useSSL=false";
      HttpSession session = request.getSession();
      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "The Best Company in the World";
      String docType =
        "<!doctype html public \"-//w3c//dtd html 4.0 " +
         "transitional//en\">\n";
         out.println(docType +
         "<html>\n" +
         "<head>  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>" +
                 title + "</title><link rel=\"stylesheet\" href=\"styles.css\">"
                         + "<script type = \"text/JavaScript\" src = \"tax.js\"></script>"
                         + "<script type = \"text/JavaScript\" src = \"fillPhone.js\"></script>"
                         + "</head>\n");
         
        out.println("<body> <div class=\"nav\">" +"<a href=\"goHome\" class=\"nav-left\">Home</a>"
                +   "</div><hr>"
                +   "<div class = \"header\"><h1>" + title + "</h1> </div>");
                 
         
         Connection conn = null;
         Statement stmt = null;
      try{    
         // Register JDBC driver
         Class.forName(JDBC_DRIVER);
         // Open a connection
         conn = DriverManager.getConnection(DB_URL, DBCredentials.USER, DBCredentials.PASS);
         // Execute SQL query
         stmt = conn.createStatement();
         String sql;
//         sql = "INSERT INTO users(firstName, lastName, email) VALUES('hey', 'hola', 'bye')";
//         PreparedStatement pst = conn.prepareStatement(sql);
//         pst.executeUpdate();
         sql = "SELECT name, price FROM guitars";
         ResultSet rs = stmt.executeQuery(sql);
         // Extract data from result set    
         String x;
         String[] y = new String[12];
         int[] quantity = new int[12];
        Integer count = 0;
        int arrayCount = 0;
        for (int a = 2; a < 14; a++) {
            x = count.toString();
            //out.println("<h1>" + a + ": " + session.getAttribute(x) + "</h1>");
            if (session.getAttribute(x) != null) {
                y[arrayCount] = x;
                quantity[arrayCount] = session.getAttribute(x).toString().length();
                arrayCount++;
            }
            
            count++;
        }
        count = 0;
        double total = 0;
        out.println("<hr>");
        for (int a = 0; a < arrayCount; a++) {
            sql = "SELECT name, price FROM guitars WHERE idguitars = "+(Integer.parseInt(y[a])+1);
            rs = stmt.executeQuery(sql);
            while(rs.next()){
               //Retrieve by column name
               String name = rs.getString("name");
               String price = rs.getString("price");
               out.println("<h2 style = \"text-align:center;\">"+quantity[a]+" x " +name+"</h2>");
               out.println("<h2 style = \"text-align:center;\">"+price+"</h2>");
               total += quantity[a] *(double) Math.round(Double.parseDouble(price) * 100.0) / 100.0;
         }
        }
               out.println("<hr style = \"width:30%\" >");
        out.println("<h2>Tax Rate: <span id=\"tax\">9%</h1>");
        out.println("<h2>Total Before Taxes: <span id= \"ogTotal\">"+total+"</span></h2>");
        out.println("<h2 style = \"text-align:center;\">Total: $<span id=\"total\">"+total*1.09+"</span></h2>");
        
        out.println("  <fieldset>\n" +
"    <legend>Personal & Billing Information</legend>\n" +
"    <form action=\"sendtoDatabase\" method=\"get\" enctype=\"text/plain\" style=\"font-size: 1.5em;\">\n" +
    "      <div class=\"container\">\n" +
"        <div class=\"first\">\n" +
"          Personal: <br>\n" +
"          First Name: <input type=\"text\" id = \"fname\" name=\"First_Name\" value = \"Place\" pattern=\"^[a-zA-Z]+$\" required> <br>\n" +
"          Last Name: <input type=\"text\" id = \"lname\" name=\"Last_Name\" value = \"Holder\" pattern=\"^[a-zA-Z]+$\" required> <br>\n" +
"          Phone: <input type=\"tel\" name=\"Phone_Number\" value = \"1111111111\" onblur = \"getPhone(this.value)\" width=\"\" pattern=\"^[0-9]{3}(-)*[0-9]{3}(-)*[0-9]{4}$\" required> <br> <br>\n" +
"        ZIP: <input type=\"text\" id = \"zip\" name=\"zip\" value = \"90022\" onblur = \"getTax(this.value)\" pattern=\"^[0-9]{5}$\" required><br></div>\n" +
"        <div class=\"second\">\n" +
"          Shipping: <br>\n" +
"          State: <input type=\"text\" id=\"state\" value = \"\" ><br>"+
"          County: <input type=\"text\" id=\"county\" value = \"\" ><br>"
                + "Shipping Address: <input type=\"text\" id=\"shipA\" name=\"Shipping_Address\" value = \"1 Placeholder St.\" pattern=\"[0-9]+ [a-zA-Z0-9 .-]+\" required> <br>\n" +
"          Shipping Method: <br>\n" +
"          <input type=\"radio\" id=\"overnight\" name=\"Shipping_Method\" value=\"Overnight\" checked required>\n" +
"          <label for=\"overnight\">Overnight ($10)</label><br>\n" +
"          <input type=\"radio\" id=\"2-days\" name=\"Shipping_Method\" value=\"2-days expedited\">\n" +
"          <label for=\"2-days\">2-days expedited ($5)</label><br>\n" +
"          <input type=\"radio\" id=\"6-days\" name=\"Shipping_Method\" value=\"6-days ground\">\n" +
"          <label for=\"6-days\">6-days ground ($0)</label> <br> <br>\n" +
"        </div>\n" +
"        <div class=\"third\">\n" +
"\n" +
"          Credit Card Information: <br>\n" +
"          Card Number: <input type=\"text\" id = \"card\" name=\"Card_Number\" value = \"111111111111\" pattern=\"[0-9]{12,}\" required> <br>\n" +
"          Expiration Date: <input type=\"text\" id = \"expD\" name=\"Card_Expiration\" value = \"1/1\" pattern=\"[0-9]+/[0-9]+\" required> <br>\n" +
"          Security Code: <input type=\"text\" id = \"secC\" name=\"Card_Security_Code\" value = \"111\" pattern=\"[0-9]{3,}\" required> <br>\n" +
"          Billing Address: <input type=\"text\" id = \"billA\" name=\"Billing_Address\" value = \"111 PlaceHolder\" pattern=\"[0-9]+ [a-zA-Z0-9 .]+\" required> <br>\n" +
"        </div>\n" +
"\n" +
"      </div>\n" +
"<input type = \"hidden\" name = \"ID\" value =\"" );
for (int a = 0; a<arrayCount; a++) {
    out.println(y[a]);
}

out.println("\">");
out.println("<input type = \"hidden\" name = \"quantity\" value =\"" );
for (int a = 0; a< arrayCount; a++) {
    out.println(quantity[a]);
}
out.println("\">");
out.println("<hr>\n" +
"      <center>\n" +
"        <input type=\"reset\" value=\"Reset\">\n" +
"        <input type=\"submit\" value=\"Send\">\n" +
"      </center>\n" +
"    </form>\n" +
"  </fieldset>");
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

    //"      <div class=\"container\">\n" +
//"        <div class=\"first\">\n" +
//"          Personal: <br>\n" +
//"          First Name: <input type=\"text\" name=\"First_Name\" pattern=\"^[a-zA-Z]+$\" required> <br>\n" +
//"          Last Name: <input type=\"text\" name=\"Last_Name\" pattern=\"^[a-zA-Z]+$\" required> <br>\n" +
//"          Phone: <input type=\"tel\" name=\"Phone_Number\" width=\"\" pattern=\"^[0-9]{3}(-)*[0-9]{3}(-)*[0-9]{4}$\" required> <br> <br>\n" +
//"        </div>\n" +
//"        <div class=\"second\">\n" +
//"          Shipping: <br>\n" +
//"          Shipping Address: <input type=\"text\" name=\"Shipping_Address\" pattern=\"[0-9]+ [a-zA-Z0-9 .-]+\" required> <br>\n" +
//"          Shipping Method: <br>\n" +
//"          <input type=\"radio\" id=\"overnight\" name=\"Shipping_Method\" value=\"Overnight\" required>\n" +
//"          <label for=\"overnight\">Overnight ($10)</label><br>\n" +
//"          <input type=\"radio\" id=\"2-days\" name=\"Shipping_Method\" value=\"2-days expedited\">\n" +
//"          <label for=\"2-days\">2-days expedited ($5)</label><br>\n" +
//"          <input type=\"radio\" id=\"6-days\" name=\"Shipping_Method\" value=\"6-days ground\">\n" +
//"          <label for=\"6-days\">6-days ground ($0)</label> <br> <br>\n" +
//"        </div>\n" +
//"        <div class=\"third\">\n" +
//"\n" +
//"          Credit Card Information: <br>\n" +
//"          Card Number: <input type=\"text\" name=\"Card_Number\" pattern=\"[0-9]{12,}\" required> <br>\n" +
//"          Expiration Date: <input type=\"text\" name=\"Card_Expiration\" pattern=\"[0-9]+/[0-9]+\" required> <br>\n" +
//"          Security Code: <input type=\"text\" name=\"Card_Security_Code\" pattern=\"[0-9]{3,}\" required> <br>\n" +
//"          Billing Address: <input type=\"text\" name=\"Billing_Address\" pattern=\"[0-9]+ [a-zA-Z0-9 .]+\" required> <br>\n" +
//"        </div>\n" +
//"\n" +
    
    
    
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


