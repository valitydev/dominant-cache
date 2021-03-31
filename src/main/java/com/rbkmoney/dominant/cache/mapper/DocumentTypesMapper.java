package com.rbkmoney.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import com.rbkmoney.damsel.domain.DocumentTypeObject;
import com.rbkmoney.damsel.domain.DomainObject;
import com.rbkmoney.damsel.domain.Reference;
import com.rbkmoney.damsel.domain_config.Snapshot;
import com.rbkmoney.damsel.dominant.cache.DocumentType;
import com.rbkmoney.dominant.cache.exception.DominantCacheException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rbkmoney.dominant.cache.constant.CashNameConstant.CACHE_NAME;

@Slf4j
public class DocumentTypesMapper {

    public static List<DocumentType> mapDocumentTypes(Cache<String, Snapshot> cache) {
        Snapshot snapshot = cache.getIfPresent(CACHE_NAME);
        Map<Reference, DomainObject> domainObjectMap = new HashMap<>();
        if (snapshot != null) {
            log.debug("Get domain to map document types from snapshot version {}", snapshot.getVersion());
            domainObjectMap = snapshot.getDomain();
        } else {
            throw new DominantCacheException("Snapshot not present into cache.");
        }
        List<DocumentType> documentTypeList = new ArrayList<>();
        for (Map.Entry<Reference, DomainObject> entry : domainObjectMap.entrySet()) {
            if (entry.getKey().isSetDocumentType()) {
                DocumentType documentType = new DocumentType();
                DocumentTypeObject documentTypeObject = entry.getValue().getDocumentType();
                documentType.setRef(String.valueOf(documentTypeObject.getRef().getId()));
                documentType.setName(documentTypeObject.getData().getName());
                documentType.setDescription(documentTypeObject.getData().getDescription());
                documentTypeList.add(documentType);
            }
        }
        return documentTypeList;
    }
}
