package xyz.pushpad.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationAction {
  @JsonProperty("title")
  private String title;

  @JsonProperty("target_url")
  private String targetUrl;

  @JsonProperty("icon")
  private String icon;

  @JsonProperty("action")
  private String action;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTargetUrl() {
    return targetUrl;
  }

  public void setTargetUrl(String targetUrl) {
    this.targetUrl = targetUrl;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }
}
