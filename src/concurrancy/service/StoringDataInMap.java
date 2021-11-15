package concurrancy.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StoringDataInMap {
}

class SharedMapWithUserContext implements Runnable {
    public static Map<Integer, Context> userContextPerUserId = new ConcurrentHashMap<>();
    private Integer userId;

    @Override
    public void run() {
        String userName = "jy";
        userContextPerUserId.put(userId, new Context(userName));
    }
}

class Context {
    String userName;

    public Context(String userName) {
        this.userName = userName;
    }
}
