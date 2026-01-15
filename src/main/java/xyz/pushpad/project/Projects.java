package xyz.pushpad.project;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Objects;
import xyz.pushpad.Pushpad;
import xyz.pushpad.PushpadException;

public final class Projects {
  private final Pushpad pushpad;

  public Projects(Pushpad pushpad) {
    this.pushpad = Objects.requireNonNull(pushpad, "pushpad");
  }

  public List<Project> list() throws PushpadException {
    return pushpad.request(
        "GET",
        "/projects",
        null,
        null,
        new int[]{200},
        new TypeReference<List<Project>>() {}
    );
  }

  public Project create(ProjectCreateParams params) throws PushpadException {
    if (params == null) {
      throw new PushpadException("pushpad: params are required");
    }

    return pushpad.request(
        "POST",
        "/projects",
        null,
        params,
        new int[]{201},
        Project.class
    );
  }

  public Project get(long projectId) throws PushpadException {
    if (projectId <= 0) {
      throw new PushpadException("pushpad: project ID is required");
    }

    return pushpad.request(
        "GET",
        String.format("/projects/%d", projectId),
        null,
        null,
        new int[]{200},
        Project.class
    );
  }

  public Project update(long projectId, ProjectUpdateParams params) throws PushpadException {
    if (params == null) {
      throw new PushpadException("pushpad: params are required");
    }
    if (projectId <= 0) {
      throw new PushpadException("pushpad: project ID is required");
    }

    return pushpad.request(
        "PATCH",
        String.format("/projects/%d", projectId),
        null,
        params,
        new int[]{200},
        Project.class
    );
  }

  public void delete(long projectId) throws PushpadException {
    if (projectId <= 0) {
      throw new PushpadException("pushpad: project ID is required");
    }

    pushpad.request(
        "DELETE",
        String.format("/projects/%d", projectId),
        null,
        null,
        new int[]{202},
        (Class<Void>) null
    );
  }
}
