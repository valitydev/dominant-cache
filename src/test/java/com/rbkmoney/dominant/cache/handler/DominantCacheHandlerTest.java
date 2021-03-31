package com.rbkmoney.dominant.cache.handler;

import com.github.benmanes.caffeine.cache.Cache;
import com.rbkmoney.damsel.domain_config.Snapshot;
import com.rbkmoney.damsel.dominant.cache.CashRegisterProvider;
import com.rbkmoney.damsel.dominant.cache.Category;
import com.rbkmoney.damsel.dominant.cache.ContractTemplate;
import com.rbkmoney.damsel.dominant.cache.DocumentType;
import com.rbkmoney.dominant.cache.DominantCacheApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static com.rbkmoney.dominant.cache.constant.CashNameConstant.CACHE_NAME;
import static com.rbkmoney.dominant.cache.utils.DomainObjectTestUtils.createTestDomainObject;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DominantCacheApplication.class)
public class DominantCacheHandlerTest {

    @Autowired
    Cache<String, Snapshot> cache;

    @Autowired
    DominantCacheHandler dominantCacheHandler;

    @Before
    public void setup() {
        Snapshot snapshot = new Snapshot();
        snapshot.setDomain(createTestDomainObject());
        cache.put(CACHE_NAME, snapshot);
    }

    @Test
    public void getCategoryTest() {
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
    public void getDocumentTypeTest() {
        assertNotNull(cache.getIfPresent(CACHE_NAME));
        List<DocumentType> documentTypeList = dominantCacheHandler.getDocumentTypes();
        Collections.sort(documentTypeList);
        assertEquals(3, documentTypeList.size());
        assertEquals("DocumentName 2", documentTypeList.get(2).getName());
        assertEquals("DocumentDescription 1", documentTypeList.get(1).getDescription());
        assertEquals("0", documentTypeList.get(0).getRef());
    }

    @Test
    public void getCashRegisterProviderTest() {
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
    public void getContractTemplatesTest() {
        assertNotNull(cache.getIfPresent(CACHE_NAME));
        List<ContractTemplate> contractTemplateList = dominantCacheHandler.getContractTemplates();
        Collections.sort(contractTemplateList);
        assertEquals(3, contractTemplateList.size());
        assertEquals("ContractTemplate 2", contractTemplateList.get(2).getName());
        assertEquals("ContractTemplateDescription 1", contractTemplateList.get(1).getDescription());
        assertEquals("0", contractTemplateList.get(0).getRef());
    }
}
