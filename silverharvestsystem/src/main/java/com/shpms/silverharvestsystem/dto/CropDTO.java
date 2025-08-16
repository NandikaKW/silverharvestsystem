package com.shpms.silverharvestsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CropDTO  implements SuperDTO{
    private String cropCode;
    private String commonName;
    private String scientificName;
    private String image;
    private String categoryId;
    private String seasonId;

}
