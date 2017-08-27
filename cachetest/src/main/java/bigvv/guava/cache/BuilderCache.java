package bigvv.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class BuilderCache {
        final static Cache<String, Integer> cache = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .concurrencyLevel(2)
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .build();
}
