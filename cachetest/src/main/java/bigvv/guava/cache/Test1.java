package bigvv.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class Test1 {
        final static Cache<String, Integer> cache = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .concurrencyLevel(2)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();


    public static void main(String[] args) throws InterruptedException {
        cache.put("name", 23);
        Thread.sleep(11*1000);
        System.out.println(cache.getIfPresent("name"));

    }
}
