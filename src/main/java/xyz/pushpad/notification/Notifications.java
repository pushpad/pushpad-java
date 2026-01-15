package xyz.pushpad.notification;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import xyz.pushpad.Pushpad;
import xyz.pushpad.PushpadException;
import xyz.pushpad.QueryParams;

public final class Notifications {
  private Notifications() {
  }

  public static List<Notification> list(NotificationListParams params) throws PushpadException {
    NotificationListParams resolved = params == null ? new NotificationListParams() : params;
    long projectId = Pushpad.resolveProjectId(resolved.getProjectId());

    QueryParams query = new QueryParams();
    if (resolved.getPage() != null && resolved.getPage() > 0) {
      query.add("page", String.valueOf(resolved.getPage()));
    }

    return Pushpad.request(
        "GET",
        String.format("/projects/%d/notifications", projectId),
        query,
        null,
        new int[]{200},
        new TypeReference<List<Notification>>() {}
    );
  }

  public static NotificationCreateResponse create(NotificationCreateParams params) throws PushpadException {
    if (params == null) {
      throw new PushpadException("pushpad: params are required");
    }
    long projectId = Pushpad.resolveProjectId(params.getProjectId());

    return Pushpad.request(
        "POST",
        String.format("/projects/%d/notifications", projectId),
        null,
        params,
        new int[]{201},
        NotificationCreateResponse.class
    );
  }

  public static NotificationCreateResponse send(NotificationCreateParams params) throws PushpadException {
    return create(params);
  }

  public static Notification get(long notificationId) throws PushpadException {
    if (notificationId <= 0) {
      throw new PushpadException("pushpad: notification ID is required");
    }

    return Pushpad.request(
        "GET",
        String.format("/notifications/%d", notificationId),
        null,
        null,
        new int[]{200},
        Notification.class
    );
  }

  public static void cancel(long notificationId) throws PushpadException {
    if (notificationId <= 0) {
      throw new PushpadException("pushpad: notification ID is required");
    }

    Pushpad.request(
        "DELETE",
        String.format("/notifications/%d/cancel", notificationId),
        null,
        null,
        new int[]{204},
        (Class<Void>) null
    );
  }
}
