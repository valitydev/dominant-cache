package dev.vality.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import dev.vality.damsel.domain.ContractTemplateObject;
import dev.vality.damsel.domain.DomainObject;
import dev.vality.damsel.domain.Reference;
import dev.vality.damsel.domain_config.Snapshot;
import dev.vality.damsel.dominant.cache.ContractTemplate;
import dev.vality.dominant.cache.mapper.utils.DomainObjectMapExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContractTemplatesMapper {

    public static List<ContractTemplate> mapContractTemplates(Cache<String, Snapshot> cache) {
        Map<Reference, DomainObject> domainObjectMap = DomainObjectMapExtractor.getDomainObjectMap(cache);
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
