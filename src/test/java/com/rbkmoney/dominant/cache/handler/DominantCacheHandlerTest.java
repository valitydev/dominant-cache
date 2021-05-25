package com.rbkmoney.dominant.cache.handler;

import com.github.benmanes.caffeine.cache.Cache;
import com.rbkmoney.damsel.domain_config.Snapshot;
import com.rbkmoney.damsel.dominant.cache.*;
import com.rbkmoney.dominant.cache.DominantCacheApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static com.rbkmoney.dominant.cache.constant.CashNameConstant.CACHE_NAME;
import static com.rbkmoney.dominant.cache.utils.DomainObjectTestUtils.createTestDomainObject;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DominantCacheApplication.class)
public class DominantCacheHandlerTest {

    @Autowired
    Cache<String, Snapshot> cache;

    @Autowired
    DominantCacheHandler dominantCacheHandler;

    @BeforeEach
    void setup() {
        Snapshot snapshot = new Snapshot();
        snapshot.setDomain(createTestDomainObject());
        cache.put(CACHE_NAME, snapshot);
    }

    @Test
    void getCategoryTest() {
        assertNotNull(cache.getIfPresent(CACHE_NAME));
        List<Category> categoryList = dominantCacheHandler.getCategories();
        Collections.sort(categoryList);
        assertEquals(3, categoryList.size());
        assertEquals("CategoryName 2", categoryList.get(2).getName());
        assertEquals("CategoryDescription 0", categoryList.get(0).getDescription());
        assertEquals("1", categoryList.get(1).getRef());
        assertEquals(com.rbkmoney.damsel.dominant.cache.CategoryType.live, categoryList.get(2).getType());
        assertEquals(com.rbkmoney.damsel.dominant.cache.CategoryType.test, categoryList.get(1).getType());
    }

    @Test
    void getDocumentTypeTest() {
        assertNotNull(cache.getIfPresent(CACHE_NAME));
        List<DocumentType> documentTypeList = dominantCacheHandler.getDocumentTypes();
        Collections.sort(documentTypeList);
        assertEquals(3, documentTypeList.size());
        assertEquals("DocumentName 2", documentTypeList.get(2).getName());
        assertEquals("DocumentDescription 1", documentTypeList.get(1).getDescription());
        assertEquals("0", documentTypeList.get(0).getRef());
    }

    @Test
    void getCashRegisterProviderTest() {
        assertNotNull(cache.getIfPresent(CACHE_NAME));
        List<CashRegisterProvider> cashRegisterProviderList = dominantCacheHandler.getCashRegisterProviders();
        Collections.sort(cashRegisterProviderList);
        assertEquals(3, cashRegisterProviderList.size());
        assertEquals("CashRegisterProviderName 1", cashRegisterProviderList.get(1).getName());
        assertEquals("CashRegisterProviderDescription 0", cashRegisterProviderList.get(0).getDescription());
        assertEquals("2", cashRegisterProviderList.get(2).getRef());
        assertEquals("0", cashRegisterProviderList.get(0).getProxy().getRef());
        assertEquals("proxyOptionValue", cashRegisterProviderList.get(0).getProxy().getOptions().get("proxyOptionKey"));
    }

    @Test
    void getContractTemplatesTest() {
        assertNotNull(cache.getIfPresent(CACHE_NAME));
        List<ContractTemplate> contractTemplateList = dominantCacheHandler.getContractTemplates();
        Collections.sort(contractTemplateList);
        assertEquals(3, contractTemplateList.size());
        assertEquals("ContractTemplate 2", contractTemplateList.get(2).getName());
        assertEquals("ContractTemplateDescription 1", contractTemplateList.get(1).getDescription());
        assertEquals("0", contractTemplateList.get(0).getRef());
    }

    @Test
    void getCountriesTest() {
        assertNotNull(cache.getIfPresent(CACHE_NAME));
        List<Country> countryList = dominantCacheHandler.getCountries();
        Collections.sort(countryList);
        assertEquals(3, countryList.size());
        assertEquals("CountryName 2", countryList.get(2).getName());
        assertEquals(3, countryList.get(1).getTradeBlocs().size());
        assertTrue(countryList.get(1).getTradeBlocs().contains("TradeBloc 2"));
        assertEquals("AUS", countryList.get(1).getRef());
    }

    @Test
    void getTradeBlockTest() {
        assertNotNull(cache.getIfPresent(CACHE_NAME));
        List<TradeBloc> tradeBlocList = dominantCacheHandler.getTradeBlocs();
        Collections.sort(tradeBlocList);
        assertEquals(3, tradeBlocList.size());
        assertEquals("TradeBlocName 0", tradeBlocList.get(0).getName());
        assertEquals("1", tradeBlocList.get(1).getRef());
        assertEquals("TradeBlocDescription 2", tradeBlocList.get(2).getDescription());
    }
}
