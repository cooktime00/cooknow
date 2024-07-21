package com.side.cooknow.document;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumDocs {

    Map<String,String> storageType;
    Map<String,String> countType;

}
