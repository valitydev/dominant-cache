package dev.vality.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import dev.vality.damsel.domain.CategoryObject;
import dev.vality.damsel.domain.CategoryType;
import dev.vality.damsel.domain.DomainObject;
import dev.vality.damsel.domain.Reference;
import dev.vality.damsel.domain_config.Snapshot;
import dev.vality.damsel.dominant.cache.Category;
import dev.vality.dominant.cache.mapper.utils.DomainObjectMapExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoriesMapper {

    public static List<Category> mapCategories(Cache<String, Snapshot> cache) {
        Map<Reference, DomainObject> domainObjectMap = DomainObjectMapExtractor.getDomainObjectMap(cache);
        List<Category> categoryList = new ArrayList<>();
        for (Map.Entry<Reference, DomainObject> entry : domainObjectMap.entrySet()) {
            if (entry.getKey().isSetCategory()) {
                Category category = new Category();
                CategoryObject categoryObject = entry.getValue().getCategory();
                category.setRef(String.valueOf(categoryObject.getRef().getId()));
                category.setName(categoryObject.getData().getName());
                category.setDescription(categoryObject.getData().getDescription());
                if (CategoryType.live.equals(categoryObject.getData().getType())) {
                    category.setType(dev.vality.damsel.dominant.cache.CategoryType.live);
                } else if (CategoryType.test.equals(categoryObject.getData().getType())) {
                    category.setType(dev.vality.damsel.dominant.cache.CategoryType.test);
                }
                categoryList.add(category);
            }
        }
        return categoryList;
    }
}
