package com.roomrentalmanagementbackend.dto.page.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    List<T> list;
    int pageNumber;
    int pageSize;
    int totalElements;
    int totalPages;
}
