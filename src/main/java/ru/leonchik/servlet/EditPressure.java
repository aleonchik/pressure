package ru.leonchik.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.leonchik.dao.PressureDao;
import ru.leonchik.dao.PressureDaoImpl;
import ru.leonchik.entiti.Pressure;
import ru.leonchik.exception.EntityNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

/**
 * Project: Pressure
 * Package: ru.leonchik.servlet
 * <p>
 * User: alexey
 * Date: вс 26 янв. 2025
 */

@WebServlet(urlPatterns = {"/editpressure"})
public class EditPressure extends HttpServlet {

    PressureDao pressureDao = new PressureDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);

        Pressure p = pressureDao.single(Integer.parseInt(req.getParameter("id")));

        p.setId(Integer.parseInt(req.getParameter("id")));
        p.setSys(Integer.parseInt(req.getParameter("sys")));
        p.setDia(Integer.parseInt(req.getParameter("dia")));
        p.setPulse(Integer.parseInt(req.getParameter("pulse")));
        DateTimeFormatter formatter = ISO_LOCAL_DATE_TIME;
        p.setDtm(LocalDateTime.parse(req.getParameter("dtm"), formatter));

        try {
            pressureDao.update(p);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<body>");

        out.println("Данные обновлены");
        out.println("<a href=\"/pressure/view\">Вернуться</a>");

        out.println("</body>");
        out.println("</html>");
    }
}
