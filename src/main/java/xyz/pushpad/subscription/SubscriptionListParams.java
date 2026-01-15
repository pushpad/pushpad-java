package xyz.pushpad.subscription;

import java.util.List;

public class SubscriptionListParams {
  private Long projectId;
  private Long page;
  private Long perPage;
  private List<String> uids;
  private List<String> tags;

  public Long getProjectId() {
    return projectId;
  }

  public SubscriptionListParams setProjectId(Long projectId) {
    this.projectId = projectId;
    return this;
  }


  public Long getPage() {
    return page;
  }

  public SubscriptionListParams setPage(Long page) {
    this.page = page;
    return this;
  }


  public Long getPerPage() {
    return perPage;
  }

  public SubscriptionListParams setPerPage(Long perPage) {
    this.perPage = perPage;
    return this;
  }


  public List<String> getUids() {
    return uids;
  }

  public SubscriptionListParams setUids(List<String> uids) {
    this.uids = uids;
    return this;
  }

  public List<String> getTags() {
    return tags;
  }

  public SubscriptionListParams setTags(List<String> tags) {
    this.tags = tags;
    return this;
  }
}
