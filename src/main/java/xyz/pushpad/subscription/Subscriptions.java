package xyz.pushpad.subscription;

import com.fasterxml.jackson.core.type.TypeReference;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import xyz.pushpad.Pushpad;
import xyz.pushpad.PushpadException;
import xyz.pushpad.QueryParams;

public final class Subscriptions {
  private Subscriptions() {
  }

  public static List<Subscription> list(SubscriptionListParams params) throws PushpadException {
    SubscriptionListParams resolved = params == null ? new SubscriptionListParams() : params;
    long projectId = Pushpad.resolveProjectId(resolved.getProjectId());

    QueryParams query = new QueryParams();
    if (resolved.getPage() != null && resolved.getPage() > 0) {
      query.add("page", String.valueOf(resolved.getPage()));
    }
    if (resolved.getPerPage() != null && resolved.getPerPage() > 0) {
      query.add("per_page", String.valueOf(resolved.getPerPage()));
    }
    if (resolved.getUids() != null) {
      query.addAll("uids[]", resolved.getUids());
    }
    if (resolved.getTags() != null) {
      query.addAll("tags[]", resolved.getTags());
    }

    return Pushpad.request(
        "GET",
        String.format("/projects/%d/subscriptions", projectId),
        query,
        null,
        new int[]{200},
        new TypeReference<List<Subscription>>() {}
    );
  }

  public static long count(SubscriptionCountParams params) throws PushpadException {
    SubscriptionCountParams resolved = params == null ? new SubscriptionCountParams() : params;
    long projectId = Pushpad.resolveProjectId(resolved.getProjectId());

    QueryParams query = new QueryParams();
    if (resolved.getUids() != null) {
      query.addAll("uids[]", resolved.getUids());
    }
    if (resolved.getTags() != null) {
      query.addAll("tags[]", resolved.getTags());
    }

    HttpResponse<String> response = Pushpad.requestRaw(
        "HEAD",
        String.format("/projects/%d/subscriptions", projectId),
        query,
        null,
        new int[]{200}
    );

    Optional<String> header = response.headers().firstValue("X-Total-Count");
    if (header.isEmpty()) {
      return 0;
    }
    try {
      return Long.parseLong(header.get());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public static Subscription create(SubscriptionCreateParams params) throws PushpadException {
    if (params == null) {
      throw new PushpadException("pushpad: params are required");
    }
    long projectId = Pushpad.resolveProjectId(params.getProjectId());

    return Pushpad.request(
        "POST",
        String.format("/projects/%d/subscriptions", projectId),
        null,
        params,
        new int[]{201},
        Subscription.class
    );
  }

  public static Subscription get(long subscriptionId) throws PushpadException {
    return get(subscriptionId, null);
  }

  public static Subscription get(long subscriptionId, SubscriptionGetParams params) throws PushpadException {
    if (subscriptionId <= 0) {
      throw new PushpadException("pushpad: subscription ID is required");
    }
    SubscriptionGetParams resolved = params == null ? new SubscriptionGetParams() : params;
    long projectId = Pushpad.resolveProjectId(resolved.getProjectId());

    return Pushpad.request(
        "GET",
        String.format("/projects/%d/subscriptions/%d", projectId, subscriptionId),
        null,
        null,
        new int[]{200},
        Subscription.class
    );
  }

  public static Subscription update(long subscriptionId, SubscriptionUpdateParams params) throws PushpadException {
    if (params == null) {
      throw new PushpadException("pushpad: params are required");
    }
    if (subscriptionId <= 0) {
      throw new PushpadException("pushpad: subscription ID is required");
    }
    long projectId = Pushpad.resolveProjectId(params.getProjectId());

    return Pushpad.request(
        "PATCH",
        String.format("/projects/%d/subscriptions/%d", projectId, subscriptionId),
        null,
        params,
        new int[]{200},
        Subscription.class
    );
  }

  public static void delete(long subscriptionId) throws PushpadException {
    delete(subscriptionId, null);
  }

  public static void delete(long subscriptionId, SubscriptionDeleteParams params) throws PushpadException {
    if (subscriptionId <= 0) {
      throw new PushpadException("pushpad: subscription ID is required");
    }
    SubscriptionDeleteParams resolved = params == null ? new SubscriptionDeleteParams() : params;
    long projectId = Pushpad.resolveProjectId(resolved.getProjectId());

    Pushpad.request(
        "DELETE",
        String.format("/projects/%d/subscriptions/%d", projectId, subscriptionId),
        null,
        null,
        new int[]{204},
        (Class<Void>) null
    );
  }
}
