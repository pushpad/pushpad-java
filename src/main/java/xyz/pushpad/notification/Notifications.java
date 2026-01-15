package xyz.pushpad.notification;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Objects;
import xyz.pushpad.Pushpad;
import xyz.pushpad.PushpadException;
import xyz.pushpad.QueryParams;

public final class Notifications {
  private final Pushpad pushpad;

  public Notifications(Pushpad pushpad) {
    this.pushpad = Objects.requireNonNull(pushpad, "pushpad");
  }

  public List<Notification> list(NotificationListParams params) throws PushpadException {
    NotificationListParams resolved = params == null ? new NotificationListParams() : params;
    long projectId = pushpad.resolveProjectId(resolved.getProjectId());

    QueryParams query = new QueryParams();
    if (resolved.getPage() != null && resolved.getPage() > 0) {
      query.add("page", String.valueOf(resolved.getPage()));
    }

    return pushpad.request(
        "GET",
        String.format("/projects/%d/notifications", projectId),
        query,
        null,
        new int[]{200},
        new TypeReference<List<Notification>>() {}
    );
  }

  public NotificationCreateResponse create(NotificationCreateParams params) throws PushpadException {
    if (params == null) {
      throw new PushpadException("pushpad: params are required");
    }
    long projectId = pushpad.resolveProjectId(params.getProjectId());

    return pushpad.request(
        "POST",
        String.format("/projects/%d/notifications", projectId),
        null,
        params,
        new int[]{201},
        NotificationCreateResponse.class
    );
  }

  public NotificationCreateResponse send(NotificationCreateParams params) throws PushpadException {
    return create(params);
  }

  public Notification get(long notificationId) throws PushpadException {
    if (notificationId <= 0) {
      throw new PushpadException("pushpad: notification ID is required");
    }

    return pushpad.request(
        "GET",
        String.format("/notifications/%d", notificationId),
        null,
        null,
        new int[]{200},
        Notification.class
    );
  }

  public void cancel(long notificationId) throws PushpadException {
    if (notificationId <= 0) {
      throw new PushpadException("pushpad: notification ID is required");
    }

    pushpad.request(
        "DELETE",
        String.format("/notifications/%d/cancel", notificationId),
        null,
        null,
        new int[]{204},
        (Class<Void>) null
    );
  }
}
