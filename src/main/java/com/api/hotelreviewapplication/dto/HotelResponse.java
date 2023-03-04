package com.api.hotelreviewapplication.dto;

import lombok.Data;

import java.util.List;
@Data
public class HotelResponse {
    private List<HotelDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}