package com.rbkmoney.dominant.cache.mapper.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.rbkmoney.damsel.domain.DomainObject;
import com.rbkmoney.damsel.domain.Reference;
import com.rbkmoney.damsel.domain_config.Snapshot;
import com.rbkmoney.dominant.cache.exception.DominantCacheException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.rbkmoney.dominant.cache.constant.CashNameConstant.CACHE_NAME;

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
