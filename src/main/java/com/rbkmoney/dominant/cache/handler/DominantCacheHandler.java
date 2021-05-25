package com.rbkmoney.dominant.cache.handler;

import com.github.benmanes.caffeine.cache.Cache;
import com.rbkmoney.damsel.domain_config.Snapshot;
import com.rbkmoney.damsel.dominant.cache.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.rbkmoney.dominant.cache.mapper.CashRegisterProvidersMapper.mapCashRegisterProviders;
import static com.rbkmoney.dominant.cache.mapper.CategoriesMapper.mapCategories;
import static com.rbkmoney.dominant.cache.mapper.ContractTemplatesMapper.mapContractTemplates;
import static com.rbkmoney.dominant.cache.mapper.CountriesMapper.mapCountries;
import static com.rbkmoney.dominant.cache.mapper.DocumentTypesMapper.mapDocumentTypes;
import static com.rbkmoney.dominant.cache.mapper.TradeBlocsMapper.mapTradeBlocs;

@Component
@Slf4j
@RequiredArgsConstructor
public class DominantCacheHandler implements DominantCacheSrv.Iface {

    private final Cache<String, Snapshot> cache;

    @Override
    public List<Category> getCategories() {
        return mapCategories(cache);
    }

    @Override
    public List<DocumentType> getDocumentTypes() {
        return mapDocumentTypes(cache);
    }

    @Override
    public List<CashRegisterProvider> getCashRegisterProviders() {
        return mapCashRegisterProviders(cache);
    }

    @Override
    public List<ContractTemplate> getContractTemplates() {
        return mapContractTemplates(cache);
    }

    @Override
    public List<TradeBloc> getTradeBlocs() {
        return mapTradeBlocs(cache);
    }

    @Override
    public List<Country> getCountries() {
        return mapCountries(cache);
    }
}
