package xyz.pushpad.notification;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import xyz.pushpad.Pushpad;
import xyz.pushpad.TestSupport;
import xyz.pushpad.TestSupport.MockResponse;
import xyz.pushpad.TestSupport.MockServer;
import xyz.pushpad.TestSupport.RecordedRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationsTest {
  @AfterEach
  void tearDown() {
    TestSupport.resetPushpad();
  }

  @Test
  void listNotifications() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(200, "[{\"id\":1,\"body\":\"Hi\"}]"))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      NotificationListParams params = new NotificationListParams()
          .setProjectId(123L)
          .setPage(2L);
      List<Notification> notifications = Notifications.list(params);

      assertEquals(1, notifications.size());
      assertEquals(1L, notifications.get(0).getId());

      RecordedRequest request = server.takeRequest();
      assertEquals("GET", request.method);
      assertEquals("/api/v1/projects/123/notifications", request.path);
      Map<String, List<String>> query = TestSupport.parseQuery(request.query);
      assertEquals(List.of("2"), query.get("page"));
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");
    }
  }

  @Test
  void createNotification() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(201, "{\"id\":123456789,\"scheduled\":9876}"))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      NotificationCreateParams params = new NotificationCreateParams()
          .setProjectId(123L)
          .setTitle("Foo Bar")
          .setBody("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
          .setTargetUrl("https://example.com")
          .setIconUrl("https://example.com/assets/icon.png")
          .setTags(List.of("tag1", "tagA && !tagB"))
          .setActions(List.of(new NotificationActionParams()
              .setTitle("A button")
              .setTargetUrl("https://example.com/button-link")
              .setIcon("https://example.com/assets/button-icon.png")
              .setAction("myActionName")));

      NotificationCreateResponse response = Notifications.create(params);

      assertEquals(123456789L, response.getId());
      assertEquals(9876L, response.getScheduled());

      RecordedRequest request = server.takeRequest();
      assertEquals("POST", request.method);
      assertEquals("/api/v1/projects/123/notifications", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderStartsWith(request, "Content-Type", "application/json");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");

      String expectedJson = "{"
          + "\"title\":\"Foo Bar\","
          + "\"body\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit.\","
          + "\"target_url\":\"https://example.com\","
          + "\"icon_url\":\"https://example.com/assets/icon.png\","
          + "\"tags\":[\"tag1\",\"tagA && !tagB\"],"
          + "\"actions\":[{"
          + "\"title\":\"A button\","
          + "\"target_url\":\"https://example.com/button-link\","
          + "\"icon\":\"https://example.com/assets/button-icon.png\","
          + "\"action\":\"myActionName\""
          + "}]"
          + "}";
      TestSupport.assertJsonEquals(expectedJson, request.body);
    }
  }

  @Test
  void getNotification() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(200, "{\"id\":9876,\"body\":\"Hello\"}"))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      Notification notification = Notifications.get(9876L, null);

      assertEquals(9876L, notification.getId());
      assertEquals("Hello", notification.getBody());

      RecordedRequest request = server.takeRequest();
      assertEquals("GET", request.method);
      assertEquals("/api/v1/notifications/9876", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");
    }
  }

  @Test
  void cancelNotification() throws Exception {
    try (MockServer server = TestSupport.startServer(new MockResponse(204, null))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      Notifications.cancel(5555L, null);

      RecordedRequest request = server.takeRequest();
      assertEquals("DELETE", request.method);
      assertEquals("/api/v1/notifications/5555/cancel", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");
    }
  }
}
