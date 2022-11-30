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
    SecretKeySpec signingKey = new SecretKeySpec(this.authToken.getBytes(), "HmacSHA256");
    String encoded = null;
    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(signingKey);
      byte[] rawHmac = mac.doFinal(data.getBytes());
      encoded = DatatypeConverter.printHexBinary(rawHmac).toLowerCase();
    } catch (NoSuchAlgorithmException | InvalidKeyException e) { 
      e.printStackTrace();
    }

    return encoded;
  }

  public Notification buildNotification(String title, String body, String targetUrl) {
    return new Notification(this, title, body, targetUrl);
  }
}
