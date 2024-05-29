package dev.vality.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import dev.vality.damsel.domain.DomainObject;
import dev.vality.damsel.domain.ProviderObject;
import dev.vality.damsel.domain.Reference;
import dev.vality.damsel.domain_config.Snapshot;
import dev.vality.damsel.dominant.cache.Provider;
import dev.vality.dominant.cache.mapper.utils.DomainObjectMapExtractor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProvidersMapper {

    public static List<Provider> mapProviders(Cache<String, Snapshot> cache) {
        Map<Reference, DomainObject> domainObjectMap = DomainObjectMapExtractor.getDomainObjectMap(cache);
        List<Provider> providers = new ArrayList<>();
        for (Map.Entry<Reference, DomainObject> entry : domainObjectMap.entrySet()) {
            if (entry.getKey().isSetProvider()) {
                Provider provider = new Provider();
                ProviderObject providerObject = entry.getValue().getProvider();
                provider.setRef(String.valueOf(providerObject.getRef().getId()));
                provider.setName(providerObject.getData().getName());
                provider.setDescription(providerObject.getData().getDescription());
                providers.add(provider);
            }
        }
        return providers;
    }
}
