package ru.javawebinar.topjava.service;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.ModelResultMatchers;
import ru.javawebinar.topjava.model.Meal;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by PeucT on 08.12.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    @Test
    public void get() throws Exception {
        Meal meal = mealService.get(MEAL_ID, USER_ID);
        assertEquals(MEAL1.toString(), meal.toString());

    }

    @Test
    public void getWrong() throws Exception {
        Meal meal = mealService.get(MEAL_ID, USER_ID);
        assertEquals(MEAL2.toString(), meal.toString());

    }

    @Test
    public void delete() throws Exception {
        mealService.delete(MEAL_ID, USER_ID);
    }

    @Test
    public void update() throws Exception {
        Meal meal = mealService.update(MEAL1, USER_ID);
        assertEquals(MEAL1.toString(), meal.toString());
    }

    @Test
    public void updateStranger() throws Exception {
        mealService.update(MEAL1, USER_ID + 1);
    }

}