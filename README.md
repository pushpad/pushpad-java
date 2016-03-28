# Pushpad: real push notifications for websites

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

`auth_token` can be found in the user account settings. 
`project_id` can be found in the project settings on Pushpad.

## Collecting user subscriptions

### Custom API

Read the [docs](https://pushpad.xyz/docs#custom_api_docs).

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

## Sending notifications

After you have collected the user subscriptions you can send them push notifications:

```java
Notification notification = pushpad.buildNotification("Website Name", "Hello world!", "http://example.com");

try {
  # deliver the notification to a user
  notification.deliverTo("100");

  # deliver the notification to a group of users
  String[] uids = {"123", "100"};
  notification.deliverTo(uids);

  # deliver to everyone
  notification.broadcast();
} catch (DeliveryException e) {
  e.printStackTrace();
}
```

If no user with that id has subscribed to push notifications, that id is simply ignored.

The methods above return a JSONObject: `res.get("scheduled")` contains the number of notifications that will be sent. For example if you call `notification.deliverTo(user)` but the user has never subscribed to push notifications the result will be `{"scheduled": 0}`.

## License

The gem is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).

