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
sealed class Either<out LeftT, out RightT> {

    /**
     * Represents the left side of [Either] class which by convention is a "Failure".
     */
    data class Left<out LeftT>(val a: LeftT) : Either<LeftT, Nothing>()

    /**
     * Represents the right side of [Either] class which by convention is a "Success".
     */
    data class Right<out RightT>(val b: RightT) : Either<Nothing, RightT>()

    val isRight get() = this is Right<RightT>
    val isLeft get() = this is Left<LeftT>

    fun <LeftT> left(a: LeftT) = Left(a)
    fun <RightT> right(b: RightT) = Right(b)

    fun either(functionLeft: (LeftT) -> Any, functionRight: (RightT) -> Any): Any =
        when (this) {
            is Left -> functionLeft(a)
            is Right -> functionRight(b)
        }
}

fun <A, B, C> ((A) -> B).compose(func: (B) -> C): (A) -> C = {
    func(this(it))
}

fun <T, LeftT, RightT> Either<LeftT, RightT>.flatMap(func: (RightT) -> Either<LeftT, T>): Either<LeftT, T> {
    return when (this) {
        is Either.Left -> Either.Left(a)
        is Either.Right -> func(b)
    }
}

fun <T, Left, RightT> Either<Left, RightT>.map(func: (RightT) -> (T)): Either<Left, T> {
    return this.flatMap(func.compose(::right))
}

fun <LeftT, RightT> Either<LeftT, RightT>.onNext(func: (RightT) -> Unit): Either<LeftT, RightT> {
    this.flatMap(func.compose(::right))
    return this
}