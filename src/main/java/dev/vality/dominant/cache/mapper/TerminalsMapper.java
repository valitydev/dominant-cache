package dev.vality.dominant.cache.mapper;

import com.github.benmanes.caffeine.cache.Cache;
import dev.vality.damsel.domain.DomainObject;
import dev.vality.damsel.domain.ProviderRef;
import dev.vality.damsel.domain.Reference;
import dev.vality.damsel.domain.TerminalObject;
import dev.vality.damsel.domain_config.Snapshot;
import dev.vality.damsel.dominant.cache.Terminal;
import dev.vality.dominant.cache.mapper.utils.DomainObjectMapExtractor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TerminalsMapper {

    public static List<Terminal> mapTerminals(Cache<String, Snapshot> cache) {
        Map<Reference, DomainObject> domainObjectMap = DomainObjectMapExtractor.getDomainObjectMap(cache);
        List<Terminal> terminals = new ArrayList<>();
        for (Map.Entry<Reference, DomainObject> entry : domainObjectMap.entrySet()) {
            if (entry.getKey().isSetTerminal() && haveProvider(entry.getValue().getTerminal())) {
                Terminal terminal = new Terminal();
                TerminalObject terminalObject = entry.getValue().getTerminal();
                terminal.setRef(String.valueOf(terminalObject.getRef().getId()));
                terminal.setName(terminalObject.getData().getName());
                terminal.setDescription(terminalObject.getData().getDescription());
                ProviderRef providerRef = terminalObject.getData().getProviderRef();
                terminal.setProviderRef(String.valueOf(providerRef.getId()));
                terminals.add(terminal);
            }
        }
        return terminals;
    }

    private static boolean haveProvider(TerminalObject object) {
        return Objects.nonNull(object.getData().getProviderRef());
    }
}
