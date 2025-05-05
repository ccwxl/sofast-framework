package cc.sofast.practice.module.redis;

import cc.sofast.framework.starter.common.dto.PageResult;
import cc.sofast.framework.starter.common.dto.Result;
import cc.sofast.framework.starter.common.utils.json.JsonUtils;
import cc.sofast.practice.module.trans.res.TransResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RSearch;
import org.redisson.api.RTimeSeries;
import org.redisson.api.RedissonClient;
import org.redisson.api.options.PlainOptions;
import org.redisson.api.search.index.FieldIndex;
import org.redisson.api.search.index.IndexInfo;
import org.redisson.api.search.index.IndexOptions;
import org.redisson.api.search.index.IndexType;
import org.redisson.api.search.query.Document;
import org.redisson.api.search.query.QueryOptions;
import org.redisson.api.search.query.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wxl
 */
@Slf4j
@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
@Tag(name = "redis", description = "redis")
public class RedisSearchTest {
    public final RedisTemplate<String, Object> redisTemplateObject;
    public final RedissonClient redissonClient;

    @PostMapping("/load-data")
    @Operation(summary = "加载数据")
    public Result<String> loadData() {
        String key = "dev:120111";
        Map<String, Object> deviceData = new HashMap<>();
        deviceData.put("deviceId", "120111");
        deviceData.put("time", System.currentTimeMillis());
        deviceData.put("temperature", 36.5);
        deviceData.put("humidity", 66);
        deviceData.put("location", "济南");
        redisTemplateObject.opsForHash().putAll(key, deviceData);

        RMap<Object, Object> map = redissonClient.getMap(key);
        Object location = map.get("location");
        log.info("location: {}", location);
        map.put("location", "北京");

        Object bjLocation = redisTemplateObject.opsForHash().get(key, "location");
        log.info("location: {}", bjLocation);

        RSearch search = redissonClient.getSearch();
        String devIdx = "dev_idx";
        search.createIndex(devIdx, IndexOptions.defaults()
                        .on(IndexType.HASH)
                        .prefix(List.of("dev:")),
                FieldIndex.text("location"),
                FieldIndex.numeric("humidity"),
                FieldIndex.numeric("time")
        );

        List<String> indexes = search.getIndexes();
        for (String idx : indexes) {
            IndexInfo info = search.info(idx);
            System.out.println(JsonUtils.toJson(info));
        }
        return Result.ok();
    }

    @GetMapping("/search")
    @Operation(summary = "搜索")
    public PageResult<Document> search() {
        RSearch search = redissonClient.getSearch();
        List<String> indexes = search.getIndexes();
        for (String idx : indexes) {
            IndexInfo info = search.info(idx);
            System.out.println(JsonUtils.toJson(info));
        }

        SearchResult result = search.search(
                "dev_idx",
                "@location:(\"北京\")",
                QueryOptions.defaults()
                        .limit(0, 100)
        );
        long total = result.getTotal();
        log.info("total: {}", total);
        List<Document> documents = result.getDocuments();
        return PageResult.list(documents);
    }
}
