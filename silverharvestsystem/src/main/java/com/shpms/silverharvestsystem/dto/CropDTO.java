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
    private String cropImage;
    private String categoryId;
    private String seasonId;
    private String fieldCode;
    private String logCode;

}
