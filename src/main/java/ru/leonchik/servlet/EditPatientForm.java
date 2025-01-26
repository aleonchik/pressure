package ru.leonchik.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.leonchik.dao.PatientDao;
import ru.leonchik.dao.PatientDaoImpl;
import ru.leonchik.entiti.Patient;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Project: Pressure
 * Package: ru.leonchik.servlet
 * <p>
 * User: alexey
 * Date: вс 26 янв. 2025
 */

@WebServlet(urlPatterns = {"/editpatientform"})
public class EditPatientForm extends HttpServlet {

    PatientDao patDao = new PatientDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        int userId = Integer.parseInt(req.getParameter("usr"));

        Patient pat = patDao.single(userId);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");

        out.println("<form method=\"GET\" action=\"editpatient\">");

            out.printf("<input type=\"hidden\" name=\"usrid\" value=\"%d\">", userId);
            out.printf("Имя: <input type=\"text\" name=\"pname\" required value=\"%s\"/><br>", pat.getName());
            out.println("Дата рожднгия: <br>");
            out.printf("День: <input type=\"text\"  name=\"dd\" size=\"2\" value=\"%d\" required />", pat.getBirth().getDayOfMonth());
            out.printf("Месяц: <input type=\"text\" name=\"mm\" size=\"2\" value=\"%d\" required />", pat.getBirth().getMonthValue());
            out.printf("Год: <input type=\"text\" name=\"yyyy\" size=\"4\" value=\"%d\" required />", pat.getBirth().getYear());
            out.println("<p><input type=\"submit\" value=\"OK\">");
            out.println("<input type=\"reset\" value=\"Очистить\"></p>");

        out.println("</form>");

        out.println("<p><a href=\"./view\">Вернуться</a></p>");

        out.println("</body>");
        out.println("</html>");
    }
}
