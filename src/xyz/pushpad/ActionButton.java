package xyz.pushpad;

import org.json.simple.JSONObject;

public class ActionButton {
  public String title;
  public String targetUrl;
  public String icon;
  public String action;

  public ActionButton(String title) {
    this.title = title;
  }

  public JSONObject toJson() {
    JSONObject result = new JSONObject();
    result.put("title", title);
    if (targetUrl != null) {
      result.put("target_url", targetUrl);
    }
    if (icon != null) {
      result.put("icon", icon);
    }
    if (action != null) {
      result.put("action", action);
    }
    return result;
  }
}
