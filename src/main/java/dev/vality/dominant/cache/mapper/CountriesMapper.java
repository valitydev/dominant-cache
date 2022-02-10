package dev.vality.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import dev.vality.damsel.domain.*;
import dev.vality.damsel.domain_config.Snapshot;
import dev.vality.damsel.dominant.cache.Country;
import dev.vality.dominant.cache.mapper.utils.DomainObjectMapExtractor;

import java.util.*;

public class CountriesMapper {

    public static List<Country> mapCountries(Cache<String, Snapshot> cache) {
        Map<Reference, DomainObject> domainObjectMap = DomainObjectMapExtractor.getDomainObjectMap(cache);
        List<Country> countryList = new ArrayList<>();
        for (Map.Entry<Reference, DomainObject> entry : domainObjectMap.entrySet()) {
            if (entry.getKey().isSetCountry()) {
                Country country = new Country();
                CountryObject countryObject = entry.getValue().getCountry();
                country.setRef(String.valueOf(CountryCode.findByValue(countryObject.getRef().getId().getValue())));
                country.setName(countryObject.getData().getName());
                Set<String> tradeBlocRefs = new HashSet<>();
                countryObject.getData().getTradeBlocs()
                        .forEach(tradeBlocRef -> tradeBlocRefs.add(tradeBlocRef.getId()));
                country.setTradeBlocs(tradeBlocRefs);
                countryList.add(country);
            }
        }
        return countryList;
    }
}
