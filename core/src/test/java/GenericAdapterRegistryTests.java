import io.space.automation.core.adapter.Adapter;
import io.space.automation.core.adapter.AdapterChannel;
import io.space.automation.core.adapter.registry.GenericAdapterRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class GenericAdapterRegistryTests {

    private final GenericAdapterRegistry<Adapter> genericAdapterRegistry = new GenericAdapterRegistry<>();

    private final Adapter stringAdapter = new StringAdapter();
    private final Adapter longAdapter = new LongAdapter();

    @BeforeEach
    void setUp() {
        genericAdapterRegistry.reset();
    }

    @Test
    void byDefaultContainsNoImpls() {
        Assertions.assertEquals(genericAdapterRegistry.getAdapter(Channel.STRING), Optional.empty());
        Assertions.assertEquals(genericAdapterRegistry.getAdapter(Channel.LONG), Optional.empty());
    }

    @Test
    void canRegister() {
        genericAdapterRegistry.register(stringAdapter, Channel.STRING);
        Assertions.assertTrue(genericAdapterRegistry.getAdapter(Channel.STRING).isPresent());
        Assertions.assertFalse(genericAdapterRegistry.getAdapter(Channel.LONG).isPresent());
    }

    @Test
    void canRegisterMultiple() {
        genericAdapterRegistry.register(stringAdapter, Channel.STRING);
        genericAdapterRegistry.register(longAdapter, Channel.LONG);
        Assertions.assertTrue(genericAdapterRegistry.getAdapter(Channel.STRING).isPresent());
        Assertions.assertTrue(genericAdapterRegistry.getAdapter(Channel.LONG).isPresent());
        Assertions.assertNotEquals(genericAdapterRegistry.getAdapter(Channel.STRING), genericAdapterRegistry.getAdapter(Channel.LONG));
    }

    @Test
    void canReset() {
        genericAdapterRegistry.register(stringAdapter, Channel.STRING);
        genericAdapterRegistry.register(longAdapter, Channel.LONG);
        Assertions.assertTrue(genericAdapterRegistry.getAdapter(Channel.STRING).isPresent());
        Assertions.assertTrue(genericAdapterRegistry.getAdapter(Channel.LONG).isPresent());
        genericAdapterRegistry.reset();
        Assertions.assertFalse(genericAdapterRegistry.getAdapter(Channel.STRING).isPresent());
        Assertions.assertFalse(genericAdapterRegistry.getAdapter(Channel.LONG).isPresent());
    }

    private enum Channel implements AdapterChannel {
        STRING,
        LONG;
    }

    private class StringAdapter implements Adapter {

    }

    private class LongAdapter implements Adapter {

    }

}
