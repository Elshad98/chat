package com.example.chat.core.interactor

import com.example.chat.core.exception.Failure
import com.example.chat.core.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Abstract class for a Use Case (Interactor in terms of
 * Clean Architecture naming convention).
 *
 * This abstraction represents an execution unit for
 * different use cases (this means that any use case
 * in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will
 * execute its job in a pool of threads using
 * [Dispatchers.IO].
 *
 * The result of the computation will be posted on the
 * same thread used by the @param 'scope' [CoroutineScope].
 */
abstract class UseCase<out TypeT, in ParamsT> where TypeT : Any {

    abstract suspend fun run(params: ParamsT): Either<Failure, TypeT>

    operator fun invoke(
        params: ParamsT,
        scope: CoroutineScope = MainScope(),
        onResult: (Either<Failure, TypeT>) -> Unit = {}
    ) {
        scope.launch {
            val deferredJob = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferredJob.await())
        }
    }
}
