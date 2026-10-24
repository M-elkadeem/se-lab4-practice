package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TorpedoStoreTest {

    private TorpedoStore store;

    @BeforeEach
    void init() {
        // A store with 5 torpedoes that never fails (failure rate 0.0)
        store = new TorpedoStore(5, 0.0);
    }

    @Test
    void fire_Success() {
        // Arrange
        TorpedoStore store = new TorpedoStore(1);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void fire_DecreasesTorpedoCount() {
        boolean result = store.fire(1);

        assertTrue(result);
        assertEquals(4, store.getTorpedoCount());
    }

    @Test
    void fire_MultipleTorpedoes() {
        boolean result = store.fire(3);

        assertTrue(result);
        assertEquals(2, store.getTorpedoCount());
    }

    @Test
    void fire_AllTorpedoes_StoreBecomesEmpty() {
        store.fire(5);

        assertEquals(0, store.getTorpedoCount());
        assertTrue(store.isEmpty());
    }

    @Test
    void fire_AlwaysFailing_ReturnsFalseAndKeepsCount() {
        // Failure rate 1.0: firing always fails
        TorpedoStore failingStore = new TorpedoStore(5, 1.0);

        boolean result = failingStore.fire(1);

        assertFalse(result);
        assertEquals(5, failingStore.getTorpedoCount());
    }

    @Test
    void fire_ZeroTorpedoes_Throws() {
        assertThrows(IllegalArgumentException.class, () -> store.fire(0));
    }

    @Test
    void fire_MoreThanAvailable_Throws() {
        assertThrows(IllegalArgumentException.class, () -> store.fire(6));
    }

    @Test
    void fire_FromEmptyStore_Throws() {
        TorpedoStore emptyStore = new TorpedoStore(0, 0.0);

        assertThrows(IllegalArgumentException.class, () -> emptyStore.fire(1));
    }

    @Test
    void isEmpty_NewStore_False() {
        assertFalse(store.isEmpty());
    }

    @Test
    void isEmpty_ZeroTorpedoes_True() {
        TorpedoStore emptyStore = new TorpedoStore(0, 0.0);

        assertTrue(emptyStore.isEmpty());
    }
}