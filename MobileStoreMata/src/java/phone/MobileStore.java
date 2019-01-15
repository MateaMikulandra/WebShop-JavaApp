/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phone;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mata
 */
public class MobileStore extends HttpServlet {

    static Connection conn;

    public static boolean connect() throws SQLException {

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile", "root", "root");
            boolean statusconn = conn.isClosed();
            if (statusconn == false) {
                System.out.println("Konekcija je uspela");
                return true;
            }

        } catch (Exception e) {
            System.out.println("Konekcija nije uspela");
            System.out.println("Razlog je: " + e.getMessage());
            return false;
        }
        return true;
    }

    public void init() throws ServletException {
        try {
            connect();
        } catch (SQLException ex) {

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            if (connect()) {

                String sql = "SELECT * FROM phones WHERE quantity1 > 0";
                Statement upit = conn.createStatement();
                ResultSet rs = upit.executeQuery(sql);
                if (!rs.next()) {
                    out.println("<h3>No phones found!!</h3>");
                } else {
                    
                    out.println("<html style='height: 100%;background-color: FFE1FF;'>");
                    out.println("<div align='center'>");
                    out.println("<form method='get' action='Order'>");
                    out.println("<table border='1' cellpadding='6'>");
                    out.println("<br/>");
                    out.println("<tr style='color: 5000FF; font-size: 20px;'>");
                    out.println("<th>OZNACI</th>");
                    out.println("<th>NAZIV</th>");
                    out.println("<th>NA STANJU</th>");
                    out.println("<th>CENA</th>");
                    out.println("<th>SLIKA</th>");
                    out.println("<th>KOLICINA</th>");
                    out.println("</tr>");
                    out.println("</div>");
                    do {

                        String id = rs.getString("phone_id");
                        out.println("<tr style='color: 5000FF; font-size: 20px;'>");
                        out.println("<td><input type='checkbox' name='id' value='" + id + "' /></td>");
                        out.println("<td>" + rs.getString("name1") + "</td>");
                        out.println("<td>" + rs.getString("quantity1") + " kom</td>");
                        out.println("<td>" + rs.getString("price") + " e</td>");
                        String source = "images/"+rs.getString("name1")+".jpg";
                        String slika = "<td><img src='"+source+"'></td>";
                        out.println(slika);
                        
                        out.println("<td><input type='text' size='3' value='1' name='kol" + id + "' /></td>");
                        out.println("</tr>");
                    } while (rs.next());
                    out.println("</table><br />");
                    out.println("<table>");
                    out.println("<tr style='color: 5000FF; font-size: 15px;'><td>Unesite ime:</td>");
                    out.println("<td><input type='text' name='name' /></td></tr>");
                    out.println("<tr style='color: 5000FF; font-size: 15px;'><td>Unesite prezime:</td>");
                    out.println("<td><input type='text' name='prezime' /></td></tr>");
                    out.println("<tr style='color: 5000FF; font-size: 15px;'><td>Unesite email:</td>");
                    out.println("<td><input type='text' name='email' /></td></tr>");
                    out.println("<tr style='color: 5000FF; font-size: 15px;'><td>Unesite broj telefona:</td>");
                    out.println("<td><input type='text' name='phone' /></td></tr></table><br />");
                    out.println("<input type='submit' value='PORUCI' />");
                    out.println("<input type='reset' value='IZBRISI' /></form>");
                    out.println("</html>");

                }

            }
        } catch (SQLException e) {
            System.out.println("razlog je: " + e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
