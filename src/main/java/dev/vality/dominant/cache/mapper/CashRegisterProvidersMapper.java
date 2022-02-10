package dev.vality.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import dev.vality.damsel.domain.CashRegisterProviderObject;
import dev.vality.damsel.domain.DomainObject;
import dev.vality.damsel.domain.ProviderParameter;
import dev.vality.damsel.domain.ProviderParameterType;
import dev.vality.damsel.domain.Proxy;
import dev.vality.damsel.domain.Reference;
import dev.vality.damsel.domain_config.Snapshot;
import dev.vality.damsel.dominant.cache.CashRegisterProvider;
import dev.vality.damsel.dominant.cache.CashRegisterProviderParameter;
import dev.vality.damsel.dominant.cache.CashRegisterProviderParameterType;
import dev.vality.damsel.dominant.cache.CashRegisterProviderProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dev.vality.dominant.cache.mapper.utils.DomainObjectMapExtractor.getDomainObjectMap;

public class CashRegisterProvidersMapper {

    public static List<CashRegisterProvider> mapCashRegisterProviders(Cache<String, Snapshot> cache) {
        Map<Reference, DomainObject> domainObjectMap = getDomainObjectMap(cache);
        List<CashRegisterProvider> cashRegisterProviderList = new ArrayList<>();
        for (Map.Entry<Reference, DomainObject> entry : domainObjectMap.entrySet()) {
            if (entry.getKey().isSetCashRegisterProvider()) {
                CashRegisterProvider cashRegisterProvider = new CashRegisterProvider();
                CashRegisterProviderObject cashRegisterProviderObject = entry.getValue().getCashRegisterProvider();
                cashRegisterProvider.setRef(String.valueOf(cashRegisterProviderObject.getRef().getId()));
                cashRegisterProvider.setName(cashRegisterProviderObject.getData().getName());
                cashRegisterProvider.setDescription(cashRegisterProviderObject.getData().getDescription());
                cashRegisterProvider.setProxy(getCashRegisterProviderProxy(cashRegisterProviderObject));
                cashRegisterProvider.setParameter(getCashRegisterProviderParameters(cashRegisterProviderObject));
                cashRegisterProviderList.add(cashRegisterProvider);
            }
        }
        return cashRegisterProviderList;
    }

    private static CashRegisterProviderProxy getCashRegisterProviderProxy(
            CashRegisterProviderObject cashRegisterProviderObject) {
        CashRegisterProviderProxy cashRegisterProviderProxy = new CashRegisterProviderProxy();
        Proxy proxy = cashRegisterProviderObject.getData().getProxy();
        cashRegisterProviderProxy.setRef(String.valueOf(proxy.getRef().getId()));
        cashRegisterProviderProxy.setOptions(proxy.getAdditional());
        return cashRegisterProviderProxy;
    }

    private static List<CashRegisterProviderParameter> getCashRegisterProviderParameters(
            CashRegisterProviderObject cashRegisterProviderObject) {
        CashRegisterProviderParameter cashRegisterProviderParameter = new CashRegisterProviderParameter();
        List<ProviderParameter> providerParameterList = cashRegisterProviderObject.getData().getParamsSchema();
        List<CashRegisterProviderParameter> cashRegisterProviderParameterList = new ArrayList<>();
        for (int i = 0; i < providerParameterList.size(); i++) {
            ProviderParameter providerParameter = providerParameterList.get(i);
            cashRegisterProviderParameter.setId(providerParameter.getId());
            cashRegisterProviderParameter.setDescription(providerParameter.getDescription());
            cashRegisterProviderParameter.setIsRequired(providerParameter.isIsRequired());
            cashRegisterProviderParameter.setType(getCashRegisterProviderParameterType(providerParameter));
            cashRegisterProviderParameterList.add(i, cashRegisterProviderParameter);
        }
        return cashRegisterProviderParameterList;
    }

    private static CashRegisterProviderParameterType getCashRegisterProviderParameterType(
            ProviderParameter providerParameter) {
        ProviderParameterType providerParameterType = providerParameter.getType();
        if (providerParameterType.isSetStringType()) {
            return CashRegisterProviderParameterType.string_type;
        } else if (providerParameterType.isSetIntegerType()) {
            return CashRegisterProviderParameterType.integer_type;
        } else if (providerParameterType.isSetPasswordType()) {
            return CashRegisterProviderParameterType.password_type;
        } else {
            return CashRegisterProviderParameterType.url_type;
        }
    }
}
