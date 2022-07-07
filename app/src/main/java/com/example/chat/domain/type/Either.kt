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
    data class Left<out LeftT>(val value: LeftT) : Either<LeftT, Nothing>()

    /**
     * Represents the right side of [Either] class which by convention is a "Success".
     */
    data class Right<out RightT>(val value: RightT) : Either<Nothing, RightT>()

    val isRight: Boolean
        get() = this is Right<RightT>
    val isLeft: Boolean
        get() = this is Left<LeftT>

    fun <LeftT> left(value: LeftT) = Left(value)

    fun <RightT> right(value: RightT) = Right(value)

    /**
     * Applies `foldLeft` if this is a [Left] or `foldRight` if this is a [Right].
     *
     * @param foldLeft the function to apply if this is a [Left]
     * @param foldRight the function to apply if this is a [Right]
     * @return the results of applying the function
     */
    inline fun <T> fold(foldLeft: (LeftT) -> T, foldRight: (RightT) -> T): T {
        return when (this) {
            is Left -> foldLeft(value)
            is Right -> foldRight(value)
        }
    }
}

fun <A, B, C> ((A) -> B).compose(func: (B) -> C): (A) -> C = {
    func(this(it))
}

fun <T, Left, RightT> Either<Left, RightT>.map(func: (RightT) -> (T)): Either<Left, T> {
    return flatMap(func.compose(::right))
}

fun <LeftT, RightT> Either<LeftT, RightT>.onNext(func: (RightT) -> Unit): Either<LeftT, RightT> {
    flatMap(func.compose(::right))
    return this
}

inline fun <T, LeftT, RightT> Either<LeftT, RightT>.flatMap(func: (RightT) -> Either<LeftT, T>): Either<LeftT, T> {
    return when (this) {
        is Either.Left -> Either.Left(value)
        is Either.Right -> func(value)
    }
}