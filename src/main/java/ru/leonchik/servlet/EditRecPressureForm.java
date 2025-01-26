package ru.leonchik.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.leonchik.dao.PressureDao;
import ru.leonchik.dao.PressureDaoImpl;
import ru.leonchik.entiti.Pressure;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Project: Pressure
 * Package: ru.leonchik.servlet
 * <p>
 * User: alexey
 * Date: вс 26 янв. 2025
 */

@WebServlet(urlPatterns = {"/editrecpressure"})
public class EditRecPressureForm  extends HttpServlet {

    PressureDao prssureDao = new PressureDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        int id = Integer.parseInt(req.getParameter("id"));

        Pressure pressureRec = prssureDao.single(id);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");

        out.println("<form method=\"GET\" action=\"editpressure\">");
            out.printf("<input type=\"hidden\" name=\"id\" value=\"%d\" />", pressureRec.getId());
            out.printf("SYS: <input type=\"text\" name=\"sys\" value=\"%d\" size=\"3\" />", pressureRec.getSys());
            out.printf("DIA: <input type=\"text\" name=\"dia\" value=\"%d\" size=\"3\" />", pressureRec.getDia());
            out.printf("Pulse: <input type=\"text\" name=\"pulse\" value=\"%d\" size=\"3\" /><br>", pressureRec.getPulse());
            out.printf("Дата: <input type=\"datetime-local\" name=\"dtm\" value=\"%s\">", pressureRec.getDtm());
            out.println("<p><input type=\"submit\" value=\"OK\">");
            out.println("<input type=\"reset\" value=\"Очистить\"></p>");
        out.println("</form>");

        out.println("<p><a href=\"./view\">Вернуться</a></p>");

        out.println("</body>");
        out.println("</html>");
    }
}
