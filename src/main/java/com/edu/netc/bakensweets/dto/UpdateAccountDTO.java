package com.edu.netc.bakensweets.dto;

import com.edu.netc.bakensweets.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountDTO {
    private String firstName;
    private String lastName;
    /*@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Etc/UTC")*/
    private String birthDate;
    private String gender;
    private String imgUrl;
}
