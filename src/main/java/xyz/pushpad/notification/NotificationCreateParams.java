package xyz.pushpad.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationCreateParams {
  @JsonIgnore
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
  private List<NotificationActionParams> actions;

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

  public Long getProjectId() {
    return projectId;
  }

  public NotificationCreateParams setProjectId(Long projectId) {
    this.projectId = projectId;
    return this;
  }


  public String getTitle() {
    return title;
  }

  public NotificationCreateParams setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getBody() {
    return body;
  }

  public NotificationCreateParams setBody(String body) {
    this.body = body;
    return this;
  }

  public String getTargetUrl() {
    return targetUrl;
  }

  public NotificationCreateParams setTargetUrl(String targetUrl) {
    this.targetUrl = targetUrl;
    return this;
  }

  public String getIconUrl() {
    return iconUrl;
  }

  public NotificationCreateParams setIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
    return this;
  }

  public String getBadgeUrl() {
    return badgeUrl;
  }

  public NotificationCreateParams setBadgeUrl(String badgeUrl) {
    this.badgeUrl = badgeUrl;
    return this;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public NotificationCreateParams setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public Long getTtl() {
    return ttl;
  }

  public NotificationCreateParams setTtl(Long ttl) {
    this.ttl = ttl;
    return this;
  }


  public Boolean getRequireInteraction() {
    return requireInteraction;
  }

  public NotificationCreateParams setRequireInteraction(Boolean requireInteraction) {
    this.requireInteraction = requireInteraction;
    return this;
  }

  public Boolean getSilent() {
    return silent;
  }

  public NotificationCreateParams setSilent(Boolean silent) {
    this.silent = silent;
    return this;
  }

  public Boolean getUrgent() {
    return urgent;
  }

  public NotificationCreateParams setUrgent(Boolean urgent) {
    this.urgent = urgent;
    return this;
  }

  public String getCustomData() {
    return customData;
  }

  public NotificationCreateParams setCustomData(String customData) {
    this.customData = customData;
    return this;
  }

  public List<NotificationActionParams> getActions() {
    return actions;
  }

  public NotificationCreateParams setActions(List<NotificationActionParams> actions) {
    this.actions = actions;
    return this;
  }

  public Boolean getStarred() {
    return starred;
  }

  public NotificationCreateParams setStarred(Boolean starred) {
    this.starred = starred;
    return this;
  }

  public OffsetDateTime getSendAt() {
    return sendAt;
  }

  public NotificationCreateParams setSendAt(OffsetDateTime sendAt) {
    this.sendAt = sendAt;
    return this;
  }

  public List<String> getCustomMetrics() {
    return customMetrics;
  }

  public NotificationCreateParams setCustomMetrics(List<String> customMetrics) {
    this.customMetrics = customMetrics;
    return this;
  }

  public List<String> getUids() {
    return uids;
  }

  public NotificationCreateParams setUids(List<String> uids) {
    this.uids = uids;
    return this;
  }

  public List<String> getTags() {
    return tags;
  }

  public NotificationCreateParams setTags(List<String> tags) {
    this.tags = tags;
    return this;
  }
}
