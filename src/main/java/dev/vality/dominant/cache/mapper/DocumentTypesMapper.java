package dev.vality.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import dev.vality.damsel.domain.DocumentTypeObject;
import dev.vality.damsel.domain.DomainObject;
import dev.vality.damsel.domain.Reference;
import dev.vality.damsel.domain_config.Snapshot;
import dev.vality.damsel.dominant.cache.DocumentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dev.vality.dominant.cache.mapper.utils.DomainObjectMapExtractor.getDomainObjectMap;

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
