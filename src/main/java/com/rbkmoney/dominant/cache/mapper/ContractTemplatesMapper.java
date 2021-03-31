package com.rbkmoney.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import com.rbkmoney.damsel.domain.ContractTemplateObject;
import com.rbkmoney.damsel.domain.DomainObject;
import com.rbkmoney.damsel.domain.Reference;
import com.rbkmoney.damsel.domain_config.Snapshot;
import com.rbkmoney.damsel.dominant.cache.ContractTemplate;
import com.rbkmoney.dominant.cache.exception.DominantCacheException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rbkmoney.dominant.cache.constant.CashNameConstant.CACHE_NAME;

@Slf4j
public class ContractTemplatesMapper {

    public static List<ContractTemplate> mapContractTemplates(Cache<String, Snapshot> cache) {
        Snapshot snapshot = cache.getIfPresent(CACHE_NAME);
        Map<Reference, DomainObject> domainObjectMap;
        if (snapshot != null) {
            log.debug("Get domain to map contract templates from snapshot version {}", snapshot.getVersion());
            domainObjectMap = snapshot.getDomain();
        } else {
            throw new DominantCacheException("Snapshot not present into cache.");
        }
        List<ContractTemplate> contractTemplateList = new ArrayList<>();
        for (Map.Entry<Reference, DomainObject> entry : domainObjectMap.entrySet()) {
            if (entry.getKey().isSetContractTemplate()) {
                ContractTemplate contractTemplate = new ContractTemplate();
                ContractTemplateObject contractTemplateObject = entry.getValue().getContractTemplate();
                contractTemplate.setRef(String.valueOf(contractTemplateObject.getRef().getId()));
                contractTemplate.setName(contractTemplateObject.getData().getName());
                contractTemplate.setDescription(contractTemplateObject.getData().getDescription());
                contractTemplate.setTermsId(String.valueOf(contractTemplateObject.getData().getTerms().getId()));
                contractTemplateList.add(contractTemplate);
            }
        }
        return contractTemplateList;
    }
}
