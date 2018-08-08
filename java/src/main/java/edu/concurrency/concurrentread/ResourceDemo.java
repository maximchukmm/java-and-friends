package edu.concurrency.concurrentread;

import java.util.ArrayList;
import java.util.List;

class ResourceDemo {
    private static final List<String> resource;

    static {
        resource = new ArrayList<>();
        resource.add("Resource One");
        resource.add("Resource Two");
        resource.add("Resource Three");
        resource.add("Resource Four");
        resource.add("Resource Five");
    }

    static String getResource(int index) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (index >= resource.size()) return null;
        return resource.get(index);
    }

    static int getResourceSize() {
        return resource.size();
    }
}
