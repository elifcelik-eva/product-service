package com.elifcelik.stockmanagement.productservice.controller;

import com.elifcelik.stockmanagement.productservice.enums.Language;
import com.elifcelik.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import com.elifcelik.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import com.elifcelik.stockmanagement.productservice.repository.entity.Product;
import com.elifcelik.stockmanagement.productservice.request.ProductCreateRequest;
import com.elifcelik.stockmanagement.productservice.response.FriendlyMessage;
import com.elifcelik.stockmanagement.productservice.response.InternalApiResponse;
import com.elifcelik.stockmanagement.productservice.response.ProductResponse;
import com.elifcelik.stockmanagement.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/1.0/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/create")
    public InternalApiResponse<ProductResponse> createProduct(@PathVariable("language")Language language,
                                                              @RequestBody ProductCreateRequest productCreateRequest){
        log.debug("[{}] [createProduct] -> request: {}", this.getClass().getSimpleName(), productCreateRequest);
        Product product = productService.createProduct(language, productCreateRequest);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}] [createProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .message(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/get/{productId}")
    public InternalApiResponse<ProductResponse> getProduct(@PathVariable("language") Language language,
                                                           @PathVariable("productId") Long productId){
        log.debug("[{}] [getProduct] -> productId: {}", this.getClass().getSimpleName(), productId);
        Product product = productService.getProduct(language,productId);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}] [getProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    private static ProductResponse convertProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .productCreatedDate(product.getProductCreatedDate())
                .build();
    }
}
