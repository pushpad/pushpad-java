package xyz.pushpad.subscription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionCreateParams {
  @JsonIgnore
  private Long projectId;

  @JsonProperty("endpoint")
  private String endpoint;

  @JsonProperty("p256dh")
  private String p256dh;

  @JsonProperty("auth")
  private String auth;

  @JsonProperty("uid")
  private String uid;

  @JsonProperty("tags")
  private List<String> tags;

  public Long getProjectId() {
    return projectId;
  }

  public SubscriptionCreateParams setProjectId(Long projectId) {
    this.projectId = projectId;
    return this;
  }


  public String getEndpoint() {
    return endpoint;
  }

  public SubscriptionCreateParams setEndpoint(String endpoint) {
    this.endpoint = endpoint;
    return this;
  }

  public String getP256dh() {
    return p256dh;
  }

  public SubscriptionCreateParams setP256dh(String p256dh) {
    this.p256dh = p256dh;
    return this;
  }

  public String getAuth() {
    return auth;
  }

  public SubscriptionCreateParams setAuth(String auth) {
    this.auth = auth;
    return this;
  }

  public String getUid() {
    return uid;
  }

  public SubscriptionCreateParams setUid(String uid) {
    this.uid = uid;
    return this;
  }

  public List<String> getTags() {
    return tags;
  }

  public SubscriptionCreateParams setTags(List<String> tags) {
    this.tags = tags;
    return this;
  }
}
