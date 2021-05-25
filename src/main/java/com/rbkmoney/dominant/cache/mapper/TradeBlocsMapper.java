package com.rbkmoney.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import com.rbkmoney.damsel.domain.*;
import com.rbkmoney.damsel.domain_config.Snapshot;
import com.rbkmoney.damsel.dominant.cache.TradeBloc;

import java.util.*;

import static com.rbkmoney.dominant.cache.mapper.utils.DomainObjectMapExtractor.getDomainObjectMap;

public class TradeBlocsMapper {

    public static List<TradeBloc> mapTradeBlocs(Cache<String, Snapshot> cache) {
        Map<Reference, DomainObject> domainObjectMap = getDomainObjectMap(cache);
        List<TradeBloc> tradeBlocList = new ArrayList<>();
        for (Map.Entry<Reference, DomainObject> entry : domainObjectMap.entrySet()) {
            if (entry.getKey().isSetTradeBloc()) {
                TradeBloc tradeBloc = new TradeBloc();
                TradeBlocObject tradeBlocObject = entry.getValue().getTradeBloc();
                tradeBloc.setRef(tradeBlocObject.getRef().getId());
                tradeBloc.setName(tradeBlocObject.getData().getName());
                tradeBloc.setDescription(tradeBlocObject.getData().getDescription());
                tradeBlocList.add(tradeBloc);
            }
        }
        return tradeBlocList;
    }
}
