package com.elifcelik.stockmanagement.productservice.exception.enums;

public enum FriendlyMessageCodes implements FriendlyMessageCode {
    OK(1000),
    SUCCESS(1002),
    ERROR(1001),
    PRODUCT_SUCCESSFULLY_CREATED(1500),
    PRODUCT_NOT_CREATED_EXCEPTION(1501);
    private final int value;

    FriendlyMessageCodes(int value){
        this.value = value;
    }

    @Override
    public int getFriendlyMessageCode() {
        return value;
    }
}
