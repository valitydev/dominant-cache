package com.rbkmoney.dominant.cache.utils;

import com.rbkmoney.damsel.domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainObjectTestUtils {

    public static Map<Reference, DomainObject> createTestDomainObject() {
        Map<Reference, DomainObject> domain = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            createCategory(domain, i);
            createDocumentType(domain, i);
            createCashRegisterProvider(domain, i);
        }
        return domain;
    }

    private static void createCategory(Map<Reference, DomainObject> domain, Integer i) {
        Reference reference = new Reference();
        reference.setCategory(new CategoryRef(i));
        DomainObject domainObject = new DomainObject();
        CategoryObject categoryObject = new CategoryObject();
        Category category = new Category();
        category.setDescription("CategoryDescription " + i);
        category.setName("CategoryName " + i);
        if (i % 2 == 0) {
            category.setType(CategoryType.live);
        } else {
            category.setType(CategoryType.test);
        }
        categoryObject.setRef(new CategoryRef(i));
        categoryObject.setData(category);
        domainObject.setCategory(categoryObject);
        domain.put(reference, domainObject);
    }

    private static void createDocumentType(Map<Reference, DomainObject> domain, Integer i) {
        Reference reference = new Reference();
        reference.setDocumentType(new DocumentTypeRef(i));
        DomainObject domainObject = new DomainObject();
        DocumentTypeObject documentTypeObject = new DocumentTypeObject();
        DocumentType documentType = new DocumentType();
        documentType.setName("DocumentName " + i);
        documentType.setDescription("DocumentDescription " + i);
        documentTypeObject.setRef(new DocumentTypeRef(i));
        documentTypeObject.setData(documentType);
        domainObject.setDocumentType(documentTypeObject);
        domain.put(reference, domainObject);
    }

    private static void createCashRegisterProvider(Map<Reference, DomainObject> domain, Integer i) {
        Reference reference = new Reference();
        reference.setCashRegisterProvider(new CashRegisterProviderRef(i));
        DomainObject domainObject = new DomainObject();
        CashRegisterProviderObject cashRegisterProviderObject = new CashRegisterProviderObject();
        CashRegisterProvider cashRegisterProvider = new CashRegisterProvider();
        Map<String, String> additional = new HashMap<>();
        additional.put("proxyOptionKey", "proxyOptionValue");
        Proxy proxy = new Proxy();
        proxy.setRef(new ProxyRef(i));
        proxy.setAdditional(additional);
        List<ProviderParameter> providerParameterList = new ArrayList<>();
        ProviderParameter providerParameter = new ProviderParameter();
        providerParameter.setId("ProviderParameterId");
        providerParameter.setDescription("ProviderParameterDescription");
        providerParameter.setIsRequired(true);
        providerParameter.setType(ProviderParameterType.integer_type(new ProviderParameterInteger()));
        providerParameterList.add(providerParameter);
        cashRegisterProvider.setDescription("CashRegisterProviderDescription " + i);
        cashRegisterProvider.setName("CashRegisterProviderName " + i);
        cashRegisterProvider.setProxy(proxy);
        cashRegisterProvider.setParamsSchema(providerParameterList);
        cashRegisterProviderObject.setRef(new CashRegisterProviderRef(i));
        cashRegisterProviderObject.setData(cashRegisterProvider);
        domainObject.setCashRegisterProvider(cashRegisterProviderObject);
        domain.put(reference, domainObject);
    }

}
