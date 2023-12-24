package com.example.chatapplication.controller;


import com.example.chatapplication.anotation.IsAdmin;
import com.example.chatapplication.common.Utils;
import com.example.chatapplication.config.Config;
import com.example.chatapplication.domain.Packages;
import com.example.chatapplication.domain.User;
import com.example.chatapplication.model.command.CreateOrderCommand;
import com.example.chatapplication.model.response.CommonRes;
import com.example.chatapplication.model.springsecurity.UserSercurity;
import com.example.chatapplication.repository.PackageRepository;
import com.example.chatapplication.repository.UserRepository;
import com.example.chatapplication.service.read.OrderCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderCommandService orderCommandService;
//    @PostMapping()
//    public ResponseEntity<?> createOrder(Authentication authentication, @RequestBody CreateOrderCommand command){
//        UserSercurity userSercurity = (UserSercurity) authentication.getPrincipal();
//        CommonRes<?> commonRes = Utils.createSuccessResponse(orderCommandService.createOrder(userSercurity.getUserId(),command.getPackageId()));
//        return ResponseEntity.status(commonRes.getStatusCode()).body(commonRes);
//    }


    private final PackageRepository packageRepository;
    private final UserRepository userRepository;
    @PostMapping("/req")
    public ResponseEntity<?>  vnPay(HttpServletRequest req,Authentication authentication,@RequestBody CreateOrderCommand command) {
        UserSercurity userSercurity=(UserSercurity) authentication.getPrincipal();
        Packages packages = packageRepository.findById(command.getPackageId()).orElse(null);
        User user = userRepository.findById(userSercurity.getUserId()).orElse(null);

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = "Thanh toán gói phim";
        String orderType = "other";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = Config.getIpAddress(req);
        String vnp_TmnCode = Config.vnp_TmnCode;
        if(packages == null || user ==null)
            throw new RuntimeException("packages not found");
        orderCommandService.createOrder(userSercurity.getUserId(),command.getPackageId(), vnp_TxnRef);
        int amount = packages.getPrice().intValue()*100;
        Map<String,Object> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = req.getParameter("bankcode");
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }else {
            vnp_Params.put("vnp_BankCode", "NCB");
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Billing
//        vnp_Params.put("vnp_Bill_Mobile", req.getParameter("txt_billing_mobile"));
//        vnp_Params.put("vnp_Bill_Email", req.getParameter("txt_billing_email"));
        String fullName = user.getFullname();
        if (!fullName.isEmpty()) {
            String [] splitNames = fullName.split("\\s+");
            String firstName = splitNames[0];
            String lastName = splitNames[splitNames.length-1];
            vnp_Params.put("vnp_Bill_FirstName", firstName);
            vnp_Params.put("vnp_Bill_LastName", lastName);
        }
//        vnp_Params.put("vnp_Bill_Address", req.getParameter("txt_inv_addr1"));
//        vnp_Params.put("vnp_Bill_City", req.getParameter("txt_bill_city"));
//        vnp_Params.put("vnp_Bill_Country", req.getParameter("txt_bill_country"));
//        if (req.getParameter("txt_bill_state") != null && !req.getParameter("txt_bill_state").isEmpty()) {
//            vnp_Params.put("vnp_Bill_State", req.getParameter("txt_bill_state"));
//        }
        // Invoice
//        vnp_Params.put("vnp_Inv_Phone", req.getParameter("txt_inv_mobile"));
//        vnp_Params.put("vnp_Inv_Email", req.getParameter("txt_inv_email"));
//        vnp_Params.put("vnp_Inv_Customer", req.getParameter("txt_inv_customer"));
//        vnp_Params.put("vnp_Inv_Address", req.getParameter("txt_inv_addr1"));
//        vnp_Params.put("vnp_Inv_Company", req.getParameter("txt_inv_company"));
//        vnp_Params.put("vnp_Inv_Taxcode", req.getParameter("txt_inv_taxcode"));
//        vnp_Params.put("vnp_Inv_Type", req.getParameter("cbo_inv_type"));
        //vnp_Params.put("transactionOrder", transactionId);
        //Build data to hash and querystring
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        Map<String,Object > res = new HashMap<>();
        res.put("code", "00");
        res.put("message", "success");
        res.put("data", paymentUrl);
        System.out.println(res);
        return ResponseEntity.ok(Utils.createSuccessResponse(res));
    }

//    @GetMapping(value = "update-order")
//    public ResponseEntity<?> ok
}
