package xyz.pushpad.sender;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SenderUpdateParams {
  @JsonProperty("name")
  private String name;

  public String getName() {
    return name;
  }

  public SenderUpdateParams setName(String name) {
    this.name = name;
    return this;
  }
}
