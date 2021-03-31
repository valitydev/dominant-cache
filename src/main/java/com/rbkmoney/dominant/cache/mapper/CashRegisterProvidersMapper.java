package com.rbkmoney.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import com.rbkmoney.damsel.domain.CashRegisterProviderObject;
import com.rbkmoney.damsel.domain.DomainObject;
import com.rbkmoney.damsel.domain.ProviderParameter;
import com.rbkmoney.damsel.domain.ProviderParameterType;
import com.rbkmoney.damsel.domain.Proxy;
import com.rbkmoney.damsel.domain.Reference;
import com.rbkmoney.damsel.domain_config.Snapshot;
import com.rbkmoney.damsel.dominant.cache.CashRegisterProvider;
import com.rbkmoney.damsel.dominant.cache.CashRegisterProviderParameter;
import com.rbkmoney.damsel.dominant.cache.CashRegisterProviderParameterType;
import com.rbkmoney.damsel.dominant.cache.CashRegisterProviderProxy;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.rbkmoney.dominant.cache.mapper.utils.DomainObjectMapExtractor.getDomainObjectMap;

@Slf4j
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
