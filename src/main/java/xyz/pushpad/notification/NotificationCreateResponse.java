package xyz.pushpad.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public class NotificationCreateResponse {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("scheduled")
  private Long scheduled;

  @JsonProperty("uids")
  private List<String> uids;

  @JsonProperty("send_at")
  private OffsetDateTime sendAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getScheduled() {
    return scheduled;
  }

  public void setScheduled(Long scheduled) {
    this.scheduled = scheduled;
  }

  public List<String> getUids() {
    return uids;
  }

  public void setUids(List<String> uids) {
    this.uids = uids;
  }

  public OffsetDateTime getSendAt() {
    return sendAt;
  }

  public void setSendAt(OffsetDateTime sendAt) {
    this.sendAt = sendAt;
  }
}
