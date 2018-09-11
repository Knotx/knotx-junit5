/*
 * Copyright (C) 2018 Knot.x Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.knotx.junit5.util;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.vertx.junit5.VertxTestContext;

public final class RequestUtil {

  /** Util class - no instantiation */
  private RequestUtil() {}

  /**
   * Safely execute onError handler on given result, passing checks to given test context
   *
   * @param context test context
   * @param result to which subscribe
   * @param onError result handler, can throw exceptions
   * @param <T> result type to process, irrelevant here
   */
  public static <T> void subscribeToResult_shouldFail(
      VertxTestContext context, Single<T> result, Consumer<Throwable> onError) {
    result
        .doOnError(onError)
        .subscribe(
            response -> context.failNow(new IllegalStateException("Error should occur")),
            error -> context.completeNow());
  }

  /**
   * Safely execute assertions on given result, passing checks to given test context
   *
   * @param context test context
   * @param result to which subscribe
   * @param assertions result handler, can throw exceptions
   * @param <T> result type to process, assertions must accept the same type
   */
  public static <T> void subscribeToResult_shouldSucceed(
      VertxTestContext context, Single<T> result, Consumer<T> assertions) {
    result.doOnSuccess(assertions).subscribe(response -> context.completeNow(), context::failNow);
  }

  /**
   * Verify that consumer doesn't error out when it accepts given consumable. Context is notified
   * both in case of failure and success.
   *
   * @param context test context
   * @param consumer which will accept consumable
   * @param consumable object to process
   * @param <T> type of consumable to process
   */
  public static <T> void processWithContextVerification(
      VertxTestContext context, Consumer<T> consumer, T consumable) {
    context.verify(
        () -> {
          try {
            consumer.accept(consumable);

            context.completeNow();
          } catch (Exception e) {
            context.failNow(e);
          }
        });
  }
}
