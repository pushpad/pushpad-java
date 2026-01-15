package xyz.pushpad.subscription;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import xyz.pushpad.Pushpad;
import xyz.pushpad.TestSupport;
import xyz.pushpad.TestSupport.MockResponse;
import xyz.pushpad.TestSupport.MockServer;
import xyz.pushpad.TestSupport.RecordedRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubscriptionsTest {
  @Test
  void listSubscriptions() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(200, "[{\"id\":10,\"endpoint\":\"https://example.com/1\"}]"))) {
      Pushpad pushpad = new Pushpad("TOKEN", null, server.baseUrl());

      SubscriptionListParams params = new SubscriptionListParams()
          .setProjectId(123L)
          .setPage(1L)
          .setPerPage(20L)
          .setUids(List.of("u1", "u2"))
          .setTags(List.of("tag1"));
      List<Subscription> subscriptions = pushpad.subscriptions().list(params);

      assertEquals(1, subscriptions.size());
      assertEquals(10L, subscriptions.get(0).getId());

      RecordedRequest request = server.takeRequest();
      assertEquals("GET", request.method);
      assertEquals("/api/v1/projects/123/subscriptions", request.path);
      Map<String, List<String>> query = TestSupport.parseQuery(request.query);

      Map<String, List<String>> expected = new LinkedHashMap<>();
      expected.put("page", List.of("1"));
      expected.put("per_page", List.of("20"));
      expected.put("uids[]", List.of("u1", "u2"));
      expected.put("tags[]", List.of("tag1"));
      assertEquals(expected, query);

      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");
    }
  }

  @Test
  void createSubscription() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(201, "{\"id\":50,\"endpoint\":\"https://example.com/push/1\"}"))) {
      Pushpad pushpad = new Pushpad("TOKEN", null, server.baseUrl());

      SubscriptionCreateParams params = new SubscriptionCreateParams()
          .setProjectId(123L)
          .setEndpoint("https://example.com/push/1")
          .setP256dh("BCQVDTlYWdl05lal3lG5SKr3VxTrEWpZErbkxWrzknHrIKFwihDoZpc_2sH6Sh08h-CacUYI-H8gW4jH-uMYZQ4=")
          .setAuth("cdKMlhgVeSPzCXZ3V7FtgQ==")
          .setUid("user1")
          .setTags(List.of("tag1", "tag2"));

      Subscription subscription = pushpad.subscriptions().create(params);

      assertEquals(50L, subscription.getId());
      assertEquals("https://example.com/push/1", subscription.getEndpoint());

      RecordedRequest request = server.takeRequest();
      assertEquals("POST", request.method);
      assertEquals("/api/v1/projects/123/subscriptions", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderStartsWith(request, "Content-Type", "application/json");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");

      String expectedJson = "{"
          + "\"endpoint\":\"https://example.com/push/1\","
          + "\"p256dh\":\"BCQVDTlYWdl05lal3lG5SKr3VxTrEWpZErbkxWrzknHrIKFwihDoZpc_2sH6Sh08h-CacUYI-H8gW4jH-uMYZQ4=\","
          + "\"auth\":\"cdKMlhgVeSPzCXZ3V7FtgQ==\","
          + "\"uid\":\"user1\","
          + "\"tags\":[\"tag1\",\"tag2\"]"
          + "}";
      TestSupport.assertJsonEquals(expectedJson, request.body);
    }
  }

  @Test
  void getSubscription() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(200, "{\"id\":50,\"endpoint\":\"https://example.com/1\"}"))) {
      Pushpad pushpad = new Pushpad("TOKEN", null, server.baseUrl());

      SubscriptionGetParams params = new SubscriptionGetParams().setProjectId(123L);
      Subscription subscription = pushpad.subscriptions().get(50L, params);

      assertEquals(50L, subscription.getId());
      assertEquals("https://example.com/1", subscription.getEndpoint());

      RecordedRequest request = server.takeRequest();
      assertEquals("GET", request.method);
      assertEquals("/api/v1/projects/123/subscriptions/50", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");
    }
  }

  @Test
  void updateSubscription() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(200, "{\"id\":50,\"uid\":\"user2\",\"tags\":[\"tag3\"]}"))) {
      Pushpad pushpad = new Pushpad("TOKEN", null, server.baseUrl());

      SubscriptionUpdateParams params = new SubscriptionUpdateParams()
          .setProjectId(123L)
          .setUid("user2")
          .setTags(List.of("tag3"));
      Subscription subscription = pushpad.subscriptions().update(50L, params);

      assertEquals(50L, subscription.getId());
      assertEquals("user2", subscription.getUid());
      assertEquals(List.of("tag3"), subscription.getTags());

      RecordedRequest request = server.takeRequest();
      assertEquals("PATCH", request.method);
      assertEquals("/api/v1/projects/123/subscriptions/50", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderStartsWith(request, "Content-Type", "application/json");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");

      String expectedJson = "{\"uid\":\"user2\",\"tags\":[\"tag3\"]}";
      TestSupport.assertJsonEquals(expectedJson, request.body);
    }
  }

  @Test
  void deleteSubscription() throws Exception {
    try (MockServer server = TestSupport.startServer(new MockResponse(204, null))) {
      Pushpad pushpad = new Pushpad("TOKEN", null, server.baseUrl());

      pushpad.subscriptions().delete(50L, new SubscriptionDeleteParams().setProjectId(123L));

      RecordedRequest request = server.takeRequest();
      assertEquals("DELETE", request.method);
      assertEquals("/api/v1/projects/123/subscriptions/50", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");
    }
  }
}
