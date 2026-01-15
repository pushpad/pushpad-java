package xyz.pushpad.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectCreateParams {
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

  public Long getSenderId() {
    return senderId;
  }

  public ProjectCreateParams setSenderId(Long senderId) {
    this.senderId = senderId;
    return this;
  }


  public String getName() {
    return name;
  }

  public ProjectCreateParams setName(String name) {
    this.name = name;
    return this;
  }

  public String getWebsite() {
    return website;
  }

  public ProjectCreateParams setWebsite(String website) {
    this.website = website;
    return this;
  }

  public String getIconUrl() {
    return iconUrl;
  }

  public ProjectCreateParams setIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
    return this;
  }

  public String getBadgeUrl() {
    return badgeUrl;
  }

  public ProjectCreateParams setBadgeUrl(String badgeUrl) {
    this.badgeUrl = badgeUrl;
    return this;
  }

  public Long getNotificationsTtl() {
    return notificationsTtl;
  }

  public ProjectCreateParams setNotificationsTtl(Long notificationsTtl) {
    this.notificationsTtl = notificationsTtl;
    return this;
  }


  public Boolean getNotificationsRequireInteraction() {
    return notificationsRequireInteraction;
  }

  public ProjectCreateParams setNotificationsRequireInteraction(Boolean notificationsRequireInteraction) {
    this.notificationsRequireInteraction = notificationsRequireInteraction;
    return this;
  }

  public Boolean getNotificationsSilent() {
    return notificationsSilent;
  }

  public ProjectCreateParams setNotificationsSilent(Boolean notificationsSilent) {
    this.notificationsSilent = notificationsSilent;
    return this;
  }
}
