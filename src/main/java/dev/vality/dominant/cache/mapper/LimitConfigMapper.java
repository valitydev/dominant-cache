package dev.vality.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import dev.vality.damsel.domain.DomainObject;
import dev.vality.damsel.domain.LimitConfigObject;
import dev.vality.damsel.domain.Reference;
import dev.vality.damsel.domain_config.Snapshot;
import dev.vality.damsel.limiter.config.LimitScopeEmptyDetails;
import dev.vality.damsel.limiter.config.LimitScopeType;
import dev.vality.dominant.cache.mapper.utils.DomainObjectMapExtractor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class LimitConfigMapper {

    public static List<dev.vality.damsel.dominant.cache.LimitConfigObject> mapLimits(Cache<String, Snapshot> cache) {
        Map<Reference, DomainObject> domainObjectMap = DomainObjectMapExtractor.getDomainObjectMap(cache);
        List<dev.vality.damsel.dominant.cache.LimitConfigObject> limitConfigs = new ArrayList<>();
        for (Map.Entry<Reference, DomainObject> entry : domainObjectMap.entrySet()) {
            if (entry.getKey().isSetLimitConfig()) {
                log.debug("Limit config found, key={}, value={}", entry.getKey(), entry.getValue());
                LimitConfigObject limitConfigObject = entry.getValue().getLimitConfig();
                dev.vality.damsel.limiter.config.LimitConfig data = limitConfigObject.getData();
                if (data != null && data.isSetScopes() && data.getScopes() != null) {
                    Set<LimitScopeType> validScopes = new HashSet<>();
                    for (LimitScopeType scope : data.getScopes()) {
                        if (scope.getSetField() != null) {
                            validScopes.add(scope);
                        } else {
                            if (scope.isSetParty()) {
                                validScopes.add(LimitScopeType.party(new LimitScopeEmptyDetails()));
                            } else if (scope.isSetShop()) {
                                validScopes.add(LimitScopeType.shop(new LimitScopeEmptyDetails()));
                            } else if (scope.isSetWallet()) {
                                validScopes.add(LimitScopeType.wallet(new LimitScopeEmptyDetails()));
                            } else if (scope.isSetIdentity()) {
                                validScopes.add(LimitScopeType.identity(new LimitScopeEmptyDetails()));
                            } else if (scope.isSetPaymentTool()) {
                                validScopes.add(LimitScopeType.payment_tool(new LimitScopeEmptyDetails()));
                            } else if (scope.isSetProvider()) {
                                validScopes.add(LimitScopeType.provider(new LimitScopeEmptyDetails()));
                            } else if (scope.isSetTerminal()) {
                                validScopes.add(LimitScopeType.terminal(new LimitScopeEmptyDetails()));
                            } else if (scope.isSetPayerContactEmail()) {
                                validScopes.add(LimitScopeType.payer_contact_email(new LimitScopeEmptyDetails()));
                            } else if (scope.isSetSender()) {
                                validScopes.add(LimitScopeType.sender(new LimitScopeEmptyDetails()));
                            } else if (scope.isSetReceiver()) {
                                validScopes.add(LimitScopeType.receiver(new LimitScopeEmptyDetails()));
                            } else {
                                log.warn("Wrong LimitScopeType. Scope must be set: {}", scope);
                            }
                        }
                    }
                    data.setScopes(validScopes);
                }

                dev.vality.damsel.dominant.cache.LimitConfigObject cacheLimitConfig =
                        new dev.vality.damsel.dominant.cache.LimitConfigObject(limitConfigObject.ref.id, data);
                limitConfigs.add(cacheLimitConfig);
            }
        }
        return limitConfigs;
    }
}
