package xyz.pushpad;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.time.Instant;

public class Notification {
  public Pushpad pushpad;
  public String body;
  public String title;
  public String targetUrl;
  public String iconUrl;
  public String imageUrl;
  public Integer ttl;
  public Boolean requireInteraction;
  public Boolean urgent;
  public String customData;
  public String[] customMetrics;
  public ActionButton[] actionButtons;
  public Boolean starred;
  public Instant sendAt;

  public Notification(Pushpad pushpad, String title, String body, String targetUrl) {
    this.pushpad = pushpad;
    this.title = title;
    this.body = body;
    this.targetUrl = targetUrl;
  }

  public JSONObject broadcast() throws DeliveryException{
    return this.deliver(this.reqBody(null, null));
  }

  public JSONObject broadcast(String[] tags) throws DeliveryException{
    return this.deliver(this.reqBody(null, tags));
  }

  public JSONObject deliverTo(String[] uids, String[] tags) throws DeliveryException {
    if (uids == null) {
      uids = new String[0]; // prevent broadcasting
    }
    return this.deliver(this.reqBody(uids, tags));
  }

  public JSONObject deliverTo(String[] uids) throws DeliveryException {
    return this.deliverTo(uids, null);
  }

  public JSONObject deliverTo(String uid) throws DeliveryException {
    String[] uids = new String[1];
    uids[0] = uid;
    return this.deliverTo(uids);
  }

  private String reqBody(String[] uids, String[] tags) {
    JSONObject body = new JSONObject();
    JSONObject notificationData = new JSONObject();
    notificationData.put("body", this.body);
    notificationData.put("title", this.title);
    notificationData.put("target_url", this.targetUrl);
    if (this.iconUrl != null) {
      notificationData.put("icon_url", this.iconUrl);
    }
    if (this.imageUrl != null) {
      notificationData.put("image_url", this.imageUrl);
    }
    if (this.ttl != null) {
      notificationData.put("ttl", this.ttl);
    }
    if (this.requireInteraction != null) {
      notificationData.put("require_interaction", this.requireInteraction);
    }
    if (this.urgent != null) {
      notificationData.put("urgent", this.urgent);
    }
    if (this.customData != null) {
      notificationData.put("custom_data", this.customData);
    }
    if (this.customMetrics != null) {
      JSONArray jsonCustomMetrics = new JSONArray();
      for (String customMetric : customMetrics) {
        jsonCustomMetrics.add(customMetric);
      }
      notificationData.put("custom_metrics", jsonCustomMetrics);
    }
    if (actionButtons != null) {
      JSONArray jsonActionButtons = new JSONArray();
      for (ActionButton actionButton : actionButtons) {
        jsonActionButtons.add(actionButton.toJson());
      }
      notificationData.put("actions", jsonActionButtons);
    }
    if (this.starred != null) {
      notificationData.put("starred", this.starred);
    }
    if (this.sendAt != null) {
      notificationData.put("send_at", this.sendAt.toString());
    }
    body.put("notification", notificationData);
    if (uids != null) {
      JSONArray jsonUids = new JSONArray();
      for (String uid:uids) {
        jsonUids.add(uid);
      }
      body.put("uids", jsonUids);
    }
    if (tags != null) {
      JSONArray jsonTags = new JSONArray();
      for (String tag:tags) {
        jsonTags.add(tag);
      }
      body.put("tags", jsonTags);
    }
    return body.toString();
  }

  private JSONObject deliver(String reqBody) throws DeliveryException {
    String endpoint = "https://pushpad.xyz/projects/" + pushpad.projectId + "/notifications";
    HttpsURLConnection connection = null;
    int code;
    String responseBody;
    JSONObject json;

    try {
      // Create connection
      URL url = new URL(endpoint);
      connection = (HttpsURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Authorization", "Token token=\"" + pushpad.authToken + "\"");
      connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
      connection.setRequestProperty("Accept", "application/json");
      connection.setRequestProperty("Content-Length", String.valueOf(reqBody.length()));
      connection.setUseCaches(false);
      connection.setDoOutput(true);

      // Send request
      DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
      writer.write(reqBody);
      writer.close();
      wr.close();

      // Get Response  
      InputStream is = connection.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
      StringBuilder response = new StringBuilder(); 
      String line;
      while((line = rd.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      rd.close();
      code = connection.getResponseCode();
      responseBody = response.toString();
    } catch(IOException e) {
      throw new DeliveryException(e.getMessage());
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }

    if (code != 201) {
      throw new DeliveryException("Response " + (new Integer(code)).toString() + ": " + responseBody);
    }

    try {
      JSONParser parser = new JSONParser();
      json = (JSONObject) parser.parse(responseBody); 
    } catch (ParseException e) {
      throw new DeliveryException(e.getMessage());
    }

    return json;
  }
}
