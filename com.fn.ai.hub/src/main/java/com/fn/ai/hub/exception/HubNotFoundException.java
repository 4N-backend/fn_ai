package com.fn.ai.hub.exception;

import com.fn.ai.common.exception.BaseException;
import com.fn.ai.common.exception.code.CommonResponseCode;

public class HubNotFoundException extends BaseException {

    public HubNotFoundException() {
        super(CommonResponseCode.DATA_NOT_FOUND.getCode(),"허브를 찾을 수 없습니다.");
    }
}
