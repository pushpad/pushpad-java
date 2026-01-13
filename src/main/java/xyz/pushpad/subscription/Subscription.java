package xyz.pushpad.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public class Subscription {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("project_id")
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

  @JsonProperty("last_click_at")
  private OffsetDateTime lastClickAt;

  @JsonProperty("created_at")
  private OffsetDateTime createdAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getProjectId() {
    return projectId;
  }

  public void setProjectId(Long projectId) {
    this.projectId = projectId;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public String getP256dh() {
    return p256dh;
  }

  public void setP256dh(String p256dh) {
    this.p256dh = p256dh;
  }

  public String getAuth() {
    return auth;
  }

  public void setAuth(String auth) {
    this.auth = auth;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public OffsetDateTime getLastClickAt() {
    return lastClickAt;
  }

  public void setLastClickAt(OffsetDateTime lastClickAt) {
    this.lastClickAt = lastClickAt;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
