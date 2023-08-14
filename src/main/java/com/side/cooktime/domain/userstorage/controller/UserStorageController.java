package com.side.cooktime.domain.userstorage.controller;

import com.side.cooktime.domain.userstorage.model.dto.request.RequestDeleteDto;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestSaveDto;
import com.side.cooktime.domain.userstorage.model.dto.request.RequestUpdateDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseDeleteDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseGetDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseSaveDto;
import com.side.cooktime.domain.userstorage.model.dto.response.ResponseUpdateDto;
import com.side.cooktime.domain.userstorage.service.UserStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Log4j2
public class UserStorageController {

    private final UserStorageService userStorageService;

    @PostMapping("/storage")
    public ResponseEntity<ResponseSaveDto> save(@RequestBody RequestSaveDto requestDto) {
        ResponseSaveDto responseSaveDto = userStorageService.save(requestDto);
        return new ResponseEntity<>(responseSaveDto, HttpStatus.CREATED);
    }

    @PutMapping("/storage")
    public ResponseEntity<ResponseUpdateDto> update(@RequestBody RequestUpdateDto requestDto) {
        ResponseUpdateDto responseDto = userStorageService.update(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/storage/delete")
    public ResponseEntity<ResponseDeleteDto> delete(@RequestBody RequestDeleteDto requestDto) {
        ResponseDeleteDto responseDto = userStorageService.delete(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/storage")
    public ResponseEntity<ResponseGetDto> get() {
        ResponseGetDto responseDto = userStorageService.get();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //TODO:페이징
//    @GetMapping("/storage")
//    public ResponseEntity<ResponseGetDto> get(Pageable pageable) {
//        ResponseGetDto responseDto = userStorageService.get(pageable);
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
//    }

}
