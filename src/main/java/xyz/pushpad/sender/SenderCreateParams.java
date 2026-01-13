package xyz.pushpad.sender;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SenderCreateParams {
  @JsonProperty("name")
  private String name;

  @JsonProperty("vapid_private_key")
  private String vapidPrivateKey;

  @JsonProperty("vapid_public_key")
  private String vapidPublicKey;

  public String getName() {
    return name;
  }

  public SenderCreateParams setName(String name) {
    this.name = name;
    return this;
  }

  public String getVapidPrivateKey() {
    return vapidPrivateKey;
  }

  public SenderCreateParams setVapidPrivateKey(String vapidPrivateKey) {
    this.vapidPrivateKey = vapidPrivateKey;
    return this;
  }

  public String getVapidPublicKey() {
    return vapidPublicKey;
  }

  public SenderCreateParams setVapidPublicKey(String vapidPublicKey) {
    this.vapidPublicKey = vapidPublicKey;
    return this;
  }
}
