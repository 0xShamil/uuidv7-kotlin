/**
 *     DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *            Version 2, December 2004
 *
 *  Copyright (C) 2024 0xShamil
 *
 *  Everyone is permitted to copy and distribute verbatim or modified
 *  copies of this license document, and changing it is allowed as long
 *  as the name is changed.
 *
 *             DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *   0. You just DO WHAT THE FUCK YOU WANT TO.
 */

import java.nio.ByteBuffer
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class UUIDv7Test {

    @Test
    fun `test no duplicate UUID should be generated`() {
        val uuidSet = mutableSetOf<UUID>()
        repeat(100_000) {
            val uuid = UUIDv7.randomUUID()
            assertTrue(uuidSet.add(uuid)) { "Duplicate UUID generated: $uuid" }
        }
    }

    @Test
    fun `test version and variant bits are set correctly`() {
        val uuid = UUIDv7.randomUUID()

        // Version should be 7
        val version = uuid.version()
        assertEquals(7, version) { "UUID version is not 7" }

        // Variant should be IETF variant
        val variant = uuid.variant()
        assertEquals(2, variant) { "UUID variant is not IETF variant (2)" }
    }

    @Test
    fun `test timestamp embedded timestamp is in the past`() {
        val uuid = UUIDv7.randomUUID()

        val timestamp = extractTimestamp(uuid)

        val currentTime = System.currentTimeMillis()

        // Allow a small delta due to execution time
        val delta = currentTime - timestamp
        assertTrue(delta in 0..1000) { "Timestamp in UUID is not recent (delta: $delta ms)" }
    }

    @Test
    fun `test UUIDv7 should be ordered`() {
        val uuid1 = UUIDv7.randomUUID()
        Thread.sleep(1)
        val uuid2 = UUIDv7.randomUUID()

        // Since UUIDv7 includes the timestamp, uuid2 should be greater than uuid1
        assertTrue(uuid1 < uuid2) { "UUIDs are not ordered correctly" }
    }

    private fun extractTimestamp(uuid: UUID): Long {
        val bytes = ByteArray(16)
        val buffer = ByteBuffer.wrap(bytes)
        buffer.putLong(uuid.mostSignificantBits)
        buffer.putLong(uuid.leastSignificantBits)

        val timestampBytes = bytes.sliceArray(0..5)
        var timestamp = 0L
        for (byte in timestampBytes) {
            timestamp = (timestamp shl 8) or (byte.toLong() and 0xFF)
        }
        return timestamp
    }
}
