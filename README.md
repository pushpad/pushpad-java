# Pushpad - Web Push Notifications

Add native push notifications to your web app using [Pushpad](https://pushpad.xyz).

Features:

- notifications are delivered even when the user is not on your website
- users don't need to install any app or plugin
- you can target specific users or send bulk notifications

Currently push notifications work on the following browsers:

- Chrome (Desktop and Android)
- Firefox (44+)
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

Pushpad offers two different ways to collect subscriptions. [Learn more](https://pushpad.xyz/docs#simple_vs_custom_api_docs)

### Custom API

Choose the Custom API if you want to use Javascript for a seamless integration. [Read the docs](https://pushpad.xyz/docs#custom_api_docs)

If you need to generate the HMAC signature for the `uid` you can use this helper:

```java
pushpad.signatureFor(currentUserId);
```

### Simple API

Let users subscribe to your push notifications with a link that you can generate with: 

```java
pushpad.path() // Subscribe anonymous to push notifications
pushpad.pathFor(currentUserId) // Subscribe current user to push notifications
```

`currentUserId` is the user currently logged in on your website.

When a user clicks the link is sent to Pushpad, automatically asked to receive push notifications and redirected back to your website.

## Sending push notifications

```java
Notification notification = pushpad.buildNotification("Website Name", "Hello world!", "http://example.com");

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
  String[] tags2 = {"segment1", "segment2"};
  notification.broadcast(tags2);

  // deliver to everyone
  notification.broadcast();
} catch (DeliveryException e) {
  e.printStackTrace();
}
```

The notification title and body have a max length of 30 and 90 characters, respectively.

If no user with that id has subscribed to push notifications, that id is simply ignored.

The methods above return a JSONObject: 

- `res.get("scheduled")` is the number of devices to which the notification will be sent
- `res.get("uids")` (`deliverTo` only) are the user IDs that will be actually reached by the notification (unless they have unsubscribed since the last notification)

## License

The library is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).

