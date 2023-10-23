package com.example.chatapplication.common;

import com.example.chatapplication.model.response.CommonRes;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static String hashPassword(String plainText){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        return encoder.encode(plainText);
    }

    public static byte[] genQrCode(String rawText) throws WriterException, IOException {
        String hasToken = DatatypeConverter.printBase64Binary(rawText.getBytes());
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(hasToken, BarcodeFormat.QR_CODE,350, 350);
        BufferedImage bufferedImage= MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        return baos.toByteArray();
    }

    public static String decodeBase64(String hash){
        byte[] result = DatatypeConverter.parseBase64Binary(hash);
        return new String(result);
    }

    public static Map<String,String> toQueryMap(String target){

        return new HashMap<>();
    }

    public static <T> CommonRes<T> createSuccessResponse(T data){
        CommonRes<T> commonRes = new CommonRes<>();
        commonRes.setData(data);
        commonRes.setStatusCode(200);
        commonRes.setIsError(false);
        return commonRes;
    }

    public static <T> CommonRes<T> createErrorResponse(Integer statusCode,String messageError){
        CommonRes<T> commonRes = new CommonRes<>();
        commonRes.setData(null);
        commonRes.setStatusCode(statusCode);
        commonRes.setIsError(true);
        Map<String, Object> error = new HashMap<>();
        error.put("error",messageError);
        commonRes.setErrorMap(error);
        return commonRes;
    }

    public static <T> Page<T> toPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        if (start > list.size())
            return new PageImpl<>(new ArrayList<>(), pageable, list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}
