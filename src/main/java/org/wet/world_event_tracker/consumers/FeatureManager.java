package org.wet.world_event_tracker.consumers;

import org.wet.world_event_tracker.World_event_tracker;
import org.wet.world_event_tracker.components.Feature;
import org.wet.world_event_tracker.features.CommandHelpFeature;
import org.wet.world_event_tracker.features.TestCommandHelpFeature;
import org.wet.world_event_tracker.features.server.ServerBridgeFeature;

public class FeatureManager {
    public void init() {
        registerFeature(new CommandHelpFeature());
        if (World_event_tracker.isTesting()) registerFeature(new TestCommandHelpFeature());
        registerFeature(new ServerBridgeFeature());
    }

    private void registerFeature(Feature feature) {
        initializeFeature(feature);
    }

    private void initializeFeature(Feature feature) {
        feature.init();
    }
}
