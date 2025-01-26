package ru.leonchik.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.leonchik.dao.PatientDao;
import ru.leonchik.dao.PatientDaoImpl;
import ru.leonchik.entiti.Patient;
import ru.leonchik.exception.EntityNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

/**
 * Project: Pressure
 * Package: ru.leonchik.servlet
 * <p>
 * User: alexey
 * Date: сб 25 янв. 2025
 */
@WebServlet(urlPatterns = {"/editpatient"})
public class EditPatient extends HttpServlet {

    PatientDao patDao = new PatientDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Boolean displayForm;
        int userId = Integer.parseInt(req.getParameter("usrid"));
        String usrName = req.getParameter("pname");
        int dd = Integer.parseInt(req.getParameter("dd"));
        int mm = Integer.parseInt(req.getParameter("mm"));
        int yyyy = Integer.parseInt(req.getParameter("yyyy"));

        Patient pat = patDao.single(userId);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");

        out.printf("Новые данные: ");
        out.printf("ID: %d<br>", userId);
        out.printf("Имя: %s<br>", usrName);
        out.printf("Дата: %d<br>", dd);
        out.printf("Месяц: %d<br>", mm);
        out.printf("Год: %d<br>", yyyy);

        pat.setId(userId);
        pat.setName(usrName);
        pat.setBirth(LocalDate.of(yyyy, mm, dd));

        try {
            patDao.update(pat);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        out.println("<a href=\"./view\">Вернуться</a>");

        out.println("</body>");
        out.println("</html>");
    }
}
