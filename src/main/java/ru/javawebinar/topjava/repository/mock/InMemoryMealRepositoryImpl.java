package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

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
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(AuthorizedUser.id());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        if (repository.get(id).getUserId() == AuthorizedUser.id()) {
            repository.remove(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Meal get(int id) {
        if (repository.get(id).getUserId() == AuthorizedUser.id()) {
            return repository.get(id);
        }else
        {
            return null;
        }
    }

    @Override
    public Collection<Meal> getAll() {
        List<Meal> meal = repository.values().stream().
                filter(entry -> entry.getUserId() == AuthorizedUser.id()).
                collect(Collectors.toList());
        Collections.sort(meal, new Comparator<Meal>() {
            @Override
            public int compare(Meal o1, Meal o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });

        return repository.values();
    }
}

