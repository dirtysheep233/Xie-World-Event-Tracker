package org.wet.world_event_tracker.net;

import net.minecraft.text.Text;
import org.wet.world_event_tracker.World_event_tracker;
import org.wet.world_event_tracker.net.type.Api;

import java.util.HashMap;
import java.util.Map;

public class NetManager {
    private final Map<String, Api> apis = new HashMap<>();
    public SocketIOClient socket = new SocketIOClient();
    public AutoUpdateApi updateApi = new AutoUpdateApi();

    @Deprecated
    public <T extends Api> T getApi(String name, Class<T> apiClass) {
        Api api = apis.get(name);
        if (apiClass.isInstance(api)) return apiClass.cast(api);
        World_event_tracker.LOGGER.error("Requested api \"{}\" does not exist/has not been loaded.", name);
        return null;
    }

    public void init() {
        registerApi(socket);
        registerApi(updateApi);
        initApis();
    }

    private <T extends Api> void registerApi(T api) {
        apis.put(api.name, api);
    }

    private void initApis() {
        for (Api a : apis.values()) {
            if (a.isDisabled()) {
                a.init();
            }
        }
    }
}
