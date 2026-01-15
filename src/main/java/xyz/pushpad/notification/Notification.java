package xyz.pushpad.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public class Notification {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("project_id")
  private Long projectId;

  @JsonProperty("title")
  private String title;

  @JsonProperty("body")
  private String body;

  @JsonProperty("target_url")
  private String targetUrl;

  @JsonProperty("icon_url")
  private String iconUrl;

  @JsonProperty("badge_url")
  private String badgeUrl;

  @JsonProperty("image_url")
  private String imageUrl;

  @JsonProperty("ttl")
  private Long ttl;

  @JsonProperty("require_interaction")
  private Boolean requireInteraction;

  @JsonProperty("silent")
  private Boolean silent;

  @JsonProperty("urgent")
  private Boolean urgent;

  @JsonProperty("custom_data")
  private String customData;

  @JsonProperty("actions")
  private List<NotificationAction> actions;

  @JsonProperty("starred")
  private Boolean starred;

  @JsonProperty("send_at")
  private OffsetDateTime sendAt;

  @JsonProperty("custom_metrics")
  private List<String> customMetrics;

  @JsonProperty("uids")
  private List<String> uids;

  @JsonProperty("tags")
  private List<String> tags;

  @JsonProperty("created_at")
  private OffsetDateTime createdAt;

  @JsonProperty("successfully_sent_count")
  private Long successfullySentCount;

  @JsonProperty("opened_count")
  private Long openedCount;

  @JsonProperty("scheduled_count")
  private Long scheduledCount;

  @JsonProperty("scheduled")
  private Boolean scheduled;

  @JsonProperty("cancelled")
  private Boolean cancelled;

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getTargetUrl() {
    return targetUrl;
  }

  public void setTargetUrl(String targetUrl) {
    this.targetUrl = targetUrl;
  }

  public String getIconUrl() {
    return iconUrl;
  }

  public void setIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
  }

  public String getBadgeUrl() {
    return badgeUrl;
  }

  public void setBadgeUrl(String badgeUrl) {
    this.badgeUrl = badgeUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public Long getTtl() {
    return ttl;
  }

  public void setTtl(Long ttl) {
    this.ttl = ttl;
  }

  public Boolean getRequireInteraction() {
    return requireInteraction;
  }

  public void setRequireInteraction(Boolean requireInteraction) {
    this.requireInteraction = requireInteraction;
  }

  public Boolean getSilent() {
    return silent;
  }

  public void setSilent(Boolean silent) {
    this.silent = silent;
  }

  public Boolean getUrgent() {
    return urgent;
  }

  public void setUrgent(Boolean urgent) {
    this.urgent = urgent;
  }

  public String getCustomData() {
    return customData;
  }

  public void setCustomData(String customData) {
    this.customData = customData;
  }

  public List<NotificationAction> getActions() {
    return actions;
  }

  public void setActions(List<NotificationAction> actions) {
    this.actions = actions;
  }

  public Boolean getStarred() {
    return starred;
  }

  public void setStarred(Boolean starred) {
    this.starred = starred;
  }

  public OffsetDateTime getSendAt() {
    return sendAt;
  }

  public void setSendAt(OffsetDateTime sendAt) {
    this.sendAt = sendAt;
  }

  public List<String> getCustomMetrics() {
    return customMetrics;
  }

  public void setCustomMetrics(List<String> customMetrics) {
    this.customMetrics = customMetrics;
  }

  public List<String> getUids() {
    return uids;
  }

  public void setUids(List<String> uids) {
    this.uids = uids;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Long getSuccessfullySentCount() {
    return successfullySentCount;
  }

  public void setSuccessfullySentCount(Long successfullySentCount) {
    this.successfullySentCount = successfullySentCount;
  }

  public Long getOpenedCount() {
    return openedCount;
  }

  public void setOpenedCount(Long openedCount) {
    this.openedCount = openedCount;
  }

  public Long getScheduledCount() {
    return scheduledCount;
  }

  public void setScheduledCount(Long scheduledCount) {
    this.scheduledCount = scheduledCount;
  }

  public Boolean getScheduled() {
    return scheduled;
  }

  public void setScheduled(Boolean scheduled) {
    this.scheduled = scheduled;
  }

  public Boolean getCancelled() {
    return cancelled;
  }

  public void setCancelled(Boolean cancelled) {
    this.cancelled = cancelled;
  }
}
