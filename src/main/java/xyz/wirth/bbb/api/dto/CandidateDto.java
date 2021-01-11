package xyz.wirth.bbb.api.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class CandidateDto {

  private Long id;

  @NotBlank private String firstName;
  @NotBlank private String lastName;

  @Min(value = 18)
  private int age;

  @NotBlank private String location;
  @NotBlank private String job;
  @NotBlank private String info;
  private int episode = -1;
  @NotBlank private String image;
}
