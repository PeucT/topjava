package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by PeucT on 11.11.2017.
 */
public class DaoImplMemory implements Dao {
    private List<Meal> meals;
    private AtomicInteger generator = new AtomicInteger(1);

    public DaoImplMemory(){
        meals = new CopyOnWriteArrayList<>();
    }

    @Override
    public void addMeal(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(generator.getAndIncrement());
        }
        meals.add(meal);
    }

    @Override
    public void deleteMeal(Integer id) {
        for (Meal entry:meals) {
            if ( entry.getId().equals(id) ) {
                meals.remove(entry);
                break;
            }
        }
    }

    @Override
    public void updateMeal(Meal meal) {
        for (Meal entry:meals) {
            if ( entry.getId().equals(meal.getId()) ) {
                meals.remove(entry);
                break;
            }
        }
        meals.add(meal);

    }

    @Override
    public List<Meal> getAllMeals() {
        if (meals == null || meals.size() == 0) {
            initialize();
        }
        Collections.sort(meals, new Comparator<Meal>() {
            @Override
            public int compare(Meal o1, Meal o2) {
                return  o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
        return meals;
    }

    @Override
    public Meal getMealById(Integer id) {
        for (Meal entry:meals) {
            if ( entry.getId().equals(id) ) {
                return entry;
            }
        }
        return null;
    }

    private void initialize(){
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510));
    }
}
