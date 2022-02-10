package dev.vality.dominant.cache.service;

import com.github.benmanes.caffeine.cache.Cache;
import dev.vality.damsel.domain_config.Head;
import dev.vality.damsel.domain_config.Reference;
import dev.vality.damsel.domain_config.RepositorySrv;
import dev.vality.damsel.domain_config.Snapshot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static dev.vality.dominant.cache.constant.CashNameConstant.CACHE_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnapshotCacheService {

    private final RepositorySrv.Iface dominantClient;
    private final Cache<String, Snapshot> cache;

    @Scheduled(fixedRateString = "${scheduling.fixed.rate}")
    public void putSnapshotToCache() throws TException {
        Reference reference = Reference.head(new Head());
        log.debug("Trying to get snapshot");
        Snapshot snapshot = dominantClient.checkout(reference);
        log.info("Trying to cache snapshot version: {}", snapshot.getVersion());
        cache.put(CACHE_NAME, snapshot);
    }

}
