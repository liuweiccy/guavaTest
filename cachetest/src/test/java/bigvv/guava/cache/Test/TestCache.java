package bigvv.guava.cache.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.Weigher;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TestCache {

    private Cache<String, String> cache;
    private Cache<String, String> cache1;

    @Before
    public void init() {
        cache = CacheBuilder.newBuilder()
                .initialCapacity(10)
                .concurrencyLevel(2)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();

        cache1 = CacheBuilder.newBuilder()
                .weigher(new Weigher<Object, Object>() { // 定义权重
                    public int weigh(Object key, Object value) {
                        return 0;
                    }
                })
                .maximumWeight(1000)
                .build();
    }

    @Test
    public void putOrGet() throws InterruptedException {
        cache.put("name", "VV");
        System.out.println(cache.getIfPresent("name"));
        Thread.sleep(4*1000);
        System.out.println(cache.getIfPresent("name"));
    }

    @Test
    public void putOrGetThread() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName());
                        cache.put("name-" + finalI + "-" + j, UUID.randomUUID().toString());
                    }
                }
            }, "线程-" + i).start();
        }
        Thread.sleep(6000);
        System.out.println(cache.size());
        System.out.println(cache.stats());
    }

    @Test
    public void testExpireAfterWrite() throws InterruptedException {
        cache.put("name", "VV");
        for (int i = 0; i < 13; i++) {
            System.out.println(cache.getIfPresent("name") + "-----" + i);
            Thread.sleep(1000);
        }
    }
}
