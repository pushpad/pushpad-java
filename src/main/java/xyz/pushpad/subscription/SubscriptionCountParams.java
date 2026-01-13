package xyz.pushpad.subscription;

import java.util.List;

public class SubscriptionCountParams {
  private Long projectId;
  private List<String> uids;
  private List<String> tags;

  public Long getProjectId() {
    return projectId;
  }

  public SubscriptionCountParams setProjectId(Long projectId) {
    this.projectId = projectId;
    return this;
  }


  public List<String> getUids() {
    return uids;
  }

  public SubscriptionCountParams setUids(List<String> uids) {
    this.uids = uids;
    return this;
  }

  public List<String> getTags() {
    return tags;
  }

  public SubscriptionCountParams setTags(List<String> tags) {
    this.tags = tags;
    return this;
  }
}
