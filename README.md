# Pushpad - Web Push Notifications Service
 
[Pushpad](https://pushpad.xyz) is a service for sending push notifications from your web app. It supports the **Push API** (Chrome, Firefox, Opera) and **APNs** (Safari).

Features:

- notifications are delivered even when the user is not on your website
- users don't need to install any app or plugin
- you can target specific users or send bulk notifications

Currently push notifications work on the following browsers:

- Chrome (Desktop and Android)
- Firefox (44+)
- Opera (42+)
- Safari

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

Pushpad offers two different products. [Learn more](https://pushpad.xyz/docs)

### Pushpad Pro

Choose Pushpad Pro if you want to use Javascript for a seamless integration. [Read the docs](https://pushpad.xyz/docs/pushpad_pro_getting_started)

If you need to generate the HMAC signature for the `uid` you can use this helper:

```java
pushpad.signatureFor(currentUserId);
```

### Pushpad Express

Let users subscribe to your push notifications with a link that you can generate with: 

```java
pushpad.path()

// If the user is logged in on your website you should track its user id to target him in the future
pushpad.pathFor(currentUserId)
```

When a user clicks the link is sent to Pushpad, asked to receive push notifications and redirected back to your website.

## Sending push notifications

```java
Notification notification = pushpad.buildNotification("Title", "Message", "http://example.com");

// optional, defaults to the project icon
notification.iconUrl = "http://example.com/assets/icon.png";
// optional, drop the notification after this number of seconds if a device is offline 
notification.ttl = 604800;

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

The notification title and body have a max length of 30 and 120 characters, respectively.

If no user with that id has subscribed to push notifications, that id is simply ignored.

The methods above return a JSONObject: 

- `res.get("id")` is the id of the notification on Pushpad
- `res.get("scheduled")` is the estimated reach of the notification (i.e. the number of devices to which the notification will be sent, which can be different from the number of users, since a user may receive notifications on multiple devices)
- `res.get("uids")` (`deliverTo` only) are the user IDs that will be actually reached by the notification because they are subscribed to your notifications. For example if you send a notification to `{"uid1", "uid2", "uid3"}`, but only `"uid1"` is subscribed, you will get `{"uid1"}` in response. Note that if a user has unsubscribed after the last notification sent to him, he may still be reported for one time as subscribed (this is due to the way the W3C Push API works).

## License

The library is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).

