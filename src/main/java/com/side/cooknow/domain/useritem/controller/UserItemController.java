package com.side.cooknow.domain.useritem.controller;

import com.side.cooknow.domain.useritem.model.UserItem;
import com.side.cooknow.domain.useritem.model.UserItems;
import com.side.cooknow.domain.useritem.model.dto.request.RequestDeleteDto;
import com.side.cooknow.domain.useritem.model.dto.request.RequestFindDto;
import com.side.cooknow.domain.useritem.model.dto.request.RequestSaveDto;
import com.side.cooknow.domain.useritem.model.dto.request.RequestUpdateDto;
import com.side.cooknow.domain.useritem.model.dto.response.*;
import com.side.cooknow.domain.useritem.service.UserItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Transactional
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Log4j2
public class UserItemController {

    private final UserItemService userItemService;

    @PostMapping("/useritems")
    public ResponseEntity<ResponseSaveDto> save(@RequestBody RequestSaveDto requestDto) {
        List<UserItem> userItems = userItemService.save(requestDto);
        return new ResponseEntity<>(new ResponseSaveDto(userItems.size()), HttpStatus.CREATED);
    }

    @PutMapping("/useritems")
    public ResponseEntity<ResponseUpdateDto> update(@RequestBody RequestUpdateDto requestDto) {
        UserItems userItems = userItemService.update(requestDto);
        return new ResponseEntity<>(new ResponseUpdateDto(userItems.getSize()), HttpStatus.OK);
    }

    @DeleteMapping("/useritems")
    public ResponseEntity<ResponseDeleteDto> delete(@RequestBody RequestDeleteDto requestDto) {
        ResponseDeleteDto responseDto = userItemService.delete(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/useritems")
    public ResponseEntity<List<ResponseFindDto>> find(@RequestBody RequestFindDto requestDto) {
        Locale requestLocale = LocaleContextHolder.getLocale();
        List<ResponseFindDto> userItems = userItemService.find(requestDto.getId(), requestDto.getType(), requestLocale);
        return new ResponseEntity<>(userItems, HttpStatus.OK);
    }

    @GetMapping("/useritems/near-expiry")
    public ResponseEntity<List<ResponseGetNearExpiryDto>> getNearExpiry() {
        UserItems userItems = userItemService.getNearExpiry();
        return new ResponseEntity<>(userItems.toDtos(ResponseGetNearExpiryDto::new), HttpStatus.OK);
    }
}
