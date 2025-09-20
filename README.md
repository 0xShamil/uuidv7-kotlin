# Kotlin UUIDv7 Generator

A dependency-free, high-performance, monotonic UUID v7 generator in 60 lines or less.

## Usage

Just copy `UUIDv7.kt` into your project and call `UUIDv7.randomUUID()`.

```kotlin
import UUIDv7

fun main() {
    val id: UUID = UUIDv7.randomUUID()
    println(id) // 0192f5cd-0c2e-7a3f-b1d2-8b9a6d2b4c11
}
```

## K-Sortability

By design, all Version 7 UUIDs are naturally sortable by the millisecond in which they were created, because the
timestamp is placed at the very beginning of the identifier. This implementation takes this property even further. It
guarantees that UUIDs generated within the exact same millisecond are also perfectly ordered relative to each other. It
achieves this by using a special 12-bit monotonic counter that increments for each new UUID created in that millisecond.
This results in a continuous stream of UUIDs that are always in a strict, lexicographical order.

*Note: The per-millisecond ordering is backed by a 12-bit counter. That means up to 4096 UUIDs per millisecond can be
strictly ordered. If more than 4096 UUIDs are generated within the same millisecond, the counter wraps and strict
monotonicity for that millisecond is no longer guaranteed.*

## High Performance

The generator is intended for use in performance-critical code paths where high throughput is required. The code avoids
unnecessary work and keeps contention low:

A per-thread `SecureRandom` to remove global RNG locks. The monotonic state is managed using Atomic variables in a
non-blocking loop, which avoids the overhead and contention of traditional locks. The byte layout is written directly
with simple shifts and masks; version/variant bits are set in place with minimal branching.

## License

This code is available under an [WTFPL License](https://github.com/0xShamil/uuidv7-kotlin/blob/main/LICENSE).