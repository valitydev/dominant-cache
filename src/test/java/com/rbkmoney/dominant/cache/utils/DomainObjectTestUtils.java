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
        Category category = new Category();
        category.setDescription("CategoryDescription " + i);
        category.setName("CategoryName " + i);
        if (i % 2 == 0) {
            category.setType(CategoryType.live);
        } else {
            category.setType(CategoryType.test);
        }
        CategoryObject categoryObject = new CategoryObject();
        categoryObject.setRef(new CategoryRef(i));
        categoryObject.setData(category);
        DomainObject domainObject = new DomainObject();
        domainObject.setCategory(categoryObject);
        domain.put(reference, domainObject);
    }

    private static void createDocumentType(Map<Reference, DomainObject> domain, Integer i) {
        Reference reference = new Reference();
        reference.setDocumentType(new DocumentTypeRef(i));
        DocumentTypeObject documentTypeObject = new DocumentTypeObject();
        DocumentType documentType = new DocumentType();
        documentType.setName("DocumentName " + i);
        documentType.setDescription("DocumentDescription " + i);
        documentTypeObject.setRef(new DocumentTypeRef(i));
        documentTypeObject.setData(documentType);
        DomainObject domainObject = new DomainObject();
        domainObject.setDocumentType(documentTypeObject);
        domain.put(reference, domainObject);
    }

    private static void createCashRegisterProvider(Map<Reference, DomainObject> domain, Integer i) {
        Reference reference = new Reference();
        reference.setCashRegisterProvider(new CashRegisterProviderRef(i));
        Map<String, String> additional = new HashMap<>();
        additional.put("proxyOptionKey", "proxyOptionValue");
        Proxy proxy = new Proxy();
        proxy.setRef(new ProxyRef(i));
        proxy.setAdditional(additional);
        ProviderParameter providerParameter = new ProviderParameter();
        providerParameter.setId("ProviderParameterId");
        providerParameter.setDescription("ProviderParameterDescription");
        providerParameter.setIsRequired(true);
        providerParameter.setType(ProviderParameterType.integer_type(new ProviderParameterInteger()));
        List<ProviderParameter> providerParameterList = new ArrayList<>();
        providerParameterList.add(providerParameter);
        CashRegisterProvider cashRegisterProvider = new CashRegisterProvider();
        cashRegisterProvider.setDescription("CashRegisterProviderDescription " + i);
        cashRegisterProvider.setName("CashRegisterProviderName " + i);
        cashRegisterProvider.setProxy(proxy);
        cashRegisterProvider.setParamsSchema(providerParameterList);
        CashRegisterProviderObject cashRegisterProviderObject = new CashRegisterProviderObject();
        cashRegisterProviderObject.setRef(new CashRegisterProviderRef(i));
        cashRegisterProviderObject.setData(cashRegisterProvider);
        DomainObject domainObject = new DomainObject();
        domainObject.setCashRegisterProvider(cashRegisterProviderObject);
        domain.put(reference, domainObject);
    }

}
