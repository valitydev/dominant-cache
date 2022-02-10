package dev.vality.dominant.cache.mapper.utils;

import com.github.benmanes.caffeine.cache.Cache;
import dev.vality.damsel.domain.DomainObject;
import dev.vality.damsel.domain.Reference;
import dev.vality.damsel.domain_config.Snapshot;
import dev.vality.dominant.cache.exception.DominantCacheException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static dev.vality.dominant.cache.constant.CashNameConstant.CACHE_NAME;

@Slf4j
public class DomainObjectMapExtractor {

    public static Map<Reference, DomainObject> getDomainObjectMap(Cache<String, Snapshot> cache) {
        Snapshot snapshot = cache.getIfPresent(CACHE_NAME);
        if (snapshot != null) {
            log.debug("Get domain to map categories from snapshot version {}", snapshot.getVersion());
            return snapshot.getDomain();
        } else {
            throw new DominantCacheException("Snapshot not present into cache.");
        }
    }
}
