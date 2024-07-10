package com.side.cooknow;

import com.side.cooknow.global.model.EnumType;
import com.side.cooknow.document.ApiResponseDto;
import com.side.cooknow.document.EnumDocs;
import com.side.cooknow.domain.ingredient.model.CountType;
import com.side.cooknow.domain.ingredient.model.StorageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class CommonDocController {

    @GetMapping("/enums")
    public ApiResponseDto<EnumDocs> findEnums() {

        Map<String, String> storageType = getDocs(StorageType.values());
        Map<String, String> countType = getDocs(CountType.values());

        return ApiResponseDto.of(EnumDocs.builder()
                .countType(countType)
                .storageType(storageType)
                .build());
    }

    @PostMapping("/error")
    public void errorSample(@RequestBody SampleRequest dto){

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SampleRequest {

//        @NotEmpty
        private String name;

//        @Email
        private String email;
    }

    private Map<String, String> getDocs(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
                .collect(Collectors.toMap(EnumType::getName, EnumType::getDescription));
    }
}
