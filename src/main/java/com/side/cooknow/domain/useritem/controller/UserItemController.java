package com.side.cooknow.domain.useritem.controller;

import com.side.cooknow.domain.useritem.model.UserItem;
import com.side.cooknow.domain.useritem.model.UserItems;
import com.side.cooknow.domain.useritem.model.dto.request.RequestDeleteDto;
import com.side.cooknow.domain.useritem.model.dto.request.RequestSaveDto;
import com.side.cooknow.domain.useritem.model.dto.request.RequestUpdateDto;
import com.side.cooknow.domain.useritem.model.dto.response.*;
import com.side.cooknow.domain.useritem.service.UserItemService;
import com.side.cooknow.global.model.dto.response.ApiResponse;
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

    @PostMapping("/user/{userId}/items")
    public ResponseEntity<ApiResponse<SaveResponse>> save(@PathVariable("userId") Long userId,
                                                          @RequestBody RequestSaveDto requestDto) {
        List<UserItem> userItems = userItemService.save(userId, requestDto);
        return createResponse("User items saved successfully", new SaveResponse(userId, userItems));
    }

    @PutMapping("/user/{userId}/items")
    public ResponseEntity<ApiResponse<UpdateResponse>> update(@PathVariable("userId") Long userId,
                                                              @RequestBody RequestUpdateDto requestDto) {
        List<UserItem> userItems = userItemService.update(userId, requestDto);
        return createResponse("User items updated successfully", new UpdateResponse(userId, userItems));
    }

    @GetMapping("/user/{userId}/items")
    public ResponseEntity<ApiResponse<FindUserItemResponse>> findItems(@PathVariable("userId") Long userId,
                                                                       @RequestParam("type") String type) {
        Locale requestLocale = LocaleContextHolder.getLocale();
        List<UserItem> userItemList = userItemService.find(userId, type);
        return createResponse("User items found successfully", new FindUserItemResponse(userId, type, userItemList, requestLocale));
    }

    @GetMapping("/user/{userId}/items/near-expiry")
    public ResponseEntity<ApiResponse<FindNearExpiryResponse>> getNearExpiry(@PathVariable("userId") Long userId) {
        Locale requestLocale = LocaleContextHolder.getLocale();
        List<UserItem> userItemList = userItemService.getNearExpiry(userId);
        return createResponse("User items found successfully", new FindNearExpiryResponse(userId, userItemList, requestLocale));
    }

    @DeleteMapping("/user/{userId}/items")
    public ResponseEntity<ApiResponse<DeleteResponse>> delete(@PathVariable("userId") Long userId,
                                                              @RequestBody RequestDeleteDto requestDto) {
        List<Long> deletedIds = userItemService.delete(requestDto);
        return createResponse("User items deleted successfully", new DeleteResponse(deletedIds));
    }

    private <T> ResponseEntity<ApiResponse<T>> createResponse(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.OK.value(), message, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
