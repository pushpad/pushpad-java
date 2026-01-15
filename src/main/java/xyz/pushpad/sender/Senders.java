package xyz.pushpad.sender;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Objects;
import xyz.pushpad.Pushpad;
import xyz.pushpad.PushpadException;

public final class Senders {
  private final Pushpad pushpad;

  public Senders(Pushpad pushpad) {
    this.pushpad = Objects.requireNonNull(pushpad, "pushpad");
  }

  public List<Sender> list() throws PushpadException {
    return pushpad.request(
        "GET",
        "/senders",
        null,
        null,
        new int[]{200},
        new TypeReference<List<Sender>>() {}
    );
  }

  public Sender create(SenderCreateParams params) throws PushpadException {
    if (params == null) {
      throw new PushpadException("pushpad: params are required");
    }

    return pushpad.request(
        "POST",
        "/senders",
        null,
        params,
        new int[]{201},
        Sender.class
    );
  }

  public Sender get(long senderId) throws PushpadException {
    if (senderId <= 0) {
      throw new PushpadException("pushpad: sender ID is required");
    }

    return pushpad.request(
        "GET",
        String.format("/senders/%d", senderId),
        null,
        null,
        new int[]{200},
        Sender.class
    );
  }

  public Sender update(long senderId, SenderUpdateParams params) throws PushpadException {
    if (params == null) {
      throw new PushpadException("pushpad: params are required");
    }
    if (senderId <= 0) {
      throw new PushpadException("pushpad: sender ID is required");
    }

    return pushpad.request(
        "PATCH",
        String.format("/senders/%d", senderId),
        null,
        params,
        new int[]{200},
        Sender.class
    );
  }

  public void delete(long senderId) throws PushpadException {
    if (senderId <= 0) {
      throw new PushpadException("pushpad: sender ID is required");
    }

    pushpad.request(
        "DELETE",
        String.format("/senders/%d", senderId),
        null,
        null,
        new int[]{204},
        (Class<Void>) null
    );
  }
}
