package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by PeucT on 11.11.2017.
 */
public interface Dao {
    void addMeal(Meal meal);
    void deleteMeal(Integer id);
    void updateMeal(Meal meal);
    List<Meal> getAllMeals();
    Meal getMealById(Integer id);
}
