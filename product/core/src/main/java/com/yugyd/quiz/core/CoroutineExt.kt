/*
 *    Copyright 2023 Roman Likhachev
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.yugyd.quiz.core

import kotlin.coroutines.cancellation.CancellationException

@Deprecated("Use custom runCatching which throw CancellationException")
inline fun <R> runCatch(
    block: () -> R,
    catch: (Throwable) -> Unit = {},
    finally: () -> Unit = {}
): R? = try {
    block.invoke()
} catch (throwable: CancellationException) {
    throw throwable
} catch (throwable: Throwable) {
    catch.invoke(throwable)
    null
} finally {
    finally()
}

inline fun <T, R> T.result(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (throwable: CancellationException) {
        throw throwable
    } catch (throwable: Throwable) {
        Result.failure(throwable)
    }
}
