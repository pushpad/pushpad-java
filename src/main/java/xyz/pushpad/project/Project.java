package xyz.pushpad.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public class Project {
  @JsonProperty("id")
  private Long id;

  @JsonProperty("sender_id")
  private Long senderId;

  @JsonProperty("name")
  private String name;

  @JsonProperty("website")
  private String website;

  @JsonProperty("icon_url")
  private String iconUrl;

  @JsonProperty("badge_url")
  private String badgeUrl;

  @JsonProperty("notifications_ttl")
  private Long notificationsTtl;

  @JsonProperty("notifications_require_interaction")
  private Boolean notificationsRequireInteraction;

  @JsonProperty("notifications_silent")
  private Boolean notificationsSilent;

  @JsonProperty("created_at")
  private OffsetDateTime createdAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getSenderId() {
    return senderId;
  }

  public void setSenderId(Long senderId) {
    this.senderId = senderId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
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

  public Long getNotificationsTtl() {
    return notificationsTtl;
  }

  public void setNotificationsTtl(Long notificationsTtl) {
    this.notificationsTtl = notificationsTtl;
  }

  public Boolean getNotificationsRequireInteraction() {
    return notificationsRequireInteraction;
  }

  public void setNotificationsRequireInteraction(Boolean notificationsRequireInteraction) {
    this.notificationsRequireInteraction = notificationsRequireInteraction;
  }

  public Boolean getNotificationsSilent() {
    return notificationsSilent;
  }

  public void setNotificationsSilent(Boolean notificationsSilent) {
    this.notificationsSilent = notificationsSilent;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
