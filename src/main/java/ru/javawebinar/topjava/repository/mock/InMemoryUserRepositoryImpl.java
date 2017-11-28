package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        repository.remove(id);
        return true;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> allUsers =  new ArrayList<User>(repository.values());
        Collections.sort(allUsers, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (o1.getName().equals(o2.getName())) {
                    return o1.getId().compareTo(o2.getId());
                } else {
                    return o1.getName().compareTo(o2.getName());
                }
            }
        });
        return allUsers;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream().
                filter(entry -> entry.getEmail().equals(email)).
                findAny().
                get();
    }
}
