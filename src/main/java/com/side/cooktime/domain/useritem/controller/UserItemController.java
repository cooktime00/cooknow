package com.side.cooktime.domain.useritem.controller;

import com.side.cooktime.domain.useritem.model.UserItem;
import com.side.cooktime.domain.useritem.model.UserItems;
import com.side.cooktime.domain.useritem.model.dto.request.RequestDeleteDto;
import com.side.cooktime.domain.useritem.model.dto.request.RequestSaveDto;
import com.side.cooktime.domain.useritem.model.dto.request.RequestUpdateDto;
import com.side.cooktime.domain.useritem.model.dto.response.*;
import com.side.cooktime.domain.useritem.service.UserItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Log4j2
public class UserItemController {

    private final UserItemService userItemService;

    @PostMapping("/storage")
    public ResponseEntity<ResponseSaveDto> save(@RequestBody RequestSaveDto requestDto) {
        List<UserItem> userItems = userItemService.save(requestDto);
        return new ResponseEntity<>(new ResponseSaveDto(userItems.size()), HttpStatus.CREATED);
    }

    @PutMapping("/storage")
    public ResponseEntity<ResponseUpdateDto> update(@RequestBody RequestUpdateDto requestDto) {
        UserItems userItems = userItemService.update(requestDto);
        return new ResponseEntity<>(new ResponseUpdateDto(userItems.getSize()), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/storages")
    public ResponseEntity<List<ResponseFindStorageDto>> findStorages(@RequestParam("type") String type){
        UserItems userItems = userItemService.findStorages(type);
        return new ResponseEntity<>(userItems.toDtos(ResponseFindStorageDto::new), HttpStatus.OK);
    }

    @DeleteMapping("/storage/delete")
    public ResponseEntity<ResponseDeleteDto> delete(@RequestBody RequestDeleteDto requestDto) {
        ResponseDeleteDto responseDto = userItemService.delete(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/storage")
    public ResponseEntity<List<ResponseGetDto>> get() {
        UserItems userItems = userItemService.get();
        return new ResponseEntity<>(userItems.toDtos(ResponseGetDto::new), HttpStatus.OK);
    }

    @GetMapping("/storage/near-expiry")
    public ResponseEntity<List<ResponseGetNearExpiryDto>> getNearExpiry(){
     List<ResponseGetNearExpiryDto> responseDtoList = userItemService.getNearExpiry();
     return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }
}
