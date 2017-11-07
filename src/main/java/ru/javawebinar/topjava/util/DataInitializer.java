package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredWithExceeded;

/**
 * Created by PeucT on 07.11.2017.
 */
public class DataInitializer {
    private static final int caloriesPerDay = 2000;

    private static List<Meal> mealList = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
    );

    private static List<MealWithExceed> mealWithExceedList =
            getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), caloriesPerDay);

    public static List<MealWithExceed> getMealWithExceedList (){
        return mealWithExceedList;
    }

    public static List<Meal> getMealList (){
        return mealList;
    }

    public static int getCaloriesPerDay(){
        return caloriesPerDay;
    }
}
