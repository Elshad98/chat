package com.example.chat.core.functional

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 */
sealed class Either<out LeftT, out RightT> {

    /**
     * The left side of the disjoint union, as opposed to the [Right] side.
     */
    data class Left<out LeftT>(
        val value: LeftT
    ) : Either<LeftT, Nothing>() {

        override fun toString(): String = "Either.Left($value)"
    }

    /**
     * The right side of the disjoint union, as opposed to the [Left] side.
     */
    data class Right<out RightT>(
        val value: RightT
    ) : Either<Nothing, RightT>() {

        override fun toString(): String = "Either.Right($value)"
    }

    /**
     * Returns `true` if this is a [Right], `false` otherwise.
     */
    val isRight: Boolean
        get() = this is Right<RightT>

    /**
     * Returns `true` if this is a [Left], `false` otherwise.
     */
    val isLeft: Boolean
        get() = this is Left<LeftT>

    /**
     * Creates a Left type.
     */
    fun <LeftT> left(value: LeftT) = Left(value)

    /**
     * Creates a Right type.
     */
    fun <RightT> right(value: RightT) = Right(value)

    /**
     * Transform an [Either] into a value of [T].
     * Alternative to using `when` to fold an [Either] into a value [T].
     *
     * @param ifLeft transform the [Either.Left] type [LeftT] to [T].
     * @param ifRight transform the [Either.Right] type [RightT] to [T].
     * @return the transformed value [T] by applying [ifLeft] or [ifRight] to [LeftT] or [RightT] respectively.
     */
    inline fun <T> fold(ifLeft: (LeftT) -> T, ifRight: (RightT) -> T): T {
        return when (this) {
            is Left -> ifLeft(value)
            is Right -> ifRight(value)
        }
    }
}

/**
 * Map, or transform, the right value [RightT] of this [Either] to a new value [T].
 */
inline fun <Left, RightT, T> Either<Left, RightT>.map(func: (RightT) -> T): Either<Left, T> {
    return flatMap { Either.Right(func(it)) }
}

/**
 * Performs the given [action] on the encapsulated [LeftT] if this instance represents [Either.Left].
 * Returns the original [Either] unchanged.
 */
inline fun <LeftT, RightT> Either<LeftT, RightT>.onFailure(
    action: (LeftT) -> Unit
): Either<LeftT, RightT> {
    return apply { if (this is Either.Left) action(value) }
}

/**
 * Performs the given [action] on the encapsulated [RightT] value if this instance represents [Either.Right].
 * Returns the original [Either] unchanged.
 */
inline fun <LeftT, RightT> Either<LeftT, RightT>.onSuccess(
    action: (RightT) -> Unit
): Either<LeftT, RightT> {
    return apply { if (this is Either.Right) action(value) }
}

/**
 * Map, or transform, the right value [RightT] of this [Either] into a new [Either] with a right value of type [T].
 * Returns a new [Either] with either the original left value of type [LeftT] or the newly transformed right value of type [T].
 *
 * @param func The function to bind across [Either.Right].
 */
inline fun <LeftT, RightT, T> Either<LeftT, RightT>.flatMap(
    func: (RightT) -> Either<LeftT, T>
): Either<LeftT, T> {
    return when (this) {
        is Either.Left -> this
        is Either.Right -> func(value)
    }
}