package xyz.pushpad.notification;

public class NotificationListParams {
  private Long projectId;
  private Long page;

  public Long getProjectId() {
    return projectId;
  }

  public NotificationListParams setProjectId(Long projectId) {
    this.projectId = projectId;
    return this;
  }


  public Long getPage() {
    return page;
  }

  public NotificationListParams setPage(Long page) {
    this.page = page;
    return this;
  }

}
