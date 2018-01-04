package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by PeucT on 04.01.2018.
 */
@ActiveProfiles(Profiles.JDBC)
public class MealServiceJdbcTest extends MealBaseTest {
}
