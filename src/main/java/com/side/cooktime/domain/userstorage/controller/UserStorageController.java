package com.side.cooktime.domain.userstorage.controller;

import com.side.cooktime.config.auth.OAuth2UserUtils;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestDeleteDto;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestSaveDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseDeleteDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseSaveDto;
import com.side.cooktime.domain.userstorage.service.UserStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Log4j2
public class UserStorageController {

    private final UserStorageService userStorageService;

    @PostMapping("/storage")
    public ResponseEntity<ResponseSaveDto> save(@RequestBody RequestSaveDto requestDto, Authentication authentication) {
        ResponseSaveDto responseSaveDto = userStorageService.saveAll(OAuth2UserUtils.getEmail(authentication), requestDto);
        return new ResponseEntity<>(responseSaveDto, HttpStatus.OK);
    }

    @DeleteMapping("/storage/delete")                         /* TODO : RequestSaveDto처럼 묶어서 진행 */
    public ResponseEntity<ResponseDeleteDto> delete(@RequestBody List<RequestDeleteDto> requestDtos) {
        ResponseDeleteDto responseDto = userStorageService.delete("test@gmail.com", requestDtos);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //TODO: update작업, getAll작업
}
