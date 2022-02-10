package dev.vality.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import dev.vality.damsel.domain.*;
import dev.vality.damsel.domain_config.Snapshot;
import dev.vality.damsel.dominant.cache.TradeBloc;
import dev.vality.dominant.cache.mapper.utils.DomainObjectMapExtractor;

import java.util.*;

public class TradeBlocsMapper {

    public static List<TradeBloc> mapTradeBlocs(Cache<String, Snapshot> cache) {
        Map<Reference, DomainObject> domainObjectMap = DomainObjectMapExtractor.getDomainObjectMap(cache);
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
