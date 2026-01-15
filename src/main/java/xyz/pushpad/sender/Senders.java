package xyz.pushpad.sender;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import xyz.pushpad.Pushpad;
import xyz.pushpad.PushpadException;

public final class Senders {
  private Senders() {
  }

  public static List<Sender> list() throws PushpadException {
    return Pushpad.request(
        "GET",
        "/senders",
        null,
        null,
        new int[]{200},
        new TypeReference<List<Sender>>() {}
    );
  }

  public static Sender create(SenderCreateParams params) throws PushpadException {
    if (params == null) {
      throw new PushpadException("pushpad: params are required");
    }

    return Pushpad.request(
        "POST",
        "/senders",
        null,
        params,
        new int[]{201},
        Sender.class
    );
  }

  public static Sender get(long senderId) throws PushpadException {
    if (senderId <= 0) {
      throw new PushpadException("pushpad: sender ID is required");
    }

    return Pushpad.request(
        "GET",
        String.format("/senders/%d", senderId),
        null,
        null,
        new int[]{200},
        Sender.class
    );
  }

  public static Sender update(long senderId, SenderUpdateParams params) throws PushpadException {
    if (params == null) {
      throw new PushpadException("pushpad: params are required");
    }
    if (senderId <= 0) {
      throw new PushpadException("pushpad: sender ID is required");
    }

    return Pushpad.request(
        "PATCH",
        String.format("/senders/%d", senderId),
        null,
        params,
        new int[]{200},
        Sender.class
    );
  }

  public static void delete(long senderId) throws PushpadException {
    if (senderId <= 0) {
      throw new PushpadException("pushpad: sender ID is required");
    }

    Pushpad.request(
        "DELETE",
        String.format("/senders/%d", senderId),
        null,
        null,
        new int[]{204},
        (Class<Void>) null
    );
  }
}
