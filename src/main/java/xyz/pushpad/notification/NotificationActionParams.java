package xyz.pushpad.notification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationActionParams {
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

  public NotificationActionParams setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getTargetUrl() {
    return targetUrl;
  }

  public NotificationActionParams setTargetUrl(String targetUrl) {
    this.targetUrl = targetUrl;
    return this;
  }

  public String getIcon() {
    return icon;
  }

  public NotificationActionParams setIcon(String icon) {
    this.icon = icon;
    return this;
  }

  public String getAction() {
    return action;
  }

  public NotificationActionParams setAction(String action) {
    this.action = action;
    return this;
  }
}
