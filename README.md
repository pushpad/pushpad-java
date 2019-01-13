# Pushpad - Web Push Notifications
 
[Pushpad](https://pushpad.xyz) is a service for sending push notifications from your web app. It supports the **Push API** (Chrome, Firefox, Opera, Edge) and **APNs** (Safari).

Features:

- notifications are delivered even when the user is not on your website
- users don't need to install any app or plugin
- you can target specific users or send bulk notifications

## Installation

Download the latest JAR from Github:

https://github.com/pushpad/pushpad-java/releases

You also need to download [JSON.simple](https://code.google.com/archive/p/json-simple/).

## Getting started

First you need to sign up to Pushpad and create a project there.

Then set your authentication credentials:

```java
String authToken = "5374d7dfeffa2eb49965624ba7596a09";
String projectId = "123";
Pushpad pushpad = new Pushpad(authToken, projectId);
```

- `auth_token` can be found in the user account settings. 
- `project_id` can be found in the project settings.

## Collecting user subscriptions to push notifications

You can subscribe the users to your notifications using the Javascript SDK, as described in the [getting started guide](https://pushpad.xyz/docs/pushpad_pro_getting_started).

If you need to generate the HMAC signature for the `uid` you can use this helper:

```java
pushpad.signatureFor(currentUserId);
```

## Sending push notifications

```java
Notification notification = pushpad.buildNotification("Title", "Message", "http://example.com/my/page");

// optional, defaults to the project icon
notification.iconUrl = "http://example.com/assets/square-icon.png";
// optional, an image to display in the notification content
notification.imageUrl = "http://example.com/assets/image.png";
// optional, drop the notification after this number of seconds if a device is offline 
notification.ttl = 604800;
// optional, prevent Chrome on desktop from automatically closing the notification after a few seconds
notification.requireInteraction = true;
// optional, enable this option only for time-sensitive alerts (e.g. incoming phone call)
notification.urgent = false;
// optional, a string that is passed as an argument to action button callbacks
notification.customData = "123";
// optional, add some action buttons to the notification
// see https://pushpad.xyz/docs/action_buttons
ActionButton button1 = new ActionButton("My Button 1"); // Title
button1.targetUrl = "http://example.com/button-link"; // optional
button1.icon = "http://example.com/assets/button-icon.png"; // optional
button1.action = "myActionName"; // optional
notification.actionButtons = new ActionButton[]{button1};
// optional, bookmark the notification in the Pushpad dashboard (e.g. to highlight manual notifications)
notification.starred = true;
// optional, use this option only if you need to create scheduled notifications (max 5 days)
// see https://pushpad.xyz/docs/schedule_notifications
Instant tomorrow = Instant.now().plusSeconds(60*60*24);
notification.sendAt = tomorrow;
// optional, add the notification to custom categories for stats aggregation
// see https://pushpad.xyz/docs/monitoring
notification.customMetrics = new String[]{"examples", "another_metric"}; // up to 3 metrics per notification

try {
  // deliver the notification to a user
  notification.deliverTo("user100");

  // deliver the notification to a group of users
  String[] users = {"user123", "user100"};
  notification.deliverTo(users);

  // deliver to some users only if they have a given preference
  // e.g. only "users" who have a interested in "events" will be reached
  String[] tags1 = {"events"};
  notification.deliverTo(users, tags1);

  // deliver to segments
  // e.g. any subscriber that has the tag "segment1" OR "segment2"
  String[] tags2 = {"segment1", "segment2"};
  notification.broadcast(tags2);
  
  // you can use boolean expressions 
  // they must be in the disjunctive normal form (without parenthesis)
  String[] tags3 = {"zip_code:28865 && !optout:local_events || friend_of:Organizer123"};
  notification.broadcast(tags3);
  String[] tags4 = {"tag1 && tag2", "tag3"}; // equal to "tag1 && tag2 || tag3"
  notification.deliverTo(users, tags4);

  // deliver to everyone
  notification.broadcast();
} catch (DeliveryException e) {
  e.printStackTrace();
}
```

You can set the default values for most fields in the project settings. See also [the docs](https://pushpad.xyz/docs/rest_api#notifications_api_docs) for more information about notification fields.

If you try to send a notification to a user ID, but that user is not subscribed, that ID is simply ignored.

The methods above return a JSONObject: 

- `res.get("id")` is the id of the notification on Pushpad
- `res.get("scheduled")` is the estimated reach of the notification (i.e. the number of devices to which the notification will be sent, which can be different from the number of users, since a user may receive notifications on multiple devices)
- `res.get("uids")` (`deliverTo` only) are the user IDs that will be actually reached by the notification because they are subscribed to your notifications. For example if you send a notification to `{"uid1", "uid2", "uid3"}`, but only `"uid1"` is subscribed, you will get `{"uid1"}` in response. Note that if a user has unsubscribed after the last notification sent to him, he may still be reported for one time as subscribed (this is due to the way the W3C Push API works).
- `res.get("send_at")` is present only for scheduled notifications. The fields `scheduled` and `uids` are not available in this case.

## License

The library is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).

