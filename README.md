# Pushpad - Web Push Notifications

[Pushpad](https://pushpad.xyz) is a service for sending push notifications from websites and web apps. It uses the **Push API**, which is a standard supported by all major browsers (Chrome, Firefox, Opera, Edge, Safari).

The notifications are delivered in real time even when the users are not on your website and you can target specific users or send bulk notifications.

## Installation

You can add the dependency with Maven:

```xml
<dependency>
  <groupId>xyz.pushpad</groupId>
  <artifactId>pushpad-java</artifactId>
  <version>2.0.0</version>
</dependency>
```

Or Gradle:

```groovy
implementation 'xyz.pushpad:pushpad-java:2.0.0'
```

Then import the classes:

```java
import xyz.pushpad.Pushpad;
import xyz.pushpad.notification.*;
import xyz.pushpad.project.*;
import xyz.pushpad.sender.*;
import xyz.pushpad.subscription.*;
```

## Getting started

First you need to sign up to Pushpad and create a project there.

Then set your authentication credentials and project:

```java
Pushpad.configure("AUTH_TOKEN", 123);
```

- `AUTH_TOKEN` can be found in the user account settings.
- `PROJECT_ID` can be found in the project settings. If your application uses multiple projects, you can pass the `projectId` as a param to functions.

```java
NotificationCreateResponse res = Notifications.create(new NotificationCreateParams()
    .setProjectId(123L)
    .setBody("Your message"));

List<Notification> notifications = Notifications.list(new NotificationListParams()
    .setProjectId(123L)
    .setPage(1L));

// ...
```

## Collecting user subscriptions to push notifications

You can subscribe the users to your notifications using the Javascript SDK, as described in the [getting started guide](https://pushpad.xyz/docs/pushpad_pro_getting_started).

If you need to generate the HMAC signature for the `uid` you can use this helper:

```java
String s = Pushpad.signatureFor("CURRENT_USER_ID");
System.out.printf("User ID Signature: %s%n", s);
```

## Sending push notifications

Use `Notifications.create()` (or the `send()` alias) to create and send a notification:

```java
NotificationCreateParams n = new NotificationCreateParams()
    // required, the main content of the notification
    .setBody("Hello world!")

    // optional, the title of the notification (defaults to your project name)
    .setTitle("Website Name")

    // optional, open this link on notification click (defaults to your project website)
    .setTargetUrl("https://example.com")

    // optional, the icon of the notification (defaults to the project icon)
    .setIconUrl("https://example.com/assets/icon.png")

    // optional, the small icon displayed in the status bar (defaults to the project badge)
    .setBadgeUrl("https://example.com/assets/badge.png")

    // optional, an image to display in the notification content
    // see https://pushpad.xyz/docs/sending_images
    .setImageUrl("https://example.com/assets/image.png")

    // optional, drop the notification after this number of seconds if a device is offline
    .setTtl(604800)

    // optional, prevent Chrome on desktop from automatically closing the notification after a few seconds
    .setRequireInteraction(true)

    // optional, enable this option if you want a mute notification without any sound
    .setSilent(false)

    // optional, enable this option only for time-sensitive alerts (e.g. incoming phone call)
    .setUrgent(false)

    // optional, a string that is passed as an argument to action button callbacks
    .setCustomData("123")

    // optional, add some action buttons to the notification
    // see https://pushpad.xyz/docs/action_buttons
    .setActions(List.of(new NotificationActionParams()
        .setTitle("My Button 1")
        .setTargetUrl("https://example.com/button-link") // optional
        .setIcon("https://example.com/assets/button-icon.png") // optional
        .setAction("myActionName"))) // optional

    // optional, bookmark the notification in the Pushpad dashboard (e.g. to highlight manual notifications)
    .setStarred(true)

    // optional, use this option only if you need to create scheduled notifications (max 5 days)
    // see https://pushpad.xyz/docs/schedule_notifications
    .setSendAt(sendAtTime) // OffsetDateTime sendAtTime = OffsetDateTime.of(2022, 12, 25, 0, 0, 0, 0, ZoneOffset.UTC)

    // optional, add the notification to custom categories for stats aggregation
    // see https://pushpad.xyz/docs/monitoring
    .setCustomMetrics(List.of("examples", "another_metric")); // up to 3 metrics per notification

NotificationCreateResponse response = Notifications.create(n);

// TARGETING:
// You can use UIDs and Tags for sending the notification only to a specific audience...

// deliver to a user
NotificationCreateParams n1 = new NotificationCreateParams()
    .setBody("Hi user1")
    .setUids(List.of("user1"));
NotificationCreateResponse res1 = Notifications.create(n1);

// deliver to a group of users
NotificationCreateParams n2 = new NotificationCreateParams()
    .setBody("Hi users")
    .setUids(List.of("user1", "user2", "user3"));
NotificationCreateResponse res2 = Notifications.create(n2);

// deliver to some users only if they have a given preference
// e.g. only "users" who have a interested in "events" will be reached
NotificationCreateParams n3 = new NotificationCreateParams()
    .setBody("New event")
    .setUids(List.of("user1", "user2"))
    .setTags(List.of("events"));
NotificationCreateResponse res3 = Notifications.create(n3);

// deliver to segments
// e.g. any subscriber that has the tag "segment1" OR "segment2"
NotificationCreateParams n4 = new NotificationCreateParams()
    .setBody("Example")
    .setTags(List.of("segment1", "segment2"));
NotificationCreateResponse res4 = Notifications.create(n4);

// you can use boolean expressions
// they can include parentheses and the operators !, &&, || (from highest to lowest precedence)
// https://pushpad.xyz/docs/tags
NotificationCreateParams n5 = new NotificationCreateParams()
    .setBody("Example")
    .setTags(List.of("zip_code:28865 && !optout:local_events || friend_of:Organizer123"));
NotificationCreateResponse res5 = Notifications.create(n5);

NotificationCreateParams n6 = new NotificationCreateParams()
    .setBody("Example")
    .setTags(List.of("tag1 && tag2", "tag3")); // equal to 'tag1 && tag2 || tag3'
NotificationCreateResponse res6 = Notifications.create(n6);

// deliver to everyone
NotificationCreateParams n7 = new NotificationCreateParams()
    .setBody("Hello everybody");
NotificationCreateResponse res7 = Notifications.create(n7);
```

You can set the default values for most fields in the project settings. See also [the docs](https://pushpad.xyz/docs/rest_api#notifications_api_docs) for more information about notification fields.

If you try to send a notification to a user ID, but that user is not subscribed, that ID is simply ignored.

These fields are returned by the API:

```java
NotificationCreateResponse response = Notifications.create(n);

// Notification ID
System.out.println(response.getId()); // => 1000

// Estimated number of devices that will receive the notification
// Not available for notifications that use SendAt
System.out.println(response.getScheduled()); // => 5

// Available only if you specify some user IDs (UIDs) in the request:
// it indicates which of those users are subscribed to notifications.
// Not available for notifications that use SendAt
System.out.println(response.getUids()); // => ["user1", "user2"]

// The time when the notification will be sent.
// Available for notifications that use SendAt
System.out.println(response.getSendAt()); // => 2025-10-30T10:09Z

// Note:
// when a field is not available in the response, it is set to its zero value
```

## Getting push notification data

You can retrieve data for past notifications:

```java
Notification notification = Notifications.get(42);

// get basic attributes
System.out.println(notification.getId()); // => 42
System.out.println(notification.getTitle()); // => Foo Bar
System.out.println(notification.getBody()); // => Lorem ipsum dolor sit amet, consectetur adipiscing elit.
System.out.println(notification.getTargetUrl()); // => https://example.com
System.out.println(notification.getTtl()); // => 604800
System.out.println(notification.isRequireInteraction()); // => false
System.out.println(notification.isSilent()); // => false
System.out.println(notification.isUrgent()); // => false
System.out.println(notification.getIconUrl()); // => https://example.com/assets/icon.png
System.out.println(notification.getBadgeUrl()); // => https://example.com/assets/badge.png
System.out.println(notification.getCreatedAt()); // => 2025-07-06T10:09:14Z

// get statistics
System.out.println(notification.getScheduledCount()); // => 1
System.out.println(notification.getSuccessfullySentCount()); // => 4
System.out.println(notification.getOpenedCount()); // => 2
```

Or for multiple notifications of a project at once:

```java
List<Notification> notifications = Notifications.list(new NotificationListParams()
    .setPage(1L));

// same attributes as for single notification in example above
System.out.println(notifications.get(0).getId()); // => 42
System.out.println(notifications.get(0).getTitle()); // => Foo Bar
```

The REST API paginates the result set. You can pass a `page` parameter to get the full list in multiple requests.

```java
List<Notification> notifications = Notifications.list(new NotificationListParams()
    .setPage(2L));
```

## Scheduled notifications

You can create scheduled notifications that will be sent in the future:

```java
OffsetDateTime sendAt = OffsetDateTime.now(ZoneOffset.UTC).plusSeconds(60);

NotificationCreateResponse scheduled = Notifications.create(new NotificationCreateParams()
    .setBody("This notification will be sent after 60 seconds")
    .setSendAt(sendAt));
```

You can also cancel a scheduled notification:

```java
Notifications.cancel(scheduled.getId());
```

## Getting subscription count

You can retrieve the number of subscriptions for a given project, optionally filtered by `tags` or `uids`:

```java
long totalCount = Subscriptions.count(new SubscriptionCountParams());
System.out.println(totalCount); // => 100

totalCount = Subscriptions.count(new SubscriptionCountParams()
    .setUids(List.of("user1")));
System.out.println(totalCount); // => 2

totalCount = Subscriptions.count(new SubscriptionCountParams()
    .setTags(List.of("sports")));
System.out.println(totalCount); // => 10

totalCount = Subscriptions.count(new SubscriptionCountParams()
    .setTags(List.of("sports && travel")));
System.out.println(totalCount); // => 5

totalCount = Subscriptions.count(new SubscriptionCountParams()
    .setUids(List.of("user1"))
    .setTags(List.of("sports && travel")));
System.out.println(totalCount); // => 1
```

## Getting push subscription data

You can retrieve the subscriptions for a given project, optionally filtered by `tags` or `uids`:

```java
List<Subscription> subscriptions = Subscriptions.list(new SubscriptionListParams());

subscriptions = Subscriptions.list(new SubscriptionListParams()
    .setUids(List.of("user1")));

subscriptions = Subscriptions.list(new SubscriptionListParams()
    .setTags(List.of("sports")));

subscriptions = Subscriptions.list(new SubscriptionListParams()
    .setTags(List.of("sports && travel")));

subscriptions = Subscriptions.list(new SubscriptionListParams()
    .setUids(List.of("user1"))
    .setTags(List.of("sports && travel")));
```

The REST API paginates the result set. You can pass `page` and `perPage` parameters to get the full list in multiple requests.

```java
List<Subscription> subscriptions = Subscriptions.list(new SubscriptionListParams()
    .setPage(2L));
```

You can also retrieve the data of a specific subscription if you already know its id:

```java
Subscriptions.get(123, null);
```

## Updating push subscription data

Usually you add data, like user IDs and tags, to the push subscriptions using the [JavaScript SDK](https://pushpad.xyz/docs/javascript_sdk_reference) in the frontend.

However you can also update the subscription data from your server:

```java
List<Subscription> subscriptions = Subscriptions.list(new SubscriptionListParams()
    .setUids(List.of("user1")));

for (Subscription subscription : subscriptions) {
  // update the user ID associated to the push subscription
  Subscriptions.update(subscription.getId(), new SubscriptionUpdateParams()
      .setUid("myuser1"));

  // update the tags associated to the push subscription
  List<String> tags = new ArrayList<>(subscription.getTags());
  tags.add("another_tag");
  Subscriptions.update(subscription.getId(), new SubscriptionUpdateParams()
      .setTags(tags));
}
```

## Importing push subscriptions

If you need to [import](https://pushpad.xyz/docs/import) some existing push subscriptions (from another service to Pushpad, or from your backups) or if you simply need to create some test data, you can use this method:

```java
Subscription createdSubscription = Subscriptions.create(new SubscriptionCreateParams()
    .setEndpoint("https://example.com/push/f7Q1Eyf7EyfAb1")
    .setP256dh("BCQVDTlYWdl05lal3lG5SKr3VxTrEWpZErbkxWrzknHrIKFwihDoZpc_2sH6Sh08h-CacUYI-H8gW4jH-uMYZQ4=")
    .setAuth("cdKMlhgVeSPzCXZ3V7FtgQ==")
    .setUid("exampleUid")
    .setTags(List.of("exampleTag1", "exampleTag2")));
```

Please note that this is not the standard way to collect subscriptions on Pushpad: usually you subscribe the users to the notifications using the [JavaScript SDK](https://pushpad.xyz/docs/javascript_sdk_reference) in the frontend.

## Deleting push subscriptions

Usually you unsubscribe a user from push notifications using the [JavaScript SDK](https://pushpad.xyz/docs/javascript_sdk_reference) in the frontend (recommended).

However you can also delete the subscriptions using this library. Be careful, the subscriptions are permanently deleted!

```java
Subscriptions.delete(id, null);
```

## Managing projects

Projects are usually created manually from the Pushpad dashboard. However you can also create projects from code if you need advanced automation or if you manage [many different domains](https://pushpad.xyz/docs/multiple_domains).

```java
Project createdProject = Projects.create(new ProjectCreateParams()
    // required attributes
    .setSenderId(123L)
    .setName("My project")
    .setWebsite("https://example.com")

    // optional configurations
    .setIconUrl("https://example.com/icon.png")
    .setBadgeUrl("https://example.com/badge.png")
    .setNotificationsTtl(604800)
    .setNotificationsRequireInteraction(false)
    .setNotificationsSilent(false));
```

You can also find, update and delete projects:

```java
List<Project> projects = Projects.list();
for (Project project : projects) {
  System.out.printf("Project %d: %s%n", project.getId(), project.getName());
}

Project existingProject = Projects.get(123);

Project updatedProject = Projects.update(existingProject.getId(), new ProjectUpdateParams()
    .setName("The New Project Name"));

Projects.delete(existingProject.getId());
```

## Managing senders

Senders are usually created manually from the Pushpad dashboard. However you can also create senders from code.

```java
Sender createdSender = Senders.create(new SenderCreateParams()
    // required attributes
    .setName("My sender")

    // optional configurations
    // do not include these fields if you want to generate them automatically
    .setVapidPrivateKey("-----BEGIN EC PRIVATE KEY----- ...")
    .setVapidPublicKey("-----BEGIN PUBLIC KEY----- ..."));
```

You can also find, update and delete senders:

```java
List<Sender> senders = Senders.list();
for (Sender sender : senders) {
  System.out.printf("Sender %d: %s%n", sender.getId(), sender.getName());
}

Sender existingSender = Senders.get(987);

Sender updatedSender = Senders.update(existingSender.getId(), new SenderUpdateParams()
    .setName("The New Sender Name"));

Senders.delete(existingSender.getId());
```

## Error handling

API requests can return errors, described by an `ApiException` that exposes the HTTP status code and response body. Network issues and other errors return a generic `PushpadException`.

```java
NotificationCreateParams n = new NotificationCreateParams()
    .setBody("Hello");
try {
  Notifications.create(n);
} catch (ApiException e) { // HTTP error from the API
  System.out.println(e.getStatusCode() + " " + e.getBody());
} catch (PushpadException e) { // network error or other errors
  System.out.println(e.getMessage());
}
```

## Documentation

- Pushpad REST API reference: https://pushpad.xyz/docs/rest_api
- Getting started guide (for collecting subscriptions): https://pushpad.xyz/docs/pushpad_pro_getting_started
- JavaScript SDK reference (frontend): https://pushpad.xyz/docs/javascript_sdk_reference

## License

The library is available as open source under the terms of the [MIT License](https://opensource.org/licenses/MIT).
