package xyz.pushpad.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectUpdateParams {
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

  public String getName() {
    return name;
  }

  public ProjectUpdateParams setName(String name) {
    this.name = name;
    return this;
  }

  public String getWebsite() {
    return website;
  }

  public ProjectUpdateParams setWebsite(String website) {
    this.website = website;
    return this;
  }

  public String getIconUrl() {
    return iconUrl;
  }

  public ProjectUpdateParams setIconUrl(String iconUrl) {
    this.iconUrl = iconUrl;
    return this;
  }

  public String getBadgeUrl() {
    return badgeUrl;
  }

  public ProjectUpdateParams setBadgeUrl(String badgeUrl) {
    this.badgeUrl = badgeUrl;
    return this;
  }

  public Long getNotificationsTtl() {
    return notificationsTtl;
  }

  public ProjectUpdateParams setNotificationsTtl(Long notificationsTtl) {
    this.notificationsTtl = notificationsTtl;
    return this;
  }


  public Boolean getNotificationsRequireInteraction() {
    return notificationsRequireInteraction;
  }

  public ProjectUpdateParams setNotificationsRequireInteraction(Boolean notificationsRequireInteraction) {
    this.notificationsRequireInteraction = notificationsRequireInteraction;
    return this;
  }

  public Boolean getNotificationsSilent() {
    return notificationsSilent;
  }

  public ProjectUpdateParams setNotificationsSilent(Boolean notificationsSilent) {
    this.notificationsSilent = notificationsSilent;
    return this;
  }
}
