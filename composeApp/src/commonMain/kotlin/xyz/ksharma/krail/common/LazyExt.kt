package xyz.ksharma.krail.common

@Suppress("NOTHING_TO_INLINE")
inline fun <T> unsafeLazy(noinline initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)
