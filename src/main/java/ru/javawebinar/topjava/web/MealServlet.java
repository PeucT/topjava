package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.DataInitializer;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by PeucT on 06.11.2017.
 */
public class MealServlet extends HttpServlet {
    private static final Logger Log = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Meal> meals = DataInitializer.getMealList();
        List<MealWithExceed> mealWithExceeds = MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, DataInitializer.getCaloriesPerDay());

        request.setAttribute("meals", mealWithExceeds);
        Log.debug("forward to meals");
        request.getRequestDispatcher("meals.jsp").forward(request,response);
    }
}
