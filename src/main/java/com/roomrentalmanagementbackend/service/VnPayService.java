package com.roomrentalmanagementbackend.service;

import com.roomrentalmanagementbackend.configuration.VNPayConfig;
import com.roomrentalmanagementbackend.dto.ApiResponse;
import com.roomrentalmanagementbackend.dto.payment.request.PaymentRequest;
import com.roomrentalmanagementbackend.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VnPayService {
    VNPayConfig vnPayConfig;
    public ApiResponse createVnPayPayment(PaymentRequest request, HttpServletRequest req) {
        long amount = (long) (request.getAmount() * 100L);

        Map<String, String> vnpParamsMap = new HashMap<>(vnPayConfig.getVNPayConfig(request.getOrder()+""));

        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));


        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(req));

        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);

        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);

        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl + "&vnp_SecureHash=" + vnpSecureHash;

        return ApiResponse.success(paymentUrl);
    }
}
