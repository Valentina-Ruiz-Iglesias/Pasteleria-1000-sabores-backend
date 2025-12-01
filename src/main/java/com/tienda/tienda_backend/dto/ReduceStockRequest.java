package com.tienda.tienda_backend.dto;

import java.util.List;

public class ReduceStockRequest {

    private List<StockItem> items;

    public static class StockItem {
        private Long productId;
        private Integer quantity;

        public StockItem() {}

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }

    public ReduceStockRequest() {}

    public List<StockItem> getItems() {
        return items;
    }

    public void setItems(List<StockItem> items) {
        this.items = items;
    }
}
