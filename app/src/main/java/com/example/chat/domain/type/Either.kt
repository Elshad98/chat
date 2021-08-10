package com.example.chat.domain.type

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {

    /**
     * Represents the left side of [Either] class which by convention is a "Failure".
     */
    data class Left<out L>(val a: L) : Either<L, Nothing>()

    /**
     * Represents the right side of [Either] class which by convention is a "Success".
     */
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    val isRight get() = this is Right<R>
    val isLeft get() = this is Left<L>

    fun <L> left(a: L) = Left(a)
    fun <R> right(b: R) = Right(b)

    fun either(functionLeft: (L) -> Any, functionRight: (R) -> Any): Any =
        when (this) {
            is Left -> functionLeft(a)
            is Right -> functionRight(b)
        }
}

fun <A, B, C> ((A) -> B).compose(func: (B) -> C): (A) -> C = {
    func(this(it))
}

fun <T, L, R> Either<L, R>.flatMap(func: (R) -> Either<L, T>): Either<L, T> {
    return when (this) {
        is Either.Left -> Either.Left(a)
        is Either.Right -> func(b)
    }
}

fun <T, L, R> Either<L, R>.map(func: (R) -> (T)): Either<L, T> {
    return this.flatMap(func.compose(::right))
}

fun <L, R> Either<L, R>.onNext(func: (R) -> Unit): Either<L, R> {
    this.flatMap(func.compose(::right))
    return this
}