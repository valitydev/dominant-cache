package dev.vality.dominant.cache.handler;

import com.github.benmanes.caffeine.cache.Cache;
import dev.vality.damsel.domain_config.Snapshot;
import dev.vality.damsel.dominant.cache.*;
import dev.vality.dominant.cache.mapper.CashRegisterProvidersMapper;
import dev.vality.dominant.cache.mapper.DocumentTypesMapper;
import dev.vality.dominant.cache.mapper.LimitConfigMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import java.util.List;

import static dev.vality.dominant.cache.mapper.CategoriesMapper.mapCategories;
import static dev.vality.dominant.cache.mapper.ContractTemplatesMapper.mapContractTemplates;
import static dev.vality.dominant.cache.mapper.CountriesMapper.mapCountries;
import static dev.vality.dominant.cache.mapper.ProvidersMapper.mapProviders;
import static dev.vality.dominant.cache.mapper.TerminalsMapper.mapTerminals;
import static dev.vality.dominant.cache.mapper.TradeBlocsMapper.mapTradeBlocs;

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
        return DocumentTypesMapper.mapDocumentTypes(cache);
    }

    @Override
    public List<CashRegisterProvider> getCashRegisterProviders() {
        return CashRegisterProvidersMapper.mapCashRegisterProviders(cache);
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

    @Override
    public List<Provider> getProviders() {
        return mapProviders(cache);
    }

    @Override
    public List<Terminal> getTerminals() {
        return mapTerminals(cache);
    }

    @Override
    public List<LimitConfigObject> getLimits() {
        return LimitConfigMapper.mapLimits(cache);
    }
}
