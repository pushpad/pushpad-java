package xyz.pushpad.sender;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import xyz.pushpad.Pushpad;
import xyz.pushpad.TestSupport;
import xyz.pushpad.TestSupport.MockResponse;
import xyz.pushpad.TestSupport.MockServer;
import xyz.pushpad.TestSupport.RecordedRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SendersTest {
  @AfterEach
  void tearDown() {
    TestSupport.resetPushpad();
  }

  @Test
  void listSenders() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(200, "[{\"id\":1,\"name\":\"Sender\"}]"))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      List<Sender> senders = Senders.list();

      assertEquals(1, senders.size());
      assertEquals(1L, senders.get(0).getId());

      RecordedRequest request = server.takeRequest();
      assertEquals("GET", request.method);
      assertEquals("/api/v1/senders", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");
    }
  }

  @Test
  void createSender() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(201, "{\"id\":12345,\"name\":\"My Sender\"}"))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      SenderCreateParams params = new SenderCreateParams()
          .setName("My Sender")
          .setVapidPrivateKey("-----BEGIN EC PRIVATE KEY----- ...")
          .setVapidPublicKey("-----BEGIN PUBLIC KEY----- ...");

      Sender sender = Senders.create(params);

      assertEquals(12345L, sender.getId());
      assertEquals("My Sender", sender.getName());

      RecordedRequest request = server.takeRequest();
      assertEquals("POST", request.method);
      assertEquals("/api/v1/senders", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderStartsWith(request, "Content-Type", "application/json");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");

      String expectedJson = "{"
          + "\"name\":\"My Sender\","
          + "\"vapid_private_key\":\"-----BEGIN EC PRIVATE KEY----- ...\","
          + "\"vapid_public_key\":\"-----BEGIN PUBLIC KEY----- ...\""
          + "}";
      TestSupport.assertJsonEquals(expectedJson, request.body);
    }
  }

  @Test
  void getSender() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(200, "{\"id\":5,\"name\":\"New Sender\"}"))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      Sender sender = Senders.get(5L);

      assertEquals(5L, sender.getId());
      assertEquals("New Sender", sender.getName());

      RecordedRequest request = server.takeRequest();
      assertEquals("GET", request.method);
      assertEquals("/api/v1/senders/5", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");
    }
  }

  @Test
  void updateSender() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(200, "{\"id\":5,\"name\":\"Updated Sender\"}"))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      SenderUpdateParams params = new SenderUpdateParams().setName("Updated Sender");
      Sender sender = Senders.update(5L, params);

      assertEquals(5L, sender.getId());
      assertEquals("Updated Sender", sender.getName());

      RecordedRequest request = server.takeRequest();
      assertEquals("PATCH", request.method);
      assertEquals("/api/v1/senders/5", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderStartsWith(request, "Content-Type", "application/json");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");

      String expectedJson = "{\"name\":\"Updated Sender\"}";
      TestSupport.assertJsonEquals(expectedJson, request.body);
    }
  }

  @Test
  void deleteSender() throws Exception {
    try (MockServer server = TestSupport.startServer(new MockResponse(204, null))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      Senders.delete(5L);

      RecordedRequest request = server.takeRequest();
      assertEquals("DELETE", request.method);
      assertEquals("/api/v1/senders/5", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");
    }
  }
}
