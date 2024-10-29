package com.plcoding.core.data.network

import com.plcoding.core.domain.util.NetworkError
import io.ktor.client.statement.HttpResponse
import java.nio.channels.UnresolvedAddressException
import com.plcoding.core.domain.util.Result
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCallApi(
    call: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        call()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        //To Avoid cancel coroutine exception.
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }
    return responseToResult(response)
}