package io.space.automation.core.adapter.registry;

import io.space.automation.core.adapter.Adapter;
import io.space.automation.core.adapter.AdapterChannel;

import java.util.Optional;

public interface AdapterRegistry<T extends Adapter> {

    /**
     * Retrieves implementation of a subtype of {@link Adapter} based on requested {@link AdapterChannel}
     */
    Optional<T> getAdapter(AdapterChannel channel);

    /**
     * Registers a new implementation of subtype of {@link Adapter}
     * and associates that specific implementation with the declared {@link AdapterChannel}
     */
    void register(T t, AdapterChannel channel);
}
