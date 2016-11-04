package xyz.pushpad;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;
import java.security.SignatureException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import javax.xml.bind.DatatypeConverter;

public class Pushpad {
  public String authToken;
  public String projectId;

  public Pushpad(String authToken, String projectId) {
    this.authToken = authToken;
    this.projectId = projectId;
  }

  public String signatureFor(String data) {
    SecretKeySpec signingKey = new SecretKeySpec(this.authToken.getBytes(), "HmacSHA1");
    String encoded = null;
    try {
      Mac mac = Mac.getInstance("HmacSHA1");
      mac.init(signingKey);
      byte[] rawHmac = mac.doFinal(data.getBytes());
      encoded = DatatypeConverter.printHexBinary(rawHmac);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) { 
      e.printStackTrace();
    }

    return encoded;
  }

  public String path() {
    return "https://pushpad.xyz/projects/" + this.projectId + "/subscription/edit";
  }

  public String pathFor(String uid) {
    String uidSignature = this.signatureFor(uid);
    return this.path() + "?uid=" + uid + "&uid_signature=" + uidSignature;
  }

  public Notification buildNotification(String title, String body, String targetUrl) {
    return new Notification(this, title, body, targetUrl);
  }
}
