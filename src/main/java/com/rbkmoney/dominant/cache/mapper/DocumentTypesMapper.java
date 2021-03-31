package com.rbkmoney.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import com.rbkmoney.damsel.domain.DocumentTypeObject;
import com.rbkmoney.damsel.domain.DomainObject;
import com.rbkmoney.damsel.domain.Reference;
import com.rbkmoney.damsel.domain_config.Snapshot;
import com.rbkmoney.damsel.dominant.cache.DocumentType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.rbkmoney.dominant.cache.mapper.utils.DomainObjectMapExtractor.getDomainObjectMap;

@Slf4j
public class DocumentTypesMapper {

    public static List<DocumentType> mapDocumentTypes(Cache<String, Snapshot> cache) {
        Map<Reference, DomainObject> domainObjectMap = getDomainObjectMap(cache);
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
