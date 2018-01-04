package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import static org.slf4j.LoggerFactory.getLogger;

@ActiveProfiles(Profiles.JPA)
public class MealServiceJpaTest extends MealBaseTest {

}