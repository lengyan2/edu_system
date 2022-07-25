package com.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {

    /**
     * response timestamp
     */
    private Long timestamp;

    /**
     * response code , 200 -> ok
     */
    private String  Status;

    private String  message;

    private T data;

    /**
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(){
        return success(null);
    }

    public static  <T> ResponseResult<T> success(T data){
        return ResponseResult.<T>builder().data(data)
                .message(ResponseStatus.SUCCESS.getDescription())
                .Status(ResponseStatus.SUCCESS.getResponseCode())
                .timestamp(System.currentTimeMillis())
                .build();
        }

        public static <T> ResponseResult<T> fail(String message){
        return fail(null,message);
        }

        public static <T> ResponseResult<T> fail(T data,String message){
        return ResponseResult.<T>builder().data(data)
                .message(message)
                .Status(ResponseStatus.FAIL.getResponseCode())
                .timestamp(System.currentTimeMillis())
                .build();
        }
}
