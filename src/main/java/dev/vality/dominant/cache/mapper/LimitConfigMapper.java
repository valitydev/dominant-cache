package dev.vality.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import dev.vality.damsel.domain.DomainObject;
import dev.vality.damsel.domain.LimitConfigObject;
import dev.vality.damsel.domain.Reference;
import dev.vality.damsel.domain_config.Snapshot;
import dev.vality.dominant.cache.mapper.utils.DomainObjectMapExtractor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class LimitConfigMapper {

    public static List<dev.vality.damsel.dominant.cache.LimitConfigObject> mapLimits(Cache<String, Snapshot> cache) {
        Map<Reference, DomainObject> domainObjectMap = DomainObjectMapExtractor.getDomainObjectMap(cache);
        List<dev.vality.damsel.dominant.cache.LimitConfigObject> limitConfigs = new ArrayList<>();
        for (Map.Entry<Reference, DomainObject> entry : domainObjectMap.entrySet()) {
            if (entry.getKey().isSetLimitConfig()) {
                log.debug("Limit config found, key={}, value={}", entry.getKey(), entry.getValue());
                LimitConfigObject limitConfigObject = entry.getValue().getLimitConfig();
                dev.vality.damsel.dominant.cache.LimitConfigObject cacheLimitConfig
                        = new dev.vality.damsel.dominant.cache.LimitConfigObject(
                                limitConfigObject.ref.id, limitConfigObject.getData());
                limitConfigs.add(cacheLimitConfig);
            }
        }
        return limitConfigs;
    }
}
