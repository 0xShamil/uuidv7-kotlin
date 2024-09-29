# Kotlin UUIDv7 Generator

A zero dependency, dead simple version 7 UUID generator in 40 lines or less.

## Features

- **Zero Dependencies:** The entire implementation is under 40 lines and requires no external dependencies.
- **Timestamp-based UUIDs:** Embeds the current timestamp in milliseconds into the UUID for ordering.
- **Randomness and Uniqueness:** Uses `SecureRandom` to generate high-entropy random bits.
- **Standards Compliance:** Sets the correct version and variant bits according to the UUIDv7 draft specification.
- **Easy Integration:** Provides a singleton object `UUIDv7` with a simple `randomUUID()` method.

## Usage

Just copy the `UUIDv7.kt` file to your project.

```kotlin
import UUIDv7

fun main() {
    val uuid = UUIDv7.randomUUID()
    println(uuid) // 01923d43-13b4-7f90-b304-cf0ca680b6c5
}
```