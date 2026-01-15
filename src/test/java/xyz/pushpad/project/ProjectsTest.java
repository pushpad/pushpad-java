package xyz.pushpad.project;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import xyz.pushpad.Pushpad;
import xyz.pushpad.TestSupport;
import xyz.pushpad.TestSupport.MockResponse;
import xyz.pushpad.TestSupport.MockServer;
import xyz.pushpad.TestSupport.RecordedRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectsTest {
  @AfterEach
  void tearDown() {
    TestSupport.resetPushpad();
  }

  @Test
  void listProjects() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(200, "[{\"id\":1,\"name\":\"Main\"}]"))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      List<Project> projects = Projects.list();

      assertEquals(1, projects.size());
      assertEquals(1L, projects.get(0).getId());

      RecordedRequest request = server.takeRequest();
      assertEquals("GET", request.method);
      assertEquals("/api/v1/projects", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");
    }
  }

  @Test
  void createProject() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(201, "{\"id\":12345,\"name\":\"My Project\"}"))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      ProjectCreateParams params = new ProjectCreateParams()
          .setSenderId(98765L)
          .setName("My Project")
          .setWebsite("https://example.com")
          .setIconUrl("https://example.com/icon.png")
          .setBadgeUrl("https://example.com/badge.png")
          .setNotificationsTtl(604800L)
          .setNotificationsRequireInteraction(false)
          .setNotificationsSilent(false);

      Project project = Projects.create(params);

      assertEquals(12345L, project.getId());

      RecordedRequest request = server.takeRequest();
      assertEquals("POST", request.method);
      assertEquals("/api/v1/projects", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderStartsWith(request, "Content-Type", "application/json");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");

      String expectedJson = "{"
          + "\"sender_id\":98765,"
          + "\"name\":\"My Project\","
          + "\"website\":\"https://example.com\","
          + "\"icon_url\":\"https://example.com/icon.png\","
          + "\"badge_url\":\"https://example.com/badge.png\","
          + "\"notifications_ttl\":604800,"
          + "\"notifications_require_interaction\":false,"
          + "\"notifications_silent\":false"
          + "}";
      TestSupport.assertJsonEquals(expectedJson, request.body);
    }
  }

  @Test
  void getProject() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(200, "{\"id\":2,\"name\":\"New Project\"}"))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      Project project = Projects.get(2L);

      assertEquals(2L, project.getId());
      assertEquals("New Project", project.getName());

      RecordedRequest request = server.takeRequest();
      assertEquals("GET", request.method);
      assertEquals("/api/v1/projects/2", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");
    }
  }

  @Test
  void updateProject() throws Exception {
    try (MockServer server = TestSupport.startServer(
        new MockResponse(200, "{\"id\":2,\"name\":\"Updated Project\"}"))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      ProjectUpdateParams params = new ProjectUpdateParams()
          .setName("Updated Project")
          .setWebsite("https://example.com/updated");
      Project project = Projects.update(2L, params);

      assertEquals(2L, project.getId());
      assertEquals("Updated Project", project.getName());

      RecordedRequest request = server.takeRequest();
      assertEquals("PATCH", request.method);
      assertEquals("/api/v1/projects/2", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderStartsWith(request, "Content-Type", "application/json");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");

      String expectedJson = "{"
          + "\"name\":\"Updated Project\","
          + "\"website\":\"https://example.com/updated\""
          + "}";
      TestSupport.assertJsonEquals(expectedJson, request.body);
    }
  }

  @Test
  void deleteProject() throws Exception {
    try (MockServer server = TestSupport.startServer(new MockResponse(202, null))) {
      Pushpad.setBaseUrl(server.baseUrl());
      Pushpad.setAuthToken("TOKEN");

      Projects.delete(2L);

      RecordedRequest request = server.takeRequest();
      assertEquals("DELETE", request.method);
      assertEquals("/api/v1/projects/2", request.path);
      TestSupport.assertHeaderEquals(request, "Authorization", "Bearer TOKEN");
      TestSupport.assertHeaderEquals(request, "Accept", "application/json");
    }
  }
}
