package xyz.pushpad.project;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import xyz.pushpad.Pushpad;
import xyz.pushpad.PushpadException;

public final class Projects {
  private Projects() {
  }

  public static List<Project> list() throws PushpadException {
    return Pushpad.request(
        "GET",
        "/projects",
        null,
        null,
        new int[]{200},
        new TypeReference<List<Project>>() {}
    );
  }

  public static Project create(ProjectCreateParams params) throws PushpadException {
    if (params == null) {
      throw new PushpadException("pushpad: params are required");
    }

    return Pushpad.request(
        "POST",
        "/projects",
        null,
        params,
        new int[]{201},
        Project.class
    );
  }

  public static Project get(long projectId) throws PushpadException {
    if (projectId <= 0) {
      throw new PushpadException("pushpad: project ID is required");
    }

    return Pushpad.request(
        "GET",
        String.format("/projects/%d", projectId),
        null,
        null,
        new int[]{200},
        Project.class
    );
  }

  public static Project update(long projectId, ProjectUpdateParams params) throws PushpadException {
    if (params == null) {
      throw new PushpadException("pushpad: params are required");
    }
    if (projectId <= 0) {
      throw new PushpadException("pushpad: project ID is required");
    }

    return Pushpad.request(
        "PATCH",
        String.format("/projects/%d", projectId),
        null,
        params,
        new int[]{200},
        Project.class
    );
  }

  public static void delete(long projectId) throws PushpadException {
    if (projectId <= 0) {
      throw new PushpadException("pushpad: project ID is required");
    }

    Pushpad.request(
        "DELETE",
        String.format("/projects/%d", projectId),
        null,
        null,
        new int[]{202},
        (Class<Void>) null
    );
  }
}
