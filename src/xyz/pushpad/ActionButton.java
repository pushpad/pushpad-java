package xyz.pushpad;

import org.json.simple.JSONObject;

/**
 * <p>You can include action buttons inside your notifications. In this way, a notification can have multiple links or actions.</p>
 * <img src="https://pushpad.xyz/assets/action_buttons-a1397c39c36f4ea02a6d9d00132fe3b1be697c138f0db9b11a8310ab558a1719.png" alt="Action buttons">
 * <p>When you send a notification you can add any number of action buttons. Each action button has the following fields:</p>
 * <ul>
 *  <li><b>Title</b>: the label displayed to the user for the action button.</li>
 *  <li><b>Link</b> (optional): the page that opens up when the user clicks the button. If you leave it blank, the main link of the notification will be used.</li>
 *  <li><b>Icon</b> (optional): the URL of a small icon for the action button. An alternative to icons is to include emoticons or other symbols inside the title.</li>
 *  <li><b>Action</b> (optional): an ID for the action that can be used to trigger a Javascript callback. If a Javascript action with that ID is defined, it will be executed. Otherwise the action ID will be ignored and the link will be used instead.</li>
 * </ul> 
 * @see <a href="https://pushpad.xyz/docs/action_buttons">https://pushpad.xyz/docs/action_buttons</a>
 */
public class ActionButton {

  /** The title. */
  private final String title;

  /** The link. */
  private String targetUrl;

  /** The icon. */
  private String icon;

  /** The action. */
  private String action;

  /**
   * Instantiates a new action button.
   *
   * @param title
   *          the title
   * @throws IllegalArgumentException
   *           if the new title exceeds the maximum length of 20 characters
   */
  public ActionButton(String title) throws IllegalArgumentException {
    if (title.length()>20){
      throw new IllegalArgumentException("Maximum length of the title is 20 characters");
    }
    this.title = title;
  }

  /**
   * Sets the target url.
   *
   * @param targetUrl          the new target url
   * @return a reference to this object.
   */
  public ActionButton setTargetUrl(String targetUrl) {
    this.targetUrl = targetUrl;
    return this;
  }

  /**
   * Sets the icon url.
   *
   * @param icon
   *          the new icon url
   * @return a reference to this object.
   */
  public ActionButton setIcon(String icon) {
    this.icon = icon;
    return this;
  }

  /**
   * Sets the action.
   *
   * @param action
   *          the new action
   * @return a reference to this object.
   * @throws IllegalArgumentException
   *           if the new action exceeds the maximum length of 50 characters
   */
  public ActionButton setAction(String action) throws IllegalArgumentException {
    if (action.length() > 50) {
      throw new IllegalArgumentException("Maximum length of the action is 50 characters");
    }
    this.action = action;
    return this;
  }

  /**
   * Converts the ActionButton to a JSONObject.
   *
   * @return the JSONObject
   */
  @SuppressWarnings("unchecked")
  public JSONObject toJson() {
    JSONObject result = new JSONObject();
    result.put("title", title);
    result.put("target_url", targetUrl);
    result.put("icon", icon);
    result.put("action", action);
    return result;
  }
}
