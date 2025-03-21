package com.fn.ai.user.model.type;

public enum DeliveryType {
    HUB_DELIVERY_MANAGER(DeliveryLabel.HUB_DELIVERY_MANAGER),
    COMPANY_DELIVERY_MANAGER(DeliveryLabel.COMPANY_DELIVERY_MANAGER);

    private final String deliveryType;

    DeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public static class DeliveryLabel {
        public static final String HUB_DELIVERY_MANAGER = "허브 배송 담당자";
        public static final String COMPANY_DELIVERY_MANAGER = "업체 배송 담당자";
    }
}
