package com.rbkmoney.dominant.cache.utils;

import com.rbkmoney.damsel.domain.*;

import java.util.*;

public class DomainObjectTestUtils {

    public static Map<Reference, DomainObject> createTestDomainObject() {
        Map<Reference, DomainObject> domain = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            createCategory(domain, i);
            createDocumentType(domain, i);
            createCashRegisterProvider(domain, i);
            createContractTemplates(domain, i);
            createCountries(domain, i);
            createTradeBlocs(domain, i);
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

    private static void createContractTemplates(Map<Reference, DomainObject> domain, Integer i) {
        Reference reference = new Reference();
        reference.setContractTemplate(new ContractTemplateRef(i));
        ContractTemplate contractTemplate = new ContractTemplate();
        contractTemplate.setName("ContractTemplate " + i);
        contractTemplate.setDescription("ContractTemplateDescription " + i);
        contractTemplate.setTerms(new TermSetHierarchyRef(i));
        ContractTemplateObject contractTemplateObject = new ContractTemplateObject();
        contractTemplateObject.setRef(new ContractTemplateRef(i));
        contractTemplateObject.setData(contractTemplate);
        DomainObject domainObject = new DomainObject();
        domainObject.setContractTemplate(contractTemplateObject);
        domain.put(reference, domainObject);
    }

    private static void createCountries(Map<Reference, DomainObject> domain, Integer i) {
        Reference reference = new Reference();
        reference.setCountry(new CountryRef(CountryCode.findByValue(i)));
        Country country = new Country();
        country.setName("CountryName " + i);
        Set<TradeBlocRef> tradeBlocs = new HashSet<>();
        for (int ti = 0; ti < 3; ti++) {
            tradeBlocs.add(new TradeBlocRef("TradeBloc " + ti));
        }
        country.setTradeBlocs(tradeBlocs);
        CountryObject countryObject = new CountryObject();
        countryObject.setRef(new CountryRef(CountryCode.findByValue(i)));
        countryObject.setData(country);
        DomainObject domainObject = new DomainObject();
        domainObject.setCountry(countryObject);
        domain.put(reference, domainObject);
    }

    private static void createTradeBlocs(Map<Reference, DomainObject> domain, Integer i) {
        Reference reference = new Reference();
        reference.setTradeBloc(new TradeBlocRef(String.valueOf(i)));
        TradeBloc tradeBloc = new TradeBloc();
        tradeBloc.setName("TradeBlocName " + i);
        tradeBloc.setDescription("TradeBlocDescription " + i);
        TradeBlocObject tradeBlocObject = new TradeBlocObject();
        tradeBlocObject.setRef(new TradeBlocRef(String.valueOf(i)));
        tradeBlocObject.setData(tradeBloc);
        DomainObject domainObject = new DomainObject();
        domainObject.setTradeBloc(tradeBlocObject);
        domain.put(reference, domainObject);
    }

}
