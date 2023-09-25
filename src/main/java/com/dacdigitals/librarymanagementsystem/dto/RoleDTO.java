package com.dacdigitals.librarymanagementsystem.dto;

import com.dacdigitals.librarymanagementsystem.entity.constant.ROLE;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class RoleDTO {
    private ROLE role;
}
