package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.DaoImplMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.DataInitializer;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by PeucT on 06.11.2017.
 */
public class MealServlet extends HttpServlet {
    private static Dao mealDao = new DaoImplMemory();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private static final Logger Log = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Meal> meals = mealDao.getAllMeals();
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, 2000);

        if (request.getParameter("id") != null && !"".equals(request.getParameter("id"))) {
            request.setAttribute("edit", mealDao.getMealById(Integer.valueOf(request.getParameter("id"))));
        }
        request.setAttribute("meals", mealWithExceeds);
        Log.debug("forward to meals");
        request.getRequestDispatcher("meals.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if ("delete".equals(req.getParameter("action"))) {
            mealDao.deleteMeal(Integer.valueOf(req.getParameter("id")));
            resp.sendRedirect("meals");
        }
        if ("add".equals(req.getParameter("action"))) {
            mealDao.addMeal(new Meal(
                    LocalDateTime.parse(req.getParameter("date"), formatter),
                    req.getParameter("description"),
                    Integer.valueOf(req.getParameter("calories"))
            ));
            resp.sendRedirect("meals");
        }
        if ("edit".equals(req.getParameter("action"))) {
            resp.sendRedirect("meals?id=" + req.getParameter("id"));
        }
        if ("editSave".equals(req.getParameter("action"))) {
            mealDao.updateMeal(new Meal(
                    Integer.valueOf(req.getParameter("id")),
                    LocalDateTime.parse(req.getParameter("date"), formatter),
                    req.getParameter("description"),
                    Integer.valueOf(req.getParameter("calories"))
            ));
            resp.sendRedirect("meals");
        }
    }
}
