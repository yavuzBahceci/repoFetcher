package com.yavuzbahceci.gitfetcher.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.yavuzbahceci.gitfetcher.ui.DataState
import com.yavuzbahceci.gitfetcher.ui.Response
import com.yavuzbahceci.gitfetcher.ui.ResponseType
import com.yavuzbahceci.gitfetcher.util.*
import com.yavuzbahceci.gitfetcher.util.ErrorHandling.Companion.ERROR_CHECK_NETWORK_CONNECTION
import com.yavuzbahceci.gitfetcher.util.ErrorHandling.Companion.ERROR_UNKNOWN
import com.yavuzbahceci.gitfetcher.util.ErrorHandling.Companion.UNABLE_TO_DO_OPERATION_WO_INTERNET
import com.yavuzbahceci.gitfetcher.util.ErrorHandling.Companion.UNABLE_TO_RESOLVE_HOST
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

abstract class NetworkBoundResource<ResponseObject, ViewStateType>
    (
    isNetWorkAvailable: Boolean
) {

    protected val result = MediatorLiveData<DataState<ViewStateType>>()
    protected lateinit var job: CompletableJob
    protected lateinit var coroutineScope: CoroutineScope

    init {
        setJob(startNewJob())
        setValue(DataState.loading(isLoading = true, cachedData = null))

        if (isNetWorkAvailable) {
            coroutineScope.launch {
                withContext(Main) {
                    val apiResponse = createCall()
                    result.addSource(apiResponse) { response ->
                        result.removeSource(apiResponse)

                        coroutineScope.launch {
                            handleNetworkCall(response)
                        }
                    }
                }
            }
            GlobalScope.launch(IO) {
                if (!job.isCompleted) {
                    Log.e(TAG, "Job Network Timeout: ")
                    job.cancel(CancellationException(UNABLE_TO_RESOLVE_HOST))
                }
            }
        } else {
            onErrorReturn(
                UNABLE_TO_DO_OPERATION_WO_INTERNET,
                shouldUseToast = true,
                shouldDialog = false
            )
        }
    }

    suspend fun handleNetworkCall(response: GenericApiResponse<ResponseObject>?) {
        when (response) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }

            is ApiErrorResponse -> {
                Log.e(TAG, "handleNetworkCall: ${response.errorMessage}")
                onErrorReturn(response.errorMessage, true, shouldUseToast = false)
            }

            is ApiEmptyResponse -> {
                Log.e(TAG, "handleNetworkCall: Request returned Empty - HTTP 204")
                onErrorReturn("Request returned Empty - HTTP 204", true, shouldUseToast = false)
            }
        }
    }

    fun onCompleteJob(dataState: DataState<ViewStateType>) {
        GlobalScope.launch(Main) {
            job.complete()
            setValue(dataState)
        }
    }

    private fun setValue(dataState: DataState<ViewStateType>) {
        result.value = dataState
    }

    fun onErrorReturn(errorMessage: String?, shouldDialog: Boolean, shouldUseToast: Boolean) {
        var message = errorMessage
        var useDialog = shouldDialog
        var responseType: ResponseType = ResponseType.None()
        if (message == null) {
            message = ERROR_UNKNOWN
        } else if (ErrorHandling.isNetworkError(message)) {
            message = ERROR_CHECK_NETWORK_CONNECTION
            useDialog = false
        }
        if (shouldUseToast) {
            responseType = ResponseType.Toast()
        }
        if (useDialog) {
            responseType = ResponseType.Dialog()
        }
        onCompleteJob(
            DataState.error(
                response = Response(
                    message = message,
                    responseType = responseType
                )
            )
        )
    }

    @OptIn(InternalCoroutinesApi::class)
    private fun startNewJob(): Job {
        Log.d(TAG, "startNewJob: started...")
        job = Job()
        job.invokeOnCompletion(
            onCancelling = true,
            invokeImmediately = true,
            handler = object : CompletionHandler {
                override fun invoke(cause: Throwable?) {
                    if (job.isCancelled) {
                        Log.e(TAG, "invoke: Job Has Been Cancelled!")
                        cause?.let {
                            onErrorReturn(it.message, shouldDialog = false, shouldUseToast = true)
                        } ?: onErrorReturn(ERROR_UNKNOWN, false, shouldUseToast = true)
                    } else if (job.isCompleted) {
                        Log.e(TAG, "invoke: Job has been completed.")
                        // Handled
                    }
                }

            })
        coroutineScope = CoroutineScope(IO + job)
        return job
    }

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

    abstract suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    abstract fun setJob(job: Job)

    companion object {
        private const val TAG = "NetworkBoundResource"
    }
}