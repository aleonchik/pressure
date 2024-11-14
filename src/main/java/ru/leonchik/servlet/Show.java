package ru.leonchik.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.leonchik.dao.PatientDao;
import ru.leonchik.dao.PatientDaoImpl;
import ru.leonchik.dao.PressureDao;
import ru.leonchik.dao.PressureDaoImpl;
import ru.leonchik.entiti.Patient;
import ru.leonchik.entiti.Pressure;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

@WebServlet(urlPatterns = {"/show"})
public class Show extends HttpServlet {

    protected PatientDao patientDao = new PatientDaoImpl();
    protected PressureDao pressureDao = new PressureDaoImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Получим араметры
        int userId = Integer.parseInt(req.getParameter("usr"));
        int period = Integer.parseInt(req.getParameter("period"));
//        String period = req.getParameter("period");

        Patient patient;
        List<Pressure> pressureList;

        // Запомним в куку
        Cookie cookieUserId = new Cookie("userId", Integer.toString(userId));
        cookieUserId.setMaxAge(60 * 60 * 24);
        cookieUserId.setValue(cookieUserId.getValue());
        resp.addCookie(cookieUserId);

        // Получим имя
        patient = patientDao.single(userId);
        // Получим данные давления определенного пациента
        pressureList = pressureDao.all(userId, period);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html>");

        out.println("<head>");
        out.println("<link rel=\"stylesheet\" href=\"styles.css\">");
        out.println("<head>");

        out.println("<body>");

        out.println("<h3>Данные для " + patient.getName() + " за " + period + " </h3>");

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE d MMMM HH:mm u");

        if (!pressureList.isEmpty()) {
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Sys</ht><th>Dia</ht><th>Pulse</ht><th>Дата</ht>");
            out.println("</tr>");
            for (Pressure p : pressureList) {
                LocalDateTime dtm = p.getDtm();

                out.println("<tr>");
                out.println("<td align=\"right\">" + p.getSys() + "</td>");
                out.println("<td align=\"right\">" + p.getDia() + "</td>");
                out.println("<td align=\"right\">" + p.getPulse() + "</td>");
                out.println("<td>" + dtm.format(fmt) + "</td>");
//                out.println("<td>" + p.getDtm() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } else {
            out.println("<h3>Данные отсутствуют</h3>");
        }

        out.println("<p>");
        out.println("<h3>Добавить данные </h3>");

        out.println("<form method=\"post\" action=\"addpressure\">");
        out.println("SYS: <input type=\"text\" name=\"sys\" required size=\"3\">");
        out.println("DIA: <input type=\"text\" name=\"dia\" required size=\"3\">");
        out.println("Pulse: <input type=\"text\" name=\"pulse\" required size=\"3\"><br>");
        out.println("Дата: <input type=\"datetime-local\" name=\"dtm\" required><br>");
        out.println("<input type=\"submit\" value=\"OK\">");
        out.println("<input type=\"hidden\" name=\"userid\" value=\"" +  userId + "\">");
        out.println("</form>");
        out.println("</p>");

        out.println("<a href='./view'>Вернуться</a>");

        out.println("</body>");
        out.println("</html>");
    }
}
