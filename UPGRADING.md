# Upgrading to version 2.x

This version is a major rewrite of the library and adds support for the full REST API, including Notifications, Subscriptions, Projects and Senders.

This version has some breaking changes:

- The recommended installation method has changed: the library is now available on Maven Central (use Maven/Gradle instead of downloading the JAR manually).
- Dropped the dependency on `JSON.simple` and using `Jackson` instead (it is included in `pom.xml` as a dependency).
- Imports are now split across domain packages (e.g. `xyz.pushpad.notification`, `xyz.pushpad.subscription`), so update imports accordingly.
- When you create a client with `new Pushpad(...)`, the `projectId` argument is now a `Long` instead of a `String` (or you can omit it and pass the project id per request).
- `Notification` is now used only for API responses, not for API requests. To create / send a notification, use `pushpad.notifications().create(new NotificationCreateParams().setBody("Hello"))`.
- The `deliverTo()` / `broadcast()` methods were removed; set `.setUids(...)` and `.setTags(...)` on `NotificationCreateParams` instead.
- Arrays like `uids`, `tags`, and `customMetrics` are now `List` values (e.g. `setUids(List.of("user1"))`).
- Action buttons are now `setActions(List.of(new NotificationActionParams()...))` instead of `ActionButton[]`.
- Numeric fields like `ttl` now use `Long` (e.g. `setTtl(604800L)`).
- Time fields like `sendAt` now use `OffsetDateTime` instead of `Instant`.
- The response to the creation of a notification is now a `NotificationCreateResponse` instead of a `JSONObject` (use getters like `getId()`, `getScheduled()`, `getUids()`, `getSendAt()`).
- Errors are now raised as `ApiException` (HTTP errors) or `PushpadException` (network/other), instead of `DeliveryException`.
