package io.space.automation.core.adapter.registry;

import io.space.automation.core.adapter.Adapter;
import io.space.automation.core.adapter.AdapterChannel;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class GenericAdapterRegistry<T extends Adapter> implements AdapterRegistry<T> {

    private final Map<AdapterChannel, T> adapterMap;

    public GenericAdapterRegistry() {
        adapterMap = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<T> getAdapter(AdapterChannel channel) {
        return Optional.ofNullable(adapterMap.get(channel));
    }

    @Override
    public void register(T t, AdapterChannel channel) {
        adapterMap.put(channel, t);
    }

    public void reset() {
        adapterMap.clear();
    }
}
