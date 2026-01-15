package xyz.pushpad.subscription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionUpdateParams {
  @JsonIgnore
  private Long projectId;

  @JsonProperty("uid")
  private String uid;

  @JsonProperty("tags")
  private List<String> tags;

  public Long getProjectId() {
    return projectId;
  }

  public SubscriptionUpdateParams setProjectId(Long projectId) {
    this.projectId = projectId;
    return this;
  }


  public String getUid() {
    return uid;
  }

  public SubscriptionUpdateParams setUid(String uid) {
    this.uid = uid;
    return this;
  }

  public List<String> getTags() {
    return tags;
  }

  public SubscriptionUpdateParams setTags(List<String> tags) {
    this.tags = tags;
    return this;
  }
}
