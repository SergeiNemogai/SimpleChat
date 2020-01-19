package com.andersenlab.util;

import lombok.Builder;
import lombok.Data;

/**
 * @author Sergei Nemogai
 * created on 17.01.2020
 */

@Data
@Builder
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
