package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
        repository.forEach((k,v) -> v.setUserId(1));
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(AuthorizedUser.getId());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        if (repository.get(id).getUserId() == AuthorizedUser.getId()) {
            return null != repository.remove(id);
        }else {
            return false;
        }
    }

    @Override
    public Meal get(int id) {
        if (repository.get(id).getUserId() == AuthorizedUser.getId()) {
            return repository.get(id);
        }else
        {
            return null;
        }
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> meal = repository.values().stream().
                filter(entry -> entry.getUserId() == AuthorizedUser.getId()).
                collect(Collectors.toList());
        Collections.sort(meal, new Comparator<Meal>() {
            @Override
            public int compare(Meal o1, Meal o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });

        return meal;
    }

    @Override
    public List<MealWithExceed> filter(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        List<MealWithExceed> meal = MealsUtil.getWithExceeded(getAll(), AuthorizedUser.getCaloriesPerDay());
        return meal.stream()
                .filter(entry -> DateTimeUtil.isBetween(entry.getDateTime().toLocalDate(), startDate, endDate))
                .filter(entry -> DateTimeUtil.isBetween(entry.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}

