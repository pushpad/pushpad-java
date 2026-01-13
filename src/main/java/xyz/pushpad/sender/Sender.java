package xyz.pushpad.sender;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public class Sender {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("vapid_private_key")
  private String vapidPrivateKey;

  @JsonProperty("vapid_public_key")
  private String vapidPublicKey;

  @JsonProperty("created_at")
  private OffsetDateTime createdAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVapidPrivateKey() {
    return vapidPrivateKey;
  }

  public void setVapidPrivateKey(String vapidPrivateKey) {
    this.vapidPrivateKey = vapidPrivateKey;
  }

  public String getVapidPublicKey() {
    return vapidPublicKey;
  }

  public void setVapidPublicKey(String vapidPublicKey) {
    this.vapidPublicKey = vapidPublicKey;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
